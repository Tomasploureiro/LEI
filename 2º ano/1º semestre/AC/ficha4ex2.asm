.data

.text
.globl main

main:
	li $t1,9
	li $t2,6
	li $t3,0
	li $t4,-1
	li $t5,0
	li $v0,1
	jal subtracao

	move $a0,$t3
	syscall
	
	j exit
subtracao:
	sub $t3,$t1,$t2
	blt $t3,0,multiplicacao
	jr $ra
	
multiplicacao:
	mult $t3,$t4
	mflo $t5
	move $a0,$t5
	syscall
exit:
	li $v0,10
    	syscall
