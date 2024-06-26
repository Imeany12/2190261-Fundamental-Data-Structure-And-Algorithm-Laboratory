import java.util.Arrays;

public class BSTRecursive {

	BSTNode root;
	int size;

	public BSTRecursive(BSTNode root, int size) {
		this.root = root;
		this.size = size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public void makeEmpty() {
		root = null;
		size = 0;
	}

	public Iterator findMin() {
		return findMin(root);
	}

	public Iterator findMin(BSTNode n) {
		if (n == null)
			return null;
		if (n.left == null) {
			Iterator itr = new TreeIterator(n);
			return itr;
		}
		return findMin(n.left);
	}

	public Iterator findMax() {
		return findMax(root);
	}

	public Iterator findMax(BSTNode n) {
		if (n == null)
			return null;
		if (n.right == null) {
			Iterator itr = new TreeIterator(n);
			return itr;
		}
		return findMax(n.right);
	}

	public Iterator find(int v) {
		return find(v, root);
	}

	public Iterator find(int v, BSTNode n) {
		if (n == null)
			return null;
		if (v == n.data)
			return new TreeIterator(n);
		if (v < n.data)
			return find(v, n.left);
		else
			return find(v, n.right);
	}

	public BSTNode insert(int v) {
		return insert(v, root, null);
	}

	// return the node n after v was added into the tree
	public BSTNode insert(int v, BSTNode n, BSTNode parent) {
		if (n == null) {
			n = new BSTNode(v, null, null, parent);
			size++;
		} else if (v < n.data) {
			n.left = insert(v, n.left, n);
		} else if (v > n.data) {
			n.right = insert(v, n.right, n);
		}
		return n;
	}

	public BSTNode remove(int v) {
		return remove(v, root, null);
	}

	// return the node n after v was removed from the tree
	public BSTNode remove(int v, BSTNode n, BSTNode parent) {
		if (n == null)
			; // do nothing, there is nothing to be removed
		else if (v < n.data) {
			n.left = remove(v, n.left, n);
		} else if (v > n.data) {
			n.right = remove(v, n.right, n);
		} else {
			if (n.left == null && n.right == null) {
				n.parent = null; // disconnect from above
				n = null; // disconnect from below
				size--;
			} else if (n.left != null && n.right == null) {
				BSTNode n2 = n.left;
				n2.parent = parent;
				n.parent = null; // disconnect from above
				n.left = null; // disconnect from below
				n = n2; // change to the node below
						// to prepare for connection from parent
				size--;
			} else if (n.right != null && n.left == null) {
				BSTNode n2 = n.right;
				n2.parent = parent;
				n.parent = null; // disconnect from above
				n.right = null; // disconnect from below
				n = n2; // change to the node below
						// to prepare for connection from parent
				size--;
			} else {
				TreeIterator i = (TreeIterator) findMin(n.right);
				int minInRightSubtree = i.currentNode.data;
				n.data = minInRightSubtree;
				n.right = remove(minInRightSubtree, n.right, n);
			}
		}
		return n;
	}

	private int height(BSTNode n) {
		if (n == null)
			return -1;
		return 1 + Math.max(height(n.left), height(n.right));
	}

	/**************************************************************************************************/
	// Edit only the following methods.
	/**************************************************************************************************/

	/**
	 * Internal method to find the number of nodes in a given subtree using a
	 * recursive method.
	 * 
	 * @param n the root of a given subtree
	 * @param v the threshold
	 * @return the number of nodes in which the data is less than v in the subtree
	 */
	public int numNodesLessThan(BSTNode n, int v) {
		int numberOfNode = 0;
		if (n == null) {
			return 0;
		}
		if (n.data < v) {
			numberOfNode += numNodesLessThan(n.left, v) + numNodesLessThan(n.right, v) + 1;
		} else {
			numberOfNode += numNodesLessThan(n.left, v) + numNodesLessThan(n.right, v);
		}
		return numberOfNode;
	}

	/**
	 * Internal method to count the number of leaves in a given subtree
	 * (recursively).
	 * 
	 * @param n root node of a given subtree
	 * @param v the threshold
	 * @return number of leaves in which the data is less than v in the subtree,
	 */
	public int numLeavesLessThan(BSTNode n, int v) {
		int numberOfLeave = 0;
		// Base case this node not exist
		if (n == null) {
			return 0;
		}

		// This node is the leave node
		if (n.left == null && n.right == null) {
			if (n.data < v) {
				return 1;
			} else {
				return 0;
			}
		}

		// This node is not leave node
		if (n.left != null || n.right != null) {
			numberOfLeave += numLeavesLessThan(n.left, v) + numLeavesLessThan(n.right, v);
		}
		return numberOfLeave;
	}

	/**
	 * In order insert all nodes from a BST that has n as the root node.
	 * 
	 * @param n the node that roots the subtree.
	 * 
	 */
	public void insertInOrder(BSTNode n) {
		if (n == null) {
			return;
		}
		this.insert(n.data);
		if (n.left != null) {
			insertInOrder(n.left);
		}
		if (n.right != null) {
			insertInOrder(n.right);
		}

	}

	/**
	 * Find the next data of v in the subtree which has n as the root node
	 * 
	 * @param n root node of a given subtree
	 * @param v the value v
	 * @return next data of v in the given subtree
	 */
	public int nextOf(BSTNode n, int v) {
		if (n == null) {
			return v;
		}
		if (n.data > v) {
			// When data in the node is more than v it could be the next value
			// But the value that v < x < n.data could exist

			// Find the data on the left-sided sub tree of n
			int nextOfSubT = nextOf(n.left, v);

			// If the data on the left-sided sub tree is v
			if (nextOfSubT == v) {
				// return n.data because is the least number that more than v
				return n.data;

				// There exist the number that less than n.data and more than v
			} else {
				return nextOfSubT;
			}
		} else {
			return nextOf(n.right, v);
		}
	}

	// Delete this before turn lab in
	public int sumOf(BSTNode n) {
		int sum = 0;
		if (n == null) {
			return 0;
		}
		sum += sumOf(n.left) + sumOf(n.right) + n.data;
		return sum;
	}

	public static void main(String[] args) {
		BSTNode r = new BSTNode(7);
		BSTRecursive t = new BSTRecursive(r, 1);
		t.insert(3);
		t.insert(1);
		t.insert(11);
		t.insert(2);
		t.insert(5);
		t.insert(9);
		t.insert(6);
	}
}
