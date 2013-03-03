package com.spun.util.creditcards.paypal;

import java.util.HashMap;
import java.util.Map;
import com.spun.util.NumberUtils;
import com.spun.util.StringUtils;
import com.spun.util.creditcards.CreditCardConfiguration;
import com.spun.util.creditcards.CreditCardResponse;

public class PaypalResponseTranslator
{
  public static CreditCardResponse translateResponse(String originalResponse, CreditCardConfiguration config)
  {
    Map<String, String> parts = parse(originalResponse);
    CreditCardResponse response = new CreditCardResponse();
    
    
    response.setAddressValid("Y".equalsIgnoreCase(parts.get("AVSADDR")));
    response.setCardSecurityCodeValid("Y".equalsIgnoreCase(parts.get("CVV2MATCH")));
    response.setZipValid("Y".equalsIgnoreCase(parts.get("AVSZIP")));
    response.setAuthorizationCode(parts.get("AUTHCODE"));
    response.setPaymentReferenceId(parts.get("PNREF"));
    response.setResult(NumberUtils.load(parts.get("RESULT"), -1));
    response.setResponseMessage(parts.get("RESPMSG"));
    response.setApproved(response.getResult() == 0);
    response.setOriginalResult(NumberUtils.load(parts.get("ORIGRESULT"), -1));
    return response;
  }
  private static Map<String, String> parse(String originalResponse)
  {
    Map<String, String> props = new HashMap<String, String>();
    String parts[] = StringUtils.split(originalResponse, "&");
    for (int i = 0; i < parts.length; i++)
    {
      int place = parts[i].indexOf("=");
      if (place == -1)
      {
        throw new Error("Invalid Response '" + parts[i] + "'");
      }
      else
      {
        props.put(parts[i].substring(0, place), parts[i].substring(place + 1));
      }
    }
    return props;
  }
}
