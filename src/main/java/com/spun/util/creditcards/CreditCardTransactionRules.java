package com.spun.util.creditcards;

import com.spun.util.servlets.ValidationError;

/***********************************************************************/

public class CreditCardTransactionRules
{
  public static final String CREDIT_CARD_TRANSACTION = "creditCardTransaction";
	/***********************************************************************/
	private static String[] asserts ={
															"userAuthenticationValid",
															"cardApproved",
															"originalTransactionValid",
															"accountNumberValid",
															"expirationDateValid",
															"fundsValid",
															"transactionValid"
                            	};

	/***********************************************************************/
	public static ValidationError createEmpty()
	{
		return new ValidationError(asserts);
	}
  /***********************************************************************/
  public static ValidationError  checkResponse(TransactionResponse response)
  {
    ValidationError rules = new ValidationError(asserts);
    switch (response.getResult())
    {
      case 0:  break; // transaction succeeded
      case 1: rules.setError("userAuthenticationValid", true, "Login failed."); break;
      case 12: 
      case 13: rules.setError("cardApproved", true, "Credit card declined"); break;
      case 19: rules.setError("originalTransactionValid", true, "Couldn't find original transaction"); break;
      case 23: rules.setError("accountNumberValid", true, "Credit Card number is invalid"); break;
      case 24: rules.setError("expirationDateValid", true, "Credit card expiration is invalid"); break;
      case 50: rules.setError("fundsValid", true, "Insufficient funds"); break;
      default : rules.setError("transactionValid", true, response.getResponseMessage()); break;
             
    }

    
    return rules;
  }
  /***********************************************************************/
  /***********************************************************************/

}
