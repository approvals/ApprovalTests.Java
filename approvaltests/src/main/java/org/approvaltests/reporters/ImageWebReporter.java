package org.approvaltests.reporters;

import com.spun.util.tests.TestUtils;

import java.awt.GraphicsEnvironment;

public class ImageWebReporter implements EnvironmentAwareReporter
{
  public static final ImageWebReporter INSTANCE = new ImageWebReporter();
  @Override
  public void report(String received, String approved)
  {
    String text = "<html><body><center><table border=1><tr><td><img src=\"file:///%s\"></td><td><img src=\"file:///%s\"></td></tr><tr><td>approved</td><td>received</td></tr></table>  %s <br/> <b>to approve :</b> copy clipboard to command window  <br/> <font size=1>%s</font></center></body></html>";
    String moveText = ClipboardReporter.getAcceptApprovalText(received, approved);
    text = String.format(text, approved, received, received, moveText);
    TestUtils.displayHtml(text);
  }
  /**
   * We assume any environment that is not headless will have a web browser to display the image in a web page.
   */
  @Override
  public boolean isWorkingInThisEnvironment(String forFile)
  {
    return !GraphicsEnvironment.isHeadless()
        && GenericDiffReporter.isFileExtensionValid(forFile, GenericDiffReporter.IMAGE_FILE_EXTENSIONS);
  }
}
