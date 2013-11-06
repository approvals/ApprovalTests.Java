package org.teachingextensions.setup.tests;

import java.io.File;

import junit.framework.TestCase;

import com.spun.util.io.ZipUtils;

public class UnzipTest extends TestCase
{
  public void ptestUnzip() throws Exception
  {
    String zip = "C:/Users/Llewellyn/Downloads/TeachingKidsProgramming.Java-master/TeachingKidsProgramming.Java-master/eclipse_workspace.zip";
    String dir = "C:/Users/Llewellyn/Downloads/TeachingKidsProgramming.Java-master/TeachingKidsProgramming.Java-master/";
    ZipUtils.doUnzip(new File(dir), new File(zip));
  }
}
