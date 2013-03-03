package com.spun.util.ups;

public class UPSConfig
{
  private String userId;
  private String password;
  private String accessLicenseNumber;
  /***********************************************************************/
  public UPSConfig(String userId, String password, String accessLicenseNumber)
  {
    this.userId = userId;
    this.password = password;
    this.accessLicenseNumber = accessLicenseNumber;
  }
  /***********************************************************************/
  public String getAccessLicenseNumber()
  {
    return accessLicenseNumber;
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
  /***********************************************************************/
}