package org.approvaltests;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.core.ApprovalTestPackageSettings;
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
import com.spun.util.ThreadUtils;

public class ReporterFactory
{
  public static ApprovalFailureReporter get()
  {
    StackTraceElement[] trace = ThreadUtils.getStackTrace();
    ApprovalFailureReporter returned = getFromAnnotation(trace);
    if (returned == null)
    {
      returned = getFromPackageSettings(trace);
    }
    if (returned == null)
    {
      returned = DiffReporter.INSTANCE;
    }
    return FirstWorkingReporter.combine(getFrontLoadedReporter(), returned);
  }
  private static ApprovalFailureReporter getFromPackageSettings(StackTraceElement[] trace)
  {
    Map<String, Settings> settings = PackageLevelSettings.getForStackTrace(trace);
    return ApprovalTestPackageSettings.USE_REPORTER.getValue(settings, () -> null);
  }
  /**
   * Loaded from PackageSettings.FrontloadedReporter
   */
  public static EnvironmentAwareReporter getFrontLoadedReporter()
  {
    Map<String, Settings> settings = PackageLevelSettings.get();
    return ApprovalTestPackageSettings.FRONTLOADED_REPORTER.getValue(settings,
        () -> DefaultFrontLoadedReporter.INSTANCE);
  }
  public static ApprovalFailureReporter getFromAnnotation(StackTraceElement[] trace)
  {
    UseReporter reporter = getAnnotationsFromStackTrace(UseReporter.class, trace).getFirst();
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
  public static <T extends Annotation> StackListings<T> getAnnotationsFromStackTrace(Class<T> annotationClass,
      StackTraceElement[] trace)
  {
    StackListings<T> listings = new StackListings<>();
    for (StackTraceElement stack : trace)
    {
      Method method = null;
      Class<?> clazz = null;
      try
      {
        String methodName = stack.getMethodName();
        clazz = ObjectUtils.loadClass(stack.getClassName());
        method = clazz.getDeclaredMethod(methodName, (Class<?>[]) null);
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
