import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 84
 *
 * ======
 *
 * Task.
 *
 * Given n non-negative integers representing the histogram's bar height where
 * the width of each bar is 1, find the area of largest rectangle in the
 * histogram.
 *
 * ======
 *
 * Source: Leetcode
 */
public class LargestRectangleInHistogram {
    /**
     * 2 mq's solution.
     */
    public class Solution0 {
        public int largestRectangleArea(int[] heights) {
            int n = heights.length;

            // default nearest value for increasing mq from left to right is -1
            MQ leftRight = new MQ(-1, n);
            for (int i = 0; i < n; i++) {
                leftRight.push(new Item(heights[i], i));
            }

            // default nearest value for increasing mq from right to left is n
            MQ rightLeft = new MQ(n, n);
            for (int i = n - 1; i >= 0; i--) {
                rightLeft.push(new Item(heights[i], i));
            }

            int maxArea = 0;
            for (int i = 0; i < n; i++) {
                int width = rightLeft.nearest[i] - leftRight.nearest[i] - 1;
                int currentArea = width * heights[i];
                maxArea = Math.max(maxArea, currentArea);
            }

            return maxArea;
        }

        private class MQ {
            Deque<Item> queue;
            int defaultNearestValue;
            int[] nearest; // nearest values indexes

            public MQ(int defaultNearestValue, int n) {
                queue = new ArrayDeque<>();
                nearest = new int[n];
                this.defaultNearestValue = defaultNearestValue;
            }

            public void push(Item newItem) {
                while (!queue.isEmpty() && newItem.val <= queue.peekLast().val) {
                    queue.removeLast();
                }

                if (queue.isEmpty()) {
                    nearest[newItem.ind] = defaultNearestValue;
                }
                else {
                    nearest[newItem.ind] = queue.peekLast().ind;
                }

                queue.addLast(newItem);
            }
        }

        private class Item {
            int val, ind;

            Item(int val, int ind) {
                this.val = val;
                this.ind = ind;
            }
        }
    }

    /**
     * Using 1 mq. The biggest area involving a height at i is between nearest
     * smallest value on the left, and nearest smallest value on the right.
     */
    public static class Solution1 {
        public int largestRectangleArea(int[] heights) {
            MQ q = new MQ();
            for (int i = 0; i <= heights.length; i++) {
                int currentValue = i != heights.length ? heights[i] : 0;
                q.push(new Item(currentValue, i));
            }
            return q.maxArea;
        }

        private class MQ {
            Deque<Item> queue;
            int maxArea;

            private MQ() {
                queue = new ArrayDeque<>();
            }

            public void push(Item newItem) {
                while (!queue.isEmpty() && newItem.val < queue.peekLast().val) {
                    Item upperBoundary = queue.removeLast();

                    int leftBoundaryIndex = !queue.isEmpty() ? queue.peekLast().ind : -1;
                    int width = newItem.ind - leftBoundaryIndex - 1;
                    int currentArea = width * upperBoundary.val;

                    maxArea = Math.max(maxArea, currentArea);
                }

                queue.addLast(newItem);
            }
        }

        private class Item {
            int val, ind;

            Item(int val, int ind) {
                this.val = val;
                this.ind = ind;
            }
        }
    }

    /**
     * Using arrays of nearest smaller values. The biggest area involving a
     * height at i is between nearest smallest value on the left, and * nearest
     * smallest value on the right.
     */
    static class Solution2 {
        public int largestRectangleArea(int[] heights) {
            int n = heights.length;

            int[] leftToRightNearestValuesLessThan = new int[n];
            for (int i = 0; i < n; i++) {
                int j = i - 1;
                while (j >= 0 && heights[i] <= heights[j]) {
                    j = leftToRightNearestValuesLessThan[j];
                }
                leftToRightNearestValuesLessThan[i] = j;
            }

            int[] rightToLeftNearestValuesLessThan = new int[n];
            for (int i = n - 1; i >= 0; i--) {
                int j = i + 1;
                while (j < n && heights[i] <= heights[j]) {
                    j = rightToLeftNearestValuesLessThan[j];
                }
                rightToLeftNearestValuesLessThan[i] = j;
            }

            int result = 0;
            for (int i = 0; i < n; i++) {
                int area = heights[i] * (rightToLeftNearestValuesLessThan[i] - leftToRightNearestValuesLessThan[i] - 1);
                result = Math.max(result, area);
            }

            return result;
        }
    }
}
