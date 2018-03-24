package com.spun.util.velocity;

import java.io.File;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.log.NullLogSystem;

import com.spun.util.Asserts;
import com.spun.util.ObjectUtils;
import com.spun.util.io.FileUtils;
import com.spun.util.parser.ParserCommons;

public class VelocityParser
{
  private static VelocityEngine currentEngine = null;
  /***********************************************************************/
  static
  {
        for (Enumeration e = LogManager.getCurrentLoggers(); e.hasMoreElements();)
        {
          ((Logger) e.nextElement()).setLevel(Level.OFF);
        }
  }
  /***********************************************************************/
  public static String parseFile(String template, ContextAware process)
  {
    Asserts.assertFileExists("Velocity template", template);
    Properties props = new Properties();
    int pathBreak = template.lastIndexOf(File.separatorChar);
    pathBreak = pathBreak == -1 ? template.length() : pathBreak;
    String path = template.substring(0, pathBreak);
    String file = template.substring(pathBreak + 1);
    props.put("resource.loader", "file");
    props.put("runtime.introspector.uberspect", TestableUberspect.class.getName());
    props.put("file.resource.loader.path", path);
    props.put("velocimacro.context.localscope", "" + true);
    props.put("velocimacro.permissions.allow.inline.local.scope", "" + true);
    return parse(file, props, new ContextAware[]{process, Default.INSTANCE});
  }
  /***********************************************************************/
  public static String parseJar(String template, ContextAware process)
  {
    Properties props = new Properties();
    props.put("resource.loader", "class");
	props.put("class.resource.loader.description", "Velocity Classpath Resource Loader");
	props.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
	props.put("class.resource.cache", "" + true);	
	props.put("runtime.introspector.uberspect", "com.spun.util.velocity.TestableUberspect");
	props.put("velocimacro.context.localscope", "" + true);
	props.put("velocimacro.permissions.allow.inline.local.scope", "" + true);
    return parse(template, props, new ContextAware[]{process,Default.INSTANCE});
  }
  /***********************************************************************/
  public static String parseString(String template, ContextAware process)
  {
    Properties props = new Properties();
    props.put("resource.loader", "class");
    props.put("class.resource.loader.class", StringResourceLoader.class.getName());
    props.put("velocimacro.context.localscope", "" + true);
    props.put("runtime.introspector.uberspect", TestableUberspect.class.getName());
    props.put("velocimacro.permissions.allow.inline.local.scope", "" + true);
    return parse(template, props, new ContextAware[]{process,Default.INSTANCE});
  }
  /***********************************************************************/
  public static String parse(String template, Properties props, ContextAware process)
  {
   
    return parse(template, props, new ContextAware[]{process,Default.INSTANCE});
}/***********************************************************************/
  public static String parse(String template, Properties props, ContextAware[] process)
  {
    StringWriter out = new StringWriter();
    parse(template, props, process, out);
    return out.toString();
  }
  /***********************************************************************/
  public static Writer parse(String template, Properties props, ContextAware process[], Writer out)
  {
    try
    {
      props.put("directive.foreach.counter.initial.value", "0");
      props.put(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, NullLogSystem.class.getName());
      VelocityEngine engine = initializeEngine(props);
      VelocityContext context = new VelocityContext();
      Template velocityTemplate = engine.getTemplate(template);
      for (int i = 0; i < process.length; i++)
      {
        if(process[i] != null) process[i].setupContext(context);
      }
      velocityTemplate.merge(context, out);
      return out;
    }
    catch (Exception e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
  public static synchronized VelocityEngine initializeEngine(Properties props) throws Exception
  {
      if (currentEngine == null || isDifferentForProperties(props, currentEngine, new String[]{"resource.loader", "file.resource.loader.path"}))
      {
        currentEngine = new VelocityEngine();
        currentEngine.init(props);
      }
      return currentEngine;
  }
  /***********************************************************************/
  private static boolean isDifferentForProperties(Properties props, VelocityEngine velo, String[] keys)
  {
    for (int i = 0; i < keys.length; i++)
    {
      String key = keys[i];
      if (!ObjectUtils.isEqual(props.get(key), velo.getProperty(key))) { return true; }
    }
    return false;
  }
  /***********************************************************************/
  /**
   * Parse a File to a File
   **/
  public static String parseFile(String templateFileName, String outputFileName, ContextAware process)
  {
    return parseFile(templateFileName, new File(outputFileName), process);
  }
  /***********************************************************************/
  /**
   * Parse a File to a File
   **/
  public static String parseFile(String templateFileName, File outputFile, ContextAware process)
  {
    try
    {
      String output = parseFile(templateFileName, process);
      FileUtils.writeFile(outputFile, output);
      return output;
    }
    catch (Exception e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
  /***********************************************************************/
  public static class FileParseCall implements ParseCall
  {
    public static FileParseCall INSTANCE = new FileParseCall();
    public String parse(String template, ContextAware process)
    {
      return parseFile(template, process);
    }
  }
  /***********************************************************************/
  public static class JarParseCall implements ParseCall
  {
    public static JarParseCall INSTANCE = new JarParseCall();
    public String parse(String template, ContextAware process)
    {
      return parseJar(template, process);
    }
  }
  /***********************************************************************/
  public static class Default implements ContextAware
  {
    public static ContextAware INSTANCE = new Default();
    public void setupContext(Context context)
    {
      context.put("commons", ParserCommons.INSTANCE);
    }
  }
  /***********************************************************************/
  /***********************************************************************/
  public static String parseFromClassPath(Class<?> clazz, String string, ContextAware context)
  {
    String resource = FileUtils.readFromClassPath(clazz, string);
    return parseString(resource, context);
  }
 
}
