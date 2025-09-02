import matplotlib.pyplot as plt
import numpy as np
import huffmancodec as huffc
#ponto 1
import pandas as pd
data = pd.read_excel('CarDataset.xlsx')
tableValues = data.to_numpy()
varNames = data.columns.values.tolist()
#ponto 2
fig, axs = plt.subplots(ncols=2, nrows=3,layout="constrained")
i = 0
h = 0
for col in range (3):
    for row in range(2):
        axs[col,row].set_ylabel('MPG')
        axs[col,row].set_xlabel(varNames[i])
        axs[col,row].set_title('MPG vs. '+ varNames[i])
        axs[col,row].scatter(tableValues[:,h],tableValues[:,6], c = 'purple', marker = '.')
        h+=1
        i+=1
#ponto 3
tableValues = tableValues.astype(np.uint16)
alphabet = list(range(65536))
#ponto 4
varFreq = [[],[],[],[],[],[],[]]
varList = [[],[],[],[],[],[],[]]
tableValuesT = tableValues.T.copy()
for i in range(len(tableValuesT)):
    tableValuesT[i] = sorted(tableValuesT[i])
for i in range(len(tableValuesT)):
        for k in range(len(tableValuesT[i])):
            count = 0
            if (tableValuesT[i][k] not in varList[i]):
                for j in range(k,len(tableValuesT[i])):
                    if (tableValuesT[i][k] == tableValuesT[i][j]):
                        count += 1
                varList[i].append(tableValuesT[i][k])
                varFreq[i].append(count)
print(varList)
print(varFreq)
#ponto 5
def graph():
    plt.figure(1)
    plt.subplots_adjust(hspace=0.1)
    for i in range(len(tableValuesT)):
        sortedArray = sorted(varList[i])
        for j in range(len(sortedArray)):
            sortedArray[j] = str(sortedArray[j])
        plt.figure(figsize=(25,10))
        plt.bar(sortedArray,varFreq[i],color = "red")
        plt.xticks(rotation = 90)
        plt.xlabel(varNames[i])
        plt.ylabel("Cont")
        plt.show()
