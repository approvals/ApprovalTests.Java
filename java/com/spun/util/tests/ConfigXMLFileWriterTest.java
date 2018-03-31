package com.spun.util.tests;

import java.lang.reflect.Field;
import java.util.List;

import org.approvaltests.Approvals;
import org.lambda.query.Query;

import com.spun.util.ConfigXMLFileWriter;

import junit.framework.TestCase;

public class ConfigXMLFileWriterTest extends TestCase
{
  public static int SECOND_FIELD;
  public static int THIRD_FIELD;
  public static int FIRST_FIELD;
  public static int IGNORED_FIELD;
  public int        nonStatic;
  public void testFields() throws Exception
  {
    Field[] fields = ConfigXMLFileWriter.getFields(getClass(), "IGNORED_FIELD");
    List<String> names = Query.select(fields, a -> (a.getName()));
    Approvals.verifyAll("Field", names);
  }
}
