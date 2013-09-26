package com.spun.util.creditcards.tests;

import com.Verisign.payment.PFProAPI;
import com.spun.util.StringUtils;
import com.spun.util.creditcards.CreditCardParameters;
import com.spun.util.creditcards.CreditCardProcessor;
import com.spun.util.creditcards.TransactionResponse;

public class MockCreditCardProcessor extends CreditCardProcessor 
{
  private int result = 0;
  /***********************************************************************/
  public TransactionResponse process(CreditCardParameters params, PFProAPI payment)
  {
    TransactionResponse response = new TransactionResponse("234", "456", getExpectedResult(params), "confirm", true, true);
    response.response.put("ORIGRESULT", "0");
    //My_System.variable("response",response.getOriginalResponse());
    return response;
  }
  /***********************************************************************/
  public int getExpectedResult(CreditCardParameters params)
  {
    if (StringUtils.isIn(params.getTransactionType(), new String[]{CreditCardParameters.SALE, CreditCardParameters.AUTHORIZATION, CreditCardParameters.DELAYED_CAPTURE}))
    {
	  return "5555555555555555".equals(params.getParameter("ACCT")) ? 50 : 0;
    }
    return 0;
  }
  /***********************************************************************/
  public int getResult()
  {
    return result;
  }
  /***********************************************************************/
  public void setResult(int result)
  {
    this.result = result;
  }
  /***********************************************************************/
  /***********************************************************************/
  
}
