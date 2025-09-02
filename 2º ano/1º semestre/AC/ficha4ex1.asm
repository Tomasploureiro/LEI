.data 
word: .word 0x40302010
.text
.globl main

main:
    lw $a0,word
    li $v0,34
    
    jal change
    
    move $a0,$t0
    syscall
    
    j exit
change:
    andi $s0,$a0,0xFF000000
    srl $s0,$s0,24
    or $t0,$t0,$s0
    andi $s0,$a0,0x00FF0000
    srl $s0,$s0,8
    or $t0,$t0,$s0  
    andi $s0,$a0,0x0000FF00
    sll $s0,$s0,8
    or $t0,$t0,$s0 
    andi $s0,$a0,0x000000FF
    sll $s0,$s0,24
    or $t0,$t0,$s0
    jr $ra
exit:
    li $v0,10
    syscall
