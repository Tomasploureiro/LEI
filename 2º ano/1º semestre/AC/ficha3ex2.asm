.data
tab: .word 0x3F,0x06,0x5B,0x4F,0x66,0x6D,0x7D,0x47,0x7F,0X6F
.text

.globl main

main:

la $t0,tab
la $t7,tab
li $s0,0xFFFF0011
li $s1,0xFFFF0010

li $t1,3 #numero esquerdo
li $t2,2 #numero direito

li $t3,4

mul $t4,$t1,$t3
mul $t5,$t2,$t3

add $t0,$t0,$t4
add $t7,$t7,$t5

lw $t6,0($t0)
sb $t6,0($s0)

lw $t6,0($t7)
sb $t6,0($s1)
