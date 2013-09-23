package com.spun.util.creditcards.paypal.tests;

import java.util.Date;
import junit.framework.TestCase;
import com.spun.util.Tuple;
import com.spun.util.creditcards.CreditCardConfiguration;
import com.spun.util.creditcards.CreditCardParameters;
import com.spun.util.creditcards.CreditCardRequest;
import com.spun.util.creditcards.CreditCardUtils;
import com.spun.util.creditcards.paypal.PaypalRequestTranslator;

public class PaypalRequestTranslatorTest extends TestCase
{
  public void testParameterTranslation() throws Exception
  {
    Tuple<CreditCardRequest, CreditCardParameters> both = createCreditCardRequest();
    CreditCardConfiguration config = new CreditCardConfiguration(null,0,null,null,null,null,null);
    assertEquals(both.getSecond().getParameterString(), new PaypalRequestTranslator().translateRequest(config,both.getFirst()));
  }
  private Tuple<CreditCardRequest, CreditCardParameters> createCreditCardRequest()
  {
    CreditCardParameters params = new CreditCardParameters();
    CreditCardRequest request = new CreditCardRequest();
    request.setAmount(100.00);
    request.setAddress("123 Fake Street");
    request.setCreditCardNumber("4111111111111111");
    request.setCardSecurityCode("123");
    request.setExpirationDate(CreditCardUtils.getExpirationDate(new Date()));
    request.setOrderNumber("F128-321");
    request.setResellerId("754382");
    request.setSalesTax(5.00);
    request.setZipCode("92109");
    request.setPaymentReferenceId("89342");
    request.setTransactionType(CreditCardParameters.AUTHORIZATION);
    // Sync Params
    params.setAmount(request.getAmount());
    params.setAddress(request.getAddress());
    params.setCreditCardNumber(request.getCreditCardNumber());
    params.setCardSecurityCode(request.getCardSecurityCode());
    params.setExpirationDate(request.getExpirationDate());
    params.setOrderNumber(request.getOrderNumber());
    params.setResellerId(request.getResellerId());
    params.setSalesTax(request.getSalesTax());
    params.setZipCode(request.getZipCode());
    params.setPaymentReferenceId(request.getPaymentReferenceId());
    params.setTransactionType(request.getTransactionType());
    return new Tuple<CreditCardRequest, CreditCardParameters>(request,params);
  }
}
