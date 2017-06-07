package com.spun.util.creditcards.paypal;

import com.spun.util.StringUtils;
import com.spun.util.creditcards.CreditCardConfiguration;
import com.spun.util.creditcards.CreditCardParameters;
import com.spun.util.creditcards.CreditCardRequest;

public class PaypalRequestTranslator
{
  public String translateRequest(CreditCardConfiguration config, CreditCardRequest request)
  {
    StringBuffer requestString = new StringBuffer();
    append(requestString, "CVV2", request.getCardSecurityCode());
    append(requestString, "STREET", formatAddress(request.getAddress()));
    append(requestString, "ORIGID", request.getPaymentReferenceId());
    append(requestString, "PONUM", request.getOrderNumber());
    append(requestString, "PARTNER", request.getResellerId());
    append(requestString, "EXPDATE", request.getExpirationDate());
    append(requestString, "TRXTYPE", request.getTransactionType());
    append(requestString, "ACCT", request.getCreditCardNumber());
    append(requestString, "TENDER", "C");
    append(requestString, "AMT", CreditCardParameters.getFormatedDouble(request.getAmount()));
    append(requestString, "ZIP", formatZipCode(request.getZipCode()));
    append(requestString, "TAXAMT", CreditCardParameters.getFormatedDouble(request.getSalesTax()));
    append(requestString, "VENDOR", config.getVenderId());
    append(requestString, "USER", config.getUserId());
    append(requestString, "PWD", config.getPassword());
    requestString.deleteCharAt(requestString.length() - 1); //remove the last '&'
    return requestString.toString();
  }
  private String formatZipCode(String zip)
  {
    if (zip == null) { return null; }
    if (9 < zip.length())
    {
      zip = zip.substring(0, 9);
    }
    return StringUtils.stripNonNumeric(zip);
  }
  private String formatAddress(String street)
  {
    if (street == null) { return null; }
    if (30 < street.length())
    {
      street = street.substring(0, 30);
    }
    return street;
  }
  private void append(StringBuffer requestString, String key, String value)
  {
    if (value == null) { return; }
    if ((value.indexOf('&') != -1) || (value.indexOf('=') != -1))
    {
      key = key + "[" + value.length() + "]";
    }
    requestString.append(key + "=" + value + "&");
  }
}
