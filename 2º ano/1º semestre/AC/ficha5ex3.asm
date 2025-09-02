.data
string: .asciiz "AAAAA"
chave: .word 0x3C
.text
.globl main

main:
	la $a1,string
	la $a2,chave
	lw $a2,0($a2)
	
	jal EncriptaDesencripta
	
	la $a0,string
	li $v0,4
	syscall	

	li $v0,10
	syscall
	
EncriptaDesencripta:
	lb $a0,0($a1)
	addi $a1,$a1,1
	bne $a0,$zero,f
	jr $ra
	
f:
	xor $t0,$a0,$a2
	sb $t0,-1($a1)
	j EncriptaDesencripta

