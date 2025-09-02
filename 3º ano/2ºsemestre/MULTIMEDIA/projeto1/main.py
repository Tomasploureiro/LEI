import matplotlib.pyplot as plt
import matplotlib.colors as clr
import cv2 as cv2
import numpy as np
from scipy.fftpack import dct, idct

cm_red   = clr.LinearSegmentedColormap.from_list("red",   [(0, 0, 0), (1, 0, 0)], N=256)
cm_green = clr.LinearSegmentedColormap.from_list("green", [(0, 0, 0), (0, 1, 0)], N=256)
cm_blue  = clr.LinearSegmentedColormap.from_list("blue",  [(0, 0, 0), (0, 0, 1)], N=256)
cm_grey  = clr.LinearSegmentedColormap.from_list("grey",  [(0, 0, 0), (1, 1, 1)], N=256)

rgb_to_YCbCr = np.array([
    [0.299, 0.587, 0.114],
    [-0.168736, -0.331264, 0.5],
    [0.5, -0.418688, -0.081312]
])
offset = np.array([0, 128, 128])
BS = 8
Cr_samp = 2

def showImg(img, title, cmap=None):
    #print(f"{title}:\n", img)
    plt.figure()
    plt.imshow(img, cmap=cmap)
    plt.axis("off")
    plt.title(title)
    plt.show()


def padding(img):
    nlAux, ncAux, _ = img.shape  
    RestoLinhas = nlAux % 32
    LinhasAdd = 32 - RestoLinhas if RestoLinhas != 0 else 0
    RestoColunas = ncAux % 32
    ColunasAdd = 32 - RestoColunas if RestoColunas != 0 else 0
    if LinhasAdd > 0:
        pad_linhas = np.repeat(img[-1:, :], LinhasAdd, axis=0)
        img = np.vstack((img, pad_linhas))
    if ColunasAdd > 0:
        pad_colunas = np.repeat(img[:, -1:], ColunasAdd, axis=1)
        img = np.hstack((img, pad_colunas))
    return img

def inverse_padding(img):
    img = img[0:nl, 0:nc, :]    
    return img

def converte_para_YCbCr(img):
    img = img.astype(np.float32)
    img = np.dot(img, rgb_to_YCbCr.T) + offset
    return img

def mostrar_canal(img):
    Y = img[:, :, 0]
    Cb = img[:, :, 1]
    Cr = img[:, :, 2]
    return Y, Cb, Cr

def converte_para_RGB(img):
    img = img.astype(np.float32)
    img -= offset
    YCbCr_to_RGB = np.linalg.inv(rgb_to_YCbCr)
    img_rgb = np.dot(img, YCbCr_to_RGB.T)
    return np.round(np.clip(img_rgb, 0, 255)).astype(np.uint8)

