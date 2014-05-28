package org.approvaltests.reporters.tests;

import static org.junit.Assert.fail;

import java.io.File;

import org.approvaltests.reporters.MultipleLocationReporterFinder;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;


public class MultipleLocationReporterFinderTest 
{
  @Rule public TemporaryFolder root = new TemporaryFolder();
  private MultipleLocationReporterFinder finder;
  
  @Test public void 
  fullPath_returnsFirstExistingReporter() throws Exception {
    File existingFile = root.newFile();
    String[] possibles = {"/tmp/nonexisting", existingFile.getAbsolutePath()};
    finder = new MultipleLocationReporterFinder(possibles);
    Assert.assertEquals(existingFile.getAbsolutePath(), finder.fullPath());
  }
  
  @Test public void 
  fullPath_returnsEmptyString_whenReporterIsntPresent() throws Exception {
    String[] possibles = {"/tmp/nonexisting", "anothernonexistingfile"};
    finder = new MultipleLocationReporterFinder(possibles);
    Assert.assertEquals("", finder.fullPath());
     
  }
  
  @Test public void 
  notFoundMessage_reportsAllTriedLocations() throws Exception {
    String[] possibles = {"/tmp/nonexisting", "anothernonexistingfile"};
    finder = new MultipleLocationReporterFinder(possibles);
    Assert.assertTrue(finder.notFoundMessage().contains("/tmp/nonexisting"));
    Assert.assertTrue(finder.notFoundMessage().contains("anothernonexistingfile"));
  }
  
  @Ignore @Test public void 
  exists_forExistingReporter_returnsTrue() throws Exception {
     
  }

  @Ignore @Test public void 
  exists_forInexistantReporter_returnsFalse() throws Exception {
    
  }
}
