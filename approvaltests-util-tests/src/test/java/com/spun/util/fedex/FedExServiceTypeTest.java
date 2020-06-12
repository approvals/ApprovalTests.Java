package com.spun.util.fedex;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

public class FedExServiceTypeTest
{
  @Test
  public void test() throws Exception
  {
    assertEquals("FedEx Ground Home Delivery", FedExServiceType.FedEx_Ground_Home_Delivery.toString());
    assertTrue(FedExServiceType.FedEx_International_Economy.isInternational());
    assertFalse(FedExServiceType.FedEx_Ground.isInternational());
    assertEquals(5, FedExServiceType.getInternationalCodes().length);
  }
  @Test
  public void testNationalCodes() throws Exception
  {
    Approvals.verifyAll("Code", FedExServiceType.getNationalCodes());
  }
}
