package com.spun.util.velocity;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

import org.apache.velocity.runtime.RuntimeLogger;
import org.apache.velocity.runtime.parser.node.AbstractExecutor;
import org.apache.velocity.runtime.parser.node.BooleanPropertyExecutor;
import org.apache.velocity.runtime.parser.node.GetExecutor;
import org.apache.velocity.runtime.parser.node.PropertyExecutor;
import org.apache.velocity.util.ArrayIterator;
import org.apache.velocity.util.EnumerationIterator;
import org.apache.velocity.util.introspection.Info;
import org.apache.velocity.util.introspection.Introspector;
import org.apache.velocity.util.introspection.IntrospectorBase;
import org.apache.velocity.util.introspection.Uberspect;
import org.apache.velocity.util.introspection.UberspectLoggable;
import org.apache.velocity.util.introspection.VelMethod;
import org.apache.velocity.util.introspection.VelPropertyGet;
import org.apache.velocity.util.introspection.VelPropertySet;

import com.spun.util.ObjectUtils;

/**
 *  Implementation of Uberspect to provide the default introspective
 *  functionality of Velocity
 *
 * @author <a href="mailto:geirm@optonline.net">Geir Magnusson Jr.</a>
 * @version $Id: UberspectImpl.java,v 1.2.4.1 2004/03/03 23:23:08 geirm Exp $
 */
