import os
import librosa #https://librosa.org/    #sudo apt-get install -y ffmpeg (open mp3 files)
import librosa.display
import librosa.beat
import warnings
import numpy as np
import csv
import matplotlib.pyplot as plt
import scipy.stats as st
from scipy.stats import pearsonr
import re
    
def statistics(feature):
        feature = feature.flatten()
        if feature.size == 0:
            return np.full(7, np.nan)
        mean = np.mean(feature)
        desv = np.std(feature)
        skew = st.skew(feature)
        kurt = st.kurtosis(feature)
        median = np.median(feature)
        max_m = feature.max()
        min_m = feature.min()

        return np.array([mean, desv, skew, kurt, median, max_m, min_m])
    
def extrair_estatisticas(ficheiro_audio, sr=22050, mono=True):
        y, sr= librosa.load(ficheiro_audio, sr=sr, mono = mono)
       
        mfcc = librosa.feature.mfcc(y = y, sr=sr, n_mfcc=13)
        mfcc_list = [statistics(mfcc[i,:]) for i in range(mfcc.shape[0])]
        stat_mfcc = np.concatenate(mfcc_list)

        spec_centroid = librosa.feature.spectral_centroid(y = y, sr=sr)
        stat_spec_centroid = statistics(spec_centroid)

        spec_rolloff = librosa.feature.spectral_rolloff(y = y, sr=sr)
        stat_spec_rolloff = statistics(spec_rolloff)

        spec_flatness = librosa.feature.spectral_flatness(y = y)
        stat_spec_flatness = statistics(spec_flatness)

        spec_bw = librosa.feature.spectral_bandwidth(y = y, sr=sr)
        stat_spec_bw = statistics(spec_bw)

        spec_contrast = librosa.feature.spectral_contrast(y=y, sr=sr)
        contrast_stats_list = []
        for i in range(spec_contrast.shape[0]):
             contrast_stats_list.append(statistics(spec_contrast[i, :]))
        
        stat_spec_contrast = np.concatenate(contrast_stats_list)


        #features temporais

        rms =  librosa.feature.rms(y = y)
        stat_rms = statistics(rms)
        
        zcr = librosa.feature.zero_crossing_rate(y = y)
        stat_zcr = statistics(zcr)
        

        f0 = librosa.yin(y,fmin=20,fmax=sr/2,sr=sr)
        f0 = f0[~np.isnan(f0)] if f0 is not None else np.array([0]) 
        if f0.size == 0:
            f0 = np.array([0])
        stat_f0 = statistics(f0)

        time = librosa.beat.tempo(y=y, sr=sr)
        stat_time = np.array([time[0]])
        
        vetor_features = np.concatenate([
        stat_mfcc,
        stat_spec_centroid,
        stat_spec_bw,
        stat_spec_contrast,
        stat_spec_flatness,
        stat_spec_rolloff,
        stat_f0,
        stat_rms,
        stat_zcr,
        stat_time
        ])
        return vetor_features

 # Normalização

def normalizar_features(matriz):
        minimos = np.min(matriz, axis=0)
        maximos = np.max(matriz, axis=0)
        range = maximos - minimos
        range[range == 0] = 1
        matriz_normalizada = (matriz - minimos) / range
        return matriz_normalizada, minimos, maximos
   
def get_difference_positions(file1, file2, decimals=4, tol=1e-4):
        data1 = np.loadtxt(file1, delimiter=",")
        data2 = np.loadtxt(file2, delimiter=",")
        
        features1 = data1[2:, :]
        features2 = data2[2:, :]
        
        differences = features1 - features2
        differences_rounded = np.round(differences, decimals=decimals)
        
        mask = np.abs(differences_rounded) > tol
        
        rows, cols = np.where(mask)
        
        diff_positions = list(zip(rows, cols, differences_rounded[rows, cols]))
        return diff_positions
    
def write_differences_to_file(diff_positions, filename="diferencas2.txt"):
        with open(filename, "w") as f:
            for linha, coluna, diff in diff_positions:
                if(coluna < 168 or coluna > 175):
                    f.write(f"Linha {linha}, Coluna {coluna}: diferença = {diff}\n")
                
        print(f"Diferenças gravadas no arquivo '{filename}'.")

