.data
word: .word 0xFFFFFFFF
.text
.globl main

main:
	lw $a1, word
	li $t1,0
	li $t2,0
	li $t3,1
	li $t4,32
	li $v0,1
loop:
	addi $t1,$t1,1
	andi $s0,$a1,1
	beq $s0,$t3, Cont
	srl $a1,$a1,1
	bne $t1,$t4,loop
	j verifica
Cont:
	addi $t2,$t2,1
	bne $t1,$t4,loop
	
verifica:
	beq $t2,-1,numimpar
	beq $t2,0,numpar
	subi $t2,$t2,2
	j verifica
numimpar:
	li $a0,0
	syscall
	li $v0,10
	syscall
numpar:
	li $a0,1
	syscall
	li $v0,10
	syscall
	
