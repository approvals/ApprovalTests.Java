package com.spun.util.fedex.tests;

import junit.framework.TestCase;

import org.approvaltests.Approvals;

import com.spun.util.fedex.FedExServiceType;

public class FedExServiceTypeTest extends TestCase
{
  public void test() throws Exception
  {
    assertEquals("FedEx Ground Home Delivery" , FedExServiceType.FedEx_Ground_Home_Delivery.toString());
    assertEquals(true, FedExServiceType.FedEx_International_Economy.isInternational());
    assertEquals(false, FedExServiceType.FedEx_Ground.isInternational());
   
    assertEquals(5, FedExServiceType.getInternationalCodes().length);
    
  }
  public void testNationalCodes() throws Exception
  {
    Approvals.verifyAll("Code", FedExServiceType.getNationalCodes());
  }
}
