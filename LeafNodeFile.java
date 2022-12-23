import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class LeafNodeFile<K extends Comparable<K>, T> extends Node<K, T> {
	protected ArrayList<T> valuesArryList;
	protected LeafNodeFile<K,T> nextLeafNode;
	protected LeafNodeFile<K,T> previousLeafNode;

	public LeafNodeFile(K firstKey, T firstValue) {
		isLeaf = true;
		keysArrayList = new ArrayList<K>();
		valuesArryList = new ArrayList<T>();
		keysArrayList.add(firstKey);
		valuesArryList.add(firstValue);

	}

	public LeafNodeFile(List<K> newKeys, List<T> newValues) {
		isLeaf = true;
		keysArrayList = new ArrayList<K>(newKeys);
		valuesArryList = new ArrayList<T>(newValues);

	}

	/**
	 * insert key/value into this node so that it still remains sorted
	 * 
	 * @param key
	 * @param value
	 */
	public void SortedInsertion(K key, T value) {
		if (key.compareTo(keysArrayList.get(0)) < 0) {
			keysArrayList.add(0, key);
			valuesArryList.add(0, value);
		} else if (key.compareTo(keysArrayList.get(keysArrayList.size() - 1)) > 0) {
			keysArrayList.add(key);
			valuesArryList.add(value);
		} else {
			ListIterator<K> iterator = keysArrayList.listIterator();
			while (iterator.hasNext()) {
				if (iterator.next().compareTo(key) > 0) {
					int position = iterator.previousIndex();
					keysArrayList.add(position, key);
					valuesArryList.add(position, value);
					break;
				}
			}

		}
	}

}
