package com.spun.util.creditcards;

import com.spun.util.servlets.ValidationError;

public class CreditCardPaymentRules
{
  /***********************************************************************/
  public static final String CREDIT_CARD_INFORMATION = "creditCard";
  private static String[]    asserts                 = {"creditCardNumberValid", "expirationDateValid", "cardSecurityCodeValid"};
  /***********************************************************************/
  public static ValidationError createEmpty()
  {
    return new ValidationError(asserts);
  }
  /***********************************************************************/
  public static ValidationError check(String creditCard, String expirationDate, String cardSecurityCode, String allowedCreditCards[])
  {
    ValidationError rules = new ValidationError(asserts);
    rules.setError("creditCardNumberValid", !CreditCardUtils.isCreditCardNumberValid(creditCard,allowedCreditCards), "The credit card number is invalid.");
    rules.setError("expirationDateValid", !CreditCardUtils.isExpirationDateValid(expirationDate), "The credit card expiration date is invalid.");
    rules.setError("cardSecurityCodeValid", !CreditCardUtils.isCardSecurityCodeValid(cardSecurityCode, creditCard), "The csc isnt't valid for the card.");
    return rules;
  }
  /***********************************************************************/
  public static ValidationError check(CreditCardHolder holder, String allowedCreditCards[])
  {
    return check(holder.getCreditCardNumber(), holder.getCreditCardExpirationDate(), holder.getCreditCardSecurityCode(), allowedCreditCards);
  }
  /***********************************************************************/
  /***********************************************************************/
}