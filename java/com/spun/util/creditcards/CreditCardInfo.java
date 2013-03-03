package com.spun.util.creditcards;

import com.spun.util.StringUtils;

public class CreditCardInfo
{
  private CreditCardHolder holder = null;
  /***********************************************************************/
  public CreditCardInfo(CreditCardHolder holder)
  {
    this.holder = holder;
  }
  /************************************************************************/
  public String getExpirationDateString()
  {
    return CreditCardUtils.getExpirationDateString(holder.getCreditCardExpirationDate());
  }
  /************************************************************************/
  public String getCreditCardLast4Digits()
  {
    return holder.getCreditCardNumber() == null ? "????" : holder.getCreditCardNumber().substring(holder.getCreditCardNumber().length() - 4); // Zero Indexed   
  }
  /************************************************************************/
  public String getCreditCardSecurity()
  {
    return holder.getCreditCardNumber() == null ? "????-????-????-????" : holder.getCreditCardNumber().substring(0, 4) + "-XXXX-XXXX-" + holder.getCreditCardNumber().substring(holder.getCreditCardNumber().length() - 4); // Zero Indexed   
  }
  /************************************************************************/
  public String getPaymentTypeDetails(String defaultCreditCard)
  {
    String type = holder.getPaymentType();
    if (CreditCardHolder.CREDIT_CARD.equals(type))
    {
      type = getCreditCardType(defaultCreditCard);
    }
    return type;
  }
  /************************************************************************/
  public String getPaymentTypeDetails()
  {
    return getPaymentTypeDetails(CreditCardHolder.VISA);
  }
  /************************************************************************/
  public String getCreditCardExpirationMonth()
  {
    if (!CreditCardHolder.CREDIT_CARD.equals(holder.getPaymentType()) || StringUtils.isEmpty(holder.getCreditCardExpirationDate())) { return null; }
    String split = holder.getCreditCardExpirationDate();
    return (split.length() > 2) ? split.substring(0, 2) : null;
  }
  /************************************************************************/
  public String getCreditCardExpirationYear()
  {
    if (!CreditCardHolder.CREDIT_CARD.equals(holder.getPaymentType()) || StringUtils.isEmpty(holder.getCreditCardExpirationDate())) { return null; }
    String split = holder.getCreditCardExpirationDate();
    return (split.length() > 2) ? split.substring(2) : null;
  }
  /************************************************************************/
  public String getCreditCardType()
  {
    return getCreditCardType("Unknown");
  }
  /************************************************************************/
  public String getCreditCardType(String defaultCreditCard)
  {
    if (!CreditCardHolder.CREDIT_CARD.equals(holder.getPaymentType())) { return null; }
    String text = defaultCreditCard;
    String number = holder.getCreditCardNumber();
    char start = (StringUtils.isEmpty(number)) ? 0 : number.charAt(0);
    switch (start)
    {
      case '3' :
        text = CreditCardHolder.AMERICAN_EXPRESS;
        break;
      case '4' :
        text = CreditCardHolder.VISA;
        break;
      case '5' :
        text = CreditCardHolder.MASTER_CARD;
        break;
      case '6' :
        text = CreditCardHolder.DISCOVER;
        break;
    }
    return text;
  }
  /***********************************************************************/
  public boolean isCreditCardNumberValid()
  {
    return CreditCardUtils.isCreditCardNumberValid(this.holder.getCreditCardNumber(), holder.getAcceptableCreditCardTypes());
  }
  /***********************************************************************/
  public boolean isCreditCardExpirationDateValid()
  {
    return CreditCardUtils.isExpirationDateValid(this.holder.getCreditCardExpirationDate());
  }
  /***********************************************************************/
  /***********************************************************************/
}