# Author: Marco Simoes
# Adapted from Java's implementation of Rui Pedro Paiva
# Teoria da Informacao, LEI, 2022

import sys
from huffmantree import HuffmanTree


class GZIPHeader:
	''' class for reading and storing GZIP header fields '''

	ID1 = ID2 = CM = FLG = XFL = OS = 0
	MTIME = []
	lenMTIME = 4
	mTime = 0

	# bits 0, 1, 2, 3 and 4, respectively (remaining 3 bits: reserved)
	FLG_FTEXT = FLG_FHCRC = FLG_FEXTRA = FLG_FNAME = FLG_FCOMMENT = 0   
	
	# FLG_FTEXT --> ignored (usually 0)
	# if FLG_FEXTRA == 1
	XLEN, extraField = [], []
	lenXLEN = 2
	
	# if FLG_FNAME == 1
	fName = ''  # ends when a byte with value 0 is read
	
	# if FLG_FCOMMENT == 1
	fComment = ''   # ends when a byte with value 0 is read
		
	# if FLG_HCRC == 1
	HCRC = []
		
		
	
	def read(self, f):
		''' reads and processes the Huffman header from file. Returns 0 if no error, -1 otherwise '''

		# ID 1 and 2: fixed values
		self.ID1 = f.read(1)[0]  
		if self.ID1 != 0x1f: return -1 # error in the header
			
		self.ID2 = f.read(1)[0]
		if self.ID2 != 0x8b: return -1 # error in the header
		
		# CM - Compression Method: must be the value 8 for deflate
		self.CM = f.read(1)[0]
		if self.CM != 0x08: return -1 # error in the header
					
		# Flags
		self.FLG = f.read(1)[0]
		
		# MTIME
		self.MTIME = [0]*self.lenMTIME
		self.mTime = 0
		for i in range(self.lenMTIME):
			self.MTIME[i] = f.read(1)[0]
			self.mTime += self.MTIME[i] << (8 * i) 				
						
		# XFL (not processed...)
		self.XFL = f.read(1)[0]
		
		# OS (not processed...)
		self.OS = f.read(1)[0]
		
		# --- Check Flags
		self.FLG_FTEXT = self.FLG & 0x01
		self.FLG_FHCRC = (self.FLG & 0x02) >> 1
		self.FLG_FEXTRA = (self.FLG & 0x04) >> 2
		self.FLG_FNAME = (self.FLG & 0x08) >> 3
		self.FLG_FCOMMENT = (self.FLG & 0x10) >> 4
					
		# FLG_EXTRA
		if self.FLG_FEXTRA == 1:
			# read 2 bytes XLEN + XLEN bytes de extra field
			# 1st byte: LSB, 2nd: MSB
			self.XLEN = [0]*self.lenXLEN
			self.XLEN[0] = f.read(1)[0]
			self.XLEN[1] = f.read(1)[0]
			self.xlen = self.XLEN[1] << 8 + self.XLEN[0]
			
			# read extraField and ignore its values
			self.extraField = f.read(self.xlen)
		
		def read_str_until_0(f):
			s = ''
			while True:
				c = f.read(1)[0]
				if c == 0: 
					return s
				s += chr(c)
		
		# FLG_FNAME
		if self.FLG_FNAME == 1:
			self.fName = read_str_until_0(f)
		
		# FLG_FCOMMENT
		if self.FLG_FCOMMENT == 1:
			self.fComment = read_str_until_0(f)
		
		# FLG_FHCRC (not processed...)
		if self.FLG_FHCRC == 1:
			self.HCRC = f.read(2)
			
		return 0
			



