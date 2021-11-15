package org.approvaltests;

import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.reporters.UseReporter;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StackListings<T>
{
  private List<T> methods = new ArrayList<>();
  private List<T> classes = new ArrayList<>();
  public void addToMethodList(T found)
  {
    methods.add(found);
  }
  public void addToClassList(T found)
  {
    classes.add(found);
  }
  @Override
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    sb.append("StackListings [\n  methods=[");
    sb.append(methods.stream().map(a -> printAnnotation((Annotation) a)).collect(Collectors.joining(", ")));
    sb.append("]\n  classes=[");
    sb.append(classes.stream().map(c -> printAnnotation((Annotation) c)).collect(Collectors.joining(", ")));
    sb.append("]\n]");
    return sb.toString();
  }
  /**
   * The reason for this method is that the built-in toString() method
   * on an annotation is platform dependent. It prints something different
   * on Windows, which causes the unit tests to fail on that platform.
   * By using this method instead, we eliminate the platform dependent tests problem.
   */
  private String printAnnotation(Annotation annotation)
  {
    String text = annotation.toString();
    if (annotation.annotationType().isAssignableFrom(UseReporter.class))
    {
      UseReporter useReporter = (UseReporter) annotation;
      return printUserReporter(text, useReporter.value());
    }
    else
    {
      return text;
    }
  }

  public static String printUserReporter(String text, Class<? extends ApprovalFailureReporter>[] values) {
    text = makeConsistentBetweenJavaVersions(text);
    StringBuilder sb = new StringBuilder();
    // on windows it uses 'interface' instead of '@'
    text = text.replace("interface", "@");
    // on windows it uses all sorts of curly brackets and stuff for displaying the 'value' array,
    // so discard that part of the string, and re-create it.
    text = text.substring(0, text.indexOf("(value"));
    sb.append(text);
    sb.append("(value=[");
    sb.append(Arrays.stream(values).map(Class::toString).collect(Collectors.joining(", ")));
    sb.append("])");
    return sb.toString();
  }

  private static String makeConsistentBetweenJavaVersions(String text) {
    return text.replace(".UseReporter({", ".UseReporter(value={");
  }

  public T getFirst()
  {
    if (!methods.isEmpty())
    {
      return methods.get(0);
    }
    else if (!classes.isEmpty())
    { return classes.get(0); }
    return null;
  }
}
