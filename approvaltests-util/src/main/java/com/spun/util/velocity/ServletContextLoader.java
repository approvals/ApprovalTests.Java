package com.spun.util.velocity;

import jakarta.servlet.ServletContext;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.loader.ResourceLoader;
import org.apache.velocity.util.ExtProperties;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;

public class ServletContextLoader extends ResourceLoader
{
  /** The root paths for templates (relative to webapp's root). */
  protected String[]              paths          = null;
  protected static ServletContext servletContext = null;
  /**
   *  This is abstract in the base class, so we need it.
   *  <br>
   *  NOTE: this expects that the ServletContext has already
   *        been placed in the runtime's application attributes
   *        under its full class name (i.e. "javax.servlet.ServletContext").
   *
   * @param configuration the {@link ExtProperties} associated with
   *        this resource loader.
   */
  public void init(ExtProperties configuration)
  {
    paths = configuration.getStringArray("path");
    if (paths == null || paths.length == 0)
    {
      paths = new String[1];
      paths[0] = "/";
    }
    else
    {
      /* make sure the paths end with a '/' */
      for (int i = 0; i < paths.length; i++)
      {
        if (!paths[i].endsWith("/"))
        {
          paths[i] += '/';
        }
      }
    }
    //My_System.variable("paths", paths);
  }

  /**
   * Get an InputStream so that the Runtime can build a
   * template with it.
   *
   * @param source name of template to get
   * @return InputStream containing the template
   */
  @Override
  public Reader getResourceReader(String source, String encoding)
  {
    if (source == null || source.length() == 0)
    { return null; }
    /* since the paths always ends in '/',
     * make sure the name never ends in one */
    while (source.startsWith("/"))
    {
      source = source.substring(1);
    }
    for (int i = 0; i < paths.length; i++)
    {
      InputStream result = null;
      result = servletContext.getResourceAsStream(paths[i] + source);
      if (result != null)
      {
        /* exit the loop if we found the template */
        return new InputStreamReader(result);
      }
    }
    throw new ResourceNotFoundException(
        String.format("Template '%s' not found from %s", source, Arrays.asList(paths)));
  }

  /**
   * Defaults to return false.
   */
  public boolean isSourceModified(Resource resource)
  {
    return false;
  }

  /**
   * Defaults to return 0
   */
  public long getLastModified(Resource resource)
  {
    return 0;
  }

  public static void registerServletContext(ServletContext servletContext)
  {
    ServletContextLoader.servletContext = servletContext;
  }
}