graph()
#ponto 6
alphabetAux = []
def binning(column, binSize):
    for i in range((max(varList[column])-min(varList[column]))//binSize):
        alphabetAux.append(min(varList[column])+binSize*i)
    for a in range(len(alphabetAux)-1):
        tableValuesAux = []
        for j in tableValuesT[column]:
            if j >= alphabetAux[a] and j < alphabetAux[a+1]:
                tableValuesAux.append(j)
        if len(tableValuesAux) != 0:
            count = 0
            num = tableValuesAux[0]
            for l in tableValuesAux:
                mostFreq = tableValuesAux.count(l)  
                if(mostFreq>count):
                    count = mostFreq
                    num = l
            for k in range(len(tableValuesT[column])):
                if tableValuesT[column][k] in tableValuesAux:
                    tableValuesT[column][k] = num
    plt.figure(figsize = (15,5))
    sortedArray = sorted(tableValuesT[column])
    freqArray = []
    tableValuesTemp = []
    mostFreq = sortedArray[0]
    count = 0
    for i in sortedArray:
        if (i == mostFreq):
            count +=1
        else:
            freqArray.append(count)
            count = 1
            tableValuesTemp.append(mostFreq)
            mostFreq = i
    for j in range(len(tableValuesTemp)):
       tableValuesTemp[j] = str(tableValuesTemp[j])
    print(freqArray)
    plt.bar(tableValuesTemp, freqArray, color = "red")
    plt.xticks(rotation = 90)
    plt.xlabel(varNames[column])
    plt.ylabel("Cont")
    plt.show()
binning(2,5)
binning(5,40)
binning(3,5)
#ponto 7
varEntropy = [[0],[0],[0],[0],[0],[0],[0]]
tableValuesTaux = [[0],[0],[0],[0],[0],[0],[0]]
varFreqAux = [[],[],[],[],[],[],[]]
varListAux = [[],[],[],[],[],[],[]]
for i in range(len(tableValuesT)):
    tableValuesTaux[i] = sorted(tableValuesT[i])
for i in range(len(tableValuesTaux)):
        for k in range(len(tableValuesTaux[i])):
            count = 0
            if (tableValuesTaux[i][k] not in varListAux[i]):
                for j in range(k,len(tableValuesTaux[i])):
                    if (tableValuesTaux[i][k] == tableValuesTaux[i][j]):
                        count += 1
                varListAux[i].append(tableValuesTaux[i][k])
                varFreqAux[i].append(count)

def entropy():
    for i in range(len(varFreqAux)):
        for j in range(len(varFreqAux[i])):
            varEntropy[i] += (-(varFreqAux[i][j]/407)*np.log2(varFreqAux[i][j]/407))
            
def entropyTotal():
    totalEntropy = 0
    tableValuesAux = np.array(tableValues)
    tableValuesFlat = tableValuesAux.flatten()
    uniqueValues, valueCounts = np.unique(tableValuesFlat, return_counts=True)
    probabilities = valueCounts / len(tableValuesFlat)
    for i in range(len(probabilities)):
        totalEntropy += (-(probabilities[i])*np.log2(probabilities[i]))
    return totalEntropy
entropy()
print(varEntropy)
totalEntropy=entropyTotal()
print(totalEntropy)
#ponto 8
def valorMedioBitsH(data,coluna):
    codec = huffc.HuffmanCodec.from_data(data[coluna])
    symbols, lengths = codec.get_code_len()
    symbols = np.array(symbols)
    lengths = np.array(lengths, dtype=float)
    unicos, contagens = np.unique(coluna, return_counts=True)
    numero_ocorrencias_total = np.sum(contagens)
    prob = contagens / numero_ocorrencias_total
    nr_medio_bits = np.sum(prob * lengths)
    variance = np.sum(prob * (lengths - nr_medio_bits)**2)
    
    print("Symbols",symbols,"Lenght",lengths,"Variance",variance, "Número médio de bits por símbolo",nr_medio_bits)
    return  variance
valorMedioBitsH(varList, 0)
valorMedioBitsH(varList, 1)
valorMedioBitsH(varList, 2)
valorMedioBitsH(varList, 3)
valorMedioBitsH(varList, 4)
valorMedioBitsH(varList, 5)
valorMedioBitsH(varList, 6)

#ponto 9
def correlacao(data):
    m = np.corrcoef(data, rowvar=False)
    variaveis = data.columns.tolist()
    resultados = {}
    for v, c in zip(variaveis, m[0]):
        if v!='MPG':
            resultados[v] = c

    return resultados
print(correlacao(data))
#ponto 10
def informaçao_mutua(m,indice):
    total = m.shape[0]
    pares = np.column_stack((m[:,-1], m[:,indice]))
    mpg, contMpg = np.unique(m[:,-1], return_counts = True)
    var, contPar = np.unique(m[:,indice], return_counts = True)
    im = 0
    for i in range(total):
        if np.where((pares == pares[i]).all(axis = 1))[0][0] < i:
            continue 
        valor_mpg = pares[i][0]
        valor_var = pares[i][1]
        indiceMpg = np.where(mpg == valor_mpg)[0][0] 
        indiceVar = np.where(var == valor_var)[0][0]
        prob_valor_mpg = contMpg[indiceMpg] / total
        prob_valor_var = contPar[indiceVar] / total
        prob_conj = sum(np.all(pares == pares[i], axis = 1))/total
        im +=prob_conj * np.log2( prob_conj/(prob_valor_mpg * prob_valor_var))
    
    return im
print(informaçao_mutua(tableValues,0))
print(informaçao_mutua(tableValues,1))
print(informaçao_mutua(tableValues,2))
print(informaçao_mutua(tableValues,3))
print(informaçao_mutua(tableValues,4))
print(informaçao_mutua(tableValues,5))
#ponto 11
def MPG():
    data['MPG_est'] = -5.241 + (-0.146 * data['Acceleration']) + (-0.4909 * data['Cylinders']) + (0.0026 * data['Displacement']) + (-0.0045 * data['Horsepower']) + (0.6725 * data['ModelYear'])+(-0.0059*data['Weight']) 
    print(data["MPG_est"]) 
MPG()
def MPG_no_acel():
    data['MPG_est_no_acel'] = -5.241 + (-0.4909 * data['Cylinders']) + (0.0026 * data['Displacement']) + (-0.0045 * data['Horsepower']) + (0.6725 * data['ModelYear'])+(-0.0059*data['Weight']) 
    print(data["MPG_est_no_acel"]) 
MPG_no_acel()
def MPG_no_year():
    data['MPG_est_no_year'] = -5.241 + (-0.146 * data['Acceleration']) + (-0.4909 * data['Cylinders']) + (0.0026 * data['Displacement']) + (-0.0045 * data['Horsepower']) +(-0.0059*data['Weight']) 
    print(data["MPG_est_no_year"]) 
MPG_no_year()
print(tableValuesT[6])