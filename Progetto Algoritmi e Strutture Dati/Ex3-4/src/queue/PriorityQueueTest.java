package queue;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class PriorityQueueTest {

  private PriorityQueue<Integer> pq;

  @Before
  public void setUp() {
    pq = new PriorityQueue<>();
  }

  @Test
  public void testPushAndTop() {
    pq.push(5);
    pq.push(2);
    pq.push(8);
    assertEquals(Integer.valueOf(8), pq.top());
  }

  @Test
  public void testPop() {
    pq.push(5);
    pq.push(2);
    pq.push(8);
    pq.pop();
    assertEquals(Integer.valueOf(5), pq.top());
  }

  @Test
  public void testRemove() {
    pq.push(5);
    pq.push(2);
    pq.push(8);
    assertTrue(pq.remove(2));
    assertFalse(pq.contains(2));
  }

  @Test
  public void testEmpty() {
    assertTrue(pq.empty());
    pq.push(5);
    assertFalse(pq.empty());
  }
}
