.data
    message1:  .asciiz  "Enter coordinate one (x1,y2):\n"
    message2:  .asciiz  "Enter coordinate two (x2,y2):\n"
    message3:  .asciiz  "The distance is:\n"
.text
.globl main

main:
    li   $v0, 4
    la   $a0, message1
    syscall
    li   $v0, 5
    syscall
    move $t0, $v0
    li   $v0, 5
    syscall
    move $t1, $v0
    #printing the next integer
    li   $v0, 4
    la   $a0, message2
    syscall
    li   $v0, 5
    syscall
    move $t2, $v0
    li   $v0, 5
    syscall
    move $t3, $v0
    #starting to compue
    sub $t5, $t0, $t2
    mul $t5, $t5, $t5
    #2nd coordinates
    sub $t4, $t1, $t3
    mul $t4, $t4, $t4
    
    add $t6, $t5, $t4
    
    move $t7, $zero  #i=0
    move $t8, $t6   #x=n
    li $t0, 2
    div $t6, $t0
    mflo $s0
    
loop:
    bge $t7, $s0, printer #end loop if condition is vialated
    div $t6, $t8
    mflo $s1
    
    add $t8, $t8, $s1
    li $t0, 2
    div $t8, $t0
    mflo $t8
    addi $t7, 1
    j loop

printer:
       li   $v0, 4
       la   $a0, message3
       syscall
       #
       li   $v0, 1
       move $a0, $t8
       syscall
exit:
    li $v0, 10
    syscall


    
    