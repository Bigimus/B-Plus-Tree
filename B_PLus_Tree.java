import java.util.AbstractMap;
import java.util.Map.Entry;
import java.util.ArrayList;


/**
 * BPlusTree Class Assumptions: 1. No duplicate keys inserted 2. Order D:
 * D<=number of keys in a node <=2*D 3. All keys are non-negative
 * TODO: Rename to B_Plus_Tree
 */
public class B_PLus_Tree<K extends Comparable<K>, T> {

	public Node<K,T> rootNode;
	public static final int D = 2;

	/**
	 * TODO Search the value for a specific key
	 * 
	 * @param key
	 * @return value
	 */
	public T searchNode(K key) {
		// Return if empty tree or key
		if(key == null || rootNode == null) {
			return null;
		}
		// Look for leaf node that key is pointing to
		LeafNodeFile<K,T> leaf = (LeafNodeFile<K,T>)searchTree(rootNode, key);
					
		// Look for value in the leaf
		for(int i=0; i<leaf.keysArrayList.size(); i++) {
			if(key.compareTo(leaf.keysArrayList.get(i)) == 0) {
				return leaf.valuesArryList.get(i);
			}
		}
					
		return null;
	}
	
	private Node<K,T> searchTree(Node<K,T> node, K key) {
		if(node.isLeaf) {
			return node;
		} 
		// The node is index node
		else {
			IndexNodeFile<K,T> index = (IndexNodeFile<K,T>)node;
			
			// K < K1, return searchTree(P0, K)
			if(key.compareTo(index.keysArrayList.get(0)) < 0) {
				return searchTree((Node<K,T>)index.ChildrensArrayList.get(0), key);
			}
			// K >= Km, return searchTree(Pm, K), m = #entries
			else if(key.compareTo(index.keysArrayList.get(node.keysArrayList.size()-1)) >= 0) {
				return searchTree((Node<K,T>)index.ChildrensArrayList.get(index.ChildrensArrayList.size()-1), key);
			}
			// Find i such that Ki <= K < K(i+1), return searchTree(Pi,K)
			else {
				// Linear searching
				for(int i=0; i<index.keysArrayList.size()-1; i++) {
					if(key.compareTo(index.keysArrayList.get(i)) >= 0 && key.compareTo(index.keysArrayList.get(i+1)) < 0) {
						return searchTree((Node<K,T>)index.ChildrensArrayList.get(i+1), key);
					}
				}
 			}
			return null;
		}
	} 
	
	/**
	 * TODO Insert a key/value pair into the BPlusTree
	 * 
	 * @param key
	 * @param value
	 */
	public void insert(K key, T value) {
		LeafNodeFile<K,T> newLeaf = new LeafNodeFile<K,T>(key, value);
		Entry<K, Node<K,T>> entry = new AbstractMap.SimpleEntry<K, Node<K,T>>(key, newLeaf);
		System.out.println(key);
		// Insert entry into subtree with rootNode node pointer
		if(rootNode == null || rootNode.keysArrayList.size() == 0) {
			rootNode = entry.getValue();
		}
		
		// newChildEntry null initially, and null on return unless child is split
		Entry<K, Node<K,T>> newChildEntry = getChildEntry(rootNode, entry, null);
		
		if(newChildEntry == null) {
			return;
		} else {
			IndexNodeFile<K,T> newrootNode = new IndexNodeFile<K,T>(newChildEntry.getKey(), rootNode, 
					newChildEntry.getValue());
			rootNode = newrootNode;
			return;
		}
		
	}
	
