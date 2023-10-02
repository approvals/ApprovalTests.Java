package org.approvaltests.reporters;

import com.spun.util.tests.TestUtils;

import java.awt.GraphicsEnvironment;

public class ImageWebReporter implements EnvironmentAwareReporter
{
  public static final ImageWebReporter INSTANCE = new ImageWebReporter();
  @Override
  public boolean report(String received, String approved)
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
    String moveText = ClipboardReporter.getAcceptApprovalText(received, approved);
    text = String.format(text, approved, received, received, moveText);
    TestUtils.displayHtml(text);
    return isWorkingInThisEnvironment(received);
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
