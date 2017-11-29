
all: myaddtest mystrlentest addarraytest factorialtest test

myaddtest: myadd.s myaddtest.c
	gcc -g -o myaddtest myadd.s myaddtest.c

mystrlentest: mystrlen.s mystrlentest.c
	gcc -g -o mystrlentest mystrlen.s mystrlentest.c

addarraytest: addarraytest.c addarray.s
	gcc -g -o addarraytest addarraytest.c addarray.s

factorialtest:  factorialtest.c factorial.s
	gcc -g -o factorialtest factorialtest.c factorial.s

test:
	./myaddtest
	./mystrlentest
	./addarraytest
	./factorialtest

clean:
	rm -f myaddtest mystrlentest addarraytest factorialtest