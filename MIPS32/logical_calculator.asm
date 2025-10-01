.data
   left:  .word  '<'
   right: .word  '>'
   rotate: .word  '&'
   #rotate_right: .word ">&"
   buffer:  .space 50
   
.text

.globl main


main:
   li $v0, 5
   syscall
   move $t0, $v0
   

   
   li $v0, 12
   syscall
   move $t5, $v0
   
   li $v0, 12
   syscall
   move $t6, $v0
   
   li $v0, 12
   syscall #new line 
   move $t7, $v0
   
   li $v0, 5
   syscall
   move $t1, $v0
   
   lw $s0,left  #load my ops
   lw $s1,right
   lw $s2,rotate
   
   beq $t5, $s0, l
   beq $t5, $s1, r
   
l:
  beq $s0, $t6, shift_l
  beq $s2, $t6, left_rotate
  
r:
  beq $s1, $t6, shift_r
  beq $s2, $t6, right_rotate
  
shift_l:
   sllv $t2,$t0, $t1
   li $v0, 1
   move $a0, $t2
   syscall
   j exit

left_rotate:
   rol $t2,$t0, $t1
   li $v0, 1
   move $a0, $t2
   syscall
   j exit
   
shift_r:
   srlv $t2,$t0, $t1
   li $v0, 1
   move $a0, $t2
   syscall 
   j exit
   
right_rotate:
   ror $t2,$t0, $t1
   li $v0, 1
   move $a0, $t2
   syscall
   j exit

   
   
   
   

exit:
   li $v0, 10
   syscall
   


