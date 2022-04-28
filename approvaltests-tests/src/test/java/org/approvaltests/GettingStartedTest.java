package org.approvaltests;

import org.approvaltests.awt.AwtApprovals;
import org.approvaltests.core.Options;
import org.approvaltests.machine_specific_tests.TvGuide;
import org.approvaltests.namer.NamedEnvironment;
import org.approvaltests.namer.NamerFactory;
import org.approvaltests.reporters.FileCaptureReporter;
import org.approvaltests.reporters.UseReporter;
import org.approvaltests.reporters.windows.BeyondCompareReporter;
import org.junit.jupiter.api.Test;

import java.awt.*;

public class GettingStartedTest
{
  @Test
  void testBasicCall()
  {
    String objectToBeVerified = "Now is the time for all good coders to come to the aid of their countrypersons";
    // begin-snippet: basic_verified_call
    Approvals.verify(objectToBeVerified);
    // end-snippet
  }
  // begin-snippet: verifying_strings
  @Test
  public void testBuildString()
  {
    /* Do */
    // create a string with "Approval" and append "Tests" to it
    String s = "Approval";
    s += " Tests";
    /* Verify */
    // Verify the resulting string
    Approvals.verify(s);
  }
  // end-snippet
  // begin-snippet: verifying_objects
  @Test
  public void testObject()
  {
    /* Do */
    // create an 100 x 200 rectangle with the top corner at (5, 10)
    Rectangle objectUnderTest = new Rectangle(5, 10, 100, 200);
    /* Verify */
    // Verify the rectangle is properly defined
    Approvals.verify(objectUnderTest.toString());
  }
  // end-snippet
  @Test
  public void testObjectWithJson()
  {
    /* Do */
    // create an 100 x 200 rectangle with the top corner at (5, 10)
    Rectangle objectUnderTest = new Rectangle(5, 10, 100, 200);
    /* Verify */
    // Verify the rectangle is properly defined
    // begin-snippet: verifying_objects_with_json
    JsonApprovals.verifyAsJson(objectUnderTest);
    // end-snippet
  }
  // begin-snippet: verifying_arrays
  @Test
  public void testArray()
  {
    /* Do */
    // create a String Array and set values in the indexes
    String[] s = new String[2];
    s[0] = "Approval";
    s[1] = "Tests";
    /* Verify */
    // Verify the array
    Approvals.verifyAll("Text", s);
  }
  // end-snippet
  @Test
  public void testTvGuide()
  {
    try (NamedEnvironment namedEnvironment = NamerFactory.asOsSpecificTest())
    {
      if (!namedEnvironment.isCurrentEnvironmentValidFor("Windows_10"))
      { return; }
    }
    // begin-snippet: verifying_gui
    /* Do */
    // create a TV Guide and select a show for 3pm
    TvGuide tv = new TvGuide();
    tv.selectTime("3pm");
    /* Verify */
    // Verify the TvGuide
    AwtApprovals.verify(tv);
    // end-snippet
  }
}