def spectral_centroid(y, sr, n_fft=2048, hop_length=512):
        #calcula a transformada de fourier. Decompoe o sinal em janelas de n_fft
        S = np.abs(librosa.stft(y, n_fft=n_fft, hop_length=hop_length))
        #frequências de cada bin
        freqs = np.fft.rfftfreq(n_fft, 1/sr)
        #multiplica a magnitude de cada bin pela frequência correspondente
        #e divide pela soma das magnitudes de todos os bins
        
        cent = np.sum(S * freqs[:, None], axis=0) / np.sum(S, axis=0) 
              
        return cent

def comparar_spectral_centroid(y, sr, n_fft=2048, hop_length=512,output_file="comparar_spectral_centroid.csv"):
    sc_custom = spectral_centroid(y, sr, n_fft=n_fft, hop_length=hop_length)
    
    sc_librosa = np.array(librosa.feature.spectral_centroid(y=y, sr=sr, n_fft=n_fft, hop_length=hop_length)[0, :])
        

    min_len = min(len(sc_custom), len(sc_librosa))
    sc_custom = sc_custom[:min_len]
    sc_librosa = sc_librosa[:min_len]
    
    r_value, _ = pearsonr(sc_custom, sc_librosa)
    rmse = np.sqrt(np.mean((sc_custom - sc_librosa) ** 2))
    
    cabeçalho = "pearson,rmse\n"
    if not os.path.isfile(output_file):
        with open(output_file, "w") as f:
            f.write(cabeçalho)
    
    with open(output_file, "a") as f:
        f.write(f"{r_value:.6f},{rmse:.6f}\n")
     
def validar_metricas(file_gerado, file_exemplo, tol=1e-6, output_csv="differences.csv"):
    # Carrega, ignorando o cabeçalho
    data1 = np.loadtxt(file_gerado, delimiter=',', skiprows=1)
    data2 = np.loadtxt(file_exemplo, delimiter=',', skiprows=1)

    # Se tiverem números de linhas diferentes, trunca ao mínimo
    n1, n2 = data1.shape[0], data2.shape[0]
    if n1 != n2:
        n = min(n1, n2)
        print(f"Ajustando comparação para {n} linhas (min de {n1} e {n2})")
        data1 = data1[:n]
        data2 = data2[:n]

    # Calcula as diferenças absolutas
    diffs = np.abs(data1 - data2)
    max_diff = np.max(diffs)

    # Prepara o CSV de saída
    with open(output_csv, 'w', newline='') as csvfile:
        writer = csv.writer(csvfile)
        writer.writerow(["linha", "metrica", "teu_valor", "ex_valor", "diff"])

        # Percorre apenas os casos acima da tolerância
        rows, cols = np.where(diffs >= tol)
        for r, c in zip(rows, cols):
            val_g = data1[r, c]
            val_e = data2[r, c]
            diff  = diffs[r, c]
            metric = "pearson" if c == 0 else "rmse"
            writer.writerow([r+1, metric, f"{val_g:.6f}", f"{val_e:.6f}", f"{diff:.2e}"])

    # Mensagem final
    if max_diff < tol:
        print(f"Validação OK! Max diff = {max_diff:.2e} < tol ({tol}).")
    else:
        print(f"Ha diferenças acima da tolerância ({tol}): max diff = {max_diff:.2e}.")
        print(f"Todas as diferenças foram gravadas em '{output_csv}'.")
#3.1
def distancia_euclidiana(x, y):
    #distancia mais curta entre dois vetores
    return np.linalg.norm(x - y)

def distancia_manhattan(x, y):
    #soma das diferenças absolutas entre os elementos dos vetores
    return np.sum(np.abs(x - y))

def distancia_coseno(x, y, eps=1e-10):
    #distancia entre dois vetores, considerando o ângulo entre eles
    #varia entre -1 (mesmo sentido) e 1 (sentidos opostos)
    num = np.dot(x, y)
    den = np.linalg.norm(x) * np.linalg.norm(y)
    if den < eps:
        return 1.0
    return 1 - num / den

def grava_top10(distances, metric_name, arquivos, top_k=10):
    idx_sorted = np.argsort(distances)
    idx_sorted = idx_sorted[idx_sorted != i_query]
    top_idx = idx_sorted[:top_k]

    # grava num CSV: rank, caminho_ficheiro, distância
    filename = f"3.3_top10_{metric_name}.csv"
    with open(filename, "w", newline="") as f:
        writer = csv.writer(f)
        writer.writerow(["rank", "arquivo", metric_name])
        for rank, idx in enumerate(top_idx, start=1):
            writer.writerow([rank, arquivos[idx], f"{distances[idx]:.6f}"])
    print(f"Top-{top_k} para {metric_name} gravado em '{filename}'")

