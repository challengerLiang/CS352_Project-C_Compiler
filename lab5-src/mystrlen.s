	.file	"mystrlen.c"
	.text
.globl mystrlen
	.type	mystrlen, @function
mystrlen:
	/* Add your code here */
    movq $0, %r9      /*int count = 0     count r9*/

while:
	mov $0,%r12b
	cmpb (%rdi),%r12b    /* while (*str != 0)  */
 	je afterwhile
	addq $1,%r9   /*count++ */
	add $1,%rdi   /*    str++*/
	movq (%rdi), %r8   /* just check (%rdi) value */
	jmp	while

afterwhile:
	movq %r9, %rax
	ret 
	