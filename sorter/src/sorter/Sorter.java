// David Simmons 7/28/2016
// Made to practice making sorting algorithms from my own memory.

package sorter;

public class Sorter {

	public static void main(String[] args) {
		int[] list = {3,1,4,1,5,9,2,6,5,3,5,8,9,7,9,3,2,3};
		System.out.println("Original: ");
		for(int i : list) {
			System.out.print(i + ",");
		}	
		System.out.println();
		
		list = new int[] {3,1,4,1,5,9,2,6,5,3,5,8,9,7,9,3,2,3};
		selectionSort(list);
		System.out.println("Selection sort: ");
		for(int i : list) {
			System.out.print(i + ",");
		}	
		System.out.println();
		
		list = new int[] {3,1,4,1,5,9,2,6,5,3,5,8,9,7,9,3,2,3};
		quicksort(list);
		System.out.println("Quicksort: ");
		for(int i : list) {
			System.out.print(i + ",");
		}	
		System.out.println();
		
		list = new int[] {3,1,4,1,5,9,2,6,5,3,5,8,9,7,9,3,2,3};
		list = mergesort(list);
		System.out.println("Mergesort: ");
		for(int i : list) {
			System.out.print(i + ",");
		}	
		System.out.println();
		
		list = new int[] {3,1,4,1,5,9,2,6,5,3,5,8,9,7,9,3,2,3};
		System.out.println("Tree Sort: ");
		treeSortPrint(list);
		
	}

	// Selection
	public static void selectionSort(int[] input) {
		int minIndex = 0;
		int temp = -1;

		for (int i = 0; i < input.length; i++) {
			minIndex = i;
			for (int j = i; j < input.length; j++) {
				if (input[j] < input[minIndex]) {
					minIndex = j;
				}
			}
			temp = input[i];
			input[i] = input[minIndex];
			input[minIndex] = temp;
		}
		
	}
	
	// Quick
	public static void quicksort(int[] list) {
		quicksort(list, 0, list.length -1);
	}

	private static void quicksort(int[] list, int i, int j) {
		if (j < list.length && (j - i) >= 1) {
			int border = i;
			int temp;
			for (int k = i; k < j; k++) {
				if (list[k] <= list[j]) {
					temp = list[border];
					list[border] = list[k];
					list[k] = temp;
					border++;		
				}
			}
		
			temp = list[border];
			list[border] = list[j];
			list[j] = temp;
			quicksort(list, i, border - 1);
			quicksort(list, border + 1, j);
		}
	}

	// Merge
	public static int[] mergesort(int[] list) {
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
	
	// Tree 
	public static class Node {
		int data;
		Node left;
		Node right;

		public Node(int data) {
			this.data = data;
		}
	}

	public static void treeSortPrint(int[] list) {
		Node tree = null;

		for (int i : list) {
			tree = add(tree, i);
		}

		printInOrder(tree);
	}

	public static Node add(Node tree, int input) {
		if (tree == null) {
			return new Node(input);
		} else if (input <= tree.data) {
			tree.left = add(tree.left, input);
		} else {
			tree.right = add(tree.right, input);
		}

		return tree;
	}

	public static void printInOrder(Node tree) {
		if (tree != null) {
			printInOrder(tree.left);
			System.out.print(tree.data + ",");
			printInOrder(tree.right);
		}
	}
}