def validar_ranking(metric_name, tol=1e-4):
    gerado = np.genfromtxt(f"3.3_top10_{metric_name}.csv", delimiter=",", 
                           dtype=None, encoding="utf-8", skip_header=1)
    exemplo = np.genfromtxt(f"./3.3_top10_{metric_name}.csv",
                            delimiter=",", dtype=None, encoding="utf-8", skip_header=1)

    n_gen, n_ex = gerado.shape[0], exemplo.shape[0]
    n = min(n_gen, n_ex)
    if n_gen != n_ex:
        print(f"Ajustando para {n} linhas (gerado={n_gen}, exemplo={n_ex})")
    diffs = []
    for i in range(n):
        arq_g, dist_g = gerado[i][1], float(gerado[i][2])
        arq_e, dist_e = exemplo[i][1], float(exemplo[i][2])
        if arq_g != arq_e or abs(dist_g - dist_e) > tol:
            diffs.append((i+1, arq_g, dist_g, arq_e, dist_e))
    if not diffs:
        print(f"Validação OK para {metric_name} (tolerância={tol})")
    else:
        print(f"{len(diffs)} diferenças em {metric_name}:")
        for linha, ag, dg, ae, de in diffs:
            print(f"  Linha {linha}: gerado=({ag},{dg:.6f}) vs exemplo=({ae},{de:.6f})")

def calcular_similaridade_metadados():
    file = "panda_dataset_taffc_metadata.csv"
    query_file = "query_metadata.csv"

    base = np.loadtxt(file, delimiter=',', dtype=str, skiprows=1, usecols=(1, 11, 9))
    query = np.loadtxt(query_file, delimiter=',', dtype=str, skiprows=1, usecols=(1, 11, 9))

    query_artist = query[0]
    query_genres = set(query[1][1:-1].split('; '))
    query_moods = set(query[2][1:-1].split('; '))
    
    similarity_matrix = np.zeros(len(base))

    for i, (artist, genres_str, moods_str) in enumerate(base):
        score = 0

        if artist == query_artist:
            score += 1

        base_genres = set(genres_str[1:-1].split('; '))
        genres_common = len(query_genres.intersection(base_genres))
        score += genres_common

        base_moods = set(moods_str[1:-1].split('; '))
        moods_common = len(query_moods.intersection(base_moods))
        score += moods_common
        
        similarity_matrix[i] = score

    np.savetxt("metadata_similarity.csv", similarity_matrix, delimiter=",", fmt="%.6f")
    
    return similarity_matrix

def obter_ranking_metadados(similarity_matrix, music_dir, top_n=11):
    ranked_indices = np.argsort(-similarity_matrix)[:top_n]
    ranked_scores = similarity_matrix[ranked_indices]
    
    music_files = [f for f in os.listdir(music_dir) if f.endswith('.mp3')]
    with open("metadata_ranking.txt", "w") as f:
        f.write("Top 10 músicas baseadas em metadados:\n")
        for i, (idx, score) in enumerate(zip(ranked_indices, ranked_scores)):
            if idx < len(music_files):
                file_name = music_files[idx]
                f.write(f"{i+1}. Index: {idx}, File: {file_name}, Score: {score:.6f}\n")
    
    return ranked_indices

def carregar_rankings_do_arquivo(ranking_file):
    rankings = {}
    current_metric = None
    indices = []
    
    with open(ranking_file, 'r') as f:
        for line in f:
            line = line.strip()
            
            if line.startswith("Top 10 for"):

                if current_metric and indices:
                    rankings[current_metric] = indices
                
                current_metric = line.split("Top 10 for ")[1].strip(":")
                indices = []
            
            elif "Index:" in line and "File:" in line:
                match = re.search(r'Index: (\d+)', line)
                if match:
                    idx = int(match.group(1))
                    indices.append(idx)
    if current_metric and indices:
        rankings[current_metric] = indices
    
    return rankings

def calcular_precision(content_ranking, metadata_ranking):
    content_set = set(content_ranking)
    metadata_set = set(metadata_ranking)
    if not content_set:
        return 0.0
    relevant_retrieved = len(content_set.intersection(metadata_set))
    return (relevant_retrieved / len(content_set)) * 100


def avaliar_rankings(metadata_ranking, content_rankings):
    precision_results = {}
    
    for metric, ranking in content_rankings.items():
        precision = calcular_precision(ranking, metadata_ranking)
        precision_results[metric] = precision

    with open("precision_results.txt", "w") as f:
        f.write("Resultados de Precision:\n")
        for metric, precision in precision_results.items():
            f.write(f"{metric}: {precision:.1f}\n")
    
    return precision_results

