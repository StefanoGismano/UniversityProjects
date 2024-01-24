#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#include "array_sort.h"

#define ROW_LENGTH 1024

typedef struct _Record {
  int id;
  char *string_field;
  int int_field;
  double double_field;
} Record;

/**
 * It compares the int_field of two records
 *
 * @param val1 The first value to compare.
 * @param val2 The second value to compare.
 *
 * @return The difference between the two int_fields.
 */
int record_int_field_comparator(const void *val1, const void *val2);

/**
 * It compares the string_field of two records
 *
 * @param val1 The first value to compare.
 * @param val2 The second value to compare.
 *
 * @return The difference between the two strings.
 */
int record_string_field_comparator(const void *val1, const void *val2);

/**
 * It compares the double_field of two records
 *
 * @param val1 The first value to compare.
 * @param val2 The second value to compare.
 *
 * @return -1, 0, 1
 */
int record_double_field_comparator(const void *val1, const void *val2);

/**
 * It opens a file and returns a pointer to it
 *
 * @param file_name The name of the file to open.
 * @param modes r, w, rw
 *
 * @return A pointer to a file.
 */
FILE *open_file(char *file_name, char *modes);

/**
 * Reads a CSV file and returns a pointer to an array of struct Record
 *
 * @param input_file The file to read.
 * @param rows The number of rows in the file.
 *
 * @return Array of records
 */
Record *read_from_file(FILE *input_file, int *rows);

/**
 * It writes the records to the output file
 *
 * @param output_file The file to write.
 * @param records The array of records to write.
 * @param rows The number of rows in the file.
 */
void *write_output_file(FILE *output_file, Record *records, int rows);

/**
 * It frees the memory allocated for the records array
 *
 * @param records the array of records
 * @param rows the number of records in the file
 */
void free_memory(Record *records, int rows);

/**
 * It sorts the records array based on the specified field
 * It puts the result in a new file and prints the time taken
 *
 * @param infile The input file that contains the records to sort
 * @param outfile The output file that contains the ordered records
 * @param k The parameter to give to merge_binary_insertion_sort
 * @param field It can be 1, 2 or 3. Decides which field of the record to base the sorting on
 */
void sort_records(FILE *infile, FILE *outfile, size_t k, size_t field);

/**
 * It checks if the user entered the correct number of arguments 
 * or if the user entered the --help argument
 *
 * @param argc The number of arguments passed to the program.
 * @param argv The arguments passed to the program.
 *
 * @return 0 if the arguments are incorrect or if the user asks for '--help',
 * and 1 if they are acceptable.
 */
int check_args(int argc, char const *argv[]);

int main(int argc, char const *argv[]) {
  if(check_args(argc, argv) == 0) return EXIT_FAILURE;

  char *infile_name = (char *)argv[1];
  char *outfile_name = (char *)argv[2];
  size_t k = atoi(argv[3]);
  size_t field = atoi(argv[4]);

  FILE *infile = open_file(infile_name, "r");
  FILE *outfile = open_file(outfile_name, "w");

  sort_records(infile, outfile, k, field);

  return EXIT_SUCCESS;
}

int record_int_field_comparator(const void *val1, const void *val2) {
  return ((Record *)val1)->int_field - ((Record *)val2)->int_field;
}

int record_string_field_comparator(const void *val1, const void *val2) {
  return strcmp(((Record *)val1)->string_field, ((Record *)val2)->string_field);
}

int record_double_field_comparator(const void *val1, const void *val2) {
  if (((Record *)val1)->double_field < ((Record *)val2)->double_field)
    return -1;
  else if ((((Record *)val1)->double_field == ((Record *)val2)->double_field))
    return 0;
  else
    return 1;
}

FILE *open_file(char *file_name, char *modes) {
  FILE *f = fopen(file_name, modes);
  if (f == NULL) {
    fprintf(stderr, "File %s does not exists!\n", file_name);
    exit(EXIT_FAILURE);
  }

  return f;
}

Record *read_from_file(FILE *input_file, int *rows) {
  char *buffer = malloc(ROW_LENGTH);
  Record *records = malloc(sizeof(Record));
  fseek(input_file,0,SEEK_SET);
  while (fgets(buffer, ROW_LENGTH, input_file)) {
    records = realloc(records, sizeof(Record) * (*rows + 1));
    char *field = strtok(buffer, ",");
    records[*rows].id = atoi(field);

    field = strtok(NULL, ",");
    records[*rows].string_field = malloc(sizeof(field));
    strcpy(records[*rows].string_field, field);

    field = strtok(NULL, ",");
    records[*rows].int_field = atoi(field);

    field = strtok(NULL, ",");
    records[*rows].double_field = strtod(field, NULL);
    *rows += 1;
  }
  free(buffer);

  return records;
}

void *write_output_file(FILE *output_file, Record *records, int rows) {
  for (int i = 0; i < rows; i++) {
    fprintf(output_file, "%d,%s,%d,%lf\n", records[i].id,
            records[i].string_field, records[i].int_field,
            records[i].double_field);
  }
}

void free_memory(Record *records, int rows) {
  for (int i = 0; i < rows; i++) {
    free(records[i].string_field);
  }
  free(records);
}

void sort_records(FILE *infile, FILE *outfile, size_t k, size_t field) {
  printf("Reading file...\n");
  int rows = 0;
  Record* records = read_from_file(infile, &rows);
  fclose(infile);
  printf("Sorting...\n");
  clock_t begin = clock();
  switch (field){
    case 1:
      merge_binary_insertion_sort((void *)records, 0, rows-1, sizeof(Record), k, record_string_field_comparator);
      break;
    case 2:
      merge_binary_insertion_sort((void *)records, 0, rows-1, sizeof(Record), k, record_int_field_comparator);
      break;
    case 3:
      merge_binary_insertion_sort((void *)records, 0, rows-1, sizeof(Record), k, record_double_field_comparator);
      break;
    default:
      printf("The sorting type wasn't selected.\n");
      free_memory(records, rows);
      fclose(outfile);
      exit(EXIT_FAILURE);
      break;
  }
  clock_t end = clock();
  printf("Sorting ended in %f\n", (double)(end - begin) / CLOCKS_PER_SEC);
  printf("Writing result on file...\n");
  write_output_file(outfile, records, rows);
  fclose(outfile);
  printf("File written\n");
  free_memory(records, rows);
}

int check_args(int argc, char const *argv[]) {
  if(strcmp(argv[1], "--help") == 0) {
    printf("Usage: array_sort_main [input_file] [output_file] [k] [field]\n");
    printf("k has to be a number, field can be either 1(String), 2(int) or 3(double)\n");
    return 0;
  }
  else if(argc != 5){
    printf("Number of arguments is incorrect. Use --help for manual.\n");
    return 0;
  }
  return 1;
}