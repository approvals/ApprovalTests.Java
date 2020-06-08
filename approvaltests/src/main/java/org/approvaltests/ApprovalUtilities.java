package org.approvaltests;

import com.spun.util.ObjectUtils;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class ApprovalUtilities
{
  public ByteArrayOutputStream writeSystemOutToStringBuffer()
  {
    try
    {
      ByteArrayOutputStream text = new ByteArrayOutputStream();
      System.setOut(new PrintStream(text, false, StandardCharsets.UTF_8.name()));
      return text;
    } catch (Exception e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
}
