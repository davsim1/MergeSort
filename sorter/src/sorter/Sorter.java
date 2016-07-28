// David Simmons 7/28/2016
// I made this recursive version of merge sort for practice without looking at any other resources.

package sorter;

public class Sorter {

	public static void main(String[] args) {
		int[] A = { 3, 1, 4, 15, 9, 2, 6, 5, 35, 8, 97, 93, 23, 84, 62, 64, 33 };
		int[] B = sort(A);

		// Print list in sorted order
		System.out.println("Sorted:");
		for (int i : B) {
			System.out.println(i);
		}
	}

	// Call this to sort an array of ints.
	public static int[] sort(int[] list) {
		int[] result = new int[list.length];
		int i = 0;

		// Copy original into result to prevent clobbering original
		for (int x : list) {
			result[i++] = x;
		}

		result = split(result, 0, result.length);

		return result;
	}

	// Returns the sorted part between i and j (excluding j) of result after
	// splitting, swapping, and combining.
	private static int[] split(int[] result, int i, int j) {
		if (j - i > 2) {
			// return the combination of each sorted half between i and j
			return combine(split(result, i, i + ((j - i) / 2) + 1), split(result, i + ((j - i) / 2) + 1, j));
		} else {
			int[] res;
			// If there is one element left, return it
			if (j - i == 1) {
				res = new int[1];
				res[0] = result[i];
				return res;
			} else {
				// If there are two elements left, return the two in sorted
				// order
				res = new int[2];
				if (result[i] <= result[j - 1]) {
					res[0] = result[i];
					res[1] = result[j - 1];
				} else {
					res[1] = result[i];
					res[0] = result[j - 1];
				}
				return res;
			}
		}
	}

	// Performs merge sort's combination routine to sift together arr1 and arr2
	// into a sorted array.
	private static int[] combine(int[] arr1, int[] arr2) {
		int[] result = new int[arr1.length + arr2.length];
		int i = 0;
		int j = 0;
		int k = 0;
		int t = 0;
		int[] curr = arr1;
		int[] other = arr2;
		int[] temp;

		// While there is more to combine
		while (k < result.length) {
			// If current's index is beyond current's bounds, swap current and
			// other
			if (i >= curr.length) {
				temp = curr;
				curr = other;
				other = temp;
				t = i;
				i = j;
				j = t;
			}
			// If there is nothing left of the other array, skip the comparison
			// and put the rest of the current array into the result
			if (j >= other.length) {
				for (int m = i; m < curr.length; m++) {
					result[k++] = curr[m];
				}
			} else {
				// Alternate current and other to compare elements between the
				// two to put values in ascending order into result
				if (curr[i] <= other[j]) {
					result[k++] = curr[i];
					i++;
				} else {
					temp = curr;
					curr = other;
					other = temp;
					t = i;
					i = j;
					j = t;
				}
			}
		}

		return result;
	}
}