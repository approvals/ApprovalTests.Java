package org.approvaltests.namer.tests;

import java.io.File;

import org.approvaltests.Approvals;
import org.approvaltests.namer.ApprovalNamer;
import org.approvaltests.namer.NamedEnvironment;
import org.approvaltests.namer.NamerFactory;
import org.approvaltests.namer.StackTraceNamer;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestNgStackTraceNamerTest
{
  @Test
  public void testGetApprovalName() throws Exception
  {
    StackTraceNamer name = new StackTraceNamer();
    Assert.assertEquals("TestNgStackTraceNamerTest.testGetApprovalName", name.getApprovalName());
  }
  @Test
  public void testGetSourceFilePath() throws Exception
  {
    StackTraceNamer name = new StackTraceNamer();
    File file = new File(name.getSourceFilePath() + "TestNgStackTraceNamerTest.java");
    Assert.assertTrue(file.exists());
  }
  @Test
  public void testName() throws Exception
  {
    Approvals.verify("foo");
  }
  @Test(dataProvider = "MyDataProvider")
  public void testDataProvider(String data) throws Exception
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
