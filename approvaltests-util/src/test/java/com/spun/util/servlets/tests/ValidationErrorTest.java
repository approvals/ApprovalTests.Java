package com.spun.util.servlets.tests;

import junit.framework.TestCase;
import com.spun.util.servlets.ValidationError;
import com.spun.util.servlets.ValidationTracker;

public class ValidationErrorTest extends TestCase
{
  private enum Asserts {
    A, B, C, D
  }
  private String asserts[]  = {"a", "b", "c", "d", "e"};
  private String errorCause = "Error Cuase";
  /***********************************************************************/
  public void testIsOnlyProblem()
  {
    //2
    ValidationError error = new ValidationError(Asserts.values());
    error.set(Asserts.A, false, errorCause);
    error.set(Asserts.B, false, errorCause);
    assertFalse(error.isOnlyProblem(Asserts.B));
    // 1 not it
    error = new ValidationError(Asserts.values());
    error.set(Asserts.B, false, errorCause);
    assertFalse(error.isOnlyProblem(Asserts.A));
    // 1 

    error = new ValidationError(Asserts.values());
    error.set(Asserts.A, false, errorCause);
    assertTrue(error.isOnlyProblem(Asserts.A));
}
  /***********************************************************************/
  public void testValidError()
  {
    ValidationError error = createValidationError(0);
    error.add("Sub", createValidationError(0));
    assertTrue("isOk", error.isOk());
    for (int i = 0; i < asserts.length; i++)
    {
      assertTrue("isValid(" + asserts[i] + ")", error.isValid(asserts[i]));
    }
  }
  /************************************************************************/
  public void testInvaildError()
  {
    ValidationError error = createValidationError(3);
    error.add("Sub", createValidationError(2));
    assertFalse("isOk", error.isOk());
    assertFalse("isValid(a)", error.isValid(asserts[0]));
    assertFalse("isValid(sub.a)", error.isValid("Sub.a"));
    assertTrue("isValid(e)", error.isValid("e"));
    assertEquals("size", 5, error.size());
    try
    {
      error.isValid("fg");
      fail("Didn't catch fg as an assertion");
    }
    catch (Error e)
    {
      // DO NOTHING
    }
  }
  /************************************************************************/
  public void testIterator()
  {
    ValidationError error = createValidationError(3);
    error.add("Sub", createValidationError(2));
    ValidationTracker iterator = error.getTracker();
    assertFalse("getDescription(a)", iterator.isValid("a"));
    assertFalse("getDescription(Sub.b)", iterator.isValid("Sub.b"));
    assertTrue("getDescription(e)", iterator.isValid("e"));
    assertEquals("Remaining 3", 3, iterator.getRemainingErrors().length);
  }
  /***********************************************************************/
  private ValidationError createValidationError(int errorCount)
  {
    ValidationError error = new ValidationError(asserts);
    for (int i = 0; i < errorCount; i++)
    {
      error.setError(asserts[i], true, errorCause);
    }
    return error;
  }
  /***********************************************************************/
}
