#include "skip_list.h"
#include <stdlib.h>
#include <string.h>

void new_skiplist(SkipList **list, size_t max_height, int (*compar)(const void*, const void*)){
  
  (*list) = malloc(sizeof(SkipList));
  (*list)->max_level = 0;
  (*list)->max_height = max_height;
  (*list)->compare = compar;
  (*list)->heads = malloc(sizeof(Node) * max_height);
}

void clear_skiplist(SkipList **list){
  free_node((*list)->heads[0]);
  free((*list)->heads);
  free((*list));
}

void free_node(Node *node) {
  Node *temp;
  while (node != NULL) {
    temp = node;
    node = node->next[0];
    free(temp->item);
    free(temp->next);
    free(temp);
  }
}

int random_level(int max_height) {
  int level = 1;
  double temp = (double)rand() / (double)RAND_MAX;
  while (temp < 0.5 && level < max_height) {
    level += 1;
    temp = (double)rand() / (double)RAND_MAX;
  }
  return level;
}

Node *create_node(void *item, int level, size_t item_size) {
  Node *new_node = malloc(sizeof(Node));
  new_node->item = malloc(item_size);
  memcpy(new_node->item, item, item_size);
  new_node->next = calloc(sizeof(Node), level);
  new_node->size = level;
  return new_node;
}

void insert_skip_list(SkipList *list, void *new_item, size_t item_size) {
  Node *new = create_node(new_item, random_level(list->max_height), item_size);

  if(new->size > list->max_level) {
    list->max_level = new->size;
  }

  Node **temp = list->heads;
  for(int i = list->max_level - 1; i >= 0; i --) {
    if(temp[i] == NULL || list->compare(new_item, temp[i]->item) < 0) {
      if(i < new->size) {
        new->next[i] = temp[i];
        temp[i] = new;
      }
    } else {
      temp = temp[i]->next;
      i ++;
    }
  }
}

void print_skiplist(SkipList *list) {
  for(int i = 0; i < list->max_level; i ++) {
    printf("Level %d : ", i + 1);

    Node *temp = list->heads[i];
    while(temp != NULL) {
      printf("%d ", *(int*)(temp->item));
      temp = temp->next[0];
    }
    printf("\n");
  }
  
}

const void *search_skip_list(SkipList *list, void *item) {
  Node **temp = list->heads;

  for(int i = list->max_level - 1; i >= 0; i --) {
    while(temp[i]->next[i] != NULL && list->compare(temp[i]->next[i]->item, item) <= 0) {
      temp = temp[i]->next;
    }
  }

  if(list->compare(temp[0]->item, item) == 0) {
    return temp[0]->item;
  }

  return NULL;
}