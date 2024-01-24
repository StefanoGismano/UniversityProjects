all: unity skip_list skip_list_main skip_list_test
	build/bin/skip_list_test

unity: lib/unity.c
	gcc -c lib/unity.c -o lib/unity.o

skip_list: src/skip_list.c 
	gcc -c src/skip_list.c -o build/skip_list.o

skip_list_main: src/skip_list_main.c
	gcc src/skip_list_main.c build/skip_list.o -o build/bin/main_ex2

skip_list_test: src/skip_list_test.c
	gcc src/skip_list_test.c lib/unity.o build/skip_list.o -o build/bin/skip_list_test
    
clear:
	rm lib/*.o
	rm build/*.o
	rm build/bin/*