package org.jrack.tests;

import java.util.Map;

import org.jrack.JRack;
import org.jrack.RackResponse;
import org.jrack.RackResponseUtils;
import org.jrack.utils.JettyUtils;

import com.spun.util.io.FileUtils;

public class JRackImage implements JRack
{
  @Override
  public RackResponse call(Map<String, Object> environment) throws Exception
  {
    char[] data = FileUtils.loadResourceFromClasspathAsBytes(getClass(), "woody_zuill.jpg");
    return RackResponseUtils.image(data);
  }
  public static void main(String[] args)
  {
    JettyUtils.startAndLaunch(7070, new JRackImage());
  }
}
