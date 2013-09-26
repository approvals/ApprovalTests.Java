package com.spun.util.creditcards;

public class CreditCardResponse
{
  private boolean approved, addressValid, cardSecurityCodeValid, zipValid;
  private String  authorizationCode, paymentReferenceId, responseMessage;
  private int     result, originalResult;
  public boolean isAddressValid()
  {
    return addressValid;
  }
  public void setAddressValid(boolean addressValid)
  {
    this.addressValid = addressValid;
  }
  public boolean isApproved()
  {
    return approved;
  }
  public void setApproved(boolean approved)
  {
    this.approved = approved;
  }
  public String getAuthorizationCode()
  {
    return authorizationCode;
  }
  public void setAuthorizationCode(String authorizationCode)
  {
    this.authorizationCode = authorizationCode;
  }
  public boolean isCardSecurityCodeValid()
  {
    return cardSecurityCodeValid;
  }
  public void setCardSecurityCodeValid(boolean cardSecurityCodeValid)
  {
    this.cardSecurityCodeValid = cardSecurityCodeValid;
  }
  public int getOriginalResult()
  {
    return originalResult;
  }
  public void setOriginalResult(int originalResult)
  {
    this.originalResult = originalResult;
  }
  public String getPaymentReferenceId()
  {
    return paymentReferenceId;
  }
  public void setPaymentReferenceId(String paymentReferenceId)
  {
    this.paymentReferenceId = paymentReferenceId;
  }
  public String getResponseMessage()
  {
    return responseMessage;
  }
  public void setResponseMessage(String responseMessage)
  {
    this.responseMessage = responseMessage;
  }
  public int getResult()
  {
    return result;
  }
  public void setResult(int result)
  {
    this.result = result;
  }
  public boolean isZipValid()
  {
    return zipValid;
  }
  public void setZipValid(boolean zipValid)
  {
    this.zipValid = zipValid;
  }
}
