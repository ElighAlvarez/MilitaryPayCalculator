// --== CS400 File Header Information ==--
// Name: Eligh Alvarez
// Email: ealvarez2@wisc.edu
// Notes to Grader: None
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Contains data and methods for accessing the data associated with 2022 Basic Allowance for
 * Housing (BAH).
 * @author Eligh Alvarez
 */
public class BAH {

  private double monthlyPay; // Monthly pay amount associated with a single BAH payment
  private String key;        // Combined string of Military Housing Area (MHA) and pay grade

  // Type 2 BAH with Dependents pay data. In order of ascending pay grade.
  private static final double[] TYPE_2_WITH_DEPENDENTS_RATES = {
      832.50, 832.50, 873.30, 939.30, 1080.90, 1201.20, 1299.60, 1400.70, 1518.60, 1154.10,
      1332.90, 1450.80, 1582.80, 1726.50, 1326.00, 1434.60, 1589.70, 1129.80, 1262.10, 1479.00,
      1787.70, 2028.30, 2104.20, 2337.60, 2337.60, 2337.60, 2337.60};
  // Type 2 BAH without Dependents pay data. In order of ascending pay grade.
  private static final double[] TYPE_2_WITHOUT_DEPENDENTS_RATES = {
      625.20, 625.20, 655.50, 705.30, 810.90, 901.20, 975.00, 1058.40, 1151.40, 877.80,
      1046.70, 1179.30, 1402.50, 1580.10, 995.10, 1144.20, 1345.20, 847.80, 987.30, 1246.50,
      1554.30, 1677.60, 1742.10, 1899.90, 1899.90, 1899.90, 1899.90};
  // Partial BAH rates in order of ascending pay grade.
  private static final double[] PARTIAL_BAH_RATES =
      {6.90, 7.20, 7.80, 8.10, 8.70, 9.90, 12.00, 15.30, 18.60, 13.80, 15.90,
          20.70, 25.20, 25.20, 13.20, 17.70, 22.20, 13.20, 17.70, 22.20, 26.70,
          33.00, 39.60, 50.70, 50.70, 50.70, 50.70};

  /**
   * Constructor for a new type 1 BAH (with or without dependents) to be stored in the type 1
   * HashTableMaps.
   * @param payGrade the pay grade associated with the new BAH
   * @param mha the MHA associated with the new BAH
   * @param monthlyPay the monthly pay associated with the new BAH
   */
  private BAH(Grade payGrade, String mha, double monthlyPay) {
    this.monthlyPay = monthlyPay;
    this.key = mha + payGrade.toString();
  }

  /**
   * Gets the monthly type 2 BAH rate for servicemembers with dependents.
   * @param payGrade the pay grade of the servicemember
   * @return the monthly type 2 BAH rate associated with the provided Grade.
   */
  public static double getType2BAHWithDependents(Grade payGrade) {
    return TYPE_2_WITH_DEPENDENTS_RATES[payGrade.toInt()];
  }
  /**
   * Gets the monthly type 2 BAH rate for servicemembers without dependents.
   * @param payGrade the pay grade of the servicemember
   * @return the monthly type 2 BAH rate associated with the provided Grade.
   */
  public static double getType2BAHWithoutDependents(Grade payGrade) {
    return TYPE_2_WITHOUT_DEPENDENTS_RATES[payGrade.toInt()];
  }

  /**
   * Gets the monthly partial BAH rate for servicemembers with government-provided housing.
   * @param payGrade the pay grade of the servicemember
   * @return the monthly partial BAH rate associated with the provided Grade
   */
  public static double getPartialBAH(Grade payGrade) {
    return PARTIAL_BAH_RATES[payGrade.toInt()];
  }

///////////////////////////////////////// BAH Type 1 ///////////////////////////////////////////////

  // Stores data for BAH Type 1 with dependents
  private static HashTableMap<String, BAH> withDependentsBAHReference
      = new HashTableMap<>(10000, 0.95);
  // Stores data for BAH Type 1 without dependents
  private static HashTableMap<String, BAH> withoutDependentsBAHReference
      = new HashTableMap<>(10000, 0.95);

  /**
   * Loads all data from outside files required for calculation of BAH
   */
  public static void loadBAH() {
    // Load zip code to MHA mappings
    loadZipCodes();
    // Load BAH Type 1 data for servicemembers with and without dependents
    loadBAHHelper("Data/BAH_Rates_With_Dependents.txt", withDependentsBAHReference);
    loadBAHHelper("Data/BAH_Rates_Without_Dependents.txt", withoutDependentsBAHReference);
  }

