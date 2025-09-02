.data
string: .asciiz "Hello"
comp: .word 0
.text
.globl main

main:
	li $v0,0
	la $a1,string
	la $t0, comp
	jal contarcaracter
	sw $v0,0($t0)
	j exit

Contarcaracter:
	lb $a0,0($a1)
	addi $a1,$a1,1
	addi $v0,$v0,1
	bne $a0,$zero,contarcaracter
	addi $v0,$v0,-1
	jr $ra

exit:
	syscall
