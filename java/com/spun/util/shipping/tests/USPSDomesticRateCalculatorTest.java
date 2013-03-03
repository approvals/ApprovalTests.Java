package com.spun.util.shipping.tests;

import java.util.Collection;
import java.util.Iterator;
import junit.framework.TestCase;
import com.spun.util.shipping.Rate;
import com.spun.util.shipping.USPSDomesticRateCalculator;

public class USPSDomesticRateCalculatorTest extends TestCase
{
  /***********************************************************************/
  public void ptestGetRates()
  {
    USPSDomesticRateCalculator process = new USPSDomesticRateCalculator();
    Collection<Rate> rates = process.getRates(USPSDomesticRateCalculator.Service.PRIORITY, "10022", "20008", 10, 5,
        USPSDomesticRateCalculator.Container.FLAT_RATE_BOX, USPSDomesticRateCalculator.Size.REGULAR, false);
    assertEquals("Number of Results", 2, rates.size());
    Iterator iter = rates.iterator();
    Rate rate = (Rate) iter.next();
    assertEquals("First rate", 7.70, rate.getRate());
    assertEquals("First Service", "Priority Mail Flat Rate Box (11.25\" x 8.75\" x 6\")", rate.getMailService());
  }
  /***********************************************************************/
  public void ptestScripted()
  {
    USPSDomesticRateCalculator process = new USPSDomesticRateCalculator();
    Collection<Rate> rates = process.getRates(USPSDomesticRateCalculator.Service.ALL, "10022", "20008", 10, 5, null,
        USPSDomesticRateCalculator.Size.LARGE, true);
    assertEquals("Number of Results", 6, rates.size());
    Iterator iter = rates.iterator();
    Rate rate = (Rate) iter.next();
    assertEquals("First rate", 39.20, rate.getRate());
    assertEquals("First Service", "Express Mail to PO Addressee", rate.getMailService());
  }
  /***********************************************************************/
  public void testNothing() throws Exception
  {
    
  }
  /***********************************************************************/
  /***********************************************************************/
}