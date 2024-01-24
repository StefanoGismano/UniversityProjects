#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int binary_search(void* array, void* item, int low, int high,
                  size_t size, int (*compar)(const void*, const void*)) {
  if (high <= low)
    return compar(item, array + low * size) > 0 ? low + 1 : low;

  int mid = (low + high) / 2;

  if (compar(item, array + mid * size) == 0) return mid + 1;

  if (compar(item, array + mid * size) > 0)
    return binary_search(array, item, mid + 1, high, size, compar);

  return binary_search(array, item, low, mid - 1, size, compar);
}

void binary_insertion_sort(void* array, int nitems, size_t size, int (*compar)(const void*, const void*)){
  int index;
  int j;
  void* selected = malloc(size);
  for (int i = 1; i < nitems; ++i) {
    j = i - 1;
    memcpy(selected, array + i * size, size);

    index = binary_search(array, selected, 0, j, size, compar);

    while (j >= index) {
      memcpy(array + (j + 1) * size, array + j * size,
             size);
      j--;
    }
    memcpy(array + (j + 1) * size, selected, size);
  }

  free(selected);
}

void merge(void *array, int left, int mid, int right, size_t size, int (*compar)(const void *, const void *)) {
  int i,j,k;
  int n1 = mid - left + 1;
  int n2 = right - mid;
  void *left_array = malloc(size * n1);
  void *right_array = malloc(size * n2);

  for(i=0; i<n1; i++){
    memcpy(left_array + i*size, array + (left+i)*size, size);
  }
  for(j=0; j<n2; j++) {
    memcpy(right_array + j*size, array + (mid+1+j)*size, size);
  }

  i=0; j=0; k=left;

  while(i<n1 && j<n2){
    if( compar(left_array + i*size, right_array + j*size) <=0 ) {
      memcpy(array + k*size, left_array + i*size, size);
      i++;
    }
    else {
      memcpy(array + k*size, right_array + j*size, size);
      j++;
    }
    k++;
  }

  while(i < n1){
    memcpy(array + k*size, left_array + i*size, size);
    i++;
    k++;
  }

  while(j < n2){
    memcpy(array + k*size, right_array + j*size, size);
    j++;
    k++;
  }

  free(left_array);
  free(right_array);
}

void merge_sort(void *array, int left, int right, size_t size, int (*compar)(const void *, const void *)) {
  if(left < right){
    int mid = left + (right - left) / 2;
    merge_sort(array, left, mid, size, compar);
    merge_sort(array, mid + 1, right, size, compar);
    merge(array, left, mid, right, size, compar);
  }
}

void merge_binary_insertion_sort(void *base, int left, int right, size_t size, size_t k, int (*compar)(const void*, const void*)) {
  int mid = left + (right - left) / 2;
  if (right-left+1 <= k) {
    binary_insertion_sort(base+(left*size), right-left+1, size, compar);
  }
  else if(left < right){
    merge_binary_insertion_sort(base, left, mid, size, k, compar);
    merge_binary_insertion_sort(base, mid+1, right, size, k, compar);
    merge(base, left, mid, right, size, compar);
  }
}