def validar_valores(file_gerado,file_exemplo,tol: float = 1e-6,output_csv = "differences.csv"):
    data1 = np.loadtxt(file_gerado, delimiter=',')
    data2 = np.loadtxt(file_exemplo, delimiter=',')

    n = min(data1.shape[0], data2.shape[0])
    if data1.shape[0] != data2.shape[0]:
        print(f"Ajustando comparação a {n} linhas (min de {data1.shape[0]} e {data2.shape[0]})")
    data1 = data1[:n]
    data2 = data2[:n]

    diffs = np.abs(data1 - data2)
    max_diff = diffs.max()

    with open(output_csv, 'w', newline='') as f:
        writer = csv.writer(f)
        writer.writerow(["linha", "teu_valor", "ex_valor", "diff"])
        for i, diff in enumerate(diffs, start=1):
            if diff >= tol:
                writer.writerow([
                    i,
                    f"{data1[i-1]:.6f}",
                    f"{data2[i-1]:.6f}",
                    f"{diff:.2e}"
                ])

    if max_diff < tol:
        print(f"Validação OK! max_diff = {max_diff:.2e} < tol ({tol}).")
    else:
        print(f"Diferenças acima da tolerância! max_diff = {max_diff:.2e}.")
        print(f"Detalhes em '{output_csv}'.")

    return max_diff, diffs

