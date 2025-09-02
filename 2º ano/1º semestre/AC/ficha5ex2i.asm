.data
tab: .word 10,11,12,13,14,21
n: .word 6

.text
.globl main

main:
	
	la $a0,tab
	la $a2,n
	lw $a2,0($a2)
	li $t1,0
	li $t2,0
	jal oddnumber
	j exit
	
oddnumber:
	loop:
		lw $t0,0($a0)
		addi $t1, $t1,1
		andi $t0, $t0,0x00000001
		addi $a0,$a0,4
		beq $t0,1,cont
		bne $t1,$a2,loop
	jr $ra
		
cont:
	addi $t2,$t2,1
	bne $t1,$a2,loop
	jr $ra

exit: