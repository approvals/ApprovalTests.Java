package com.spun.util;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

  @Test
  void testFindWithFullPackagePath()
  {
    assertFileFound("com", "spun", "util", "ClassUtilsTest.java");
  }

  @Test
  void testFindWithPartialPackagePath()
  {
    // Kotlin-style case: common root package omitted from directory structure
    assertFileFound("fake", "root", "com", "spun", "util", "ClassUtilsTest.java");
  }

  @Test
  void testFindWithOnlyFilename()
  {
    // Edge case: only filename, no package segments (default package)
    assertFileFound("ClassUtilsTest.java");
  }

  private void assertFileFound(String... pathSegments)
  {
    File found = findInSourceRoot(pathSegments);
    File sourceFile = ClassUtils.getAdjacentFile(this.getClass(), "ClassUtilsTest.java");
    assertEquals(found.getAbsolutePath(), sourceFile.getAbsolutePath());
  }

  @Test
  void testFindNonExistentFile()
  {
    File found = findInSourceRoot("com", "spun", "util", "NonExistentFile.java");
    assertNull(found, "Should return null for non-existent file");
  }

  private File findInSourceRoot(String... pathSegments)
  {
    File sourceDir = ClassUtils.getSourceDirectory(getClass());
    File sourceRoot = sourceDir.getParentFile().getParentFile().getParentFile();
    return ClassUtils.find(sourceRoot, Arrays.asList(pathSegments));
  }
}
