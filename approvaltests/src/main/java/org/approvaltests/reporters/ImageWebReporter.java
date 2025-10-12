package org.approvaltests.reporters;

import com.spun.util.tests.TestUtils;
import org.approvaltests.core.ApprovalFailureReporter;

import java.awt.GraphicsEnvironment;

public class ImageWebReporter implements ApprovalFailureReporter
{
  public static final ImageWebReporter INSTANCE = new ImageWebReporter();
  @Override
  public boolean report(String received, String approved)
  {
    if (!isWorkingInThisEnvironment(received))
    { return false; }
    String moveText = ClipboardReporter.getAcceptApprovalText(received, approved);
    String text = getHtml(received, approved, moveText);
    TestUtils.displayHtml(text);
    return true;
  }

  private static String getHtml(String received, String approved, String moveText)
  {
    //language=HTML
    String text = "<html>\n" + "<script>\n" + "    function copyToClipboard() {\n"
        + "        const copyText = document.getElementById(\"moveText\");\n"
        + "        navigator.clipboard.writeText(copyText.textContent);\n" + "    }\n" + "</script>\n" + "<body>\n"
        + "<center>\n" + "    <table border=1>\n" + "        <tr>\n"
        + "            <td><img src=\"file:///%s\"></td>\n" + "            <td><img src=\"file:///%s\"></td>\n"
        + "        </tr>\n" + "        <tr>\n" + "            <td>approved</td>\n"
        + "            <td>received</td>\n" + "        </tr>\n" + "    </table>\n"
        + "    %s <br/> <b>to approve :</b> copy clipboard to command window <br/> <font size=1 id=\"moveText\">%s</font>\n"
        + "    <br /><button onclick=\"copyToClipboard()\">Copy to clipboard</button>\n" + "</center>\n"
        + "</body>\n" + "</html>\n";
    text = String.format(text, approved, received, received, moveText);
    return text;
  }

  /**
   * We assume any environment that is not headless will have a web browser to display the image in a web page.
   */
  private boolean isWorkingInThisEnvironment(String forFile)
  {
    return !GraphicsEnvironment.isHeadless()
        && GenericDiffReporter.isFileExtensionValid(forFile, GenericDiffReporter.IMAGE_FILE_EXTENSIONS);
  }
}
