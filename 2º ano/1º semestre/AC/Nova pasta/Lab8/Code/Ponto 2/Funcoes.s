	.data
	
	.text
	.globl	max
	.globl	min
	
max:
	move $s0, $a0
	loop:
		bgt $a1, $s0,t1
		bgt $a2, $s0, t2
		bgt $a3, $s0, t3
		jr	$ra
	t1:
		move $s0, a1
		j loop
	t2:
		move $s0, $a2
		j loop
	t3:
		move $s0, $a3
		j loop

	

min:

	move $s0, $a0
	loop:
		blt $a1, $s0,r1
		blt $a2, $s0, r2
		blt $a3, $s0, r3
		jr	$ra
	r1:
		move $s0, $a1
		j loop
	r2:
		move $s0, $a2
		j loop
	r3:
		move $s0, $a3
		j loop
