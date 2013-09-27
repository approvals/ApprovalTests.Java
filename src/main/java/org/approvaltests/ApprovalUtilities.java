package org.approvaltests;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ApprovalUtilities
{
  public ByteArrayOutputStream writeSystemOutToStringBuffer()
  {
    ByteArrayOutputStream text = new ByteArrayOutputStream();
    System.setOut(new PrintStream(text));
    return text;
  }
}
