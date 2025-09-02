	.data
	
	.text
	.globl	conta_char
	
conta_char:
	li $t1, 0

	loop: 
		lb $t0, 0($a0)
		beq $t0, $a1, cont
		ola:
		addi $a0, $a0, 1
		bne $a0, 0, loop
	move $v0, $t1	
	jr	$ra
cont:
	addi $t1, $t1, 1
	j ola