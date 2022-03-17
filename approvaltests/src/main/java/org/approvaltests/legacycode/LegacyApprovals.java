package org.approvaltests.legacycode;

import com.spun.util.MethodExecutionPath.Parameters;
import org.approvaltests.Approvals;
import org.approvaltests.core.Options;

import java.lang.reflect.Method;
import java.util.Arrays;

public class LegacyApprovals
{
  public static void LockDown(Object call, String method, Object[]... parametersVariations)
  {
    LockDown(new Options(), call, method, parametersVariations);
  }
  public static void LockDown(Options options, Object call, String method, Object[]... parametersVariations)
  {
    StringBuffer sb = new StringBuffer();
    IndexPermutations perms = new IndexPermutations(getSizes(parametersVariations));
    Method m = null;
    for (Integer[] indexs : perms)
    {
      Object[] p = getParameters(parametersVariations, indexs);
      if (m == null)
      {
        m = new Parameters(p).getBestFitMethod(call.getClass(), method);
      }
      Object out;
      try
      {
        out = m.invoke(call, p);
      }
      catch (Throwable t)
      {
        out = t;
      }
      sb.append(String.format("%s = %s \n", Arrays.toString(p), out));
    }
    Approvals.verify(sb, options);
  }
  private static Object[] getParameters(Object[][] parametersVariations, Integer[] index)
  {
    Object[] parameters = new Object[index.length];
    for (int i = 0; i < index.length; i++)
    {
      parameters[i] = parametersVariations[i][index[i]];
    }
    return parameters;
  }
  public static Integer[] getSizes(Object[]... parametersVariations)
  {
    Integer[] sizes = new Integer[parametersVariations.length];
    for (int i = 0; i < sizes.length; i++)
    {
      sizes[i] = parametersVariations[i].length;
    }
    return sizes;
  }
}
