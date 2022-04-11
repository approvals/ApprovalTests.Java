package org.approvaltests;

import com.spun.util.ObjectUtils;
import com.spun.util.io.FileUtils;
import com.spun.util.logger.SimpleLogger;
import org.approvaltests.core.Options;
import org.lambda.functions.Function1;
import org.lambda.functions.Functions;
import org.lambda.query.Query;
import org.lambda.query.Queryable;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Collection;

public class MethodVerification
{
  public static void verifyMethodSignatures(String header, Queryable<Class<?>> classes,
      Function1<Class<?>, Collection<Method>> getMethods)
  {
    Queryable<String> methods = classes.selectMany(getMethods)
        .select(m -> String.format("%s.%s", m.getDeclaringClass().getName(), m.getName()))
        .orderBy(m -> m.toString());
    Options options = new Options();
    Approvals.verifyAll(header, methods, c -> c.toString(), options);
  }
  public static Queryable<Class<?>> getClasses(String startingDirectory, Function1<String, Boolean> pathSelector,
      Function1<String, Boolean> secondaryPathSelector)
  {
    File[] files = FileUtils.getRecursiveFileList(new File(startingDirectory), f -> f.getName().endsWith(".java"));
    Queryable<String> paths = Query.select(files,
        Functions.unchecked(f -> f.getCanonicalPath().replace(File.separatorChar, '.')));
    paths = paths.where(pathSelector).where(secondaryPathSelector);
    return paths.select(f -> MethodVerification.getJavaClass(f));
  }
  public static Class<?> getJavaClass(String path)
  {
    String className = getJavaClassNameFromPath(path);
    try
    {
      Class<?> aClass = Class.forName(className);
      return aClass;
    }
    catch (Throwable e)
    {
      SimpleLogger.variable("Path", path);
      SimpleLogger.variable("className", className);
      throw ObjectUtils.throwAsError(e);
    }
  }
  public static String getJavaClassNameFromPath(String path)
  {
    String className = path.substring(0, path.length() - ".java".length());
    String[] top = {"com", "org", "tests"};
    for (String head : top)
    {
      head = "." + head + ".";
      if (className.contains(head))
      { return className.substring(className.indexOf(head) + 1); }
    }
    return className;
  }
}
