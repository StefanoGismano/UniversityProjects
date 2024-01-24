#include "array_sort.h"

#include <stdio.h>

#include "../lib/unity.h"

int int_comparator(const void *var1, const void *var2) {
  return (*(int *)var1) - (*(int *)var2);
}

static void test_binary_search() {
  int array[] = {2, 5, 1, 6, 7, 3, 4};
  TEST_ASSERT_EQUAL_INT(1, binary_search((void *)array, (void *)&array[5], 0, 6, sizeof(int), int_comparator));
}

static void test_binary_insertion_sort() {
  int array[] = {2, 5, 1, 6, 7, 3, 4};
  int expected[] = {1, 2, 3, 4, 5, 6, 7};
  binary_insertion_sort(array, 7, sizeof(int), int_comparator);
  TEST_ASSERT_EQUAL_INT_ARRAY(expected, array, 7);
}

static void test_binary_insertion_sort_null() {
  int *array = NULL;
  binary_insertion_sort((void *)array, 0, sizeof(int), int_comparator);
  TEST_ASSERT_NULL(array);
}

static void test_merge_sort() {
  int array[] = {2, 5, 1, 6, 7, 3, 4};
  int expected[] = {1, 2, 3, 4, 5, 6, 7};
  merge_sort(array, 0, 6, sizeof(int), int_comparator);
  TEST_ASSERT_EQUAL_INT_ARRAY(expected, array, 7);
}

static void test_merge_sort_null() {
  int *array = NULL;
  merge_sort((void *)array, 0, 0, sizeof(int), int_comparator);
  TEST_ASSERT_NULL(array);
}

static void test_merge_binary_insertion_sort() {
  int array[] = {2, 5, 1, 6, 7, 3, 4};
  int expected[] = {1, 2, 3, 4, 5, 6, 7};
  merge_binary_insertion_sort(array, 0, 6, sizeof(int), 2 , int_comparator);
  TEST_ASSERT_EQUAL_INT_ARRAY(expected, array, 7);
}

static void test_merge_binary_insertion_sort_k0() {
  int array[] = {2, 5, 1, 6, 7, 3, 4};
  int expected[] = {1, 2, 3, 4, 5, 6, 7};
  merge_binary_insertion_sort(array, 0, 6, sizeof(int), 0 , int_comparator);
  TEST_ASSERT_EQUAL_INT_ARRAY(expected, array, 7);
}

static void test_merge_binary_insertion_sort_kbig() {
  int array[] = {2, 5, 1, 6, 7, 3, 4};
  int expected[] = {1, 2, 3, 4, 5, 6, 7};
  merge_binary_insertion_sort(array, 0, 6, sizeof(int), 10 , int_comparator);
  TEST_ASSERT_EQUAL_INT_ARRAY(expected, array, 7);
}

static void test_merge_binary_insertion_sort_null() {
  int *array = NULL;
  merge_binary_insertion_sort(array, 0, 0, sizeof(int), 0 , int_comparator);
  TEST_ASSERT_NULL(array);
}

int main(int argc, char const *argv[])
{
  UNITY_BEGIN();
  RUN_TEST(test_binary_search);
  RUN_TEST(test_binary_insertion_sort);
  RUN_TEST(test_binary_insertion_sort_null);
  RUN_TEST(test_merge_sort);
  RUN_TEST(test_merge_sort_null);
  RUN_TEST(test_merge_binary_insertion_sort);
  RUN_TEST(test_merge_binary_insertion_sort_k0);
  RUN_TEST(test_merge_binary_insertion_sort_kbig);
  RUN_TEST(test_merge_binary_insertion_sort_null);
  return UNITY_END();
}
