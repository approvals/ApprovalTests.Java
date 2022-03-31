package org.approvaltests;

import com.spun.util.ObjectUtils;
import com.spun.util.io.FileUtils;
import com.spun.util.logger.SimpleLogger;
import org.approvaltests.core.Options;
import org.junit.jupiter.api.Test;
import org.lambda.functions.Function1;
import org.lambda.functions.Functions;
import org.lambda.query.Query;
import org.lambda.query.Queryable;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

public class TestFilesTest
{
  @Test
  void testTestFileNamesEndInTest()
  {
    // Get all of the classes for all the packages
    Queryable<Class<?>> classes = getAllClasses();
    // Filter for methods in the classes that have a checked exception
    Queryable<String> methods = classes.selectMany(s -> getTestMethods(s))
        .select(m -> String.format("%s.%s", m.getDeclaringClass().getName(), m.getName()))
        .orderBy(m -> m.toString());
    // Verify the methods
    Options options = new Options();
    Approvals.verifyAll(
        "Test Methods in files that do not contain the word 'Test' (these will not be run in our CI build)",
        methods, c -> c.toString(), options);
  }
  private List<Method> getTestMethods(Class<?> aClass)
  {
    Method[] declaredMethods = aClass.getDeclaredMethods();
    return Query.where(declaredMethods,
        m -> Queryable.as(m.getAnnotations()).any(a -> a.toString().contains("Test")));
  }
  private Queryable<Class<?>> getAllClasses()
  {
    return CheckedExceptionsTest.getClasses(".", p -> p.contains(".test."), 
            p1 -> !p1.contains("Test.java"));
  }
}
