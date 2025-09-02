		.data

		.text
		.globl	polycalc
polycalc:

	addi $sp, $sp, 16
	move $t0, $sp
	addi $t1, $a0, $a1
	mul $t1, $t1, 5
	mul $t2, $a3, 3
	mul $t2, $t2, $t0
	subi $t2, $a2, $t2
	mul $t3, $t1, $t2
	move $v0, $t3
	jr	$ra
