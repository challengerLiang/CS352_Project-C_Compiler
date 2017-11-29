	.text
.globl addarray
	.type	addarray, @function
addarray:
	/* Add your code here */
	movq $0,%rdx      # i=0 rdx: i;
	movq $0,%rax   # sum = array[0]   rax is return value;

while:
	cmpq %rdx,%rdi   # while i < n
	jle afterwhile
	addq (%rsi), %rax  #sum += array[i]
	addq $1,%rdx       #i++
	addq $4,%rsi       #array++
	jmp while

afterwhile:
	ret

	