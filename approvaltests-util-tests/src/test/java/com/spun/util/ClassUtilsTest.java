package com.spun.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
  @Test
  void testGetGreatestCommonClass()
  {
    List<Comparable> comparables = new ArrayList<>();
    comparables.add(null);
    comparables.add(1);
    comparables.add(3.1415);
    assertEquals(Number.class, ClassUtils.getGreatestCommonBaseType(comparables));
    comparables.add("Lars");
    assertEquals(Comparable.class, ClassUtils.getGreatestCommonBaseType(comparables));
  }
}
