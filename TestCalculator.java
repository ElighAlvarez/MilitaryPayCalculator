// --== CS400 File Header Information ==--
// Name: Eligh Alvarez
// Email: ealvarez2@wisc.edu
// Notes to Grader: None
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * A testing class for MilitaryPayCalculator using jUnit5.
 * @author Eligh Alvarez
 */
public class TestCalculator {

  /**
   * Loads necessary data from files for tests.
   */
  @BeforeAll
  public static void loadData() {
    BAH.loadBAH();
    BasePay.loadBasePay();
  }

  /**
   * Tests BAH.getMHA with an invalid zip code.
   */
  @Test
  public void testInvalidZipCode() {
    assertThrows(NoSuchElementException.class, () -> BAH.getMHA(55555));
  }

  /**
   * Tests BAH.getMHA with a valid zip code.
   */
  @Test
  public void testValidZipCode() {
    assertEquals("WI316", BAH.getMHA(53590));
  }

  /**
   * Tests SpecialPay by creating a new SpecialPay, which automatically adds it to a
   * HashTableMap, and then attempts to create another pay with the same name. Also attempts to
   * create a pay with a negative pay value.
   */
  @Test
  public void testSpecialPay() {
    new SpecialPay("Demo Pay", 150.0);
    assertEquals(150.0, SpecialPay.getSpecialMonthlyPay("Demo Pay"));
    assertThrows(IllegalArgumentException.class, () -> new SpecialPay("Demo Pay", 100.0));
    assertThrows(IllegalArgumentException.class, () -> new SpecialPay("Pay2", -2.0));
  }

  /**
   * Tests edge cases for BasePay.getMonthlyPay.
   */
  @Test
  public void testBasicPay() {
    assertEquals(16974.9, BasePay.getMonthlyPay(new Grade("O10"), 50));
    assertEquals(0.0, BasePay.getMonthlyPay(new Grade("O10"), 0));
    assertEquals(1833.3, BasePay.getMonthlyPay(new Grade("E1"), 0));
  }

  /**
   * Tests BAH.getBAHType1 with valid inputs.
   */
  @Test
  public void testBAH() {
    double actual = BAH.getBAHType1("NV211", new Grade("E5"), true);
    assertEquals(1386.00, actual);
  }

  /**
   * Tests PayCalculatorAppDriver.getValidGrade with simulated user input.
   */
  @Test
  public void testGetValidGrade() {
    Scanner input = new Scanner("442\nNot a grade\nE-3\nO2E\nE3\n");
    assertEquals("O2E", PayCalculatorAppDriver.getValidGrade(input).toString());
  }
}
