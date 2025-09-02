.data
disp: .word 0x10010000
.text

.globl main

main:

la $s0, disp
lw $s0, 0($s0)

li $t2,0
li $t5,0
li $t3, 256
li $t4, 128
li $t6,0
li $t9,0
loop:
      la $s1, ($t6)
      sw $s1, 0($s0)
      addi $s0,$s0,4
      addi $t5,$t5,1
      addi $t6,$t6,1024
      bne $t5,$t3,loop
      addi $t2,$t2,1
      li $t6,0
      li $t5,0
      add $t9,$t9,1048576
      add $t6,$t6,$t9
      bne $t2,$t4,loop