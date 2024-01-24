#include <stdio.h>
#ifndef _SKIP_LIST_H
#define _SKIP_LIST_H


typedef struct _Node Node;
typedef struct _SkipList SkipList;

typedef struct _SkipList {
  Node **heads;
  size_t max_level;
  size_t max_height;
  int (*compare)(const void*, const void*);
} SkipList;

typedef struct _Node {
  Node **next;
  size_t size;
  void *item;
} Node;


/**
 * It creates a new skip list
 * and returns a pointer to it
 * @param list memory location to save the list
 * @param max_height maximum height of the list
 * @param compar comparator function
 */
void new_skiplist(SkipList **list, size_t max_height, int (*compar)(const void*, const void*));


/**
 *It prints the contents of the skiplist 
 *
 * @param list The skiplist to print
 */
void print_skiplist(SkipList *list);

/**
 * It generates a random number between 1 and max_height.
 * 
 * @param max_height maximum height
 *
 * @return A random level between 1 and max_height.
 */
int random_level(int max_height);

/**
 * It creates a new node with a given level and copies the given item into the
 * node
 *
 * @param item The item to be stored in the node
 * @param level The level of the node.
 * @param item_size The size of the item to be stored in the node.
 *
 * @return A pointer to a Node struct.
 */
Node *create_node(void *item, int level, size_t item_size);

/**
 * It inserts a new item in the skip list.
 *
 * @param list A pointer to the skip list.
 * @param new_item The item to be inserted.
 * @param item_size The size of the item to be inserted
 */
void insert_skip_list(SkipList *list, void *new_item, size_t item_size);

/**
 * Search for an item in the skip list.
 *
 * @param list A pointer to the skip list.
 * @param item The item to be searched.
 *
 * @return The pointer of item that is being searched for or NULL if it is not
 * found.
 */
const void *search_skip_list(SkipList *list, void *item);

/**
 * It frees the memory allocated for all the nodes in the skip list.
 *
 * @param node The node to be freed
 */
void free_node(Node *node);

/**
 * It frees the memory allocated for the skip list.
 *
 * @param list The skip list to be freed.
 */
void clear_skiplist(SkipList **list);
#endif