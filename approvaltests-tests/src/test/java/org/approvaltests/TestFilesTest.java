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
    String startingDirectory = ".";
    Function1<String, Boolean> pathSelector = p -> p.contains(".test.");
    Function1<String, Boolean> secondaryPathSelector = p -> !p.contains("Test.java");
    File[] files = FileUtils.getRecursiveFileList(new File(startingDirectory), f -> f.getName().endsWith(".java"));
    Queryable<String> paths = Query.select(files,
        Functions.unchecked(f -> f.getCanonicalPath().replace(File.separatorChar, '.')));
    paths = paths.where(pathSelector).where(secondaryPathSelector);
    return paths.select(f -> CheckedExceptionsTest.getJavaClass(f));
  }
}
