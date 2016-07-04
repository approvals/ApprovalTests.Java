package org.approvaltests;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.reporters.DefaultFrontLoadedReporter;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.EnvironmentAwareReporter;
import org.approvaltests.reporters.FirstWorkingReporter;
import org.approvaltests.reporters.MultiReporter;
import org.approvaltests.reporters.UseReporter;
import org.packagesettings.PackageLevelSettings;
import org.packagesettings.Settings;

import com.spun.util.ClassUtils;
import com.spun.util.ObjectUtils;

public class ReporterFactory
{
  public static final String FRONTLOADED_REPORTER = "FrontloadedReporter";
  public static ApprovalFailureReporter get()
  {
    ApprovalFailureReporter returned = getFromAnnotation();
    if (returned == null)
    {
      returned = DiffReporter.INSTANCE;
    }
    return FirstWorkingReporter.combine(getFrontLoadedReporter(), returned);
  }
  /**
   * Loaded from PackageSettings.FrontloadedReporter
   */
  public static EnvironmentAwareReporter getFrontLoadedReporter()
  {
    Map<String, Settings> settings = PackageLevelSettings.get();
    Settings value = settings.get(FRONTLOADED_REPORTER);
    if (value != null && value.getValue() instanceof EnvironmentAwareReporter)
    {
      return (EnvironmentAwareReporter) value.getValue();
    }
    else
    {
      return DefaultFrontLoadedReporter.INSTANCE;
    }
  }
  public static ApprovalFailureReporter getFromAnnotation()
  {
    UseReporter reporter = getAnnotationsFromStackTrace(UseReporter.class).getFirst();
    return reporter == null ? null : getReporter(reporter);
  }
  private static ApprovalFailureReporter getReporter(UseReporter reporter)
  {
    Class<? extends ApprovalFailureReporter>[] classes = reporter.value();
    List<ApprovalFailureReporter> reporters = new ArrayList<ApprovalFailureReporter>();
    for (Class<? extends ApprovalFailureReporter> clazz : classes)
    {
      ApprovalFailureReporter instance = ClassUtils.create(clazz);
      reporters.add(instance);
    }
    return reporters.size() == 1 ? reporters.get(0) : new MultiReporter(reporters);
  }
  public static <T extends Annotation> StackListings<T> getAnnotationsFromStackTrace(Class<T> annotationClass)
  {
    StackListings<T> listings = new StackListings<>();
    StackTraceElement[] trace = Thread.currentThread().getStackTrace();
    for (StackTraceElement stack : trace)
    {
      Method method = null;
      Class<?> clazz = null;
      try
      {
        String methodName = stack.getMethodName();
        clazz = ObjectUtils.loadClass(stack.getClassName());
        method = clazz.getMethod(methodName, (Class<?>[]) null);
      }
      catch (Throwable e)
      {
        //ignore
      }
      T annotation = null;
      if (method != null)
      {
        annotation = method.getAnnotation(annotationClass);
      }
      if (annotation != null)
      {
        listings.addToMethodList(annotation);
      }
      if (clazz != null)
      {
        annotation = clazz.getAnnotation(annotationClass);
      }
      if (annotation != null)
      {
        listings.addToClassList(annotation);
      }
    }
    return listings;
  }
}
