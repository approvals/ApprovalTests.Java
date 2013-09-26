package org.approvaltests.namer;

import java.io.File;

import org.approvaltests.namer.JUnitStackTraceNamer;

/**
 * Uses maven test resources folder as base approval file folder.
 *
 * @author mzagar
 *
 */
public final class MavenResourcesApprovalNamer implements ApprovalNamer
{
  private static final String        mavenSourceBase = "src/test/java".replace("/", File.separator);
  private static final String        mavenTestBase   = "src/test/resources".replace("/", File.separator);
  private final JUnitStackTraceNamer namer;
  
  public MavenResourcesApprovalNamer()
  {
    this(new JUnitStackTraceNamer());
  }
  
  public MavenResourcesApprovalNamer(JUnitStackTraceNamer junitStackTraceNamer)
  {
    this.namer = junitStackTraceNamer;
  }
  
  @Override
  public String getApprovalFileBasePath()
  {
    return namer.getApprovalFileBasePath().replace(mavenSourceBase, mavenTestBase);
  }
  
  @Override
  public String getApprovalName()
  {
    return namer.getApprovalName();
  }
}
