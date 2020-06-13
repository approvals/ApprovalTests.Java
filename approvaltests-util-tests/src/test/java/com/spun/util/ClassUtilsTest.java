package com.spun.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.Serializable;
import org.junit.jupiter.api.Test;

public class ClassUtilsTest
{
  @Test
  public void testHasMethod() throws Exception
  {
    assertFalse(ClassUtils.hasMethod(Serializable.class, "getClass"));
    assertTrue(ClassUtils.hasMethod(Object.class, "getClass"));
  }
  @Test
  public void testSourceDirectory() throws Exception
  {
    File dir = ClassUtils.getSourceDirectory(getClass());
    assertNotNull(dir);
    assertTrue(dir.isDirectory());
  }
}
