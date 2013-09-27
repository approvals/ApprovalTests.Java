package com.spun.util.creditcards;

public interface CreditCardHolder
{
  public static final String   CREDIT_CARD      = "Credit Card";
  public static final String   MASTER_CARD      = "Master Card";
  public static final String   VISA             = "Visa";
  public static final String   DISCOVER         = "Discover";
  public static final String   AMERICAN_EXPRESS = "American Express";
  public static final String[] ALL              = {MASTER_CARD, VISA, DISCOVER, AMERICAN_EXPRESS};
  /***********************************************************************/
  public String getCreditCardExpirationDate();
  /***********************************************************************/
  public String getCreditCardNumber();
  /***********************************************************************/
  public String getPaymentType();
  /***********************************************************************/
  public String[] getAcceptableCreditCardTypes();
  /***********************************************************************/
  /***********************************************************************/
  public String getCreditCardSecurityCode();
}