	private Entry<K, Node<K,T>> getChildEntry(Node<K,T> node, Entry<K, Node<K,T>> entry, 
			Entry<K, Node<K,T>> newChildEntry) {
		if(!node.isLeaf) {
			// Choose subtree, find i such that Ki <= entry's key value < J(i+1)
			IndexNodeFile<K,T> index = (IndexNodeFile<K,T>) node;
			int i = 0;
			while(i < index.keysArrayList.size()) {
				if(entry.getKey().compareTo(index.keysArrayList.get(i)) < 0) {
					break;
				}
				i++;
			}
			// Recursively, insert entry
			newChildEntry = getChildEntry((Node<K,T>) index.ChildrensArrayList.get(i), entry, newChildEntry);
			
			// Usual case, didn't split child
			if(newChildEntry == null) {
				return null;
			} 
			// Split child case, must insert newChildEntry in node
			else { 
				int j = 0;
				while (j < index.keysArrayList.size()) {
					if(newChildEntry.getKey().compareTo(index.keysArrayList.get(j)) < 0) {
						break;
					}
					j++;
				}
				
				index.sortedInsertion(newChildEntry, j);
				
				// Usual case, put newChildEntry on it, set newChildEntry to null, return
				if(!index.isOverflowed()) {
					return null;
				} 
				else{
					newChildEntry = splitIndexNode(index);
					
					// rootNode was just split
					if(index == rootNode) {
						// Create new node and make tree's rootNode-node pointer point to newrootNode
						IndexNodeFile<K,T> newrootNode = new IndexNodeFile<K,T>(newChildEntry.getKey(), rootNode, 
								newChildEntry.getValue());
						rootNode = newrootNode;
						return null;
					}
					return newChildEntry;
				}
			}
		}
		// Node pointer is a leaf node
		else {
			LeafNodeFile<K,T> leaf = (LeafNodeFile<K,T>)node;
			LeafNodeFile<K,T> newLeaf = (LeafNodeFile<K,T>)entry.getValue();
			
			leaf.SortedInsertion(entry.getKey(), newLeaf.valuesArryList.get(0));
			
			// Usual case: leaf has space, put entry and set newChildEntry to null and return
			if(!leaf.isOverflowed()) {
				return null;
			}
			// Once in a while, the leaf is full
			else {
				newChildEntry = splitLeafNode(leaf);
				if(leaf == rootNode) {
					IndexNodeFile<K,T> newrootNode = new IndexNodeFile<K,T>(newChildEntry.getKey(), leaf, 
							newChildEntry.getValue());
					rootNode = newrootNode;
					return null;
				}
				return newChildEntry;
			}
		}
	}

	/**
	 * TODO Split a leaf node and return the new right node and the splitting
	 * key as an Entry<slitingKey, RightNode>
	 * 
	 * @param leaf, any other relevant data
	 * @return the key/node pair as an Entry
	 */
	public Entry<K, Node<K,T>> splitLeafNode(LeafNodeFile<K,T> leaf) {
		ArrayList<K> newKeys = new ArrayList<K>();
		ArrayList<T> newValues = new ArrayList<T>();
		
		// The rest D entries move to brand new node
		for(int i=D; i<=2*D; i++) {
			newKeys.add(leaf.keysArrayList.get(i));
			newValues.add(leaf.valuesArryList.get(i));
		}
		
		// First D entries stay
		for(int i=D; i<=2*D; i++) {
			leaf.keysArrayList.remove(leaf.keysArrayList.size()-1);
			leaf.valuesArryList.remove(leaf.valuesArryList.size()-1);
		}
		
		K splitKey = newKeys.get(0);
		LeafNodeFile<K,T> rightNode = new LeafNodeFile<K,T>(newKeys, newValues);
		
		// Set sibling pointers
		LeafNodeFile<K,T> tmp = leaf.nextLeafNode;
		leaf.nextLeafNode = rightNode;
		leaf.nextLeafNode.previousLeafNode = rightNode;
		rightNode.previousLeafNode = leaf;
		rightNode.nextLeafNode = tmp;
        
		Entry<K, Node<K,T>> newChildEntry = new AbstractMap.SimpleEntry<K, Node<K,T>>(splitKey, rightNode);
		
		return newChildEntry;
	}

	/**
	 * TODO split an indexNode and return the new right node and the splitting
	 * key as an Entry<slitingKey, RightNode>
	 * 
	 * @param index, any other relevant data
	 * @return new key/node pair as an Entry
	 */
	public Entry<K, Node<K,T>> splitIndexNode(IndexNodeFile<K,T> index) {
		ArrayList<K> newKeys = new ArrayList<K>();
		ArrayList<Node<K,T>> newChildren = new ArrayList<Node<K,T>>();
		
		// Note difference with splitting leaf page, 2D+1 key values and 2D+2 node pointers
		K splitKey = index.keysArrayList.get(D);
		index.keysArrayList.remove(D);
		
		// First D key values and D+1 node pointers stay
		// Last D keys and D+1 pointers move to new node
		newChildren.add(index.ChildrensArrayList.get(D+1));
		index.ChildrensArrayList.remove(D+1);
		
		while(index.keysArrayList.size() > D) {
			newKeys.add(index.keysArrayList.get(D));
			index.keysArrayList.remove(D);
			newChildren.add(index.ChildrensArrayList.get(D+1));
			index.ChildrensArrayList.remove(D+1);
		}

		IndexNodeFile<K,T> rightNode = new IndexNodeFile<K,T>(newKeys, newChildren);
		Entry<K, Node<K,T>> newChildEntry = new AbstractMap.SimpleEntry<K, Node<K,T>>(splitKey, rightNode);

		return newChildEntry;
	}
	
