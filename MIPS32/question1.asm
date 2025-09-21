.data 
    message1:   .asciiz  "Enter a number:\n"
    message2 :  .asciiz "The Fibonacci number is:\n"

.text
.globl main

main:
    li $v0, 4
    la $a0, message1
    syscall
    li $v0, 5
    syscall
    move $s0, $v0
    move $s1, $s0
     


    move $t0,$zero
    li $t1, 1
    addi $sp, $sp, -8
    sw $t0, 4($sp)
    sw $t1, 0($sp)

    beq $s0, $zero, printer
    beq $s0, $t1, printer
    li $s2, 2  #counter
    
    j fibonacci

fibonacci:
         bgt $s2, $s1, clean
         addi $sp, $sp, -4
         lw $t3, 4($sp)
         lw $t4, 8($sp)
         add $t5, $t3, $t4
         sw $t5, 0($sp)
         move $s0, $t5
         addi $s2, $s2, 1
         j fibonacci

clean:
     addi $s3, $s1, 1
     li $t8, 4
     mul  $s3, $s3, $t8
     add $sp, $sp, $s3
     j printer

printer:
       li $v0, 4
       la $a0, message2
       syscall
       
       li $v0,1
       move $a0, $s0
       syscall
exit:
    li      $v0, 10
    syscall
       