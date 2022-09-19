// --== CS400 File Header Information ==--
// Name: Eligh Alvarez
// Email: ealvarez2@wisc.edu
// Notes to Grader: None
/**
 * Contains methods to verify and reference pay grades.
 * @author Eligh Alvarez
 */
public class Grade {
  // List of valid grades in order of ascending rank
  private final static String[] VALID_GRADES = {"E1","E2","E3","E4","E5","E6","E7","E8","E9",
      "W1","W2","W3","W4","W5","O1E","O2E","O3E",
      "O1","O2","O3","O4","O5","O6","O7","O8","O9","O10"};
  private String payGrade; // The string representation of this Grade

  /**
   * Creates a new Grade with a properly formatted String input. ex: "E1", "O3E", "W2", "O10".
   * @param payGrade a String representation of the desired Grade
   * @throws IllegalArgumentException if payGrade does not represent a valid Grade.
   */
  public Grade (String payGrade) {
    if (isValidGrade(payGrade)) {
      this.payGrade = payGrade;
    } else {
      throw new IllegalArgumentException(payGrade + " is not a valid pay grade");
    }
  }

  /**
   * Creates a new Grade from an int in order of ascending rank. 0 represents E1, 26 represents O10.
   * @param payGradeInt
   * @throws IllegalArgumentException if the int does not correspond with a Grade
   */
  public Grade (int payGradeInt) {
    if (payGradeInt > -1 && payGradeInt < VALID_GRADES.length) {
      this.payGrade = VALID_GRADES[payGradeInt];
    } else {
      throw new IllegalArgumentException(payGradeInt + " is not a valid pay grade");
    }
  }

  /**
   * Checks to see if the provided String is a valid representation of a Grade.
   * @param payGrade the String to verify as a valid Grade
   * @return true if the String is a valid representation of a Grade, false otherwise.
   */
  public static boolean isValidGrade(String payGrade) {
    for (int i = 0; i < VALID_GRADES.length; i++) {
      if (VALID_GRADES[i].equals(payGrade)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Returns the int representation of this Grade from 0 to 26 in order of ascending rank.
   * @return the int representation of this Grade from 0 to 26 in order of ascending rank.
   */
  public int toInt() {
    for (int i = 0; i < VALID_GRADES.length; i++) {
      if (payGrade.equals(VALID_GRADES[i])) {
        return i;
      }
    }
    return -1;
  }

  @Override
  public String toString() {
    return payGrade;
  }
}