	/**
	 * TODO Delete a key/value pair from this B+Tree
	 * 
	 * @param key
	 */
	public void delete(K key) {
		System.out.println("Called");
		if(key == null || rootNode == null) {
			System.out.println("keynull");
			return;
			
		}
		// Check if entry key exist in the leaf node
		LeafNodeFile<K,T> leaf = (LeafNodeFile<K,T>)searchTree(rootNode, key);
		if(leaf == null) {
			System.out.println("leafnull");
			return;
		}
		
		// Delete entry from subtree with rootNode node pointer
		Entry<K, Node<K,T>> entry = new AbstractMap.SimpleEntry<K, Node<K,T>>(key, leaf);
		
		// oldChildEntry null initially, and null upon return unless child deleted
		Entry<K, Node<K,T>> oldChildEntry = deleteChildEntry(rootNode, rootNode, entry, null);
		
		// Readjust the rootNode, no child is deleted
		if(oldChildEntry == null) {
			System.out.println("rootNode ajustment");
			if(rootNode.keysArrayList.size() == 0) {
				System.out.println("rootNode ajustment2");
				if(!rootNode.isLeaf) {
					System.out.println("leafnull");
					rootNode = ((IndexNodeFile<K,T>) rootNode).ChildrensArrayList.get(0);
				}
			}
			return;
		}
		// Child is deleted
		else {
			System.out.println("child delted");
			
			// Find empty node
			int i = 0;
			K oldKey = oldChildEntry.getKey();
			while(i < rootNode.keysArrayList.size()) {
				System.out.println("leafnull");
				if(oldKey.compareTo(rootNode.keysArrayList.get(i)) == 0) {
					break;
				}
				i++;
			}
			// Return if empty node already discarded
			if(i == rootNode.keysArrayList.size()) {
				System.out.println("empty node already discared");
				return;
			}
			// Discard empty node
			rootNode.keysArrayList.remove(i);
			System.out.println("discard");
			((IndexNodeFile<K,T>)rootNode).ChildrensArrayList.remove(i+1);
			return;
		}
	}
	
