// David Simmons 8/2/2016
// This was made to practice recreating sorting algorithms from my own memory.

package sorter;

public class Sorter {

	public static void main(String[] args) {
		int[] list = resetList();
		System.out.println("Original: ");
		for(int i : list) {
			System.out.print(i + ",");
		}	
		System.out.println();
		System.out.println();
		
		list = resetList();
		selectionSort(list);
		System.out.println("Selection sort: ");
		for(int i : list) {
			System.out.print(i + ",");
		}	
		System.out.println();
		
		list = resetList();
		quicksort(list);
		System.out.println("Quick sort: ");
		for(int i : list) {
			System.out.print(i + ",");
		}	
		System.out.println();
		
		list = resetList();
		list = mergesort(list);
		System.out.println("Merge sort: ");
		for(int i : list) {
			System.out.print(i + ",");
		}	
		System.out.println();
		
		list = resetList();
		System.out.println("Tree Sort: ");
		treeSortPrint(list);
		System.out.println();
		
		list = resetList();
		list = heapsort(list);
		System.out.println("Heap sort: ");
		for(int i : list) {
			System.out.print(i + ",");
		}	
		System.out.println();
	}
	
	public static int[] resetList() {
		return new int[] {3,1,4,1,5,9,2,6,5,3,5,8,9,7,9,3,2,3};
	}

	// ----------- Selection Sort ---------------------------------------------
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
	
	// ----------- Quick Sort ---------------------------------------------
	public static void quicksort(int[] list) {
		quicksort(list, 0, list.length -1);
	}

	// Quick sort list between i and j.  This could use a random index instead
	// of j to help prevent the O(n^2) behavior.
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

	// ----------- Merge Sort ---------------------------------------------
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
			return combine(split(result, i, i + ((j - i) / 2) + 1), 
					split(result, i + ((j - i) / 2) + 1, j));
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
	
	// ----------- Tree Sort ---------------------------------------------
	public static class Node {
		int data;
		Node left;
		Node right;

		public Node(int data) {
			this.data = data;
		}
	}

	// Create a binary search tree from the list and do an in order traversal
	// to print the elements in ascending order
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
	
	// ----------- Heap Sort ---------------------------------------------
	public static int[] heapsort(int[] list) {
		// Make max heap
		int[] result = heapify(list);
		
		// Get each element in descending order with side effect of 
		// sorting the list ascending
		for (int i = list.length - 1; i >= 0; i--) {
			get(result, i);
		}
		
		return result;
	}

	// Make a new heap and add all of the inputs to it
	public static int[] heapify(int[] list) {
		int[] result = new int[list.length];
		
		for(int i = 0; i < list.length; i++) {
			add(result, i, list[i]);
		}

		return result;
	}

	// Add and element to the list as if it were a heap by putting the new
	// element at the bottom and bubbling it up
	public static void add(int[] list, int i, int num) {
		int parent = 0;	
		list[i] = num;
		
		if (i != 0) {
			parent = getParent(list, i);
			while(i > 0 && list[parent] < num) {
				swap(list, parent, i);
				i = parent;
				if (i != 0) {
					parent = getParent(list, i);
				}
			}
		}
	}

	// Get the max element from list as if it were a max heap then 
	// restore the heap by swapping a bottom element to the top
	// and bubbling it down
	public static int get(int[] list, int i) {
		int result = list[0];
		int num = -1;
		int max = -1;
		int j = 0;
		boolean swapMade = true;

		if (i > 0) {
			swap(list, 0, i);
			num = list[0];
			j = 0;

			while (swapMade == true && j < i) {
				swapMade = false;
				if (hasLeft(i, j) && hasRight(i, j)) {
					max = maxIndex(list, getLeft(j), getRight(j));		
				} else if (hasLeft(i, j)) {
					max = getLeft(j);
				} else {
					max = j;
				}
		
				if (max != j && list[max] > list[j]) {
					swap(list, max, j);
					swapMade = true;
					j = max;
				}
			}
		}
		
		return result;
	}

	// Return the index i or j corresponding to the max element in list
	public static int maxIndex(int[] list, int i, int j) {
		return (list[i] >= list[j])? i : j;
	}

	// Return if there is a left child of the element at index i within the bound
	public static boolean hasLeft(int bound, int i) {
		return (i * 2 + 1) >= 0 && (i * 2 + 1) < bound;
	}

	// Return if there is a right child of the element at index i within the bound
	public static boolean hasRight(int bound, int i) {
		return (i * 2 + 2) >= 0 && (i * 2 + 2) < bound;
	}

	// Return the index of the left child of the element at index i
	public static int getLeft(int i) {
		return i * 2 + 1;
	}

	// Return the index of the right child of the element at index i
	public static int getRight(int i) {
		return i * 2 + 2;
	}

	public static void swap(int[] list, int i, int j) {
		int temp = list[i];
		list[i] = list[j];
		list[j] = temp;
	}

	// Return the index of the parent of the element at index i in list
	public static int getParent(int[] list, int i) {
		return (i-1)/2;
	}
}