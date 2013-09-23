/*
 * Created on Dec 10, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.spun.util.fedex;

public enum FedExPaymentType {
  Bill_Sender("1"), Bill_Recipient("2"), Bill_3rd_Party("3");
  private String fullName;
  private String serviceCode;
  /***********************************************************************/
  private FedExPaymentType(String serviceCode)
  {
    this.serviceCode = serviceCode;
    this.fullName = this.toString().replace("_", " ");
  }
  /***********************************************************************/
  @Override
  public String toString()
  {
    return fullName == null ? super.toString() : fullName;
  }
  /***********************************************************************/
  public String getServiceCode()
  {
    return serviceCode;
  }
  /***********************************************************************/
  /***********************************************************************/
}
