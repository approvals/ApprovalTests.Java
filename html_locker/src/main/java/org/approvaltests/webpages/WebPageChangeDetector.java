package org.approvaltests.webpages;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JFrame;

import org.approvaltests.approvers.FileApprover;
import org.approvaltests.reporters.DiffReporter;

import com.spun.util.Colors;
import com.spun.util.ObjectUtils;
import com.spun.util.WindowUtils;

public class WebPageChangeDetector implements ActionListener
{
  private boolean                 keyPressed = false;
  private URI                     url;
  private boolean                 validUrl;
  private Boolean                 filesMatched;
  public WebPageChangeDetectorGui gui;
  public WebPageChangeDetector()
  {
    gui = new WebPageChangeDetectorGui(this);
  }
  @Override
  public void actionPerformed(ActionEvent e)
  {
    if (keyPressed) { return; }
    try
    {
      keyPressed = true;
      if (e.getSource() == gui.lock)
      {
        doLock();
      }
      else if (e.getSource() == gui.check)
      {
        doCheck();
      }
    }
    finally
    {
      keyPressed = false;
    }
  }
  private void doCheck()
  {
    updateModel();
    WebPageApproval.captureWebPage(url, getRecievedFile());
    filesMatched = verifyFiles(getApprovedFile(), getRecievedFile());
    if (!filesMatched)
    {
      try
      {
        new DiffReporter().report(getApprovedFile(), getRecievedFile());
      }
      catch (Exception e)
      {
        throw ObjectUtils.throwAsError(e);
      }
    }
    updateScreen();
  }
  private boolean verifyFiles(String approvedFile, String recievedFile)
  {
    return FileApprover.approveTextFile(new File(approvedFile), new File(recievedFile));
  }
  private String getRecievedFile()
  {
    return "webPageImages/" + WebPageApproval.convertToLegalFileName(url, "recieved.png");
  }
  private void doLock()
  {
    updateModel();
    WebPageApproval.captureWebPage(url, getApprovedFile());
    updateScreen();
  }
  private String getApprovedFile()
  {
    return "webPageImages/" + WebPageApproval.convertToLegalFileName(url, "png");
  }
  private void updateModel()
  {
    try
    {
      url = new URI(gui.urlTextField.getText());
    }
    catch (URISyntaxException e)
    {
      validUrl = false;
      url = null;
    }
  }
  private void updateScreen()
  {
    gui.urlTextField.setText(url.toString());
    Color color = null;
    if (filesMatched == null)
    {
      color = Colors.Yellows.Yellow;
    }
    else if (filesMatched == true)
    {
      color = Colors.Greens.Green;
    }
    else if (filesMatched == false)
    {
      color = Colors.Reds.FireBrick;
    }
    gui.check.setBackground(color);
  }
  public static void main(String[] args) throws URISyntaxException
  {
    WebPageChangeDetector panel = new WebPageChangeDetector();
    String url = args.length == 0 ? "http://cosmoquest.org" : args[0];
    panel.url = new URI(url);
    panel.updateScreen();
    panel.showFrame();
  }
  private void showFrame()
  {
    JFrame test = new JFrame("CSS/HTML Refactor Locker");
    test.getContentPane().add(gui);
    WindowUtils.testFrame(test, true);
  }
}
