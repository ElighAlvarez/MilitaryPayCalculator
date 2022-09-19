// --== CS400 File Header Information ==--
// Name: Eligh Alvarez
// Email: ealvarez2@wisc.edu
// Notes to Grader: None
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Contains methods to run the user-interactive front end of this 2022 Military Pay Calculator.
 * @author Eligh Alvarez
 */
public class PayCalculatorAppDriver {

  /**
   * Calls methods to load 2022 military pay information and starts the front end of this program.
   * @param args (unused)
   */
  public static void main(String[] args) {
    // Call methods to load data from text files
    BasePay.loadBasePay();
    BAH.loadBAH();

    // Start user-interactive interface and repeats until the user indicates that they are done
    while(getMonthlyPay());

  }

  /**
   * Prompts a user for all necessary information to determine their monthly pay. The
   * calculation includes Basic Pay, Basic Allowance for Housing (BAH), Basic Allowance for
   * Subsistence (BAS), and any user-specified additional pays. Prints the determined monthly pay
   * as an itemized list and as a total.
   * @return True if the user indicates that they would like to repeat these calculations for new
   * inputs. False otherwise.
   */
  public static boolean getMonthlyPay() {
    // Create a Scanner for user input
    Scanner userInput = new Scanner(System.in);

    // Declare user information variables
    Grade payGrade = null;
    int yearsOfService;
    String mha;
    boolean isOnShortOrders;
    boolean hasDependents;
    boolean hasGovernmentQuarters = false;

    // Declare pay amount variables
    double basePay = -1.0;
    double bah = -1.0;
    double bas = -1.0;
    double specialPay = -1.0;
    ArrayList<String> specialPayKeys;
    double total = 0.0;

    // Welcome the user
    System.out.print("\nTo calculate your monthly pay:\n\n");

    // Determine base pay. If a pay comes back as 0.0, the grade/yearsOfService combination is
    // impossible so try again.
    while (basePay < 10.0) {
      // Prompt user for payGrade
      payGrade = getValidGrade(userInput);
      // Prompt user for yearsOfService
      yearsOfService = getValidYears(userInput);
      // Find base pay associated with payGrade and yearsOfService
      basePay = BasePay.getMonthlyPay(payGrade, yearsOfService);
      if (basePay < 10.0) {
        System.out.println("Grade and years of service combination is invalid. Please try again.");
      }
    }

    // Determine BAH
    System.out.print("Do you have dependents? (y/n, defaults to n with other inputs): ");
    hasDependents = userInput.nextLine().toUpperCase().charAt(0) == 'Y';
    System.out.print("Are your current orders longer than 30 days? (y/n, defaults to y with "
                           + "other inputs): ");
    isOnShortOrders = userInput.nextLine().toUpperCase().charAt(0) == 'N';
    // If user is on orders shorter than 30 days, they qualify for Type 2 BAH
    if (isOnShortOrders) {
      bah = hasDependents ?
          BAH.getType2BAHWithDependents(payGrade) : BAH.getType2BAHWithoutDependents(payGrade);
    } else { // If user is on orders longer than 30 days, they qualify for either partial or type 1
      // If user doesn't have dependents, check for gov. quarters.
      if (!hasDependents) {
        System.out.print("Do you have government-provided quarters? (y/n, defaults to n with "
                               + "other inputs): ");
        hasGovernmentQuarters = userInput.nextLine().toUpperCase().charAt(0) == 'Y';
      }
      // If user has gov. quarters, they get Partial BAH, otherwise they get Type 1 BAH
      if (hasGovernmentQuarters) {
        bah = BAH.getPartialBAH(payGrade);
      } else {
        // Prompt user for zipCode
        mha = getMHAFromZipCode(userInput);
        bah = BAH.getBAHType1(mha, payGrade, hasDependents);
      }
    }

    // Determine BAS
    bas = BAS.getBAS(payGrade);

    // Determine Special Pays
    specialPayKeys = getSpecialPayKeys(userInput);

    // Print base pay, bah, and bas
    total = basePay + bah + bas;
    System.out.println("\nBase Pay: $" + String.format("%.2f", basePay));
    System.out.println("BAH:      $" + String.format("%.2f", bah));
    System.out.println("BAS:      $" + String.format("%.2f", bas));
    // Print each special pay
    for (String key : specialPayKeys) {
      specialPay = SpecialPay.getSpecialMonthlyPay(key);
      total += specialPay;
      System.out.println(key + ": $" + String.format("%.2f", specialPay));
    }
    // Print total monthly pay
    System.out.println("\nTotal:    $" + String.format("%.2f", total));

    // Ask user if they would like to repeat
    System.out.print("\nWould you like to calculate pay for another person? (y/n, defaults to n "
                         + "with other inputs): ");
    return userInput.nextLine().toUpperCase().charAt(0) == 'Y';
  }

