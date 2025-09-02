import os
import librosa
import librosa.display
import librosa.beat
import sounddevice as sd
import warnings
import numpy as np
import matplotlib.pyplot as plt
import scipy.stats as st
import scipy.fft
import csv
from scipy.stats import pearsonr
from scipy.spatial.distance import euclidean, cityblock, cosine

if __name__ == "__main__":
    plt.close('all')
    np.set_printoptions(threshold=np.inf)

    fName = "./Queries/MT0000414517.mp3"
    sr = 22050
    mono = True
    warnings.filterwarnings("ignore")
    y, fs = librosa.load(fName, sr=sr, mono=mono)
    print(y.shape)
    print(fs)

    plt.figure()
    librosa.display.waveshow(y)

    Y = np.abs(librosa.stft(y))
    Ydb = librosa.amplitude_to_db(Y, ref=np.max)
    fig, ax = plt.subplots()
    img = librosa.display.specshow(Ydb, y_axis='linear', x_axis='time', ax=ax)
    ax.set_title('Power spectrogram')
    fig.colorbar(img, ax=ax, format="%+2.0f dB")

    sc = librosa.feature.spectral_centroid(y=y)
    sc = sc[0, :]
    print(sc.shape)
    times = librosa.times_like(sc)
    plt.figure(), plt.plot(times, sc)
    plt.xlabel('Time (s)')
    plt.title('Spectral Centroid')

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
        y, sr = librosa.load(ficheiro_audio, sr=sr, mono=mono)

        mfcc = librosa.feature.mfcc(y=y, sr=sr, n_mfcc=13)
        mfcc_list = [statistics(mfcc[i, :]) for i in range(mfcc.shape[0])]
        stat_mfcc = np.concatenate(mfcc_list)

        spec_centroid = librosa.feature.spectral_centroid(y=y, sr=sr)
        stat_spec_centroid = statistics(spec_centroid)

        spec_rolloff = librosa.feature.spectral_rolloff(y=y, sr=sr)
        stat_spec_rolloff = statistics(spec_rolloff)

        spec_flatness = librosa.feature.spectral_flatness(y=y)
        stat_spec_flatness = statistics(spec_flatness)

        spec_bw = librosa.feature.spectral_bandwidth(y=y, sr=sr)
        stat_spec_bw = statistics(spec_bw)

        spec_contrast = librosa.feature.spectral_contrast(y=y, sr=sr)
        contrast_stats_list = []
        for i in range(spec_contrast.shape[0]):
            contrast_stats_list.append(statistics(spec_contrast[i, :]))
        stat_spec_contrast = np.concatenate(contrast_stats_list)

        rms = librosa.feature.rms(y=y)
        stat_rms = statistics(rms)

        zcr = librosa.feature.zero_crossing_rate(y=y)
        stat_zcr = statistics(zcr)

        f0 = librosa.yin(y, fmin=20, fmax=sr/2, sr=sr)
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

    pasta_musicas = "./musicas"
    arquivos = [os.path.join(pasta_musicas, f) for f in os.listdir(pasta_musicas) if f.endswith('.mp3')]
    arquivos.sort()

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
                if coluna < 168 or coluna > 175:
                    f.write(f"Linha {linha}, Coluna {coluna}: diferença = {diff}\n")
        print(f"Diferenças gravadas no arquivo '{filename}'.")

    def spectral_centroid_manual(y, sr=22050, n_fft=2048, hop_length=512):
        frames = librosa.util.frame(y, frame_length=n_fft, hop_length=hop_length)
        frames = frames * np.hanning(n_fft)[:, None]
        fft_frames = scipy.fft.rfft(frames, axis=0)
        mag_frames = np.abs(fft_frames)
        freqs = np.linspace(0, sr / 2, mag_frames.shape[0])[:, None]
        numerator = np.sum(freqs * mag_frames, axis=0)
        denominator = np.sum(mag_frames, axis=0)
        denominator[denominator == 0] = 1e-6
        centroid = numerator / denominator
        return centroid

    def comparar_sc(y, sr=22050):
        sc_manual = spectral_centroid_manual(y, sr=sr)
        sc_librosa = librosa.feature.spectral_centroid(y=y, sr=sr, n_fft=2048, hop_length=512)[0]

        if sc_manual.shape[0] > sc_librosa.shape[0]:
            sc_manual = sc_manual[2:2+sc_librosa.shape[0]]
        else:
            sc_librosa = sc_librosa[0:sc_manual.shape[0]]

        pearson_corr, _ = pearsonr(sc_manual, sc_librosa)
        rmse = np.sqrt(np.mean((sc_manual - sc_librosa)**2))
        rmse = np.round(rmse, 6)

        return pearson_corr, rmse

    def processar_musicas(pasta_musicas, output_csv="2.2.csv"):
        arquivos = [os.path.join(pasta_musicas, f) for f in os.listdir(pasta_musicas) if f.endswith('.mp3')]
        arquivos.sort()
        resultados = []
        for arquivo in arquivos:
            try:
                y, _ = librosa.load(arquivo, sr=22050, mono=True)
                pearson_corr, rmse = comparar_sc(y)
                resultados.append((pearson_corr, rmse))
            except Exception as e:
                print(f"Erro ao processar {arquivo}: {e}")
        with open(output_csv, mode='w', newline='') as file:
            writer = csv.writer(file)
            for linha in resultados:
                writer.writerow(linha)
        print(f"Resultados guardados em '{output_csv}'")

    processar_musicas(pasta_musicas)

    diff_positions2 = get_difference_positions("2.1.2.csv", "./validação de resultados_TP2/notNormFM_All.csv")
    write_differences_to_file(diff_positions2)


    def carregar_features_normalizadas(ficheiro):
        data = np.loadtxt(ficheiro, delimiter=",")
        return data[2:, :], data[0, :], data[1, :]

    def extrair_query_features(query_path, minimos, maximos):
        vetor_features = extrair_estatisticas(query_path)
        vetor_features = (vetor_features - minimos) / (maximos - minimos)
        vetor_features[(maximos - minimos) == 0] = 0
        return vetor_features

    def calcular_similaridade(features, query_feature, distancia):
        sim = []
        for feat in features:
            if distancia == 'euclidiana':
                d = euclidean(query_feature, feat)
            elif distancia == 'manhattan':
                d = cityblock(query_feature, feat)
            elif distancia == 'cosseno':
                d = cosine(query_feature, feat)
            sim.append(d)
        return np.array(sim)

    def gerar_ranking(similaridades):
        return np.argsort(similaridades)[:10]

    def salvar_matriz_similaridade(similaridades, filename):
        np.savetxt(filename, similaridades, delimiter=",", fmt="%lf")

    def salvar_ranking(ranking, filename):
        with open(filename, mode='w', newline='') as f:
            writer = csv.writer(f)
            for idx in ranking:
                writer.writerow([idx])

    # --- Executar parte 3 ---

    features, minimos, maximos = carregar_features_normalizadas("2.1.4.csv")
    query_feature = extrair_query_features("./Queries/MT0000414517.mp3", minimos, maximos)

    distancias = ['euclidiana', 'manhattan', 'cosseno']

    for dist in distancias:
        similaridades = calcular_similaridade(features, query_feature, dist)
        salvar_matriz_similaridade(similaridades, f"sim_{dist}.csv")
        ranking = gerar_ranking(similaridades)
        salvar_ranking(ranking, f"ranking_{dist}.csv")