class GZIP:
	''' class for GZIP decompressing file (if compressed with deflate) '''

	gzh = None
	gzFile = ''
	fileSize = origFileSize = -1
	numBlocks = 0
	f = None
	

	bits_buffer = 0
	available_bits = 0		

	
	def __init__(self, filename):
		self.gzFile = filename
		self.f = open(filename, 'rb')
		self.f.seek(0,2)
		self.fileSize = self.f.tell()
		self.f.seek(0)

		
	

	def decompress(self):
		''' main function for decompressing the gzip file with deflate algorithm '''
		
		numBlocks = 0

		# get original file size: size of file before compression
		origFileSize = self.getOrigFileSize()
		print(origFileSize)
		
		# read GZIP header
		error = self.getHeader()
		if error != 0:
			print('Formato invalido!')
			return
		
		# show filename read from GZIP header
		print(self.gzh.fName)
		
		
		# MAIN LOOP - decode block by block
		BFINAL = 0	
		while not BFINAL == 1:	
			
			BFINAL = self.readBits(1)
							
			BTYPE = self.readBits(2)					
			if BTYPE != 2:
				print('Error: Block %d not coded with Huffman Dynamic coding' % (numBlocks+1))
				return
			
									
			#--- STUDENTS --- ADD CODE HERE
			# 
			# 
            #-------------------------Exercício 1-------------------------

			HLIT, HDIST, HCLEN = self.readBlockFormat()

			print("\nValue of HLIT: " + str(HLIT))
			print("Value of HDIST: " + str(HDIST))
			print("Value of HCLEN: " + str(HCLEN) + "\n")

			#-------------------------Exercício 2-------------------------

			codeLengthsOrder = [16, 17, 18, 0, 8, 7, 9, 6, 10, 5, 11, 4, 12, 3, 13, 2, 14, 1, 15]   #Array que contem as ordens das sequências de 3 bits.
            
			codeLengths = [0] * 19       #Array dos comprimentos dos códigos de HCLEN

			for i in range(HCLEN):
				codeLengths[codeLengthsOrder[i]] = self.readBits(3)

			print(str(codeLengths) + "\n")

			#-------------------------Exercício 3-------------------------

			huffmanCodes = self.codeLengthsHuffman(codeLengths)     #Dicionário

			print(str(huffmanCodes) + "\n")

			hft = self.generateTree(huffmanCodes)   #Árvore de Huffman com os códigos de Huffman do alfabeto de comprimentos de códigos.

			print("\n")

			#-------------------------Exercício 4-------------------------

			literalLengthsHLIT = []

			literalLengthsHLIT = self.literalLengthValues(hft, HLIT)

			print(str(literalLengthsHLIT) + "\n")

			#-------------------------Exercício 5-------------------------

			literalLengthsHDIST = []

			literalLengthsHDIST = self.literalLengthValues(hft, HDIST)

			print(str(literalLengthsHDIST) + "\n")
            
            #-------------------------Exercício 6-------------------------
            
			literalHuffcodesDic = {}
            
			literalHuffcodesDic = self.codeLengthsHuffman(literalLengthsHLIT)
            
			literalHuffcodes = []
            
			for i in literalHuffcodesDic.items():
			    literalHuffcodes.append(i)
                
			print(str(literalHuffcodes) + "\n")
            
            
																																								
			# update number of blocks read
			numBlocks += 1
		

		
		# close file			
		
		self.f.close()	
		print("End: %d block(s) analyzed." % numBlocks)
	
	
	def getOrigFileSize(self):
		''' reads file size of original file (before compression) - ISIZE '''
		
		# saves current position of file pointer
		fp = self.f.tell()
		
		# jumps to end-4 position
		self.f.seek(self.fileSize-4)
		
		# reads the last 4 bytes (LITTLE ENDIAN)
		sz = 0
		for i in range(4): 
			sz += self.f.read(1)[0] << (8*i)
		
		# restores file pointer to its original position
		self.f.seek(fp)
		
		return sz		
	

	
	def getHeader(self):  
		''' reads GZIP header'''

		self.gzh = GZIPHeader()
		header_error = self.gzh.read(self.f)
		return header_error
		

	def readBits(self, n, keep=False):
		''' reads n bits from bits_buffer. if keep = True, leaves bits in the buffer for future accesses '''

		while n > self.available_bits:
			self.bits_buffer = self.f.read(1)[0] << self.available_bits | self.bits_buffer
			self.available_bits += 8
		
		mask = (2**n)-1
		value = self.bits_buffer & mask

		if not keep:
			self.bits_buffer >>= n
			self.available_bits -= n

		return value

	def readBlockFormat(self):    #Método para ler o formato do bloco
		#HLIT -> 257 - 286
		HLITValue = self.readBits(5) + 257

		#HDIST -> 1 - 32
		HDISTValue = self.readBits(5) + 1

		#HCLEN -> 4 - 19
		HCLENValue = self.readBits(4) + 4

		return HLITValue, HDISTValue, HCLENValue
    
	def codeLengthsHuffman(self, codeLengthsArray):     #Método para converter os comprimentos dos códigos de HCLEN em códigos de Hufman
		huffmanCodesDic = {}   #Dicionário que será retornado com os códigos de Huffman.

		blCountDic = {}
		for i in codeLengthsArray:
			if (i not in blCountDic.keys()):   #Se o valor de comprimento não se encontrar nas keys, este é adicionado e iniciada a sua contagem.
				blCountDic.setdefault(i, 1)

			else:
				blCountDic[i] += 1
        
		blCountDic = dict(sorted(blCountDic.items()))   #Ordena o dicionário pelas keys (Valores dos comprimentos)

		maxKeyValue = list(blCountDic.keys())[-1]
        
		blCountAux = [0] * (maxKeyValue + 1)
		for i in blCountDic.keys():
			blCountAux[i] = blCountDic[i]
		#print(blCountAux)

		nextCode = [0] * (maxKeyValue + 1)   #Inicializa um array de zeros com tamanho igual ao valor da maior key do dicionário de comprimentos (blCountAux).

		code = 0
		blCountAux[0] = 0
		#print(blCountAux)
		for i in range(1, len(nextCode)):
			code = (code + blCountAux[i - 1]) << 1   #Deslocamento de bits à esquerda.
			#print(code)
			nextCode[i] = code
		#print(nextCode)
		huffmanCodesInt = []
		for i in range(len(codeLengthsArray)):
			lenCode = codeLengthsArray[i]

			if (lenCode != 0):
				huffmanCodesInt.append(nextCode[lenCode])
				nextCode[lenCode] += 1

		codeLengthsAux = [i for i in codeLengthsArray if i != 0]   #Array que contém os tamanhos de todos os códigos (Com os zeros removidos).
		print(codeLengthsAux)
		huffmanCodes = []
		for i in huffmanCodesInt:
			huffmanCodes.append(format(i, "#010b"))   #Converte o valor diretamente de inteiro para binário adicionando os zeros à esquerda.
			
		for i in range(len(huffmanCodes)):
			huffmanCodes[i] = huffmanCodes[i][2 : len(huffmanCodes[i])]   #Remove os "0b" à frente do número em binário.

		for i in range(len(codeLengthsAux)):
			if (codeLengthsAux[i] < 8):       # este 8 pode tirar de ser IMP
				zeroPosition = 8 - codeLengthsAux[i]   #São removidos os 0's em excesso à esquerda (Tal ocorre somente nos códigos de comprimento inferior a 8).
				huffmanCodes[i] = huffmanCodes[i][zeroPosition : len(huffmanCodes[i])]
                
		codeSymbols = [i for i in range(len(codeLengthsArray)) if codeLengthsArray[i] != 0]   #Remove os símbolos cujo valor do comprimento seja igual a 0.
		for i in range(len(codeSymbols)):
			huffmanCodesDic.setdefault(codeSymbols[i], huffmanCodes[i])

		return huffmanCodesDic
    
    #Método responsável por gerar a árvore de Huffman e adicionar os códigos dos comprimentos de códigos.
	def generateTree(self, huffmanCodes):
		hft = HuffmanTree()   #Inicializa uma árvore de Huffman.
		verbose = True   #Campo verbose que disponibiliza uma mensagem após a inserção na árvore.

		for i in huffmanCodes.keys():
			hft.addNode(huffmanCodes[i], i, verbose)   #huffmanCodes[i] -> código de Huffman, i -> índice do código no alfabeto, verbose -> True ou False.

		return hft
    
    #Método responsável por ler e armazenar num array os HLIT + 257 ou os HDIST + 1 comprimentos dos códigos referentes ao alfabeto de literais/comprimentos.
	def literalLengthValues(self, hft, HType):
		count = 0
		array = [0] * HType   #É inicializado um array de 0's com tamanho igual a HType (HLIT ou HDIST).

		while (count < HType):   #O código é executado enquanto que o valor do contador for inferior ao valor de HType passado como parâmetro.
			bit = self.readBits(1)
			pos = hft.nextNode(str(bit))
				
			if(pos >= 0):   #Se o valor da variável pos, retornado pelo método nextNode do ficheiro huffmantree.py, for superior a 0, indica que foi encontrada um folha, contendo o valor do seu índice no alfabeto (Corresponde ao símbolo).
				if(pos == 16):   #Se o valor de pos for igual a 16, é necessário repetir o comprimento anterior pelo menos 3 vezes de acordo com os dois próximos bits a ler.
					repeat = 3
					bit = 0

					for i in range(2):
						bits = self.readBits(1)   #É lido um bit do buffer.
						bits = bits << i   #Deslocamento de i bits para a esquerda.
						bit = bit | bits   

					repeat += bit   #A variável repeat contém no número de repetições do comprimento anterior no array de comprimentos de códigos.

					for i in range(repeat):   #É repetido o comprimento anterior n vezes (Pelo menos 3).
						array[count] = array[count-1]   #O valor do comprimento do código de Huffman será igual ao valor do comprimento anterior.
						count += 1   #É incrementado o contador.

				elif(pos == 17):   #Se o valor da variável pos for igual a 17 os próximos códigos terão comprimento igual 0 de acordo com o número de bits extra a ler (Pelo menos 3).
					repeat = 3
					bit = 0

					for i in range(3):
						bits = self.readBits(1)   #É lido um bit do buffer.
						bits = bits << i   #São deslocados i bits para a esquerda.
						bit = bit | bits

					repeat += bit   #A variável repeat contém o número de repetições do comprimento do código de Huffman no array de comprimentos.

					for i in range(repeat):   #Ciclo que irá adicionar o valor do comprimento igual a 0 aos próximos códigos.
						array[count] = 0   #O valor do comprimento do código será 0.
						count += 1   #É incrementado o contador.

				elif(pos == 18):   #Se o valor da variável pos for igual a 18, pelo menos os próximos 11 comprimentos de códigos (11 - 128) serão iguais a 0.
					repeat = 11
					bit = 0

					for i in range(7):
						bits = self.readBits(1)   #É lido um bit do buffer.
						bits = bits << i   #São deslocados i bits para a esquerda.
						bit = bit | bits

					repeat += bit

					for i in range(repeat):   #Ciclo que irá adicionar o valor do comprimento igual 0 aos próximos códigos.
						array[count] = 0   #O valor do comprimento do código será 0.
						count += 1   #É incrementado o contador.

				else:   #Caso contrário, se o valor da variável pos for inferior a 15, este será o valor do comprimento de códigos.
					array[count] = pos
					count += 1

				hft.resetCurNode()   #A posição na Árvore é "reiniciada", isto é volta para a posição inicial, para a raiz.

		return array


if __name__ == '__main__':

	# gets filename from command line if provided
	fileName = "FAQ.txt.gz"
	if len(sys.argv) > 1:
		fileName = sys.argv[1]			

	# decompress file
	gz = GZIP(fileName)
	gz.decompress()
	