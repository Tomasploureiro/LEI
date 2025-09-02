.data
tab: .word 0x3F,0x06,0x5B,0x4F,0x66,0x6D,0x7D,0x47,0x7F,0X6F
.text
.globl main

main:

li $s0,0xFFFF0011
li $s1,0xFFFF0010

la $t0,tab
la $t7,tab

li $t1,0
li $t6,0

loop:
	lw $t2,0($t7)
	sb $t2,0($s0)
	addi $t7,$t7,4
	addi $t6,$t6,4
	beq $t6,44,exit
	
loop2:
	lw $t2,0($t0)
	sb $t2,0($s1)
	addi $t1,$t1,4
	addi $t0,$t0,4
	bne $t1,40,loop2
	
reset:
	la $t0,tab
	li $t1,0
	lw $t2,0($t0)
	sb $t2,0($s1)
	j loop
exit:
	li $v0,10
	syscall

