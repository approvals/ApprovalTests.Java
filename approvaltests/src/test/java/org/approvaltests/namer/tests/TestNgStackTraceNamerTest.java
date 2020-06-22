package org.approvaltests.namer.tests;

import org.approvaltests.Approvals;
import org.approvaltests.namer.ApprovalNamer;
import org.approvaltests.namer.NamedEnvironment;
import org.approvaltests.namer.NamerFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestNgStackTraceNamerTest
{
  @Test
  public void testGetApprovalName()
  {
    StackTraceNamerUtils.assertNamerForFramework("TestNgStackTraceNamerTest", "testGetApprovalName");
  }
  @Test
  public void testName()
  {
    Approvals.verify("foo");
  }
  @Test(dataProvider = "MyDataProvider")
  public void testDataProvider(String data)
  {
    try (NamedEnvironment en = NamerFactory.asMachineSpecificTest(data))
    {
      ApprovalNamer name = Approvals.createApprovalNamer();
      Assert.assertEquals("TestNgStackTraceNamerTest.testDataProvider.hello", name.getApprovalName());
    }
  }
  @DataProvider(name = "MyDataProvider")
  public Object[][] data()
  {
    return new Object[][]{{"hello"}};
  }
}
