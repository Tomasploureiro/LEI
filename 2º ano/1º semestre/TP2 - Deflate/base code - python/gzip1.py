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
							
			#Exercicio 1
            
			HLIT = self.readBits(5) + 257
			HDIST = self.readBits(5) + 1
			HCLEN = self.readBits(4) + 4
            
			print("HLIT Value: "+ str(HLIT) + "\nHDIST Value: "+ str(HDIST) + "\nHCLEN Value: " + str(HCLEN))


			#Exercicio 2
            
			codeLength = [0] * 19
			codeLengthSequence = [16, 17, 18, 0, 8, 7, 9, 6, 10, 5, 11, 4, 12, 3, 13, 2, 14, 1, 15]
			for i in range(HCLEN):
				codeLength[codeLengthSequence[i]] = self.readBits(3)
			print(str(codeLength) + "\n")
            
            
            #Exercicio 3
            
			huffmanCode = self.huffmanCodeLength(codeLength)
			print(str(huffmanCode) + "\n")
            
			huffmanT = self.createTree(huffmanCode)
            
            
			#Exercicio 4

			literallengthHLIT = self.literalLength(huffmanT, HLIT)

			print("\n" + str(literallengthHLIT) + "\n")
            
            
            #Exercicio 5
    				
			literallengthHDIST = self.literalLength(huffmanT, HDIST)

			print(str(literallengthHDIST) + "\n")
            
            
            #Exercicio 6
            
			huffmanCodeHLIT = self.huffmanCodeLength(literallengthHLIT)
			print(str(huffmanCodeHLIT) + "\n")
            
			huffmanCodeHLITAux = self.toArray(huffmanCodeHLIT)
			print(str(huffmanCodeHLITAux) + "\n")
        
			huffmanCodeHDIST = self.huffmanCodeLength(literallengthHDIST)
			print(str(huffmanCodeHDIST) + "\n")
            
			huffmanCodeHDISTAux = self.toArray(huffmanCodeHDIST)
			print(str(huffmanCodeHDISTAux) + "\n")
            
			huffmanTHLIT = self.createTree(huffmanCodeHLIT)
			print("\n")
            
			huffmanTHDIST = self.createTree(huffmanCodeHDIST)
			print("\n")
            
            
            #Exercicio 7
            
			deflatedArray = self.deflate(huffmanTHLIT, huffmanTHDIST)
			print(str(deflatedArray) + "\n")
            
            #Exercicio 8
            
			self.file(deflatedArray)
            						
												
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
        
    
	def huffmanCodeLength(self, codeLength):
		huffmanCodeDict = {}
		
		freqCodeLength = {}
		for i in codeLength:
			if(i not in freqCodeLength.keys()):
				freqCodeLength.setdefault(i, 1)
			else:
				freqCodeLength[i] += 1
            
		freqCodeLength = dict(sorted(freqCodeLength.items()))
		maxKeyValue = list(freqCodeLength.keys())[-1]
		
		freqCodeLengthAux = [0] * (maxKeyValue + 1)
		for i in freqCodeLength.keys():
			  freqCodeLengthAux[i] = freqCodeLength[i]
              
		codeAux = [0] * (maxKeyValue + 1)
		code = 0
		freqCodeLengthAux[0] = 0
		for i in range(len(codeAux) - 1):
			  code = (code + freqCodeLengthAux[i]) << 1
			  codeAux[i+1] = code
                
		huffmanCodeValue = []
		for i in codeLength:
			if i != 0:
				huffmanCodeValue.append(codeAux[i])
				codeAux[i] += 1
                
		codeLengthAux = [i for i in codeLength if i != 0]
        
		huffmanCode = []
		for i in range(len(huffmanCodeValue)):
			huffmanCode.append(format(huffmanCodeValue[i], "#010b"))
			huffmanCode[i] = huffmanCode[i][2 : len(huffmanCode[i])]
            
		for i in range(len(codeLengthAux)):
			if codeLengthAux[i] < 8:
				excessBits = 8 - codeLengthAux[i]
				huffmanCode[i] = huffmanCode[i][excessBits : len(huffmanCode[i])]
		huffmanCodeCont = 0
		for i in range(len(codeLength)):
			if codeLength[i] != 0:
				huffmanCodeDict.setdefault(i, huffmanCode[huffmanCodeCont])
				huffmanCodeCont+=1

		return huffmanCodeDict
    
    
	def createTree(self, huffmanCode):
		huffmanT = HuffmanTree()
        
		for i in huffmanCode.keys():
			huffmanT.addNode(huffmanCode[i], i, True)
                
		return huffmanT
        
        
	def literalLength(self, huffmanT, H):
		count = 0
		HAux = [0] * H
        
		while count < H:
			bit = self.readBits(1)
			pos = huffmanT.nextNode(str(bit))
			if pos >= 0:
				if pos == 16:
					repetition = 3
					bit = 0
                    
					for i in range(2):
						bits = self.readBits(1)
						bits = bits << i
						bit = bit | bits
                        
					repetition += bit				
    
					for i in range(repetition):
						HAux[count] = HAux[count - 1]
						count += 1
    
				elif pos == 17:
					repetition = 3
					bit = 0

					for i in range(3):
						bits = self.readBits(1)
						bits = bits << i
						bit = bit | bits

					repetition += bit

					for i in range(repetition):
						HAux[count] = 0
						count += 1
            
				elif pos == 18:
					repetition = 11
					bit = 0

					for i in range(7):
						bits = self.readBits(1)
						bits = bits << i
						bit = bit | bits

					repetition += bit

					for i in range(repetition):
						HAux[count] = 0
						count += 1
                        
				else:
					HAux[count] = pos
					count += 1
                    
				huffmanT.resetCurNode()
    
		return HAux
    
    
	def toArray(self, huffmanCodeH):
		huffmanCodeHAux = []
		
		for i in huffmanCodeH.values():
			huffmanCodeHAux.append(i)
            
		return huffmanCodeHAux
    
        
	def deflate(self, huffmanTHLIT, huffmanTHDIST):
		deflated = []
		pos = 0
		HLITDict = {'265': [11,1], '266': [13,1], '267': [15,1], '268':[17,1],'269': [19,2], '270': [23,2], '271': [27,2], '272':[31,2],'273': [35,3], '274': [43,3], '275': [51,3], '276':[59,3],'277': [67,4], '278': [83,4], '279': [99,4], '280':[115,4],'281': [131,5], '282': [163,5], '283': [195,5], '284':[227,5]}
		HDISTDict = {'4': [5, 1], '5': [7, 1], '6': [9, 2], '7': [13, 2], '8': [17, 3], '9': [25, 3], '10': [33, 4], '11': [49, 4], '12': [65, 5], '13': [97, 5], '14': [129, 6], '15': [193, 6], '16': [257, 7], '17': [385, 7], '18': [513, 8], '19': [769, 8], '20': [1025, 9], '21': [1537, 9], '22': [2049, 10], '23': [3073, 10], '24': [4097, 11], '25': [6145, 11], '26': [8193, 12], '27': [12289, 12], '28': [16385, 13], '29': [24577, 13]}
		while pos != 256:
			toDir = self.readBits(1)
			pos = huffmanTHLIT.nextNode(str(toDir))
			if pos >= 0:
				if pos >= 256:
					compDist = [0, 0]

					if (257 <= pos <= 264):   
						compDist[0] = pos - 254

					elif (pos == 285):
						compDist[0] = 258

					else:
						for s in HLITDict:
							if(pos == int(s)):
							    comp = HLITDict[s][0]
							    bitsExtra = self.readBits(HLITDict[s][1])
							    comp += bitsExtra
							    compDist[0] = comp
                    

					posDist = -2
					while (posDist < 0):
						posDist = huffmanTHDIST.nextNode(str(self.readBits(1)))

					huffmanTHDIST.resetCurNode()
					if (posDist < 4):
						compDist[1] = posDist + 1

					else: 
						for s in HDISTDict:
							if(posDist == int(s)):
							    dist = HDISTDict[s][0]
							    bitsExtra = self.readBits(HDISTDict[s][1])
							    dist += bitsExtra
							    compDist[1] = dist

					start = len(deflated) - compDist[1]
					for i in range(compDist[0]):
						deflated += [deflated[start + i]]
				else:
					deflated.append(pos)
				huffmanTHLIT.resetCurNode()

		return deflated
        
      
	def file(self, data):
		fileName = self.gzh.fName
        
		f = open(fileName, "w")
        
		dataToFile = ""
        
		for i in data:
			dataToFile = dataToFile + chr(i)
    
		f.write(dataToFile)
        
		f.close()
        
		return
        
    
if __name__ == '__main__':

	# gets filename from command line if provided
	fileName = "FAQ.txt.gz"
	if len(sys.argv) > 1:
		fileName = sys.argv[1]			

	# decompress file
	gz = GZIP(fileName)
	gz.decompress()
	