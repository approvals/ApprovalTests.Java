package com.spun.util.creditcards;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.Verisign.payment.PFProAPI;
import com.spun.util.StringUtils;
import com.spun.util.logger.SimpleLogger;

public class CreditCardUtils
{
  private static CreditCardProcessor processor = new CreditCardProcessor();
  /***********************************************************************/
  public static void setCreditCardProcessor(CreditCardProcessor processor)
  {
    CreditCardUtils.processor = processor;
  }
  /***********************************************************************/
  /**
   * @ Param date Format MMYY
   **/
  public static boolean isExpirationDateValid(String date)
  {
    if ((date == null) || (date.length() != 4)) { return false; }
    int cMonth = Integer.parseInt(date.substring(0, 2));
    int cYear = Integer.parseInt("20" + date.substring(2, 4));
    Calendar calendar = new GregorianCalendar();
    Date trialTime = new Date();
    calendar.setTime(trialTime);
    int month = calendar.get(Calendar.MONTH) + 1; // It's zero indexed
    int year = calendar.get(Calendar.YEAR);
    SimpleLogger.variable("credit, real = [" + cMonth + "/" + cYear + "], [" + month + "/" + year + "]");
    return ((year < cYear) || ((year == cYear) && (month <= cMonth)));
  }
  /************************************************************************/
  /**
   * @ Param date Format MMYY
   **/
  public static String getExpirationDateString(String date)
  {
    if ((date == null) || (date.length() != 4)) { return "invalid Date - '" + date + "'"; }
    return date.substring(0, 2) + "/20" + date.substring(2);
  }
  /************************************************************************/
  public static String getExpirationDate(Date date)
  {
    Calendar calendar = new GregorianCalendar();
    calendar.setTime(date);
    String month = StringUtils.padNumber(calendar.get(Calendar.MONTH) + 1, 2);
    String year = StringUtils.padNumber(calendar.get(Calendar.YEAR), 2).substring(2);
    return month + year;
  }
  /************************************************************************/
  public static boolean isCreditCardNumberValid(String number, String[] allowedCreditCardTypes)
  {
    if ((number == null) || (number.length() == 0)) { return false; }
    number = StringUtils.stripNonNumeric(number);
    char firstChar = number.charAt(0);
    switch (firstChar)
    {
      case '4' :
        return (number.length() == 16) && StringUtils.isIn(CreditCardHolder.VISA, allowedCreditCardTypes);
      case '5' :
        return (number.length() == 16) && StringUtils.isIn(CreditCardHolder.MASTER_CARD, allowedCreditCardTypes);
      case '6' :
        return (number.length() == 16) && StringUtils.isIn(CreditCardHolder.DISCOVER, allowedCreditCardTypes);
      case '3' :
        return (number.length() == 15)
            && StringUtils.isIn(CreditCardHolder.AMERICAN_EXPRESS, allowedCreditCardTypes);
      default :
        return false;
    }
  }
  /************************************************************************/
  public static boolean isCardSecurityCodeValid(String cardSecurityCode, String creditCard)
  {
    if (StringUtils.isEmpty(cardSecurityCode)) { return true; }
    char firstChar = creditCard.charAt(0);
    SimpleLogger.variable(String.format("security code: %s, cc#: %s", cardSecurityCode, firstChar));
    switch (firstChar)
    {
      case '4' :
        return (cardSecurityCode.length() == 3);
      case '5' :
        return (cardSecurityCode.length() == 3);
      case '6' :
        return false;
      case '3' :
        return (cardSecurityCode.length() == 4);
      default :
        return false;
    }
  }
  /************************************************************************/
  public static String constructVerifiedCreditCardNumber(String number)
  {
    String ver = StringUtils.stripNonNumeric(number);
    if (CreditCardUtils.isCreditCardNumberValid(ver, CreditCardHolder.ALL))
    {
      char firstChar = number.charAt(0);
      switch (firstChar)
      {
        case '4' :
        case '5' :
        case '6' :
          ver = ver.substring(0, 4) + "-" + ver.substring(4, 8) + "-" + ver.substring(8, 12) + "-"
              + ver.substring(12, 16);
          break;
        case '3' :
          ver = ver.substring(0, 4) + "-" + ver.substring(4, 10) + "-" + ver.substring(10, 15);
          break;
      }
      return ver;
    }
    else
    {
      return number;
    }
  }
  /***********************************************************************/
  public static TransactionResponse processCreditCard(CreditCardConfiguration configuration,
      CreditCardParameters params)
  {
    return processCreditCard(configuration.getHostAddress(), configuration.getHostPort(),
        configuration.getCertificatePath(), params);
  }
  /***********************************************************************/
  public static TransactionResponse processCreditCard(String hostAddress, int hostPort, String certificatePath,
      CreditCardParameters params)
  {
    PFProAPI payment = new PFProAPI();
    payment.CreateContext(hostAddress, hostPort, 30, "", 0, "", "");
    payment.SetCertPath(certificatePath);
    return processor.process(params, payment);
  }
  /***********************************************************************/
  /***********************************************************************/
}