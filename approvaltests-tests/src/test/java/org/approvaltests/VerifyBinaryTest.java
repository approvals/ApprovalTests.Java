package org.approvaltests;

import com.spun.util.ClassUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;

public class VerifyBinaryTest
{
  @Test
  void verifyBinary() throws Exception
  {
    // begin-snippet: verify_binary
    File image = new File(ClassUtils.getSourceDirectory(getClass()), "smiley.png");
    byte[] png = Files.readAllBytes(image.toPath());
    Approvals.verifyBinary(png, ".png");
    // end-snippet
  }
}
