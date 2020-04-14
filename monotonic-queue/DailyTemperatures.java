import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 739
 *
 * ======
 *
 * Task.
 *
 * Given a list of daily temperatures T, return a list such that, for each day
 * in the input, tells you how many days you would have to wait until a warmer
 * temperature. If there is no future day for which this is possible, put 0
 * instead.
 *
 * For example, given the list of temperatures T = [73, 74, 75, 71, 69, 72, 76,
 * 73], your output should be [1, 1, 4, 2, 1, 1, 0, 0].
 *
 * ======
 *
 * Source: Leetcode
 */
public class DailyTemperatures
{
	class Solution
	{
		public int[] dailyTemperatures(int[] T)
		{
			DMQ q = new DMQ(T.length, 0);

			for (int i = T.length - 1; i >= 0; i--)
			{
				q.push(new Item(T[i], i));
			}

			return q.getNearestValues();
		}

		public class DMQ
		{
			private Deque<Item> q = new ArrayDeque<>();
			private int[] nearestValues;
			private int defaultValue;

			public DMQ(int size, int defaultValue)
			{
				this.nearestValues = new int[size];
				this.defaultValue = defaultValue;
			}

			public void push(Item currentItem)
			{
				while (!q.isEmpty() && currentItem.value >= q.peekLast().value)
				{
					q.removeLast();
				}

				setNearestValue(currentItem.index);

				q.addLast(currentItem);
			}

			private void setNearestValue(int currentItemIndex)
			{
				nearestValues[currentItemIndex] = defaultValue;
				if (!q.isEmpty())
				{
					nearestValues[currentItemIndex] = q.peekLast().index - currentItemIndex;
				}
			}

			public int[] getNearestValues()
			{
				return nearestValues;
			}
		}

		private class Item
		{
			int value, index;

			Item(int val, int ind)
			{
				this.value = val;
				this.index = ind;
			}
		}
	}

	class Solution2
	{
		public int[] dailyTemperatures(int[] T)
		{
			int n = T.length;
			int[] nearest = new int[n]; // nearest biggest indexes from right to left
			int[] result = new int[n];

			for (int i = n - 1; i >= 0; i--)
			{
				int j = i + 1;
				while (j < n && T[j] <= T[i])
				{
					j = nearest[j];
				}
				nearest[i] = j;

				result[i] = nearest[i] == n ? 0 : nearest[i] - i;
			}

			return result;
		}
	}
}
