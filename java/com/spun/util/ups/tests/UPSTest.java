package com.spun.util.ups.tests;

import java.util.Arrays;

import org.lambda.query.Query;

import com.spun.util.parser.MassAmount;
import com.spun.util.ups.UPSConfig;
import com.spun.util.ups.UPSPackage;
import com.spun.util.ups.UPSQuote;
import com.spun.util.ups.UPSQuoteRetriever;
import com.spun.util.ups.UPSServiceType;
import com.spun.util.ups.UPSUtils;

import junit.framework.TestCase;

public class UPSTest extends TestCase
{
  private static boolean TEST_LIVE            = false;
  private static String  ORIGINATION_ZIP_CODE = "48910";
  private UPSConfig      config               = new UPSConfig("username", "password", "accessLicenseNumber");
  private UseCase        useCases[]           = {new UseCase(
      new UPSPackage(ORIGINATION_ZIP_CODE, "H3A 1Y1", "CA", 10, MassAmount.POUNDS, false),
      new UPSQuote(UPSServiceType.UPS_Standard, 17.35), false),
                                                 new UseCase(
                                                     new UPSPackage(ORIGINATION_ZIP_CODE, "92130", "US", 10,
                                                         MassAmount.POUNDS, false),
                                                     new UPSQuote(UPSServiceType.UPS_Ground, 8.19), true),
                                                 new UseCase(new UPSPackage[]{new UPSPackage(ORIGINATION_ZIP_CODE,
                                                     "92130", "US", 150, MassAmount.POUNDS, false),
                                                                              new UPSPackage(ORIGINATION_ZIP_CODE,
                                                                                  "92130", "US", 50,
                                                                                  MassAmount.POUNDS, false)},
                                                     new UPSQuote(UPSServiceType.UPS_Ground, 127.07), false),
                                                 new UseCase(
                                                     new UPSPackage(ORIGINATION_ZIP_CODE, "92130", "US", 200,
                                                         MassAmount.POUNDS, false),
                                                     new UPSQuote(UPSServiceType.UPS_Ground, 127.07), false),
                                                 new UseCase(
                                                     new UPSPackage(ORIGINATION_ZIP_CODE, "92130", "US", 15,
                                                         MassAmount.POUNDS, false),
                                                     new UPSQuote(UPSServiceType.UPS_Ground, 11.8), false),
                                                 new UseCase(
                                                     new UPSPackage(ORIGINATION_ZIP_CODE, "92130", "US", 10,
                                                         MassAmount.POUNDS, 12, 12, 2, false),
                                                     new UPSQuote(UPSServiceType.UPS_Ground, 8.55), false),
                                                 new UseCase(new UPSPackage[]{new UPSPackage(ORIGINATION_ZIP_CODE,
                                                     "92130", "US", 10, MassAmount.POUNDS, false),
                                                                              new UPSPackage(ORIGINATION_ZIP_CODE,
                                                                                  "92130", "US", 15,
                                                                                  MassAmount.POUNDS, false)},
                                                     new UPSQuote(UPSServiceType.UPS_Ground, 8.55 + 11.8),
                                                     false),};
  /***********************************************************************/
  public void test() throws Exception
  {
    for (int i = 0; i < useCases.length; i++)
    {
      if (TEST_LIVE || useCases[i].mock)
      {
        assertValid(useCases[i]);
      }
    }
  }
  public void assertValid(UseCase useCase) throws Exception
  {
    UPSUtils.setUPSQuoteRetriever(useCase.mock ? new MockUPSQuoteRetriever() : new UPSQuoteRetriever());
    UPSQuote quotes[] = UPSUtils.getQuote(config, useCase.packages);
    UPSQuote q = getQuoteForService(useCase.quote.getServiceType(), quotes);
    assertEquals("Price for " + useCase, useCase.quote.getPrice(), q.getPrice(), useCase.mock ? 0.005 : 1);
  }
  /***********************************************************************/
  public static UPSQuote getQuoteForService(UPSServiceType type, UPSQuote[] quotes)
  {
    return (UPSQuote) Query.first(quotes, o -> type.equals(o.getServiceType()));
  }
  /***********************************************************************/
}

class UseCase
{
  public UPSPackage packages[] = null;
  public UPSQuote   quote      = null;
  public boolean    mock       = true;
  public UseCase(UPSPackage packages[], UPSQuote quote, boolean mock)
  {
    this.packages = packages;
    this.quote = quote;
    this.mock = mock;
  }
  public UseCase(UPSPackage package1, UPSQuote quote, boolean mock)
  {
    this(new UPSPackage[]{package1}, quote, mock);
  }
  @Override
  public String toString()
  {
    return "UseCase [packages=" + Arrays.toString(packages) + ", quote=" + quote + ", mock=" + mock + "]";
  }
}
/***********************************************************************/
/***********************************************************************/
