package org.approvaltests.reporters;

public interface ReporterFinder
{
  String fullPath();
  String notFoundMessage();
  boolean exists();
}