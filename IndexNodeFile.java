import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public class IndexNodeFile<K extends Comparable<K>, T> extends Node<K,T> {

	// m nodes
	protected ArrayList<Node<K,T>> ChildrensArrayList; // m+1 children

	public IndexNodeFile(K key, Node<K,T> chil1, Node<K,T> chil2) {
		isLeaf = false;
		keysArrayList = new ArrayList<K>();
		keysArrayList.add(key);
		ChildrensArrayList = new ArrayList<Node<K,T>>();
		ChildrensArrayList.add(chil1);
		ChildrensArrayList.add(chil2);
	}

	public IndexNodeFile(List<K> newKeys, List<Node<K,T>> newChildren) {
		isLeaf = false;

		keysArrayList = new ArrayList<K>(newKeys);
		ChildrensArrayList = new ArrayList<Node<K,T>>(newChildren);

	}

	/**
	 *insert the node in sorted way
	 * 
	 * @param e
	 * @param index
	 */
	public void sortedInsertion(Entry<K, Node<K,T>> e, int index) {
		K key = e.getKey();
		Node<K,T> child = e.getValue();
		if (index >= keysArrayList.size()) {
			keysArrayList.add(key);
			ChildrensArrayList.add(child);
		} else {
			keysArrayList.add(index, key);
			ChildrensArrayList.add(index+1, child);
		}
	}

}
