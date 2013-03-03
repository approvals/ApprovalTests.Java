package org.approvaltests.reporters;

import java.io.File;

import org.approvaltests.core.ApprovalFailureReporter;

import com.spun.util.io.FileUtils;
import com.spun.util.tests.TestUtils;

public class TextWebReporter implements ApprovalFailureReporter
{
  @Override
  public void report(String received, String approved) throws Exception
  {
    String text = "<html><body><center><table border=1><tr><td><pre>%s</pre></td><td><pre>%s</pre></td></tr><tr><td>approved</td><td>received</td></tr></table> <b>to approve :</b> copy clipboard to command window </center></body></html>";
    String aText = new File(approved).exists() ? FileUtils.readFile(approved) : "";
    String rText = FileUtils.readFile(received);
    text = String.format(text, aText.replace("<", "&lt;"), rText.replace("<", "&lt;"));
    TestUtils.displayHtml(text);
  }
}
