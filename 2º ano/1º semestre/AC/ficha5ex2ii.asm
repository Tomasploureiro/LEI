.data
tab: .word 2,3,4,5,6,7,8
bit: .word 0x00000001
comp: .word 7
.text
.globl main

main:
    la $a0,tab
    la $a2,comp

    lw $a2,0($a2)
    li $v0,0

    jal oddnumber

    move $a0,$v0
    li $v0,1
    syscall
    li $v0,10
    syscall

oddnumber:
    li $t5,0
    loop:
        lw $t0,0($a0)
        andi $t1,$t0,0x00000001
        addi $a0,$a0,4
        addi $t5,$t5,1
        beq $t1,1,altera
        bne $t5,$a2,loop
    jr $ra

altera:
    addi $t0,$t0,-1
    sw $t0,-4($a0) 
    bne $t5,$a2,loop
    jr $ra
