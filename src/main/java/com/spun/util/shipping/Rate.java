package com.spun.util.shipping;

import java.util.Collection;

public class Rate
{
  
//  private String size;  
  private String mailService;    
  private double      rate;
  /***********************************************************************/
  public Rate(String mailService, double rate)
  {
    this.mailService = mailService;
    this.rate = rate;
  }
  /***********************************************************************/
  public String getMailService()
  {
    return mailService;
  }
  /***********************************************************************/
  public double getRate()
  {
    return rate;
  }
  /***********************************************************************/
  public static Rate[] toArray(Collection<Rate> rates)
  {
    if (rates == null) { return new Rate[0]; }
    Rate array[] = new Rate[rates.size()];
    java.util.Iterator iterator = rates.iterator();
    int i = 0;
    while (iterator.hasNext())
    {
      Object object = iterator.next();
      if (object instanceof Rate)
      {
        array[i++] = (Rate) object;
      }
      else
      {
        throw new Error("toArray[" + i + "] is not an instance of Rate but a " + object.getClass().getName());
      }
    }
    return array;
  }
  /***********************************************************************/
  /***********************************************************************/
}
