main:
	pushq	%rbx
	pushq	%r10
	pushq	%r13
	pushq	%r14
	pushq	%r15
	subq	$256, %rsp
	# save argument
	movq	$L0, %rbx
	# push num 10 top=1
	movq	$10, %r10
	# push num 20 top=2
	movq	$20, %r13
	# +
	addq	%r10, %r13
	movq	%r13, %rsi
	movq	%r10, %rdi
	movq	$0, %rax
	call	printf
	movq	%rax, %r10
	movq	$L1, %rbx
	# push num 1 top=1
	movq	$1, %r10
	# push num 2 top=2
	movq	$2, %r13
	# +
	addq	%r10, %r13
	# push num 2 top=3
	movq	$2, %r14
	# +
	addq	%r10, %r13
	movq	%r14, %rsi
	movq	%r13, %rdi
	movq	$0, %rax
	call	printf
	movq	%rax, %r13
	addq	$256, %rsp
	# restore registers
	popq	%r15
	popq	%r14
	popq	%r13
	popq	%r10
	popq	%rbx
	retq
L0:
	.string "30: %ld\n"
L1:
	.string "5: %ld\n"
.text
	.globl	main

