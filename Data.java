 // --== CS400 File Header Information ==--
// Name: Eligh Alvarez
// Email: ealvarez2@wisc.edu
// Notes to Grader: This code provided by CS400 instructors for prior HashTableMap implementation

/**
 * Stores a key-value pair in one Data object.
 * @param <K> The type of the key.
 * @param <V> The type of the value.
 * @author Eligh Alvarez
 */
public class Data <K, V> {

  K key;
  V value;

  /**
   * Creates a new Data from a key-value pair
   * @param key The key for the new Data.
   * @param value The value for the new Data.
   */
  Data(K key, V value) {
    this.key = key;
    this.value = value;
  }

  /**
   * Returns the key stored in this Data.
   * @return the key stored in this Data.
   */
  public K getKey() {
    return key;
  }

  /**
   * Returns the value stored in this Data.
   * @return the value stored in this Data.
   */
  public V getValue() {
    return value;
  }

  /**
   * Returns true if this Data's key equals() another Object's key.
   * @param o the Object to compare to this Data.
   * @return true if this Data's key equals() o's key, false otherwise.
   */
  public boolean equals(Object o) {
    return o instanceof Data && this.getKey().equals(((Data)o).getKey());
  }
}
