package com.spun.util.creditcards;

import com.spun.util.NumberUtils;
import com.spun.util.StringUtils;
import java.io.Serializable;
import java.util.Properties;

public class TransactionResponse implements Serializable
{
	public String originalResponse = null;
	public Properties response = new Properties();
  /***********************************************************************/
  public TransactionResponse(String originalResponse)
  {
    this.originalResponse = originalResponse;
    parse(originalResponse);
  }
  /***********************************************************************/
  public TransactionResponse(String refId, String authCode, int result, String message, boolean zipVerified, boolean addressVerified)
  {
    response.put("PNREF", refId);
    response.put("AUTHCODE", authCode);
    response.put("RESULT", "" + result);
    response.put("RESPMSG", message);
    response.put("AVSADDR", addressVerified ? "Y" : "N");
    response.put("AVSZIP", zipVerified ? "Y" : "N");
    this.originalResponse = "PNREF=" + refId + "&AUTHCODE=" + authCode + "&RESULT=" + result + "&RESPMSG=" + message + "&AVSADDR=" + (addressVerified ? "Y" : "N") + "&AVSZIP=" + (zipVerified ? "Y" : "N");
  }
  /***********************************************************************/
	private void parse(String originalResponse)
	{
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
				response.setProperty(parts[i].substring(0,place), parts[i].substring(place + 1));
			}
		}
	}
  /***********************************************************************/
	public String getOriginalResponse()
	{
		return originalResponse;
	}
	/***********************************************************************/
	public String getPaymentReferenceId()
	{
		return response.getProperty("PNREF");
	}
	/***********************************************************************/
	public String getAuthorizationCode()
	{
		return response.getProperty("AUTHCODE");
	}
  /***********************************************************************/
  public boolean isApproved()
  {
		return getResult() == 0;
  }
  /***********************************************************************/
  public int getResult()
  {
  	return NumberUtils.load(response.getProperty("RESULT"), -1);
  }
  /***********************************************************************/
  public String getResponseMessage()
  {
  	return response.getProperty("RESPMSG");
  }
  /***********************************************************************/
  public boolean isAddressValid()
  {
  	return "Y".equalsIgnoreCase(response.getProperty("AVSADDR"));
  }
  /***********************************************************************/
  public boolean isCardSecurityCodeValid()
  {
    return "Y".equalsIgnoreCase(response.getProperty("CVV2MATCH"));
  }
  
  /***********************************************************************/
  public boolean isZipValid()
  {
  	return "Y".equalsIgnoreCase(response.getProperty("AVSZIP"));
  }
  /***********************************************************************/
	public String toString()
	{
		return originalResponse;
	}
  /***********************************************************************/
  public int getOriginalResult()
  {
    return NumberUtils.load(response.getProperty("ORIGRESULT"), -1);
  }
  /***********************************************************************/
  /***********************************************************************/
  
}