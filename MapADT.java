// --== CS400 File Header Information ==--
// Name: Eligh Alvarez
// Email: ealvarez2@wisc.edu
// Notes to Grader: This interface was provided by CS400 Instructors for the prior implementation
// of the HashTableMap class.

import java.util.NoSuchElementException;

public interface MapADT <KeyType, ValueType> {

  public boolean put(KeyType key, ValueType value);
  public ValueType get(KeyType key) throws NoSuchElementException;
  public int size();
  public boolean containsKey(KeyType key);
  public ValueType remove(KeyType key);
  public void clear();

}