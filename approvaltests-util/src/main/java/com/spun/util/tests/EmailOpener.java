package com.spun.util.tests;

import com.spun.util.ObjectUtils;
import jakarta.mail.Message;

import java.io.File;
import java.io.FileOutputStream;

public class EmailOpener
{
  public static void displayEmail(Message email)
  {
    if (email == null)
    { return; }
    try
    {
      File f = File.createTempFile("email", ".eml");
      f.deleteOnExit();
      FileOutputStream out = new FileOutputStream(f);
      email.writeTo(out);
      out.close();
      TestUtils.displayFile(f.getAbsolutePath());
    }
    catch (Exception e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
}