def downsampling(Y, Cb, Cr, Cr_samp):
    if Cr_samp == 2:
        Y_down = Y
        Cb_down = cv2.resize(Cb, (Cb.shape[1] // 2, Cb.shape[0]), interpolation=cv2.INTER_LINEAR)
        Cr_down = cv2.resize(Cr, (Cr.shape[1] // 2, Cr.shape[0]), interpolation=cv2.INTER_LINEAR)
    else:
        Y_down = Y
        Cb_down = cv2.resize(Cb, (Cb.shape[1] // 2, Cb.shape[0] // 2), interpolation=cv2.INTER_LINEAR)
        Cr_down = cv2.resize(Cr, (Cr.shape[1] // 2, Cr.shape[0] // 2), interpolation=cv2.INTER_LINEAR)
    return Y_down, Cb_down, Cr_down

def upsampling(Y, Cb, Cr, Cr_samp):
    if Cr_samp == 2:
        Y_up = Y
        Cb_up = cv2.resize(Cb, (Cb.shape[1] * 2, Cb.shape[0]), interpolation=cv2.INTER_LINEAR)
        Cr_up = cv2.resize(Cr, (Cr.shape[1] * 2, Cr.shape[0]), interpolation=cv2.INTER_LINEAR)
    else:
        Y_up = Y
        Cb_up = cv2.resize(Cb, (Cb.shape[1] * 2, Cb.shape[0] * 2), interpolation=cv2.INTER_LINEAR)
        Cr_up = cv2.resize(Cr, (Cr.shape[1] * 2, Cr.shape[0] * 2), interpolation=cv2.INTER_LINEAR)
    return Y_up, Cb_up, Cr_up

def DCT(Y, Cb, Cr):
    Ydct  = dct(dct(Y, norm='ortho').T, norm='ortho').T
    Cbdct = dct(dct(Cb, norm='ortho').T, norm='ortho').T
    Crdct = dct(dct(Cr, norm='ortho').T, norm='ortho').T
    return Ydct, Cbdct, Crdct

def DCT_BS(canal, BS):
    nl, nc = canal.shape
    dct_canal = np.zeros_like(canal, dtype=float)
    for i in range(0, nl, BS):
        for j in range(0, nc, BS):
            bloco = canal[i:i+BS, j:j+BS]
            dct_bloco = dct(dct(bloco, norm='ortho').T, norm='ortho').T
            dct_canal[i:i+BS, j:j+BS] = dct_bloco
    return dct_canal

def IDCT_BS(BS, dct_canal):
    nl, nc = dct_canal.shape
    idct_canal = np.zeros_like(dct_canal, dtype=float)
    for i in range(0, nl, BS):
        for j in range(0, nc, BS):
            bloco = dct_canal[i:i+BS, j:j+BS]
            idct_bloco = idct(idct(bloco, norm='ortho').T, norm='ortho').T
            idct_canal[i:i+BS, j:j+BS] = idct_bloco
    return idct_canal

def get_quantization_matrix(quality, is_luminance=True):
    if is_luminance:
        Q_std = np.array([[16,11,10,16,24,40,51,61],
                          [12,12,14,19,26,58,60,55],
                          [14,13,16,24,40,57,69,56],
                          [14,17,22,29,51,87,80,62],
                          [18,22,37,56,68,109,103,77],
                          [24,35,55,64,81,104,113,92],
                          [49,64,78,87,103,121,120,101],
                          [72,92,95,98,112,100,103,99]])
    else:
        Q_std = np.array([[17,18,24,47,99,99,99,99],
                          [18,21,26,66,99,99,99,99],
                          [24,26,56,99,99,99,99,99],
                          [47,66,99,99,99,99,99,99],
                          [99,99,99,99,99,99,99,99],
                          [99,99,99,99,99,99,99,99],
                          [99,99,99,99,99,99,99,99],
                          [99,99,99,99,99,99,99,99]])
    if quality < 50:
        scale = 50 / quality
    else:
        scale = (100 - quality) / 50
    Q = np.round((Q_std * scale ) )
    Q[Q < 1] = 1
    Q[Q>255] = 255
    return Q

def quantize_dct(dct_coeff, quality, block_size=8, is_luminance=True):
    quantized = np.zeros_like(dct_coeff, dtype=np.int32)
    Q = get_quantization_matrix(quality, is_luminance)
    nl, nc = dct_coeff.shape
    for i in range(0, nl, block_size):
        for j in range(0, nc, block_size):
            bloco = dct_coeff[i:i+block_size, j:j+block_size]
            quantized[i:i+block_size, j:j+block_size] = np.round(bloco / Q).astype(np.int32)
    return quantized

def dequantize_dct(q_dct_coeff, quality, block_size=8, is_luminance=True):
    dct_coeff = np.zeros_like(q_dct_coeff, dtype=float)
    Q = get_quantization_matrix(quality, is_luminance)
    nl, nc = q_dct_coeff.shape
    for i in range(0, nl, block_size):
        for j in range(0, nc, block_size):
            bloco = q_dct_coeff[i:i+block_size, j:j+block_size]
            dct_coeff[i:i+block_size, j:j+block_size] = bloco * Q
    return dct_coeff

def dpcm_encode(q_dct, block_size=8):
    rows, cols = q_dct.shape
    out = q_dct.copy()
    prev = None
    for i in range(0, rows, block_size):
        for j in range(0, cols, block_size):
            current_dc = q_dct[i, j]
            if prev is None:
                out[i, j] = current_dc
            else:
                out[i, j] = current_dc - prev
            prev = current_dc
    return out

def dpcm_decode(dpcm_q_dct, block_size=8):
    rows, cols = dpcm_q_dct.shape
    out = dpcm_q_dct.copy()
    prev = None
    for i in range(0, rows, block_size):
        for j in range(0, cols, block_size):
            if prev is None:
                out[i, j] = dpcm_q_dct[i, j]
                prev = out[i, j]
            else:
                out[i, j] = dpcm_q_dct[i, j] + prev
                prev = out[i, j]
    return out


def Mse(img, imgRec):
    mse = np.sum((img-imgRec)**2)/ (img.shape[0]*img.shape[1])
    return mse

def Rmse(mse):
    rmse = np.sqrt(mse)
    return rmse

def Snr(img, mse):

    signal_power = np.sum(img ** 2) / (img.shape[0]*img.shape[1])
    snr = 10 * np.log10(signal_power / mse)
    return snr

def Psnr(img, mse):
    psnr = 10 * np.log10((np.max(img) ** 2) / mse)
    return psnr


def max_avg_diff(img, imgRec):
    diff = np.abs(img - imgRec)
    max_diff = np.max(diff)
    avg_diff = np.mean(diff)
    return max_diff, avg_diff, diff


def encoder(img):
    #quality_factors = [10, 25, 50, 100, 75]
    quality_factors = [75]

    img_padded = padding(img)
    R, G, B = mostrar_canal(img_padded)
    showImg(R, "R", cm_red)
    showImg(G, "G", cm_green)
    showImg(B, "B", cm_blue)
    
    img_YCbCr = converte_para_YCbCr(img_padded)
    Y, Cb, Cr = mostrar_canal(img_YCbCr)
    showImg(Y, "Y", cm_grey)
    showImg(Cb, "Cb", cm_grey)
    showImg(Cr, "Cr", cm_grey)
    
    Y_d, Cb_d, Cr_d = downsampling(Y, Cb, Cr, Cr_samp)
    showImg(Y_d, "Y downsampling 4:2:2", cm_grey)
    showImg(Cb_d, "Cb downsampling 4:2:2", cm_grey)
    showImg(Cr_d, "Cr downsampling 4:2:2", cm_grey)
    
    Y_dct_block_1, Cb_dct_block_1, Cr_dct_block_1 = DCT(Y_d, Cb_d, Cr_d)
    showImg(np.log(np.abs(Y_dct_block_1) + 0.0001), "Y_DCT", cm_grey)
    showImg(np.log(np.abs(Cb_dct_block_1) + 0.0001), "Cb_DCT", cm_grey)
    showImg(np.log(np.abs(Cr_dct_block_1) + 0.0001), "Cr_DCT", cm_grey)
    
    Y_dct = DCT_BS(Y_d, BS)
    Cb_dct = DCT_BS(Cb_d, BS)
    Cr_dct = DCT_BS(Cr_d, BS)
    showImg(np.log(np.abs(Y_dct) + 0.0001), "Yb_DCT", cm_grey)
    showImg(np.log(np.abs(Cb_dct) + 0.0001), "Cbb_DCT", cm_grey)
    showImg(np.log(np.abs(Cr_dct) + 0.0001), "Crb_DCT", cm_grey)
    
    for quality in quality_factors:
        Y_q  = quantize_dct(Y_dct, quality, block_size=BS, is_luminance=True)
        Cb_q = quantize_dct(Cb_dct, quality, block_size=BS, is_luminance=False)
        Cr_q = quantize_dct(Cr_dct, quality, block_size=BS, is_luminance=False)
        showImg(np.log(np.abs(Y_q) + 0.0001), f"Yb_Q (QF={quality})", cm_grey)
        showImg(np.log(np.abs(Cb_q) + 0.0001), f"Cbb_Q (QF={quality})", cm_grey)
        showImg(np.log(np.abs(Cr_q) + 0.0001), f"Crb_Q (QF={quality})", cm_grey)
    Y_dpcm  = dpcm_encode(Y_q, BS)
    Cb_dpcm = dpcm_encode(Cb_q, BS)
    Cr_dpcm = dpcm_encode(Cr_q, BS)

    showImg(np.log(np.abs(Y_dpcm) + 0.0001), f"Yb_DPCM", cm_grey)
    showImg(np.log(np.abs(Cb_dpcm) + 0.0001), f"Cbb_DPCM", cm_grey)
    showImg(np.log(np.abs(Cr_dpcm) + 0.0001), f"Crb_DPCM", cm_grey)
    
    return Y_dpcm, Cb_dpcm, Cr_dpcm, Y

def decoder(Y_dpcm, Cb_dpcm, Cr_dpcm, quality=75):
    Y_q  = dpcm_decode(Y_dpcm, BS)
    Cb_q = dpcm_decode(Cb_dpcm, BS)
    Cr_q = dpcm_decode(Cr_dpcm, BS)
    Y_deq  = dequantize_dct(Y_q, quality, block_size=BS, is_luminance=True)
    Cb_deq = dequantize_dct(Cb_q, quality, block_size=BS, is_luminance=False)
    Cr_deq = dequantize_dct(Cr_q, quality, block_size=BS, is_luminance=False)
    Y_rec  = IDCT_BS(BS, Y_deq)
    Cb_rec = IDCT_BS(BS, Cb_deq)
    Cr_rec = IDCT_BS(BS, Cr_deq)
    Y_up, Cb_up, Cr_up = upsampling(Y_rec, Cb_rec, Cr_rec, Cr_samp)
    imgRec = np.stack((Y_up, Cb_up, Cr_up), axis=-1)
    imgRec = converte_para_RGB(imgRec)
    imgRec = inverse_padding(imgRec)
    return imgRec, Y_up

def main():
    global nl, nc
    fName = "projeto\\TP1\\imagens\\airport.bmp"
    img = plt.imread(fName)
    showImg(img, "Img Orig")
    
    Y_dpcm, Cb_dpcm, Cr_dpcm, y_original = encoder(img)
    
    nl, nc, _ = img.shape
    imgRec, y_rec = decoder(Y_dpcm, Cb_dpcm, Cr_dpcm, quality=75)
    showImg(imgRec, "Img Reconstr")

    image_metrics = img.astype(np.uint16)
    imgRec_metrics = imgRec.astype(np.uint16)
    max_diff, avg_diff, diff = max_avg_diff(y_original, y_rec)
    mse = Mse(image_metrics, imgRec_metrics)
    rmse = Rmse(mse)
    snr = Snr(image_metrics, mse)
    psnr =Psnr(image_metrics, mse)

    print(f"MSE: {mse}")
    print(f"RMSE: {rmse}")
    print(f"SNR: {snr}")
    print(f"PSNR: {psnr}")
    print("Max Diff: ", max_diff)
    print("Avg Diff: ", avg_diff)
    showImg(np.log(np.abs(diff) + 0.0001), "Img diff", cm_grey)

if __name__ == "__main__":
    main()
