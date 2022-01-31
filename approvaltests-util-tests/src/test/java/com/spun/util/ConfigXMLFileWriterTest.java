package com.spun.util;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;
import org.lambda.query.Query;

import java.lang.reflect.Field;
import java.util.List;

public class ConfigXMLFileWriterTest
{
  public static int SECOND_FIELD;
  public static int THIRD_FIELD;
  public static int FIRST_FIELD;
  public static int IGNORED_FIELD;
  public int        nonStatic;
  @Test
  public void testFields()
  {
    Field[] fields = ConfigXMLFileWriter.getFields(getClass(), "IGNORED_FIELD");
    List<String> names = Query.select(fields, a -> (a.getName()));
    Approvals.verifyAll("Field", names);
  }
}
