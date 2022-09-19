// --== CS400 File Header Information ==--
// Name: Eligh Alvarez
// Email: ealvarez2@wisc.edu
// Notes to Grader: None
/**
 * Contains the necessary methods for creating and saving special pays and their associated
 * monthly pay.
 * @author Eligh Alvarez
 */
public class SpecialPay {
  private String description; // Description of this pay
  private double monthlyPay;  // The monthly pay associated with this SpecialPay

  // A HashTableMap that stores all special pays created during this session for later reference.
  private static HashTableMap<String, SpecialPay> specialPaysReference = new HashTableMap<>();

  /**
   * Creates a SpecialPay based on the provided description and monthly pay. Automatically stores
   * the new SpecialPay in a HashTableMap for later reference.
   * @param description The description of this SpecialPay. description is used as a unique
   *                    identifier for this SpecialPay.
   * @param monthlyPay The monthly pay associated with this SpecialPay
   * @throws IllegalArgumentException if a special pay with this description already exists or if
   * monthlyPay is negative.
   */
  public SpecialPay(String description, double monthlyPay) {
    // Check to see if this special pay already exists. Don't continue if it does.
    if (specialPaysReference.containsKey(description)) {
      throw new IllegalArgumentException(description + " already exists!");
    } else if (monthlyPay < 0) {
      throw new IllegalArgumentException("monthlyPay cannot be negative");
    }
    this.description = description;
    this.monthlyPay = monthlyPay;
    // Add this to specialPaysReference for later use.
    specialPaysReference.put(this.description, this);
  }

  /**
   * Returns the monthly pay associated with description.
   * @param description the description of the pay to reference.
   * @return the monthly pay associated with description.
   * @throws java.util.NoSuchElementException if description does not reference a previously
   * existing SpecialPay
   */
  public static double getSpecialMonthlyPay(String description) {
    return specialPaysReference.get(description).monthlyPay;
  }
}
