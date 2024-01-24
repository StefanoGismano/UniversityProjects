#include <stdio.h>
#ifndef _ARRAY_SORT_H
#define _ARRAY_SORT_H

/**
 *
 * An algorithm that is used to find the index of an element in
 * an array. It works by splitting the array into two parts, and then
 * recursively searching the correct part of the array.
 *
 * @param array Pointer to the first element of the array to search.
 * @param item The item to search for.
 * @param low The lowest index of the array.
 * @param high The highest index of the array.
 * @param size Size of the elements of the array in bytes.
 * @param compar Function that compares two elements of the array.
 *
 * @return The index of the item in the array.
 */
int binary_search(void* array, void* item, int low, int high, size_t size, int (*compar)(const void*, const void*));

/**
 * Binary insertion sort is a sorting algorithm that use binary search
 * to find the location where an element should be inserted.
 *
 * @param array Pointer to the first element of the array to sort.
 * @param nitems Number of elements in the array.
 * @param size Size of the elements of the array in bytes.
 * @param compar Function that compares two elements of the array.
 */
void binary_insertion_sort(void* array, int nitems, size_t size, int (*compar)(const void*, const void*));

/**
 * Merge merges two subarrays of the given array
 * The first subarray is array[left...mid]
 * The second subarray is array[mid+1...right]
 * 
 * @param array The array that contains the subarrays to merge
 * @param left The start of the left subarray
 * @param right The end of the right subarray
 * @param mid The last index of the left array
 * @param size Size of the elements of the array in bytes.
 * @param compar Function that compares two elements of the array.
 */
void merge(void *array, int left, int right, int mid, size_t size, int (*compar)(const void *, const void *));

/**
 * Merge_sort implements the merge sort sorting algorithm.
 * It's based on dividing the array in multiple subarrays that are then merged together.
 * 
 * @param array Pointer to the array to sort
 * @param left Leftmost index of the array
 * @param right Rightmost index of the array
 * @param size Size of the elements of the array in bytes.
 * @param compar Function that compares two elements of the array.
 */
void merge_sort(void *array, int left, int right, size_t size, int (*compar)(const void *, const void *));

/**
 * An algorithm that combines binary sort and insertion sort.
 * It will implement one of those sorting algorithms, choosing whichever one is the most efficient
 * depending on the size of the given array.
 * 
 * @param base Pointer to the first element of the array to sort.
 * @param nitems Number of elements in the array.
 * @param size Size of the elements of the array in bytes.
 * @param k Parameter that decides which algorithm to use.
 * @param compar Function that compares two elements of the array.
 */
void merge_binary_insertion_sort(void *base, int left, int right, size_t size, size_t k, int (*compar)(const void*, const void*));


#endif