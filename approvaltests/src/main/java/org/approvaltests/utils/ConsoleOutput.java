package org.approvaltests.utils;

import org.approvaltests.Approvals;
import org.approvaltests.core.Options;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ConsoleOutput implements AutoCloseable
{
  private final ByteArrayOutputStream outContent;
  private final ByteArrayOutputStream errContent;
  private final PrintStream           originalOut;
  private final PrintStream           originalErr;
  public ConsoleOutput()
  {
    originalOut = System.out;
    originalErr = System.err;
    outContent = new ByteArrayOutputStream();
    errContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));
  }

  public String getOutput()
  {
    return outContent.toString();
  }

  public String getError()
  {
    return errContent.toString();
  }

  public void verifyOutput()
  {
    verifyOutput(new Options());
  }

  public void verifyOutput(Options options)
  {
    Approvals.verify(getOutput(), options);
  }

  public void verifyError()
  {
    Approvals.verify(getError());
  }

  public void verifyError(Options options)
  {
    Approvals.verify(getError(), options);
  }

  public void verifyAll()
  {
    verifyAll(new Options());
  }

  public void verifyAll(Options options)
  {
    String combined = "Output:\n" + getOutput() + "\nError:\n" + getError();
    Approvals.verify(combined, options);
  }

  @Override
  public void close()
  {
    System.setOut(originalOut);
    System.setErr(originalErr);
  }
}