	private Entry<K, Node<K,T>> deleteChildEntry(Node<K,T> parentNode, Node<K,T> node, 
			Entry<K, Node<K,T>> entry, Entry<K, Node<K,T>> oldChildEntry) {
		System.out.println("called");
		if(!node.isLeaf) {
			// Choose subtree, find i such that Ki <= entry's key value < K(i+1)
			IndexNodeFile<K,T> index = (IndexNodeFile<K,T>)node;
			int i = 0;
			K entryKey = entry.getKey();
			while(i < index.keysArrayList.size()) {
				if(entryKey.compareTo(index.keysArrayList.get(i)) < 0) {
					break;
				}
				i++;
			}
			// Recursive delete
			oldChildEntry = deleteChildEntry(index, index.ChildrensArrayList.get(i), entry, oldChildEntry);
			
			// Usual case: child not deleted
			if(oldChildEntry == null) {
				return null;
			}
			// Discarded child node case
			else {
				int j = 0;
				K oldKey = oldChildEntry.getKey();
				while(j < index.keysArrayList.size()) {
					if(oldKey.compareTo(index.keysArrayList.get(j)) == 0) {
						break;
					}
					j++;
				}
				// Remove oldChildEntry from node
				index.keysArrayList.remove(j);
				index.ChildrensArrayList.remove(j+1);
				
				// Check for underflow, return null if empty
				if(!index.isUnderflowed() || index.keysArrayList.size() == 0) {
					// Node has entries to spare, delete doesn't go further
					return null; 
				}
				else {
					// Return if rootNode
					if(index == rootNode) {
						return oldChildEntry;
					}
					// Get sibling S using parent pointer
					int s = 0;
					K firstKey = index.keysArrayList.get(0);
					while(s < parentNode.keysArrayList.size()) {
						if(firstKey.compareTo(parentNode.keysArrayList.get(s)) < 0) {
							break;
						}
						s++;
					}
					// Handle index underflow
					int splitKeyPos;
					IndexNodeFile<K,T> parent = (IndexNodeFile<K,T>)parentNode;
					
					if(s > 0 && parent.ChildrensArrayList.get(s-1) != null) {
						splitKeyPos = handleIndexNodeUnderflow(
								(IndexNodeFile<K,T>)parent.ChildrensArrayList.get(s-1), index, parent);
					} else {
						splitKeyPos = handleIndexNodeUnderflow(
								index, (IndexNodeFile<K,T>)parent.ChildrensArrayList.get(s+1), parent);
					}
					// S has extra entries, set oldChildentry to null, return
					if(splitKeyPos == -1) {
						return null;
					}
					// Merge indexNode and S
					else {
						K parentKey = parentNode.keysArrayList.get(splitKeyPos);
						oldChildEntry = new AbstractMap.SimpleEntry<K, Node<K,T>>(parentKey, parentNode);
						return oldChildEntry;
					}
				}
			}
		}
		// The node is a leaf node
		else {
			LeafNodeFile<K,T> leaf = (LeafNodeFile<K,T>)node;
			// Look for value to delete
			for(int i=0; i<leaf.keysArrayList.size(); i++) {
				if(leaf.keysArrayList.get(i) == entry.getKey()) {
					leaf.keysArrayList.remove(i);
					leaf.valuesArryList.remove(i);
					break;
				}
			}
			// Usual case: no underflow
			if(!leaf.isUnderflowed()) {
				return null;
			}
			// Once in a while, the leaf becomes underflow
			else {
				// Return if rootNode
				if(leaf == rootNode || leaf.keysArrayList.size() == 0) {
					return oldChildEntry;
				}
				// Handle leaf underflow
				int splitKeyPos;
				K firstKey = leaf.keysArrayList.get(0);
				K parentKey = parentNode.keysArrayList.get(0);
				
				if(leaf.previousLeafNode != null && firstKey.compareTo(parentKey) >= 0) {
					splitKeyPos = handleLeafNodeUnderflow(leaf.previousLeafNode, leaf, (IndexNodeFile<K,T>)parentNode);
				} else {
					splitKeyPos = handleLeafNodeUnderflow(leaf, leaf.nextLeafNode, (IndexNodeFile<K,T>)parentNode);
				}
				// S has extra entries, set oldChildEntry to null, return
				if(splitKeyPos == -1) {
					return null;
				} 
				// Merge leaf and S
				else {
					parentKey = parentNode.keysArrayList.get(splitKeyPos);
					oldChildEntry = new AbstractMap.SimpleEntry<K, Node<K,T>>(parentKey, parentNode);
					return oldChildEntry;
				}	
			}
		}
	}

