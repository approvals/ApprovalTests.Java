package org.approvaltests.namer;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestNgStackTraceNamerTest
{
  @Test
  public void testGetApprovalName()
  {
    StackTraceNamerUtils.assertNamerForFramework("TestNgStackTraceNamerTest", "testGetApprovalName");
  }
  @Test(dataProvider = "MyDataProvider")
  public void testDataProvider(String data)
  {
    StackTraceNamerUtils.assertParameterizedTest(getClass().getSimpleName(), "testDataProvider", data);
  }
  @DataProvider(name = "MyDataProvider")
  public Object[][] data()
  {
    return new Object[][]{{"hello"}};
  }
}
