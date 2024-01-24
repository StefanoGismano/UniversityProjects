package queue;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class PriorityQueue<E extends Comparable<E>> {

  private ArrayList<E> elements;
  private Comparator<E> comparator;

  public PriorityQueue() {
    elements = new ArrayList<>();
  }

  public PriorityQueue(Comparator<E> comparator) {
    this.comparator = comparator;
    elements = new ArrayList<>();
  }

  public boolean empty() {
    return elements.isEmpty();
  }

  public boolean push(E newElement) {
    elements.add(newElement);
    siftUp(elements.size() - 1);
    return true;
  }

  public boolean contains(E e) {
    return elements.contains(e);
  }

  public E top() {
    if (empty()) {
      throw new NoSuchElementException("Priority queue is empty");
    }
    return elements.get(0);
  }

  public void pop() {
    if (empty()) {
      throw new NoSuchElementException("Priority queue is empty");
    }
    int lastIndex = elements.size() - 1;
    elements.set(0, elements.get(lastIndex));
    elements.remove(lastIndex);
    siftDown(0);
  }

  public boolean remove(E e) {
    if (contains(e)) {
      int index = elements.indexOf(e);
      int lastIndex = elements.size() - 1;
      elements.set(index, elements.get(lastIndex));
      elements.remove(lastIndex);
      siftUp(index);
      siftDown(index);
      return true;
    }
    return false;
  }

  private void siftUp(int index) {
    while (index > 0) {
      int parentIndex = (index - 1) / 2;
      if (compare(elements.get(index), elements.get(parentIndex)) <= 0) {
        break;
      }
      swap(index, parentIndex);
      index = parentIndex;
    }
  }

  private void siftDown(int index) {
    int size = elements.size();
    while (true) {
      int leftChildIndex = 2 * index + 1;
      int rightChildIndex = 2 * index + 2;
      int largestIndex = index;

      if (leftChildIndex < size && compare(elements.get(leftChildIndex), elements.get(largestIndex)) > 0) {
        largestIndex = leftChildIndex;
      }

      if (rightChildIndex < size && compare(elements.get(rightChildIndex), elements.get(largestIndex)) > 0) {
        largestIndex = rightChildIndex;
      }

      if (largestIndex == index) {
        break;
      }

      swap(index, largestIndex);
      index = largestIndex;
    }
  }

  private void swap(int i, int j) {
    E temp = elements.get(i);
    elements.set(i, elements.get(j));
    elements.set(j, temp);
  }

  public int size() {
    return elements.size();
  }

  private int compare(E a, E b) {
    if (comparator != null) {
      return comparator.compare(a, b);
    } else {
      return a.compareTo(b);
    }
  }

}