  /**
   * Gets a list of special pays from a user. If the user specifies a new pay type, a monthly pay
   * value to associate with the pay is acquired from the user. It is then added to a
   * HashTableMap for later use. If a previously defined pay type is specified by the user, the
   * already-stored pay is used.
   * @param userInput The Scanner currently being used for user input.
   * @return A list of keys associated with the user-specified pay types. (For use with
   * SpecialPay.getSpecialMonthlyPay)
   */
  public static ArrayList<String> getSpecialPayKeys(Scanner userInput) {
    ArrayList<String> specialPayKeys = new ArrayList<>();
    // Ask user about initial pay addition
    System.out.print("Would you like to add a pay other than Base Pay, BAH, and BAS? (y/n, "
                           + "defaults to n with other inputs): ");
    boolean addAnotherPay = userInput.nextLine().toUpperCase().charAt(0) == 'Y';
    String description;
    double monthlyPay;
    // Continue to add pays until the user is done adding pays
    while (addAnotherPay) {
      System.out.print("Provide a description for the pay: ");
      description = userInput.nextLine();
      // Check the SpecialPay HashTableMap for the description. If it exists, add it to the
      // ArrayList to return, otherwise a NoSuchElementException is thrown.
      try {
        SpecialPay.getSpecialMonthlyPay(description);
        specialPayKeys.add(description);
      } catch (NoSuchElementException e) {
        // If this Exception is thrown, the description does not exist in the HashTableMap yet so
        // the user is prompted for an associated monthly pay amount and the pay is added to the
        // HashTableMap for future reference
        monthlyPay = getValidPay(userInput);
        new SpecialPay(description, monthlyPay);
        specialPayKeys.add(description);
      }

      // See if the user has more pays to add
      System.out.print("Would you like to add another pay? (y/n, defaults to n with other "
                             + "inputs): ");
      addAnotherPay = userInput.nextLine().toUpperCase().charAt(0) == 'Y';
    }

    return specialPayKeys;
  }

  /**
   * Gets a valid (positive) pay value from a user. If an invalid input is detected, the user
   * will be prompted repeatedly until a valid pay amount is provided.
   * @param userInput The Scanner currently being used for user input.
   * @return A valid user-provided pay value.
   */
  public static double getValidPay(Scanner userInput) {
    double pay = -1.0;
    // Inputs are invalid unless they are a positive double.
    while (pay < 0.0) {
      System.out.print("Please enter the monthly amount associated with the pay: $");
      try {
        pay = userInput.nextDouble();
        if (pay < 0.0) {
          System.out.println("The provided value is invalid, try again.");
        }
      } catch (InputMismatchException e) {
        System.out.println("The provided value is invalid, try again.");
      }
      userInput.nextLine();
    }
    return pay;
  }

  /**
   * Determines the military housing area (MHA) associated with a valid zip code from a user. If
   * an invalid input is detected, the user will be prompted repeatedly until a valid zip code is
   * provided.
   * @param userInput The Scanner currently being used for user input.
   * @return The MHA associated with a valid user-provided zip code.
   */
  public static String getMHAFromZipCode(Scanner userInput) {
    String mha = "";
    int zipCode;

    // Prompt user for a valid zip code until one is provided.
    while (mha.isEmpty()) {
      System.out.print("Please enter your 5-digit zip code: ");
      try {
        zipCode = userInput.nextInt();
        // Check zip to mha reference for provided zip code. Throws NoSuchElementException if
        // it's not found, so the zip must be invalid.
        mha = BAH.getMHA(zipCode);
      } catch (InputMismatchException e) {
        System.out.println("Input value was invalid. Try again.");
      } catch (NoSuchElementException e) {
        System.out.println("Zip Code was invalid. Try again.");
      }
      userInput.nextLine();
    }
    return mha;
  }

  /**
   * Gets a valid pay grade from a user. If an invalid input is detected, the user will be
   * prompted repeatedly until a valid number is provided.
   * @param userInput The Scanner currently being used for user input.
   * @return A valid user-provided pay grade.
   */
  public static Grade getValidGrade(Scanner userInput) {
    Grade userGrade = null;
    // Prompt user for a valid grade until one is provided
    while(userGrade == null) {
      System.out.print("Please enter your grade (E1, O3E, etc.): ");
      try {
        // Create a new Grade, if an IllegalArgumentException is thrown, the provided value is
        // invalid
        userGrade = new Grade(userInput.next());
      } catch (IllegalArgumentException ignored) {
        System.out.println("Input value was invalid. Try again.");
      }
      userInput.nextLine();
    }
    return userGrade;
  }

  /**
   * Gets a valid number of years of service (an int zero or greater) from a user. If an invalid
   * input is detected, the user will be prompted repeatedly until a valid number is provided.
   * @param userInput The Scanner currently being used for user input.
   * @return A valid user-provided number of years of military service.
   */
  public static int getValidYears(Scanner userInput) {
    int yearsOfService = -1;
    // Prompt user for a valid years of service amount (positive int) until one is provided
    while (yearsOfService < 0) {
      System.out.print("Please enter your years of service: ");
      try {
        yearsOfService = userInput.nextInt();
      } catch (InputMismatchException e) {
        System.out.println("Input value was invalid. Try again.");
      }
      userInput.nextLine();
    }
    return yearsOfService;
  }
}
