package org.approvaltests;

import com.spun.util.ClassUtils;
import com.spun.util.logger.SimpleLogger;

import org.junit.jupiter.api.Test;
import org.lambda.query.Query;
import org.lambda.query.Queryable;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import static org.approvaltests.MethodVerification.getClasses;
import static org.approvaltests.MethodVerification.verifyMethodSignatures;

public class CheckedExceptionsTest
{
  @Test
  void testTheVerifyApi() throws Exception
  {
    try (var l = SimpleLogger.quiet())
    {
      verifyMethodSignatures("Methods with checked exceptions", getAllClasses(),
          this::getMethodsWithCheckedExceptions);
    }
  }
  private List<Method> getMethodsWithCheckedExceptions(Class<?> aClass)
  {
    Method[] declaredMethods = aClass.getDeclaredMethods();
    return Query.where(declaredMethods,
        m -> m.getExceptionTypes().length != 0 && Modifier.isPublic(m.getModifiers()));
  }
  private Queryable<Class<?>> getAllClasses()
  {
    return getClasses(ClassUtils.getProjectRootPath() + "/..", p -> p.contains("main"),
        p1 -> p1.contains(".approvaltests.src.") || p1.contains(".approvaltests-util."));
  }
}
