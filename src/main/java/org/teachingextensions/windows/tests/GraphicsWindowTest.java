package org.teachingextensions.windows.tests;

import junit.framework.TestCase;

import org.approvaltests.reporters.DelayedClipboardReporter;
import org.approvaltests.reporters.FileLauncherReporter;
import org.approvaltests.reporters.UseReporter;

@UseReporter({FileLauncherReporter.class, DelayedClipboardReporter.class})
public class GraphicsWindowTest extends TestCase
{
  public void testEmptyWindow() throws Exception
  {
  }
}