public class TestableUberspect implements Uberspect, UberspectLoggable
{
  /**
   *  the default Velocity introspector
   */
  private static IntrospectorBase introspector;
  private static Introspector     introspectorWithLog;
  private RuntimeLogger           log;
  private static boolean          beKindToNulls = false;
  /**
   *  init - does nothing - we need to have setRuntimeLogger
   *  called before getting our introspector, as the default
   *  vel introspector depends upon it.;
   */
  public void init() throws Exception
  {
  }
  public void setRuntimeLogger(RuntimeLogger runtimeLogger)
  {
    introspector = new IntrospectorBase();
    introspectorWithLog = new Introspector(runtimeLogger);
    log = runtimeLogger;
  }
  public void setBeKindToNulls(boolean behavior)
  {
    beKindToNulls = behavior;
  }
  public Iterator<?> getIterator(Object obj, Info i) throws Exception
  {
    return getStandardIterator(obj, i);
  }
  public static Iterator<?> getStandardIterator(Object obj, Info i)
  {
    if (obj.getClass().isArray())
    {
      return new ArrayIterator(obj);
    }
    else if (obj instanceof Collection)
    {
      return ((Collection<?>) obj).iterator();
    }
    else if (obj instanceof Map)
    {
      return ((Map<?, ?>) obj).values().iterator();
    }
    else if (obj instanceof Iterator)
    {
      return ((Iterator<?>) obj);
    }
    else if (obj instanceof Enumeration)
    { return new EnumerationIterator((Enumeration<?>) obj); }
    throw new VelocityParsingError("Could not determine type of iterator in " + "#foreach loop ", i);
  }
  public VelMethod getMethod(Object obj, String methodName, Object[] args, Info i) throws Exception
  {
    if (obj == null)
    {
      if (beKindToNulls)
      {
        return null;
      }
      else
      {
        throw new VelocityParsingError("tried " + getMethodText("null", methodName, args), i);
      }
    }
    Method m = introspector.getMethod(obj.getClass(), methodName, args);
    if (m == null)
    {
      throw new VelocityParsingError(
          "Method " + getMethodText(obj.getClass().getName(), methodName, args) + " does not exist.", i);
    }
    return new VelMethodImpl(m);
  }
  public static String getMethodText(String className, String methodName, Object[] args)
  {
    StringBuffer methodSignature = new StringBuffer();
    for (int i = 0; args != null && i < args.length; i++)
    {
      methodSignature.append(ObjectUtils.getClassName(args[i]));
      methodSignature.append(i == (args.length - 1) ? "" : ", ");
    }
    return className + "." + methodName + "(" + methodSignature + ") ";
  }
  public VelPropertyGet getPropertyGet(Object obj, String identifier, Info i) throws Exception
  {
    AbstractExecutor executor;
    if (obj == null)
    { throw new VelocityParsingError("tried " + getPropertyText("null", identifier), i); }
    Class<? extends Object> claz = obj.getClass();
    // trying getFoo()
    executor = new PropertyExecutor(log, introspectorWithLog, claz, identifier);
    if (!executor.isAlive())
    {
      // trying  get("foo")
      executor = new GetExecutor(log, introspectorWithLog, claz, identifier);
    }
    if (!executor.isAlive())
    {
      // trying  isFoo()
      executor = new BooleanPropertyExecutor(log, introspectorWithLog, claz, identifier);
    }
    if (!executor.isAlive())
    { throw new VelocityParsingError("Did not find " + getPropertyText(obj.getClass().getName(), identifier), i); }
    return new VelGetterImpl(executor);
  }
  private String getPropertyText(String className, String identifier)
  {
    return className + "." + identifier + " ";
  }
  public VelPropertySet getPropertySet(Object obj, String identifier, Object arg, Info i) throws Exception
  {
    Class<? extends Object> claz = obj.getClass();
    VelMethod vm = null;
    try
    {
      /*
       *  first, we introspect for the set<identifier> setter method
       */
      Object[] params = {arg};
      try
      {
        vm = getMethod(obj, "set" + identifier, params, i);
        if (vm == null)
        { throw new NoSuchMethodException(); }
      }
      catch (NoSuchMethodException nsme2)
      {
        StringBuffer sb = new StringBuffer("set");
        sb.append(identifier);
        if (Character.isLowerCase(sb.charAt(3)))
        {
          sb.setCharAt(3, Character.toUpperCase(sb.charAt(3)));
        }
        else
        {
          sb.setCharAt(3, Character.toLowerCase(sb.charAt(3)));
        }
        vm = getMethod(obj, sb.toString(), params, i);
        if (vm == null)
        { throw new NoSuchMethodException(); }
      }
    }
    catch (NoSuchMethodException nsme)
    {
      /*
       *  right now, we only support the Map interface
       */
      if (Map.class.isAssignableFrom(claz))
      {
        Object[] params = {new Object(), new Object()};
        vm = getMethod(obj, "put", params, i);
        if (vm != null)
          return new VelSetterImpl(vm, identifier);
      }
    }
    return (vm != null) ? new VelSetterImpl(vm) : null;
  }
  /*                          INNER CLASS                                */
  public static class VelMethodImpl implements VelMethod
  {
    Method method = null;
    public VelMethodImpl(Method m)
    {
      method = m;
    }
    public Object invoke(Object o, Object[] params) throws Exception
    {
      return method.invoke(o, params);
    }
    public boolean isCacheable()
    {
      return true;
    }
    public String getMethodName()
    {
      return method.getName();
    }
    public Class<?> getReturnType()
    {
      return method.getReturnType();
    }
  }
  public static class VelGetterImpl implements VelPropertyGet
  {
    AbstractExecutor ae = null;
    public VelGetterImpl(AbstractExecutor exec)
    {
      ae = exec;
    }
    public Object invoke(Object o) throws Exception
    {
      return ae.execute(o);
    }
    public boolean isCacheable()
    {
      return true;
    }
    public String getMethodName()
    {
      return ae.getMethod().getName();
    }
  }
  public static class VelSetterImpl implements VelPropertySet
  {
    VelMethod vm     = null;
    String    putKey = null;
    public VelSetterImpl(VelMethod velmethod)
    {
      this.vm = velmethod;
    }
    public VelSetterImpl(VelMethod velmethod, String key)
    {
      this.vm = velmethod;
      putKey = key;
    }
    public Object invoke(Object o, Object value) throws Exception
    {
      ArrayList<Object> al = new ArrayList<Object>();
      if (putKey != null)
      {
        al.add(putKey);
        al.add(value);
      }
      else
      {
        al.add(value);
      }
      return vm.invoke(o, al.toArray());
    }
    public boolean isCacheable()
    {
      return true;
    }
    public String getMethodName()
    {
      return vm.getMethodName();
    }
  }
}
