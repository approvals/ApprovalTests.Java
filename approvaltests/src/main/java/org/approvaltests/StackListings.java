package org.approvaltests;

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
    StringBuilder sb = new StringBuilder();
    if (annotation.annotationType().isAssignableFrom(UseReporter.class))
    {
      UseReporter useReporter = (UseReporter) annotation;
      String useReporterAsString = useReporter.toString();
      // on windows it uses 'interface' instead of '@'
      useReporterAsString = useReporterAsString.replace("interface", "@");
      // on windows it uses all sorts of curly brackets and stuff for displaying the 'value' array,
      // so discard that part of the string, and re-create it.
      useReporterAsString = useReporterAsString.substring(0, useReporterAsString.indexOf("(value"));
      sb.append(useReporterAsString);
      sb.append("(value=[");
      sb.append(Arrays.stream(useReporter.value()).map(Class::toString).collect(Collectors.joining(", ")));
      sb.append("])");
    }
    else
    {
      sb.append(annotation.toString());
    }
    return sb.toString();
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
