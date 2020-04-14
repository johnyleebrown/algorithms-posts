import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 862
 *
 * ======
 *
 * Task.
 *
 * Return the length of the shortest, non-empty, contiguous subarray of A with sum at least K.
 *
 * If there is no non-empty subarray with sum at least K, return -1.
 *
 * ======
 *
 * Source: Leetcode
 */
public class ShortestSubarrayWithSumAtLeastK
{
	class Solution
	{
		public int shortestSubarray(int[] A, int K)
		{
			IMQ q = new IMQ(K);
			q.push(new Item(0, -1));
			for (int i = 0; i < A.length; i++)
			{
				// rewriting array with prefix sums
				A[i] = i > 0 ? A[i] + A[i - 1] : A[0];
				q.push(new Item(A[i], i));
			}

			return q.min != Integer.MAX_VALUE ? q.min : -1;
		}

		// increasing monotone queue
		private class IMQ
		{
			private Deque<Item> q = new ArrayDeque<>();
			private int min = Integer.MAX_VALUE;
			private int K;

			public IMQ(int K)
			{
				this.K = K;
			}

			private void push(Item newItem)
			{
				while (!q.isEmpty() && newItem.val < q.peekLast().val)
				{
					q.removeLast();
				}

				// sliding window (minimum) part - bigger subarray satisfies our condition
				// that's why we can short it (move left pointer)
				// prefixSum[j] - prefixSum[i] >= K => prefixSum[i] <= prefixSum[j] - K
				// while pre[r] - pre[l] >= k
				while (!q.isEmpty() && newItem.val - q.peekFirst().val >= K)
				{
					min = Math.min(min, newItem.ind - q.peekFirst().ind);
					q.removeFirst();
				}

				q.addLast(newItem);
			}
		}

		private class Item
		{
			int val, ind;

			Item(int val, int ind)
			{
				this.val = val;
				this.ind = ind;
			}
		}
	}
}
