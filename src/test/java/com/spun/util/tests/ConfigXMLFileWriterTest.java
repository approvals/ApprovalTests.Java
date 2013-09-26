package com.spun.util.tests;

import java.lang.reflect.Field;
import java.util.List;

import junit.framework.TestCase;

import org.approvaltests.Approvals;
import org.lambda.functions.implementations.F1;
import org.lambda.query.Query;

import com.spun.util.ConfigXMLFileWriter;

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
    List<String> names = Query.select(fields, new F1<Field, String>(fields[0])
    {
      {
        ret(a.getName());
      }
    });
    Approvals.verifyAll("Field", names);
  }
}
