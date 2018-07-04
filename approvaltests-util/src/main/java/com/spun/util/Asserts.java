package com.spun.util;

import java.io.File;

public class Asserts
{
  public static void t()
  {
    // can you see it now?  good.
    // how about this?  
    // kind of slow...
  }
  /***********************************************************************/
  public static <T> T assertNotNull(String label, T o)
  {
    if (o == null) { throw new NullPointerException(label + " was null"); }
    return o;
  }
  /***********************************************************************/
  public static void assertEqual(String label, int expected, int actual)
  {
    if (expected != actual) { throw new IllegalStateException(label + " " + expected + "!=" + actual); }
  }
  /***********************************************************************/
  public static void assertFileDoesNotExist(String label, String fileName)
  {
    File file = new File(fileName);
    if (file.exists()) { throw new IllegalStateException(label + " file " + file.getAbsolutePath()
        + " already exists"); }
  }
  /***********************************************************************/
  public static void assertFileExists(String label, String fileName)
  {
    File file = new File(fileName);
    if (!file.exists()) { throw new IllegalStateException(label + " file " + file.getAbsolutePath()
        + " does not exist"); }
  }
  /***********************************************************************/
  public static void equals(String label, double expected, double actual, double percission)
  {
    if (!NumberUtils.equals(expected, actual, percission)) { throw new IllegalStateException(label + " "
        + expected + "!=" + actual); }
  }
  /***********************************************************************/
  public static void notEquals(String label, double expected, double actual)
  {
    if (actual == expected) { throw new IllegalStateException(label + " " + expected + "!=" + actual); }
  }
  /***********************************************************************/
  public static void assertEqual(String label, Object expected, Object actual)
  {
    if (!ObjectUtils.isEqual(expected, actual)) { throw new IllegalStateException(label + " " + expected + "!="
        + actual); }
  }
  /***********************************************************************/
  public static void assertNotEqual(String label, Object expected, Object actual)
  {
    if (ObjectUtils.isEqual(expected, actual)) { throw new IllegalStateException(label + " can't = " + expected); }
  }
  /***********************************************************************/
  public static void assertEqual(String label, double expected, double actual, double precision)
  {
    if (!NumberUtils.equals(expected, actual, precision)) { throw new IllegalStateException(label + " " + expected
        + "!=" + actual + "within delta of" + precision); }
  }
  /***********************************************************************/
  public static void assertStartsWith(String label, String haystack, String needle)
  {
    if (haystack == null || !haystack.startsWith(needle)) { throw new IllegalStateException(label + " " + haystack
        + " does not start with " + needle); }
  }
  /***********************************************************************/
  public static void assertTrue(String label, boolean b)
  {
    if (!b) { throw new IllegalStateException(label + " was false"); }
  }
}
