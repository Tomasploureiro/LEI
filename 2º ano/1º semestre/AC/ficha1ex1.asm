.data
.text
.globl main
main:
li $s0, 10
li $s1, 6
li $s2, 7
li $s3, 10

sub $t0, $s0, $s2
sub $t1, $s1, $s3
sub $v0, $t0, $t1 
addi $v0, $v0, 5