  /**
   * Loads BAH Type 1 data from the specified file into the specified HashTableMap
   * @param filePathname the filePath of the BAH Type 1 data file
   * @param hashTableForBAH the HashTableMap to which the data from the specified file will be
   *                        loaded
   */
  private static void loadBAHHelper(
      String filePathname, HashTableMap<String, BAH> hashTableForBAH) {
    File bahData = new File(filePathname);
    try (Scanner fileInput = new Scanner(bahData)) {
      // Data is comma delimited
      fileInput.useDelimiter(",");
      String tempMHA;
      BAH tempBAH;
      // Each line starts with an MHA and is followed by monthly pay values (doubles) for Grades
      // in ascending order
      while (fileInput.hasNextLine()) {
        tempMHA = fileInput.next();
        for (int i = 0; i < 27; i++) {
          // Read each value and put it into the applicable HashTableMap
          tempBAH = new BAH(new Grade(i), tempMHA, fileInput.nextDouble());
          hashTableForBAH.put(tempBAH.key, tempBAH);
        }
        fileInput.nextLine();
      }
    } catch (FileNotFoundException e) {
      System.out.println(bahData + " could not be located!");
    }
  }

  /**
   * Gets the monthly BAH Type 1 pay value associated with the provided values.
   * @param mha the servicemember's MHA
   * @param payGrade the servicemember's Grade
   * @param hasDependents whether the servicemember has dependents
   * @return the monthly BAH Type 1 pay value associated with the provided value.
   */
  public static double getBAHType1(String mha, Grade payGrade, boolean hasDependents) {
    if (hasDependents) {
      return getBAHWithDependents(mha, payGrade);
    } else {
      return getBAHWithoutDependents(mha, payGrade);
    }
  }

  /**
   * Gets the monthly BAH Type 1 pay value associated with the provided MHA and Grade for a
   * servicemember with dependents
   * @param mha the servicemember's MHA
   * @param payGrade the servicemember's Grade
   * @return the monthly BAH Type 1 pay value associated with the provided MHA and Grade
   */
  private static double getBAHWithDependents(String mha, Grade payGrade) {
    // Concatenate MHA and payGrade to determine the key of the BAH and search the HashTableMap
    return withDependentsBAHReference.get(mha + payGrade.toString()).monthlyPay;
  }
  /**
   * Gets the monthly BAH Type 1 pay value associated with the provided MHA and Grade for a
   * servicemember without dependents
   * @param mha the servicemember's MHA
   * @param payGrade the servicemember's Grade
   * @return the monthly BAH Type 1 pay value associated with the provided MHA and Grade
   */
  private static double getBAHWithoutDependents(String mha, Grade payGrade) {
    // Concatenate MHA and payGrade to determine the key of the BAH and search the HashTableMap
    return withoutDependentsBAHReference.get(mha + payGrade.toString()).monthlyPay;
  }

/////////////////////////////////// Zip Code to MHA ////////////////////////////////////////////////

  // Contains mappings from zip codes to MHAs, using zip code as key
  private static HashTableMap<Integer, Locality> zipCodeMHAReference
      = new HashTableMap<>(50000, 0.95);

  /**
   * Loads mappings from zip codes to MHAs from data file into applicable HashTableMap.
   */
  private static void loadZipCodes() {
    File zipCodeData = new File("Data/ZIP_to_MHA_Code.txt");
    try (Scanner fileInput = new Scanner(zipCodeData)) {
      // Each line starts with a zip code and ends with an MHA. Load each line as data
      int tempZip;
      while (fileInput.hasNextLine()) {
        tempZip = fileInput.nextInt();
        zipCodeMHAReference.put(tempZip, new Locality(tempZip, fileInput.next()));
        fileInput.nextLine();
      }

    } catch (FileNotFoundException e) {
      System.out.println(zipCodeData + " could not be located!");
    }
  }

  /**
   * Finds the MHA associated with the provided 5-digit zip code.
   * @param zipCode The desired zip code to search for.
   * @return The MHA associated with the provided 5-digit zip code.
   * @throws java.util.NoSuchElementException if the provided zip code does not have a mapping
   */
  public static String getMHA(int zipCode) {
    // Check HashTableMap for provided zipcode and return associated MHA
    return zipCodeMHAReference.get(zipCode).getMHA();
  }
}
