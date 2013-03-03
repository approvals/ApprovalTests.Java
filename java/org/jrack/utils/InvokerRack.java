package org.jrack.utils;

import java.util.Map;

import org.jrack.JRack;
import org.jrack.RackEnvironment;
import org.jrack.RackResponse;
import org.jrack.RackResponseUtils;

import com.spun.util.Asserts;
import com.spun.util.FormattedException;

public class InvokerRack implements JRack
{
  private final String mask;
  public InvokerRack(String mask)
  {
    mask = mask.replace(".", "\\.");
    mask = mask.replace("*", ".*");
    this.mask = mask;
  }
  @Override
  public RackResponse call(Map<String, Object> input) throws Exception
  {
    String clazz = RackEnviromentHelper.getPathInfo(input);
    clazz = getClasspathFromUrl(clazz);
    if (ignoreCall(clazz)) { return RackResponseUtils.standardHtml(""); }
    Asserts.assertNotNull("map[" + RackEnvironment.PATH_INFO + "]", clazz);
    assertValidClass(clazz);
    JRack rack = getRack(clazz);
    return rack.call(input);
  }
  private boolean ignoreCall(String clazz)
  {
    return "favicon.ico".equals(clazz);
  }
  private String getClasspathFromUrl(String clazz)
  {
    int indexOf = clazz.lastIndexOf('/');
    if (indexOf != -1) { return clazz.substring(indexOf + 1); }
    return clazz;
  }
  private JRack getRack(String classpath) throws Exception
  {
    return (JRack) Class.forName(classpath).newInstance();
  }
  private void assertValidClass(String classpath)
  {
    if (!classpath.matches(mask)) { throw new FormattedException("Invalid class [%s] for mask [%s]", classpath,
        mask); }
  }
}
