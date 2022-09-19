// --== CS400 File Header Information ==--
// Name: Eligh Alvarez
// Email: ealvarez2@wisc.edu
// Notes to Grader: None
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Contains data and methods for accessing the data associated with 2022 Basic Monthly Pays
 * @author Eligh Alvarez
 */
public class BasePay {
  // HashTableMap for storing all base pay data. We are using a perfect hash, so can bump up the
  // threshold
  private static HashTableMap<Integer, BasePay> payReference = new HashTableMap<>(1300, 0.95);

  private Grade payGrade;
  private int yearsOfService;
  private double monthlyPay;
  private int key;

  /**
   * Creates a new BasePay for storing basic pay information in the applicable HashTableMap.
   * Automatically puts the BasePay in payReference HashTableMap.
   * @param payGrade the Grade associated with this BasePay
   * @param yearsOfService the years of service associated with this BasePay
   * @param monthlyPay the monthly pay associated with this BasePay
   */
  private BasePay(Grade payGrade, int yearsOfService, double monthlyPay) {
    this.payGrade = payGrade;
    if (yearsOfService > 40) yearsOfService = 40;
    this.yearsOfService = yearsOfService;
    this.key = generateKey(this.payGrade, this.yearsOfService);
    this.monthlyPay = monthlyPay;
    // Put this BasePay in payReference HashTableMap
    payReference.put(this.key, this);
  }

  /**
   * Generates and returns a key for a perfect hash with the payReference HashTableMap.
   * @param payGrade the Grade associated with the BasePay to find/put
   * @param yearsOfService the years of service associated with the BasePay to find/put
   * @return the key for storage of a BasePay in payReference HashTableMap
   */
  private static int generateKey(Grade payGrade, int yearsOfService) {
    return payGrade.toInt() * 41 + yearsOfService;
  }

  /**
   * Loads 2022 basic pay information and monthly pay from Data/Base_Pay.txt and stores it in a
   * HashTableMap for later lookup via the methods in this class.
   */
  public static void loadBasePay() {
    File basePayData = new File("Data/Base_Pay.txt");
    try (Scanner fileInput = new Scanner(basePayData)) {
      fileInput.nextLine(); // Skip the first line since it has no data
      Grade tempGrade;
      // Loop through remaining 27 lines (each grade's data)
      for (int i = 0; i < 27; i++) {
        tempGrade = new Grade(fileInput.next());
        // Load each double for a monthly pay corresponding to grade and time in service
        for (int timeInService = 0; timeInService <= 40; timeInService++) {
          new BasePay(tempGrade, timeInService, fileInput.nextDouble());
        }
        fileInput.nextLine();
      }
    } catch (FileNotFoundException e) {
      System.out.println(basePayData + " could not be located!");
    }
  }

  /**
   * Returns the monthly basic pay associated with a provided Grade and years of service
   * @param payGrade the Grade to look up
   * @param yearsOfService the years of service to look up
   * @return the monthly basic pay associated with payGrade and yearsOfService
   */
  public static double getMonthlyPay(Grade payGrade, int yearsOfService) {
    // 40 years is the highest value. Larger values are valid, but do not result in a higher pay.
    if (yearsOfService > 40) yearsOfService = 40;
    return payReference.get(generateKey(payGrade, yearsOfService)).monthlyPay;
  }
}
