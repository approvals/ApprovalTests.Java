package com.spun.util;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    // Standard case: full package path exists in directory structure
    // Get the source directory for this test class as the base
    File sourceDir = ClassUtils.getSourceDirectory(getClass());
    // Go up to 'java' directory (from com/spun/util -> src/test/java)
    File baseDir = sourceDir.getParentFile().getParentFile().getParentFile();
    List<String> matches = Arrays.asList("com", "spun", "util", "ClassUtilsTest.java");
    File found = ClassUtils.find(baseDir, matches);
    assertNotNull(found, "Should find file with full package path");
    assertEquals("ClassUtilsTest.java", found.getName());
  }

  @Test
  void testFindWithPartialPackagePath()
  {
    // Kotlin-style case: common root package omitted from directory structure
    // File is at: src/test/java/com/spun/util/ClassUtilsTest.java
    // But we search as if package was "fake.root.com.spun.util"
    File sourceDir = ClassUtils.getSourceDirectory(getClass());
    File baseDir = sourceDir.getParentFile().getParentFile().getParentFile();
    List<String> matches = Arrays.asList("fake", "root", "com", "spun", "util", "ClassUtilsTest.java");
    File found = ClassUtils.find(baseDir, matches);
    assertNotNull(found, "Should find file even when leading package segments don't exist in directory");
    assertEquals("ClassUtilsTest.java", found.getName());
  }

  @Test
  void testFindWithOnlyFilename()
  {
    // Edge case: only filename, no package segments
    File sourceDir = ClassUtils.getSourceDirectory(getClass());
    File baseDir = sourceDir.getParentFile().getParentFile().getParentFile();
    List<String> matches = Arrays.asList("ClassUtilsTest.java");
    File found = ClassUtils.find(baseDir, matches);
    assertNotNull(found, "Should find file with just filename");
    assertEquals("ClassUtilsTest.java", found.getName());
  }

  @Test
  void testFindNonExistentFile()
  {
    File sourceDir = ClassUtils.getSourceDirectory(getClass());
    File baseDir = sourceDir.getParentFile().getParentFile().getParentFile();
    List<String> matches = Arrays.asList("com", "spun", "util", "NonExistentFile.java");
    File found = ClassUtils.find(baseDir, matches);
    assertNull(found, "Should return null for non-existent file");
  }
}
