import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 456
 *
 * ======
 *
 * Task.
 *
 * Given a sequence of n integers a1, a2, ..., an, a 132 pattern is a
 * subsequence ai, aj, ak such that i < j < k and ai < ak < aj. Design an
 * algorithm that takes a list of n numbers as input and checks whether there is
 * a 132 pattern in the list.
 *
 * ======
 *
 * Source: Leetcode
 */
public class A132Pattern {
    public static class Solution {
        public boolean find132pattern(int[] a) {
            MQ queue = new MQ();

            for (int i = a.length - 1; i >= 0; i--) {
                if (queue.push(a[i])) {
                    return true;
                }
            }

            return false;
        }

        private class MQ {
            private Deque<Integer> queue;
            private int prev;

            private MQ() {
                queue = new ArrayDeque<>();
                prev = Integer.MIN_VALUE;
            }

            public boolean push(int cur) {
                if (prev > cur) {
                    return true;
                }

                while (!queue.isEmpty() && queue.peekLast() < cur) {
                    prev = queue.peekLast();
                    queue.removeLast();
                }

                queue.addLast(cur);
                return false;
            }
        }
    }
}
