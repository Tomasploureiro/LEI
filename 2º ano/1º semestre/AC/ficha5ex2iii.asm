.data 
frase: .asciiz "Teste"
.text

.globl main

main:
	la $a1,frase
	
	move $a0,$a1
	li $v0,4
	syscall
	
	jal func
	
	sb $zero,-1($a1) #carater nulo no final
	la $a0,frase
	
	li $v0,4
	syscall
	
	li $v0,10
	syscall
	
func:
	lb $a0,0($a1)
	addi $a1,$a1,1
	bge $a0,0x61,muda
	bne $a0,$zero,func	
	jr $ra

muda:
	li $t0,0
	addi $t0,$a0,-0x20
	sb $t0,-1($a1)
	bne $a0,$zero,func
	jr $ra
