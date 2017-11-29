	.text
.globl fact
	.type	fact, @function
fact:
	/* Add your code here */

	mov   $1, %r8d    #result = 1   result: in %r8d;
					   #argument in rdi : n
 while:
	mov $1,%r9d
	cmp %r9d,%edi     #while n >= 1
	jl afterwhile
	imul %edi, %r8d		#result *= n
	dec  %edi			#n--
	jmp while 

afterwhile:
	CVTSI2SD %r8d, %xmm0
	ret