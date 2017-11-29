.text
scanint: 
.string "%ld" 
afterSort:
.string "Sorted:\n"
sortedNum:
.string "%ld\n"

test:
.string "Length:%ld\n"

.data
	.comm val_address, 4
	.comm a, 1024

.text
.globl main
.type main, @function	
			

main:
	movq $-1,%r13   #r13 means the length
	movq $a,%r14	#r14 means pointer	


	
scan:
 	movq $scanint, %rdi # scanf("%ld",&val_adress);
	movq $val_address, %rsi 	
	movq $0, %rax 
	call scanf 
	incq %r13
	movq $val_address, %r12
	movq (%r12), %r12
	movq %r12,(%r14)
	addq $8,%r14 

	cmpq $1,%rax
	jne after_scan
	jmp scan

after_scan:	
	#preparation for sort function
	movq $a,%rdi
	movq %r13,%rsi

	call sort

	movq %rax, %r14		#pass return value to r14
	#movq %rax, %r14   #need check

	movq $afterSort, %rdi
	movq $0,%rax
	call printf

	movq $0, %r15				 #int count = 0			
print:
	cmpq %r13,%r15				#while(count < length)
	jge after_print

	movq $sortedNum, %rdi
	movq (%r14),%rsi
	addq $8, %r14
	movq $0,%rax
	call printf
	incq %r15
	jmp print

after_print:
	ret


sort:
	subq $48,%rsp
	movq %r15, 40(%rsp)    #originally is the counter	
	movq %rdi, 32(%rsp)   #a
	movq %rsi, 24(%rsp)   #length
	movq %r13, 16(%rsp)    #original r13 means length
	movq %r14, 8(%rsp)	   #original r13 means pointer

	
	movq $0, %r13	 #r13 is new counter:i
	movq $0, %r14    #r14 is new counter:j
	movq %rsi, %r15   #r15 is length - 1 - i 
	subq $1, %rsi
	while1:
		cmpq %rsi, %r13	  #for (int i = 0; i < length - 1; i++)
		jge after_while1
		sub $1,%r15
		movq $0, %r14				#j = 0
		while2:
			cmpq %r15,%r14           #for (int j = 0; j < length -1 -i; j++)
			jge after_while2

			movq $8, %r8
			imulq %r14, %r8
			addq 32(%rsp),%r8
											#r8 = a[j]
			movq %r8, %r9
			addq $8,%r9
											#r9 = a[j+1]
			movq (%r8), %r10
			movq (%r9), %r11

			cmp %r11, %r10    				#if (a > b)
			jle after_if					# r12: temp t
			movq %r11, %r12                 # a = t 
			movq %r10, (%r9)				# b = a
			movq %r12, (%r8)				# t = a
		after_if:
			inc %r14
			jmp while2
		after_while2:
	inc %r13
	jmp while1
	after_while1:
	movq 32(%rsp), %rax

	#restore value
	movq 40(%rsp), %r15
	movq 32(%rsp), %rdi
	movq 24(%rsp), %rsi
	movq 16(%rsp), %r13
	movq 8(%rsp), %r14
	addq $48, %rsp
	ret 


	