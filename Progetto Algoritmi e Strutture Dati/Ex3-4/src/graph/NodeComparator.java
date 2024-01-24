package graph;

import java.util.Comparator;

/**
 * NodeComparator used to compare two nodes with respect to their distance from
 * source.
 *
 */
public class NodeComparator<V extends Comparable<V>, L> implements Comparator<Node<V, L>> {
  @Override
  public int compare(Node<V, L> o1, Node<V, L> o2) {
    return o1.getDistanceFromSource().compareTo(o2.getDistanceFromSource());
  }

}
