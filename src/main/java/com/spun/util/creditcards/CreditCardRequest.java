package com.spun.util.creditcards;

public class CreditCardRequest
{
  private double amount;
  private double salesTax;
  private String address;
  private String creditCardNumber;
  private String cardSecurityCode;
  private String expirationDate;
  private String orderNumber;
  private String resellerId;
  private String zipCode;
  private String paymentReferenceId;
  private String transactionType;
  
  public String getAddress()
  {
    return address;
  }
  public void setAddress(String address)
  {
    this.address = address;
  }
  public double getAmount()
  {
    return amount;
  }
  public void setAmount(double amount)
  {
    this.amount = amount;
  }
  public String getCardSecurityCode()
  {
    return cardSecurityCode;
  }
  public void setCardSecurityCode(String cardSecurityCode)
  {
    this.cardSecurityCode = cardSecurityCode;
  }
  public String getCreditCardNumber()
  {
    return creditCardNumber;
  }
  public void setCreditCardNumber(String creditCardNumber)
  {
    this.creditCardNumber = creditCardNumber;
  }
  public String getExpirationDate()
  {
    return expirationDate;
  }
  public void setExpirationDate(String expirationDate)
  {
    this.expirationDate = expirationDate;
  }
  public String getOrderNumber()
  {
    return orderNumber;
  }
  public void setOrderNumber(String orderNumber)
  {
    this.orderNumber = orderNumber;
  }
  public String getPaymentReferenceId()
  {
    return paymentReferenceId;
  }
  public void setPaymentReferenceId(String paymentReferenceId)
  {
    this.paymentReferenceId = paymentReferenceId;
  }
  public String getResellerId()
  {
    return resellerId;
  }
  public void setResellerId(String resellerId)
  {
    this.resellerId = resellerId;
  }
  public double getSalesTax()
  {
    return salesTax;
  }
  public void setSalesTax(double salesTax)
  {
    this.salesTax = salesTax;
  }
  public String getTransactionType()
  {
    return transactionType;
  }
  public void setTransactionType(String transactionType)
  {
    this.transactionType = transactionType;
  }
  public String getZipCode()
  {
    return zipCode;
  }
  public void setZipCode(String zipCode)
  {
    this.zipCode = zipCode;
  }
}
