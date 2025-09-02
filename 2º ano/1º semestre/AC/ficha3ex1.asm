.text

main:
li $s0,0xFFFF0010
li $s1,0x01
li $t0,0

loop:
    sb $s1,0($s0)
    sll $s1,$s1,1
    addi $t0,$t0,1
    bne $t0,8,loop
j main