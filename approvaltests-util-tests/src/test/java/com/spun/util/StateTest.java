package com.spun.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

public class StateTest
{
  @Test
  public void testState()
  {
    String[] inputs = {"CALIFORNIA", "California", "ca", "Ca", null, "toronto", "rhode island"};
    Approvals.verifyAll("states", inputs, s -> String.format("%s -> %s", s, State.toStandardText(s)));
  }
  @Test
  public void testAll()
  {
    Approvals.verifyAll("states", State.getStringValues());
  }
  @Test
  public void testIsState()
  {
    assertTrue(State.isStateAbbreviation("ca"));
    assertTrue(State.isStateAbbreviation("CA"));
    assertFalse(State.isStateAbbreviation("US"));
  }
}
