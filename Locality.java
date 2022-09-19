// --== CS400 File Header Information ==--
// Name: Eligh Alvarez
// Email: ealvarez2@wisc.edu
// Notes to Grader: None
/**
 * Contains the necessary components to keep track of a zip code/MHA mapping.
 * @author Eligh Alvarez
 */
public class Locality {
  private int zipCode;
  private String mhaCode;

  /**
   * Creates a new Locality for storage in a HashTableMap
   * @param zipCode the 5-digit zip code associated with this Locality
   * @param mhaCode the MHA code associated with this Locality
   */
  public Locality(int zipCode, String mhaCode) {
    this.zipCode = zipCode;
    this.mhaCode = mhaCode;
  }

  /**
   * Returns the MHA code associated with this Locality
   * @return the MHA code associated with this Locality
   */
  public String getMHA() {
    return mhaCode;
  }
}