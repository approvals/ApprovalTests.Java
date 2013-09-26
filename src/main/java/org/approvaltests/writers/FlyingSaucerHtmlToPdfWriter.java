package org.approvaltests.writers;

import org.approvaltests.core.ApprovalWriter;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class FlyingSaucerHtmlToPdfWriter implements ApprovalWriter
{
  private final String html;
  public FlyingSaucerHtmlToPdfWriter(String html)
  {
    this.html = html;
  }
  @Override
  public String getApprovalFilename(String base)
  {
    return base + Writer.approved + ".pdf";
  }
  @Override
  public String getReceivedFilename(String base)
  {
    return base + Writer.received + ".pdf";
  }
  @Override
  public String writeReceivedFile(String received) throws Exception
  {
    // String s = new ApprovalTextWriter(html,
    // ".html").writeReceivedFile(File.createTempFile("temp",
    // ".html").getAbsolutePath());
    // File f = new File(s);
    // String url = f.toURI().toURL().toString();
    // OutputStream os = new FileOutputStream(received);
    // ITextRenderer renderer = new ITextRenderer();
    // renderer.setDocument(url);
    // renderer.layout();
    // renderer.createPDF(os);
    // os.close();
    // return received;
    throw new NotImplementedException();
  }
}
