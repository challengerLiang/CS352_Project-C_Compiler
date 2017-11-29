	.text
.globl add
	.type	add, @function
add:
	/* Add your code here */
	movq %rdi, %rax
	addq %rsi, %rax
	addq %rdx, %rax
	ret




