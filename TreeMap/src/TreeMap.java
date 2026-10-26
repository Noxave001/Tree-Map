import java.util.Arrays;

/**
 * A map collection with an inner binary tree structure
 *
 * @author Ace Breaux
 * @version 5-21-2025
 *
 * @param <K> the type for keys in this TreeMap
 * @param <V> the type for values in this TreeMap
 */
public class TreeMap<K extends Comparable<K>, V> implements TreeMapInterface<K, V> {
    /** The root of the overall tree */
    private TreeMapNode<K, V> overallRoot;
    /** The number of elements stored in the tree */
    private int size;

    /**
     * Creates an empty TreeMap
     */
    public TreeMap() {
        clear();
    }

    /**
     * Clears the existing tree, removing any and all existing key/value pairs.
     */
    public void clear() {
        overallRoot = null;
        size = 0;
    }

    /**
     * Retrieves the number of key/value pair elements managed by the map
     *
     * @return number of elements
     */
    public int size() {
        return size;
    }

    /**
     * Adds a key/value pair to the tree map.
     *
     * @param key   the key. If not already in the tree, the key/value pair is added.  If already in the
     *              tree, the existing value is replaced with the one specified here.
     * @param value the value in the key/value pair.
     */
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        if (!containsKey(key)) {
            size++;
        }
        overallRoot = put(overallRoot, key, value);
    }

    /**
     * Inner recursive structure for the put method
     *
     * @param current the current node being accessed
     * @param key   the key. If not already in the tree, the key/value pair is added.  If already in the
     *              tree, the existing value is replaced with the one specified here.
     * @param value the value in the key/value pair.
     * @return a reference to the current node being accessed
     */
    private TreeMapNode<K, V> put(TreeMapNode<K, V> current, K key, V value) {
        if (current == null) {
            current = new TreeMapNode<>(key, value);
        } else if (current.key.compareTo(key) == 0) {
            current.value = value;
        } else if (current.key.compareTo(key) > 0) {
            current.left = put(current.left, key, value);
        } else {
            current.right = put(current.right, key, value);
        }
        return current;
    }

    /**
     * Checks the tree to see if it contains the specified key.
     *
     * @param key the key to search for.
     * @return true, if the key is in the tree map; false, if not.
     */
    public boolean containsKey(K key) {
        return containsKey(overallRoot, key);
    }

    /**
     * Inner recursive structure for the containsKey method
     *
     * @param current the current node being accessed
     * @param key the key to search for.
     * @return true, if the key is in the tree map; false, if not.
     */
    private boolean containsKey(TreeMapNode<K, V> current, K key) {
        if (current == null) {
            return false;
        } else {
            int compare = key.compareTo(current.key);
            if (compare == 0) {
                return true;
            } else if (compare < 0) {
                return containsKey(current.left, key);
            } else {   // compare > 0
                return containsKey(current.right, key);
            }
        }
    }

    /**
     * Retrieves the corresponding value for the specified key.
     *
     * @param key key of interest
     * @return value corresponding to the specified key, or null if the key is not found.
     */
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        return get(overallRoot, key);
    }

    /**
     * Inner recursive structure for the get method
     *
     * @param current the current node being accessed
     * @param key key of interest
     * @return value corresponding to the specified key, or null if the key is not found.
     */
    private V get(TreeMapNode<K, V> current, K key) {
        if (current == null) {
            return null;
        } else {
            int compare = key.compareTo(current.key);
            if (compare == 0) {
                return current.value;
            } else if (compare < 0) {
                return get(current.left, key);
            } else {   // compare > 0
                return get(current.right, key);
            }
        }
    }

    /**
     * Retrieves an array of key data from the map, in order,
     *
     * @param array to fill in.  If smaller than the map's size, a new array will be created.  If larger than the
     *              map's size, data will be filled in from index 0, with a null reference just after the copied-in data.
     * @return a reference to the filled-in array; may be a different array than the one passed in.
     */
    public K[] toKeyArray(K[] array) {
        // Precondition
        if (array == null) {
            throw new IllegalArgumentException("Array provided must not be null");
        }

        // Correcting array size/values if needed
        if (array.length > size) {
            array[size] = null;
        } else if (array.length < size) {
            array = Arrays.copyOf(array, size);
        }

        toKeyArray(array, overallRoot, new int[1]);
        return array;
    }

    /**
     * Inner recursive structure for the toKeyArray method
     *
     * @param array to fill in
     * @param current the current node being accessed
     * @param index an array of size 1 that contains current index being accessed; utilizes the fact that
     *              arrays are mutable to properly keep track of the index during recursion
     */
    private void toKeyArray(K[] array, TreeMapNode<K, V> current, int[] index) {
        if (current != null) {
            toKeyArray(array, current.left, index);
            array[index[0]++] = current.key;
            toKeyArray(array, current.right, index);
        }
    }

    /**
     * Singular nodes for usage with the TreeMap
     *
     * @param <K> the type for keys in this node
     * @param <V> the type for values in this node
     */
    private static class TreeMapNode<K, V> {
        /** The key associated with the data */
        public K key;
        /** The data stored in the node */
        public V value;
        /** A reference to the left subtree of this node */
        public TreeMapNode<K, V> left;
        /** A reference to the right subtree of this node */
        public TreeMapNode<K, V> right;

        /**
         * Constructs a leaf node with the given value
         *
         * @param key the key associated with the data
         * @param value the data to be stored in the node
         */
        public TreeMapNode(K key, V value) {
            this(key, value, null, null);
        }

        /**
         * Constructs a node with the given value and references to its subtrees
         *
         * @param key the key associated with the data
         * @param value the data to be stored in the node
         * @param left a reference to the left subtree of this node
         * @param right a reference to the right subtree of this node
         */
        public TreeMapNode(K key, V value, TreeMapNode<K, V> left,
                              TreeMapNode<K, V> right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }
}
