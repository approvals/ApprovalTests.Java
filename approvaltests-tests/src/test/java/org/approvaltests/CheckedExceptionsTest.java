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
import java.util.Collection;
import java.util.List;

public class CheckedExceptionsTest
{
  @Test
  void testTheVerifyApi()
  {
    verifyMethodSignatures("Methods with checked exceptions", getAllClasses(),
            this::getMethodsWithCheckedExceptions);
  }

  public static void verifyMethodSignatures(String header, Queryable<Class<?>> classes, Function1<Class<?>, Collection<Method>> getMethods) {
    Queryable<String> methods = classes.selectMany(getMethods)
        .select(m -> String.format("%s.%s", m.getDeclaringClass().getName(), m.getName()))
        .orderBy(m -> m.toString());
    Options options = new Options();
    Approvals.verifyAll(header, methods, c -> c.toString(), options);
  }

  private List<Method> getMethodsWithCheckedExceptions(Class<?> aClass)
  {
    Method[] declaredMethods = aClass.getDeclaredMethods();
    return Query.where(declaredMethods,
        m -> m.getExceptionTypes().length != 0 && Modifier.isPublic(m.getModifiers()));
  }
  private Queryable<Class<?>> getAllClasses()
  {
    return getClasses("..", p -> p.contains("main"), 
            p1 -> p1.contains(".approvaltests.src.") || p1.contains(".approvaltests-util."));
  }

  public static Queryable<Class<?>> getClasses(String startingDirectory, Function1<String, Boolean> pathSelector, Function1<String, Boolean> secondaryPathSelector) {
    File[] files = FileUtils.getRecursiveFileList(new File(startingDirectory), f -> f.getName().endsWith(".java"));
    Queryable<String> paths = Query.select(files,
        Functions.unchecked(f -> f.getCanonicalPath().replace(File.separatorChar, '.')));
    paths = paths.where(pathSelector)
        .where(secondaryPathSelector);
    return paths.select(f -> getJavaClass(f));
  }

  public static Class<?> getJavaClass(String path)
  {
    String className = path.substring(0, path.length() - ".java".length());
    if (className.contains(".com."))
    {
      className = className.substring(className.indexOf(".com.") + 1);
    }
    if (className.contains(".org."))
    {
      className = className.substring(className.indexOf(".org.") + 1);
    }
    try
    {
      Class<?> aClass = Class.forName(className);
      return aClass;
    }
    catch (Throwable e)
    {
      SimpleLogger.variable("Path", path);
      throw ObjectUtils.throwAsError(e);
    }
  }
}