	/**
	 * TODO Handle LeafNode Underflow (merge or redistribution)
	 * 
	 * @param left
	 *            : the smaller node
	 * @param right
	 *            : the bigger node
	 * @param parent
	 *            : their parent index node
	 * @return the splitkey position in parent if merged so that parent can
	 *         delete the splitkey later on. -1 otherwise
	 */
	public int handleLeafNodeUnderflow(LeafNodeFile<K,T> left, LeafNodeFile<K,T> right,
			IndexNodeFile<K,T> parent) {
		// Find entry in parent for node on right
		int i = 0;
		K rKey = right.keysArrayList.get(0);
		while(i < parent.keysArrayList.size()) {
			if(rKey.compareTo(parent.keysArrayList.get(i)) < 0) {
				break;
			}
			i++;
		}	
		// Redistribute evenly between right and left nodes
		// If S has extra entries
		if(left.keysArrayList.size() + right.keysArrayList.size() >= 2*D) {
			// Left node has more entries
			if(left.keysArrayList.size() > right.keysArrayList.size()) {
				while(left.keysArrayList.size() > D) {
					right.keysArrayList.add(0, left.keysArrayList.get(left.keysArrayList.size()-1));
					right.valuesArryList.add(0, left.valuesArryList.get(left.keysArrayList.size()-1));
					left.keysArrayList.remove(left.keysArrayList.size()-1);
					left.valuesArryList.remove(left.valuesArryList.size()-1);
				}
			}
			// Right node has more entries
			else {
				while(left.keysArrayList.size() < D) {
					left.keysArrayList.add(right.keysArrayList.get(0));
					left.valuesArryList.add(right.valuesArryList.get(0));
					right.keysArrayList.remove(0);
					right.valuesArryList.remove(0);
				}
			}
			// Replace key value in parent entry by low-key in right node
			parent.keysArrayList.set(i-1, right.keysArrayList.get(0));
			
			return -1;
		}
		// No extra entries, return splitKeyPos
		else {
			// Move all entries from right to left node
			while(right.keysArrayList.size() > 0) {
				left.keysArrayList.add(right.keysArrayList.get(0));
				left.valuesArryList.add(right.valuesArryList.get(0));
				right.keysArrayList.remove(0);
				right.valuesArryList.remove(0);
			}
			// Adjust sibling pointers
			if(right.nextLeafNode != null) {
				right.nextLeafNode.previousLeafNode = left;
			}
			left.nextLeafNode = right.nextLeafNode;
			
			return i-1;
		}
	}
	
	/**
	 * TODO Handle IndexNode Underflow (merge or redistribution)
	 * 
	 * @param left
	 *            : the smaller node
	 * @param right
	 *            : the bigger node
	 * @param parent
	 *            : their parent index node
	 * @return the splitkey position in parent if merged so that parent can
	 *         delete the splitkey later on. -1 otherwise
	 */
	public int handleIndexNodeUnderflow(IndexNodeFile<K,T> leftIndex,
			IndexNodeFile<K,T> rightIndex, IndexNodeFile<K,T> parent) {
		// Find entry in parent for node on right
		int i = 0;
		K rKey = rightIndex.keysArrayList.get(0);
		while(i < parent.keysArrayList.size()) {
			if(rKey.compareTo(parent.keysArrayList.get(i)) < 0) {
				break;
			}
			i++;
		}
		// Redistribute evenly between node and S through parent
		// If S has extra entries
		if(leftIndex.keysArrayList.size() + rightIndex.keysArrayList.size() >= 2*D) {
			// Left node has more entries
			if(leftIndex.keysArrayList.size() > rightIndex.keysArrayList.size()) {
				while(leftIndex.keysArrayList.size() > D) {
					rightIndex.keysArrayList.add(0, parent.keysArrayList.get(i-1));
					rightIndex.ChildrensArrayList.add(leftIndex.ChildrensArrayList.get(leftIndex.ChildrensArrayList.size()-1));
					parent.keysArrayList.set(i-1, leftIndex.keysArrayList.get(leftIndex.keysArrayList.size()-1));
					leftIndex.keysArrayList.remove(leftIndex.keysArrayList.size()-1);
					leftIndex.ChildrensArrayList.remove(leftIndex.ChildrensArrayList.size()-1);
				}
			}
			// Right node has more entries
			else {
				while(leftIndex.keysArrayList.size() < D) {
					leftIndex.keysArrayList.add(parent.keysArrayList.get(i-1));
					leftIndex.ChildrensArrayList.add(rightIndex.ChildrensArrayList.get(0));
					parent.keysArrayList.set(i-1, rightIndex.keysArrayList.get(0));
					rightIndex.keysArrayList.remove(0);
					rightIndex.ChildrensArrayList.remove(0);
				}
			}
			return -1;
		}
		// No extra entries, return spiltKeyPos
		else {
			leftIndex.keysArrayList.add(parent.keysArrayList.get(i-1));
			// Move all entries from right to left node
			while(rightIndex.keysArrayList.size() > 0) {
				leftIndex.keysArrayList.add(rightIndex.keysArrayList.get(0));
				leftIndex.ChildrensArrayList.add(rightIndex.ChildrensArrayList.get(0));
				rightIndex.keysArrayList.remove(0);
				rightIndex.ChildrensArrayList.remove(0);
			}
			leftIndex.ChildrensArrayList.add(rightIndex.ChildrensArrayList.get(0));
			rightIndex.ChildrensArrayList.remove(0);
			
			return i-1;
		}
	}
}