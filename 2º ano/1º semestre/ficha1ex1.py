import sounddevice as sd
from scipy.io import wavfile
import matplotlib.pyplot as plt
import numpy as np
[fs, data] = wavfile.read("guitar.wav")
sd.play(data, fs)
status = sd.wait()


def apresentarInfo(ficheiro, fs, nrBitsQuant):
    print("Nome:\n", ficheiro)
    print("Taxa de amostragem: \n", fs/1000)
    print("n de bits: \n", nrBitsQuant)


apresentarInfo("guitar.wav", fs, data.itemsize)


def visualizacaoGrafica(data, fs):
    Ts = 1/fs
    B = np.random.rand
    print(B)
    Num_Amostras = data.shape[0]
    print(data)
    A = np.arange(0, Num_Amostras, 1)
    plt.figure(1)
    plt.subplot(211)
    plt.plot(A, data)
    plt.plot(A, B)
    plt.xlabel('Tempo [s]')
    plt.ylabel('Amplitude [-1:1]')
    plt.title('Canal Esquerdo')
    plt.subplots_adjust(hspace=0.1)
    plt.show()


visualizacaoGrafica(data, fs)
