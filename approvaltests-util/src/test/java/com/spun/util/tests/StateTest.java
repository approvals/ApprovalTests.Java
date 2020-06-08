package com.spun.util.tests;

import org.approvaltests.Approvals;
import org.junit.Test;

import com.spun.util.State;

import junit.framework.TestCase;

public class StateTest extends TestCase
{

  public void testState()
  {
    String[] strings = {"CALIFORNIA",
                        "CA",
                        "California",
                        "CA",
                        "ca",
                        "CA",
                        "Ca",
                        "CA",
                        null,
                        null,
                        "toronto",
                        "toronto",
                        "rhode island",
                        "RI"};
    for (int i = 0; i < strings.length; i += 2)
    {
      assertEquals(strings[i + 1], State.toStandardText(strings[i]));
    }
  }
  @Test
  public void testAll() throws Exception
  {
    Approvals.verifyAll("states", State.getStringValues());
  }
  public void testIsState() throws Exception
  {
    assertTrue(State.isStateAbbreviation("ca"));
    assertTrue(State.isStateAbbreviation("CA"));
    assertFalse(State.isStateAbbreviation("US"));
  }



}
