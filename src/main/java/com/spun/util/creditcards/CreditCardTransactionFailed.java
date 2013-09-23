package com.spun.util.creditcards;

public class CreditCardTransactionFailed extends Exception
{
  private TransactionResponse response;
  /***********************************************************************/
  public CreditCardTransactionFailed(TransactionResponse tr)
  {
    this("Credit Card Failed", tr);
  }
  /***********************************************************************/
  public CreditCardTransactionFailed(String message, TransactionResponse tr)
  {
    super(message + " :: " + tr.getResponseMessage() + "\n OriginalMessage = " + tr.originalResponse);
    this.response = tr;
  }
  /***********************************************************************/
  public TransactionResponse getResponse()
  {
    return response;
  }
  /***********************************************************************/
  /***********************************************************************/
}
