#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#include "skip_list.h"

#define ROW_LENGTH 1024

/**
 * It opens a file and returns a pointer to it
 *
 * @param file_name The name of the file to open.
 * @param modes r, w, a, r+, w+, a+
 *
 * @return A pointer to a file.
 */
FILE *open_file(char *file_name, char *modes);

/**
 * It reads a file line by line, and inserts each line into the skip list
 *
 * @param list The skip list to insert the lines.
 * @param input_file The file to read the lines.
 */
void read_from_file(SkipList *list, FILE *input_file);

/**
 * It reads a file and check if all the words are in the skip list. If they not,
 * it prints them.
 *
 * @param list The SkipList to search in.
 * @param input_file The file to search for words in.
 */
void find_errors(SkipList *list, FILE *input_file);

/**
 * It compares two strings, ignoring case
 *
 * @param val1 The first value to compare.
 * @param val2 The second value to compare.
 *
 * @return The difference between the two strings.
 */
int string_comparator(const void *val1,const void *val2);

int int_comparator(const void* val1, const void *val2);

/**
 * It checks if the user
 * entered the correct number of arguments or if the user entered the --help
 * argument
 *
 * @param argc The number of arguments passed to the program.
 * @param argv Arguments.
 *
 * @return 0 if the arguments are not correct or if the user asks for '--help',
 * and 1 if they are.
 */
int check_args(int argc, char const *argv[]);

int main(int argc, char const *argv[]) {
  if (check_args(argc, argv) == 0) return EXIT_FAILURE;
  FILE *dictionary_file = open_file((char *)argv[1], "r");

  SkipList **list;
  int max_height = atoi(argv[3]);
  printf("MAX HEAGHT SET TO %d\n", max_height);
  new_skiplist(list, max_height, string_comparator);

  printf("START INSERT IN SKIPLIST\n");
  clock_t begin = clock();
  read_from_file((*list), dictionary_file);
  clock_t end = clock();
  printf("INSERT END IN %fs\n\n", (double)(end - begin)/CLOCKS_PER_SEC);

  FILE *correctme_file = open_file((char *)argv[2], "r");
  printf("START SEARCH IN SKIPLIST\n\n");
  begin = clock();
  find_errors((*list), correctme_file);
  end = clock();
  printf("\nSEARCH END IN %fs\n", (double)(end - begin) / CLOCKS_PER_SEC);


  fclose(dictionary_file);
  fclose(correctme_file);
  clear_skiplist(list);
  return 0;
}

FILE *open_file(char *file_name, char *modes) {
  FILE *f = fopen(file_name, modes);
  if (f == NULL) {
    fprintf(stderr, "File %s not exists!\n", file_name);
    exit(EXIT_FAILURE);
  }

  return f;
}

void read_from_file(SkipList *list, FILE *input_file) {
  char *buffer = malloc(ROW_LENGTH);
  while (fgets(buffer, ROW_LENGTH, input_file)) {
    buffer[strcspn(buffer, "\n")] = 0;
    insert_skip_list(list, (void *)buffer, sizeof(char *));
  }
  free(buffer);
}

void find_errors(SkipList *list, FILE *input_file) {
  fseek(input_file, 0, SEEK_END);
  size_t file_size = ftell(input_file);
  rewind(input_file);

  char *file_content = malloc(file_size);
  fread(file_content, 1, file_size, input_file);

  char *word = strtok(file_content, " ,.;:\n");
  while (word != NULL) {
    if (search_skip_list(list, (void *)word) == NULL) {
      printf("%s\n", word);
    }
    word = strtok(NULL, " ,.;:\n");
  }

  free(file_content);
}

int string_comparator(const void *val1, const void *val2) {
  return strcasecmp((char *)val1, (char *)val2);
}

int int_comparator(const void* val1, const void *val2) {
    return *(int*)val1 - *(int*)val2;
}

int check_args(int argc, char const *argv[]) {
  if(argv[1] == NULL) {
    printf("Usage: skip_list_main [dictionary_file] [correct_me_file] [max_height]\n\n");
    return 0;
  }

  if (strcmp(argv[1], "--help") == 0) {
    printf("Usage: skip_list_main [dictionary_file] [correct_me_file] [max_height]\n\n");
    return 0;
  }

  if (argc != 4) {
    fprintf(stderr, "Bad usage of skip_list_main. Use --help for manual!\n");
    return 0;
  }

  return 1;
}
