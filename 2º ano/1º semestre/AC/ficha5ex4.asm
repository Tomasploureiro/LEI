.data
tab: .word 10,24,32,40,64,128
comp: .word 6
.text
.globl main

main:

	la $a1,tab
	la $a2,comp
	lw $a2,0($a2)
	li $a3,0
	li $v0,0
	
	jal f
	move $a0,$v0
	li $v0,1
	syscall
	
	li $v0,10
	syscall
	
f:
	lw $a0,0($a1)
	addi $a1,$a1,4
	addi $a3,$a3,1
	bne $a3,1,soma
	add $v0,$v0,$a0
	bne $a3,$a2,f
	jr $ra

soma:
	li $t5,1
	loop:
		srl $a0,$a0,1
		addi $t5,$t5,1
		bne $t5,$a3,loop
	add $v0,$v0,$a0
	bne $a3,$a2,f
	jr $ra