if __name__ == "__main__":
    plt.close('all')
    np.set_printoptions(threshold=np.inf)
    
    #--- Load file
    fName = "./Queries\\MT0000414517.mp3"    
    y, sr = librosa.load(fName, sr=22050, mono=True)
    sr = 22050
    n_fft     = 2048
    hop_length= 512
    mono = True
    warnings.filterwarnings("ignore")
    y, fs = librosa.load(fName, sr=sr, mono = mono)
    print(y.shape)
    print(fs)
    

    #--- Play Sound
    #sd.play(y, sr, blocking=False)
    
    #--- Plot sound waveform
    plt.figure()
    librosa.display.waveshow(y)
    
    #--- Plot spectrogram
    Y = np.abs(librosa.stft(y))
    Ydb = librosa.amplitude_to_db(Y, ref=np.max)
    fig, ax = plt.subplots()
    img = librosa.display.specshow(Ydb, y_axis='linear', x_axis='time', ax=ax)
    ax.set_title('Power spectrogram')
    fig.colorbar(img, ax=ax, format="%+2.0f dB")
        
    #--- Extract features    
    sc = librosa.feature.spectral_centroid(y = y)  #default parameters: sr = 22050 Hz, mono, window length = frame length = 92.88 ms e hop length = 23.22 ms 
    sc = sc[0, :]
    print(sc.shape)
    times = librosa.times_like(sc)
    plt.figure(), plt.plot(times, sc)
    plt.xlabel('Time (s)')
    plt.title('Spectral Centroid')

    pasta_musica1 = "./Queries"
    pasta_musicas = "./musicas"

    arquivos = [os.path.join(pasta_musicas, f) for f in os.listdir(pasta_musicas) if f.endswith('.mp3')]

    arquivos.sort()
    lista_features = []

    matriz = np.loadtxt("2.1.2.csv", delimiter=",")
    matriz_norm, minimos, maximos = normalizar_features(matriz)
    matriz_norm = np.loadtxt("./validação de resultados_TP2\\notNormFM_All.csv", delimiter=",")
 
    for arquivo in arquivos:
        try:        
            vetor = extrair_estatisticas(arquivo)
            lista_features.append(vetor)
            
        except Exception as e:
            print(f"Erro ao processar {arquivo}: {e}")
    
    matriz_features = np.array(lista_features)
    print(matriz_features.shape)
    np.savetxt("2.1.2.csv", matriz_features, delimiter=",", fmt="%lf")
    ("Arquivo com features não normalizadas salvo como '2.1.2.csv'.")

    
    # 2.2.2 – calcula e grava
    sc_manual = spectral_centroid(y, sr, n_fft, hop_length)
    sc_librosa = librosa.feature.spectral_centroid(y=y, sr=sr, n_fft=n_fft, hop_length=hop_length)[0]
    r_value, p_value = pearsonr(sc_manual, sc_librosa)
    rmse = np.sqrt(np.mean((sc_manual - sc_librosa)**2))
    print(f"2.2.2 — Pearson: {r_value:.6f}, RMSE: {rmse:.6f}")
    with open("2.2.2_metrics.csv", "w") as f:
        f.write("pearson,rmse\n")
        f.write(f"{r_value:.6f},{rmse:.6f}\n")
  
    #compara com a função do librosa
    arquivos = [os.path.join(pasta_musicas, f)
                for f in os.listdir(pasta_musicas) if f.endswith('.mp3')]
    arquivos.sort()
    output_csv = "comparar_spectral_centroid.csv"

    if os.path.isfile(output_csv):
        os.remove(output_csv)
    with open(output_csv, "w") as f:
        f.write("pearson,rmse\n")

    for arquivo in arquivos:
        y, sr = librosa.load(arquivo, sr=sr, mono=mono)
        comparar_spectral_centroid(y,sr,n_fft=2048,hop_length=512,output_file=output_csv)

    #2.2.4
    #valida a função de comparação
    gerado = "comparar_spectral_centroid.csv"
    exemplo = os.path.join("validação de resultados_TP2", "metricsSpectralCentroid.csv")
    validar_metricas(gerado, exemplo, tol=1e-6, output_csv="2.2.4_differences.csv")

    #3.2
    v_query = extrair_estatisticas(fName)

    i_query = 0  # índice da música na lista 'arquivos'
    v_query_norm = (v_query - minimos) / (maximos - minimos)

    dist_euc  = [distancia_euclidiana(v_query_norm, v) for v in matriz_norm]    
    dist_man  = [distancia_manhattan(v_query_norm, v)  for v in matriz_norm]
    dist_cos  = [distancia_coseno(v_query_norm, v)     for v in matriz_norm]

    #grava distâncias em CSV
    np.savetxt("3.2_dist_euclidiana.csv", dist_euc, delimiter=",", fmt="%.6f")
    np.savetxt("3.2_dist_manhattan.csv", dist_man, delimiter=",", fmt="%.6f")
    np.savetxt("3.2_dist_coseno.csv", dist_cos, delimiter=",", fmt="%.6f")

    gerado = "3.2_dist_euclidiana.csv"
    exemplo = os.path.join("validação de resultados_TP2", "de.csv")
    validar_valores(gerado, exemplo, tol=1e-6, output_csv="euclidiana_diferenca.csv")

    gerado = "3.2_dist_coseno.csv"
    exemplo = os.path.join("validação de resultados_TP2", "dc.csv")
    validar_valores(gerado, exemplo, tol=1e-6, output_csv="cosseno_diferenca.csv")

    gerado = "3.2_dist_manhattan.csv"
    exemplo = os.path.join("validação de resultados_TP2", "dm.csv")
    validar_valores(gerado, exemplo, tol=1e-6, output_csv="manhattan_diferenca.csv")

    #3.3
    grava_top10(dist_euc,  "euclidiana", arquivos)
    grava_top10(dist_man,  "manhattan",  arquivos)
    grava_top10(dist_cos,  "coseno",     arquivos)


    #3.4
    validar_ranking("euclidiana")
    validar_ranking("manhattan")
    validar_ranking("coseno")

    sim_matrix = calcular_similaridade_metadados()
    meta_indices = obter_ranking_metadados(sim_matrix, pasta_musicas, top_n=10)

    content_rankings = {}
    for metric in ("euclidiana", "manhattan", "coseno"):
        content_rankings[metric] = []
        with open(f"3.3_top10_{metric}.csv", newline="", encoding="utf-8") as csvfile:
            reader = csv.DictReader(csvfile)
            for row in reader:
                # row["arquivo"] é o caminho completo que gravaste em grava_top10
                # procuramos o índice desse ficheiro na lista 'arquivos'
                idx = arquivos.index(row["arquivo"])
                content_rankings[metric].append(idx)

    precision = avaliar_rankings(meta_indices, content_rankings)

    gerado = "metadata_similarity.csv"
    exemplo = os.path.join("validação de resultados_TP2", "mdSim.csv")
    validar_valores(gerado, exemplo, tol=100000, output_csv="metadata_similitary_differences.csv")


    diff_positions = get_difference_positions("2.1.4.csv", "./validação de resultados_TP2/FM_All.csv")
    diff_positions2 = get_difference_positions("./2.1.2.csv", "./validação de resultados_TP2\\notNormFM_All.csv")
    write_differences_to_file(diff_positions2)