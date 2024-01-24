#include "skip_list.h"

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <time.h>

#include "../lib/unity.h"

int int_comparator(const void* val1, const void *val2) {
    return *(int*)val1 - *(int*)val2;
}

SkipList *list;

static void create_new_skiplist() {
  new_skiplist(&list, 15, int_comparator);
}

static void clear_all() {
  clear_skiplist(&list);
}

static void test_random_level() {
  int level = random_level(15);
  TEST_ASSERT_EQUAL_INT(1, level >= 1 && level <= 15);
}

static void test_insert_skip_list() {
  int n = 1;
  insert_skip_list(list, &n, sizeof(int));
  TEST_ASSERT_EQUAL_INT(1, *(int *)(list->heads[0]->item));
}

static void test_search_skip_list() {
  int n = 1;
  TEST_ASSERT_EQUAL_INT(1, *(int *)(search_skip_list(list, &n)));
}

static void test_search_skip_list_not_found() {
  int b = 3;
  TEST_ASSERT_NULL(search_skip_list(list, (void *)&b));
}

int main(int argc, char const *argv[]) {
  srand(time(NULL));
  create_new_skiplist();
  UNITY_BEGIN();
  RUN_TEST(test_random_level);
  RUN_TEST(test_insert_skip_list);
  RUN_TEST(test_search_skip_list);
  RUN_TEST(test_search_skip_list_not_found);
  UNITY_END();
  clear_all();
  return 0;
}
