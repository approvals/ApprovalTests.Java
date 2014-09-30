package org.approvaltests.webpages;

import java.io.File;
import java.net.URI;

import org.approvaltests.Approvals;

import com.spun.util.ObjectUtils;
import com.spun.util.io.FileUtils;

public class WebPageApproval
{
  public static void verifyRenderedPage(URI uri)
  {
    String imageFile = convertToLegalFileName(uri, "png");
    captureWebPage(uri, imageFile);
    Approvals.verify(new File(imageFile));
  }
  public static void captureWebPage(URI uri, String imageFile) throws Error
  {
    try
    {
      File jsFile = createPhantomjsCommand(uri, imageFile);
      Process exec = Runtime.getRuntime().exec(String.format("C:\\tools\\PhantomJS\\phantomjs.exe %s", jsFile));
      int waitFor = exec.waitFor();
    }
    catch (Throwable e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
  private static File createPhantomjsCommand(URI uri, String imageFile) throws Throwable
  {
    File jsFile = File.createTempFile("capture", ".js");
    String template = "var page = require('webpage').create();\n" + "page.open('%s', function() {\n"
        + "  page.render('%s');\n" + "  phantom.exit();\n" + "});";
    String js = String.format(template, uri, imageFile);
    FileUtils.writeFile(jsFile, js);
    return jsFile;
  }
  public static String convertToLegalFileName(URI uri, String extentionWithoutDot)
  {
    return uri.toString().replaceAll("[^a-zA-Z0-9\\.\\-]", "_") + "." + extentionWithoutDot;
  }
}
