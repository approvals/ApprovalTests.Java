package com.spun.util.creditcards;

public class CreditCardConfiguration
{

  private String certificatePath;
  private int    hostPort;
  private String hostAddress;
  private String resellerId;
  private String venderId;
  private String userId;
  private String password;
  /***********************************************************************/
  public CreditCardConfiguration(String certificatePath, int hostPort, String hostAddress, 
                                 String resellerId, String venderId, 
                                 String userId, String password)
  {
    this.certificatePath = certificatePath;
    this.hostPort = hostPort;
    this.hostAddress = hostAddress;
    this.resellerId = resellerId;
    this.venderId = venderId;
    this.userId = userId;
    this.password = password;
    
  }
  /***********************************************************************/
  public String getHostAddress()
  {
    return hostAddress;
  }

  /***********************************************************************/
  public int getHostPort()
  {
    return hostPort;
  }

  /***********************************************************************/
  public String getCertificatePath()
  {
    return certificatePath;
  }
  /***********************************************************************/
  public String getPassword()
  {
    return password;
  }
  /***********************************************************************/
  public String getUserId()
  {
    return userId;
  }
  /***********************************************************************/
  public String getVenderId()
  {
    return venderId;
  }
  /***********************************************************************/
  public String getResellerId()
  {
    return resellerId;
  }
  /***********************************************************************/
  /***********************************************************************/
}