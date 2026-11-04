package org.approvaltests.partial;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

public class JarBuilder
{
  public static final String[] MODULES = {"approvaltests-util", "approvaltests"};
  public static class Stats
  {
    public int  classes;
    public long classBytes;
    public long jarBytes;
  }
  public static List<File> build(File checkout)
  {
    Shell.run(checkout, "mvn", "-B", "-q", "-pl", String.join(",", MODULES), "clean", "package", "-Dmaven.test.skip=true",
        "-Dformatter.skip=true");
    List<File> jars = new ArrayList<>();
    for (String module : MODULES)
    {
      jars.add(findJar(new File(checkout, module + "/target")));
    }
    return jars;
  }

  private static File findJar(File target)
  {
    File[] jars = target.listFiles((d, name) -> name.endsWith(".jar") && !name.endsWith("-sources.jar")
        && !name.endsWith("-javadoc.jar") && !name.endsWith("-tests.jar"));
    if (jars == null || jars.length != 1)
    { throw new RuntimeException("Expected exactly one jar in " + target); }
    return jars[0];
  }

  public static File merge(List<File> jars, File output, String version)
  {
    output.getParentFile().mkdirs();
    Manifest manifest = new Manifest();
    manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
    manifest.getMainAttributes().putValue("Implementation-Title", "ApprovalTests (partial evaluation)");
    manifest.getMainAttributes().putValue("Implementation-Version", version);
    Set<String> written = new HashSet<>();
    written.add(JarFile.MANIFEST_NAME);
    try (JarOutputStream out = new JarOutputStream(new FileOutputStream(output), manifest))
    {
      for (File jar : jars)
      {
        try (JarFile in = new JarFile(jar))
        {
          Enumeration<JarEntry> entries = in.entries();
          while (entries.hasMoreElements())
          {
            JarEntry entry = entries.nextElement();
            if (!written.add(entry.getName()))
            {
              continue;
            }
            out.putNextEntry(new JarEntry(entry.getName()));
            try (InputStream stream = in.getInputStream(entry))
            {
              stream.transferTo(out);
            }
            out.closeEntry();
          }
        }
      }
    }
    catch (IOException e)
    {
      throw new RuntimeException(e);
    }
    return output;
  }

  public static Stats stats(List<File> jars)
  {
    Stats stats = new Stats();
    for (File jar : jars)
    {
      stats.jarBytes += jar.length();
      try (JarFile in = new JarFile(jar))
      {
        Enumeration<JarEntry> entries = in.entries();
        while (entries.hasMoreElements())
        {
          JarEntry entry = entries.nextElement();
          if (entry.getName().endsWith(".class"))
          {
            stats.classes++;
            stats.classBytes += entry.getSize();
          }
        }
      }
      catch (IOException e)
      {
        throw new RuntimeException(e);
      }
    }
    return stats;
  }
}
