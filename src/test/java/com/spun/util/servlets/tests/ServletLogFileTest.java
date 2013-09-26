package com.spun.util.servlets.tests;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.TestCase;

import com.spun.util.ObjectUtils;
import com.spun.util.io.FileUtils;
import com.spun.util.parser.TemplateError;
import com.spun.util.servlets.BasicServlet;
import com.spun.util.servlets.SecondaryErrorProcessor;
import com.spun.util.servlets.ServletLogWriterFactory;

public class ServletLogFileTest
  extends TestCase
{
  
  /***********************************************************************/
  public void testLogFileCreation() throws Exception
  {
    File directory = setupTempLogDirectory();
    File logTemplate = createLogTemplate(directory);
    String logToFile = directory.getAbsolutePath() + File.separator + "logs" + File.separator + "ErrorThrowingServlet.log";
    ServletLogWriterFactory.reset();
    new ErrorThrowingServlet(logTemplate.getAbsolutePath()).doGet(new MockHttpServletRequest(), new MockHttpServletResponse());
    
    File file = new File(logToFile);
    assertTrue(file.exists());
    assertTrue(FileUtils.readFile(file).contains("supposed to happen"));
    FileUtils.deleteDirectory(directory);
  }
  public void testTwice() throws Exception
  {
    testLogFileCreation();
  }
  private File createLogTemplate(File directory) throws IOException
  {
    File logs = new File(directory.getAbsolutePath() + File.separator + "logtemplate.txt");
    assertTrue("error template created", logs.createNewFile());
    return logs;
  }
  private File setupTempLogDirectory() throws IOException
  {
    File directory = FileUtils.createTempDirectory();
    File logs = new File(directory.getAbsolutePath() + File.separator + "logs");
    logs.mkdir();
    String TOMCAT_DIRECTORY = "catalina.base";
    System.setProperty(TOMCAT_DIRECTORY, directory.getAbsolutePath());
    return directory;
  }
  /***********************************************************************/
  public static class ErrorThrowingServlet extends BasicServlet implements SecondaryErrorProcessor
  {
    String errorTemplate = null;
    
    public ErrorThrowingServlet(String errorTemplate)
    {
      this.errorTemplate = errorTemplate;
    }
    /***********************************************************************/
    public void doGet(HttpServletRequest req, HttpServletResponse res)
    {
      try
      {
        throw new ArithmeticException("This error is supposed to happen.");
      }
      catch (ArithmeticException t)
      {
        try
        {
          processError(t, req,this);
        }
        catch(Throwable t2)
        {
          ObjectUtils.throwAsError(t2);
        }
      }
    }
    /***********************************************************************/
    protected String getErrorTemplate()
    {
      return errorTemplate;
    }
    @Override
    public String processError(TemplateError error, Throwable t2)
    {
      throw ObjectUtils.throwAsError(t2);
    }
  }
  
  /***********************************************************************/
  /***********************************************************************/
}
