	.data
	
	.text
	.globl	max
	.globl	min
	
max: 
	li $t1, $a1 
	move $s0, 0($a0)
	loop:
		lw $t0, 0($a0)
		addi $t2, $t2, 1
		bgt $t0, $s0, t1
		bne $t2, $t1, loop
		jr	$ra
	t1:
		move $s0, $t0
		addi $a0, $a0, 4
		j loop

min: 
	li $t1, $a1 
	move $s0, 0($a0)
	loop:
		lw $t0, 0($a0)
		addi $t2, $t2, 1
		blt $t0, $s0, t1
		bne $t2, $t1, loop
		jr	$ra
	t1:
		move $s0, $t0
		addi $a0, $a0, 4
		j loop
