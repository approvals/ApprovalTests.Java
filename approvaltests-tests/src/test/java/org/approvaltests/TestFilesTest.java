package org.approvaltests;

import org.junit.jupiter.api.Test;
import org.lambda.query.Query;
import org.lambda.query.Queryable;

import java.lang.reflect.Method;
import java.util.List;

import static org.approvaltests.MethodVerification.getClasses;
import static org.approvaltests.MethodVerification.verifyMethodSignatures;

public class TestFilesTest
{
  @Test
  void testTestFileNamesEndInTest()
  {
    verifyMethodSignatures(
        "Test Methods in files that do not contain the word 'Test' (these will not be run in our CI build)",
        getAllClasses(), this::getTestMethods);
  }
  private List<Method> getTestMethods(Class<?> aClass)
  {
    Method[] declaredMethods = aClass.getDeclaredMethods();
    return Query.where(declaredMethods,
        m -> Queryable.as(m.getAnnotations()).any(a -> a.toString().contains("Test")));
  }
  private Queryable<Class<?>> getAllClasses()
  {
    return getClasses(".", p -> p.contains(".test."), p1 -> !p1.contains("Test.java"));
  }
}
