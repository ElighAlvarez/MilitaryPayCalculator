// --== CS400 File Header Information ==--
// Name: Eligh Alvarez
// Email: ealvarez2@wisc.edu
// Notes to Grader: A portion of this code was provided by CS400 Instructors for the prior
// implementation of the HashTableMap class.

import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * This class represents a hash table and contains methods useful for the manipulation and
 * storage of Data within the HashTableMap object.
 * @param <K> The type of the key stored in each Data.
 * @param <V> The type of the value stored in each Data.
 * @author Eligh Alvarez
 */
public class HashTableMap <K, V> implements MapADT<K, V> {

  protected LinkedList<Data<K, V>>[] contents;
  private int size;
  private final double LOAD_THRESHOLD;

  /**
   * Creates a new HashTableMap with the provided capacity and load threshold.
   * @param capacity The number of locations available for key-value pairs within the hash table.
   * @param loadThreshold The load threshold this HashTableMap will use (1.0 is 100% capacity)
   */
  @SuppressWarnings("unckecked")
  public HashTableMap(int capacity, double loadThreshold) {
    contents = new LinkedList[capacity];
    for (int i = 0; i < capacity; i++) {
      contents[i] = new LinkedList<>();
    }
    LOAD_THRESHOLD = loadThreshold;
  }

  /**
   * Creates a new HashTableMap with a default capacity of 10 and loadThreshold of 85%.
   */
  public HashTableMap() {
    this(10, 0.85);
  }

  /**
   * Adds a key-value pair to this HashTableMap. Increases the capacity of this HashTableMap if
   * the addition of the key-value pair causes the load capacity threshold to be exceeded.
   * @param key The key associated with the key-value pair.
   * @param value The value associated with the key-value pair.
   * @return true if the key-value pair was added to this HashTableMap, false otherwise.
   */
  @Override
  public boolean put(K key, V value) {
    if (key == null || this.containsKey(key)) {
      return false;
    }

    contents[indexOfKey(key)].add(new Data<>(key, value));
    size++;

    if (isOverLoaded()) {
      doubleCapacity();
    }
    return true;
  }

  /**
   * Returns the value associated with the provided key if the key-value pair is stored in this
   * HashTableMap.
   * @param key The key associated with the desired value.
   * @return The value associated with the provided key.
   * @throws NoSuchElementException if the key-value pair cannot be located in this HashTableMap.
   */
  @Override
  public V get(K key) throws NoSuchElementException {
    LinkedList<Data<K, V>> list = contents[indexOfKey(key)];
    for (Data<K, V> data : list) {
      if (data.getKey().equals(key)) {
        return data.getValue();
      }
    }
    throw new NoSuchElementException("Key was not located in Hash Table.");
  }

  /**
   * Returns the number of key-value pairs stored in this HashTableMap.
   * @return the number of key-value pairs stored in this HashTableMap.
   */
  @Override
  public int size() {
    return size;
  }

  /**
   * Indicates whether a key-value pair is currently stored in this HashTableMap.
   * @param key The key for the desired key-value pair.
   * @return true if the key-value pair is currently stored in this HashTableMap, false otherwise.
   */
  @Override
  public boolean containsKey(K key) {
    try {
      get(key);
    } catch (NoSuchElementException e) {
      return false;
    }
    return true;
  }

  /**
   * Removes a key-value pair from this HashTableMap and returns the value associated with the
   * provided key.
   * @param key The key associated with the key-value pair to remove.
   * @return The value associated with the key-value pair to remove. Null if the pair is not
   * currently contained in this HashTableMap.
   */
  @Override
  public V remove(K key) {
    // Isolate the LinkedList at the corresponding hash table index
    LinkedList<Data<K, V>> list = contents[indexOfKey(key)];
    Data<K, V> temp = null;
    // Search list for the provided key and break once it is found
    for (Data<K, V> data : list) {
      if (data.getKey().equals(key)) {
        temp = data;
        break;
      }
    }

    // If temp is still null, then the key was not found. Return null as specified by the
    // instructions
    if (temp == null) {
      return null;
    }

    // Remove the data from the hash table and return the value corresponding to the provided key
    list.remove(temp);
    size--;
    return temp.getValue();
  }

  /**
   * Clears this HashTableMap. Does not decrease the current capacity.
   */
  @Override
  public void clear() {
    contents = new LinkedList[contents.length];
    for (int i = 0; i < contents.length; i++) {
      contents[i] = new LinkedList<>();
    }
    size = 0;
  }

  /**
   * Doubles the capacity of this HashTableMap and rehashes all currently stored key-value pairs.
   */
  @SuppressWarnings("unchecked")
  private void doubleCapacity() {
    // Hold current table temporarily
    LinkedList<Data<K, V>>[] temp = contents;
    // Replace contents with new hash table double in size
    contents = new LinkedList[contents.length * 2];
    for (int i = 0; i < contents.length; i++) {
      contents[i] = new LinkedList<>();
    }

    // Iterate through all stored data: rehash and transfer old contents to new hash table
    int newIndex;
    for (LinkedList<Data<K, V>> list : temp) {
      for (Data<K, V> data : list) {
        newIndex = indexOfKey(data.getKey());
        contents[newIndex].add(data);
      }
    }
  }

  /**
   * Determines the index at which to store a key-value pair, based on the hashcode of the
   * provided key.
   * @param key The key with which to determine an index.
   * @return The index at which to store a key-value pair with the provided key.
   */
  private int indexOfKey(K key) {
    return Math.abs(key.hashCode()) % contents.length;
  }

  /**
   * Determines whether this HashTableMap is overloaded.
   * @return true if this HashTableMap is overloaded, false otherwise.
   */
  private boolean isOverLoaded() {
    return (double)size / (double)contents.length > LOAD_THRESHOLD - .0001;
  }
}
