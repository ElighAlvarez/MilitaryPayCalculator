// --== CS400 File Header Information ==--
// Name: Eligh Alvarez
// Email: ealvarez2@wisc.edu
// Notes to Grader: None
/**
 * Contains data and methods for calculating 2022 Basic Allowance for Subsistence (BAS).
 */
public class BAS {
  private static final double ENLISTED_BAS_RATE = 406.98; // The BAS rate for enlisted personnel
  private static final double OFFICER_BAS_RATE = 280.29;  // The BAS rate for officer personnel

  /**
   * Returns the monthly BAS rate associated with the provided Grade.
   * @param payGrade the Grade for which to determine BAS
   * @return the monthly BAS rate associated with the provided Grade
   */
  public static double getBAS(Grade payGrade) {
    return payGrade.toInt() < 9 ? ENLISTED_BAS_RATE : OFFICER_BAS_RATE;
  }
}
