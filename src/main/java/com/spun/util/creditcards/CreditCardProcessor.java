package com.spun.util.creditcards;

import com.Verisign.payment.PFProAPI;

public class CreditCardProcessor
{
  /***********************************************************************/
  public TransactionResponse process(CreditCardParameters params, PFProAPI payment)
  {
    String responseString = payment.SubmitTransaction(params.getParameterString());
    TransactionResponse response = new TransactionResponse(responseString);
    return response;
  }
  /***********************************************************************/
  /***********************************************************************/
}
