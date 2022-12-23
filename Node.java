import java.util.ArrayList;

public class Node<K extends Comparable<K>, T> {
	protected boolean isLeaf;
	protected ArrayList<K> keysArrayList;

	public boolean isOverflowed() {
		return keysArrayList.size() > 2 * B_PLus_Tree.D;
	}

	public boolean isUnderflowed() {
		return keysArrayList.size() < B_PLus_Tree.D;
	}

}
