.data
str: .asciiz "estada com carros"
c1: .ascii "a"
c2: .ascii "x"
.text
.globl main

main:
	la $a1,str
	la $a2,c1
	la $a3,c2
	
	lb $a2,0($a2)
	lb $a3,0($a3)
	
	jal substituir
	
	la $a0,str
	
	li $v0,4 # print string
	syscall
	
	li $v0,10 # encerra programa
	syscall

substituir:
	lb $t0,0($a1)
	addi $a1,$a1,1
	beq $t0,$a2,troca
	bne $t0,$zero,substituir
	jr $ra
	
troca:	
	sb $a3,-1($a1)
	bne $t0,$zero,substituir
	jr $ra
	