package com.spun.util.creditcards.paypal.tests;

import junit.framework.TestCase;
import com.spun.util.creditcards.CreditCardConfiguration;
import com.spun.util.creditcards.CreditCardResponse;
import com.spun.util.creditcards.TransactionResponse;
import com.spun.util.creditcards.paypal.PaypalResponseTranslator;

public class PaypalResponseTranslatorTest extends TestCase
{
  public void testParameterTranslation() throws Exception
  {
    CreditCardConfiguration config = new CreditCardConfiguration(null, 0, null, null, null, null, null);
    String sampleResponse = "PNREF=234&AUTHCODE=456&RESULT=50&RESPMSG=confirm&AVSADDR=Y&AVSZIP=Y";
    CreditCardResponse newResponse = PaypalResponseTranslator.translateResponse(sampleResponse, config);
    TransactionResponse oldResponse = new TransactionResponse(sampleResponse);
    assertSame(newResponse, oldResponse);
  }
  private void assertSame(CreditCardResponse newResponse, TransactionResponse oldResponse)
  {
    assertEquals(oldResponse.getAuthorizationCode(), newResponse.getAuthorizationCode());
    assertEquals(oldResponse.getPaymentReferenceId(), newResponse.getPaymentReferenceId());
    assertEquals(oldResponse.isApproved(), newResponse.isApproved());
    assertEquals(oldResponse.getResult(), newResponse.getResult());
    assertEquals(oldResponse.getResponseMessage(), newResponse.getResponseMessage());
    assertEquals(oldResponse.isAddressValid(), newResponse.isAddressValid());
    assertEquals(oldResponse.isCardSecurityCodeValid(), newResponse.isCardSecurityCodeValid());
    assertEquals(oldResponse.isZipValid(), newResponse.isZipValid());
    assertEquals(oldResponse.getOriginalResult(), newResponse.getOriginalResult());
  }
}
