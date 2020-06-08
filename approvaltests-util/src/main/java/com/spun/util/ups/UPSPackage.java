/*
 * Created on Dec 10, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.spun.util.ups;

import java.io.Serializable;

import com.spun.util.parser.MassAmount;

public class UPSPackage implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String            originatingZipCode;
  private String            toZipCode;
  private String            serviceType;
  private String            toCountryCode;
  private boolean           residential;
  private double            packageWeight;
  private int               packageLength;
  private int               packageWidth;
  private int               packageHeight;
  private int               packageWeightUnits;
  
  public UPSPackage(String originatingZipCode, String toZipCode, String toCountryCode, double packageWeight,
      int packageWeightUnits, boolean residential)
  {
    this(originatingZipCode, toZipCode, toCountryCode, packageWeight, packageWeightUnits, 0, 0, 0, residential);
  }
  
  public UPSPackage(String originatingZipCode, String toZipCode, String toCountryCode, double packageWeight,
      int packageWeightUnits, int packageLength, int packageWidth, int packageHeight, boolean residential)
  {
    this.originatingZipCode = originatingZipCode;
    this.toZipCode = toZipCode;
    this.toCountryCode = toCountryCode;
    this.packageWeight = packageWeight;
    this.packageWeightUnits = packageWeightUnits;
    this.packageWidth = packageWidth;
    this.packageLength = packageLength;
    this.packageHeight = packageHeight;
    this.residential = residential;
  }
  
  public String getToCountryCode()
  {
    return toCountryCode;
  }
  
  public String getOriginatingZipCode()
  {
    return originatingZipCode;
  }
  
  public double getPackageWeightInPounds()
  {
    return MassAmount.convertUnits(this.packageWeight, this.packageWeightUnits, MassAmount.POUNDS);
  }
  
  public double getPackageWeight()
  {
    return packageWeight;
  }
  
  public int getPackageWeightUnits()
  {
    return packageWeightUnits;
  }
  
  public String getServiceType()
  {
    return serviceType;
  }
  
  public String getToZipCode()
  {
    return toZipCode;
  }
  
  public int getPackageHeight()
  {
    return packageHeight;
  }
  
  public int getPackageLength()
  {
    return packageLength;
  }
  
  public int getPackageWidth()
  {
    return packageWidth;
  }
  /************************************************************************/
  public boolean isResidential()
  {
    return residential;
  }
  /************************************************************************/
  public String toString()
  {
    String value = "com.spun.util.ups.UPSPackage[";
    value += " originatingZipCode = '" + originatingZipCode + "'" + ",\n" + " packageWeight = " + packageWeight
        + ",\n" + " packageWeightUnits = " + packageWeightUnits + ",\n" + " packageLength = " + packageLength
        + ",\n" + " packageWidth = " + packageWidth + ",\n" + " packageHeight = " + packageHeight + ",\n"
        + " serviceType = '" + serviceType + "'" + ",\n" + " toCountryCode = '" + toCountryCode + "'" + ",\n"
        + " toZipCode = '" + toZipCode + "'" + " residential = '" + residential + "'" + "]";
    return value;
  }
  /************************************************************************/
  /**
   * A convenience function to turn a vector of com.spun.util.ups.UPSPackage objects
   * into an Array of the com.spun.util.ups.UPSPackage objects.
   * @param vectorOf a Vector of com.spun.util.ups.UPSPackage objects
   * @return the array of com.spun.util.ups.UPSPackage.
   * @throws Error if an element of vectorOf is not a com.spun.util.ups.UPSPackage object.
   **/
  public static com.spun.util.ups.UPSPackage[] toArray(java.util.Collection<?> vectorOf)
  {
    if (vectorOf == null) { return new com.spun.util.ups.UPSPackage[0]; }
    com.spun.util.ups.UPSPackage array[] = new com.spun.util.ups.UPSPackage[vectorOf.size()];
    java.util.Iterator<?> iterator = vectorOf.iterator();
    int i = 0;
    while (iterator.hasNext())
    {
      java.lang.Object rowObject = iterator.next();
      if (rowObject instanceof com.spun.util.ups.UPSPackage)
      {
        array[i++] = (com.spun.util.ups.UPSPackage) rowObject;
      }
      else
      {
        throw new Error("toArray[" + i + "] is not an instance of com.spun.util.ups.UPSPackage but a "
            + rowObject.getClass().getName());
      }
    }
    return array;
  }
  
  
}
