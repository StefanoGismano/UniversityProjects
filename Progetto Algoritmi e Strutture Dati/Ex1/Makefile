all: array_sort unity array_sort_test main_ex1
	build/bin/array_sort_test

array_sort: src/array_sort.c
	gcc -c src/array_sort.c -o build/array_sort.o

unity: lib/unity.c
	gcc -c lib/unity.c -o lib/unity.o
 
array_sort_test: src/array_sort_test.c
	gcc src/array_sort_test.c build/array_sort.o lib/unity.o -o build/bin/array_sort_test


main_ex1: src/main_ex1.c
	gcc src/main_ex1.c build/array_sort.o -o build/bin/main_ex1

clear:
	rm build/*.o
	rm build/bin/*