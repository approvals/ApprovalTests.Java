package org.approvaltests.combinations;

import java.util.ArrayList;
import java.util.List;

import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.lambda.actions.Action9;
import org.lambda.functions.Function9;

public class CombinationsHelper
{
  public static final Object   EMPTY_ENTRY = new Object();
  public static final Object[] EMPTY       = {EMPTY_ENTRY};
  public static <IN1, IN2, IN3, IN4, IN5, IN6, IN7, IN8, IN9, OUT> void verifyAllCombinations(
      Function9<IN1, IN2, IN3, IN4, IN5, IN6, IN7, IN8, IN9, OUT> call, IN1[] parameters1, IN2[] parameters2,
      IN3[] parameters3, IN4[] parameters4, IN5[] parameters5, IN6[] parameters6, IN7[] parameters7,
      IN8[] parameters8, IN9[] parameters9, Options options)
  {
    StringBuffer output = new StringBuffer();
    Action9<IN1, IN2, IN3, IN4, IN5, IN6, IN7, IN8, IN9> foo = (in11, in12, in13, in14, in15, in16, in17, in18,
        in19) -> {
      String result;
      try
      {
        result = "" + call.call(in11, in12, in13, in14, in15, in16, in17, in18, in19);
      }
      catch (SkipCombination e)
      {
        return;
      }
      catch (Throwable t)
      {
        result = String.format("%s: %s", t.getClass().getName(), t.getMessage());
      }
      output.append(
          String.format("%s => %s \n", filterEmpty(in11, in12, in13, in14, in15, in16, in17, in18, in19), result));
    };
    doForAllCombinations(parameters1, parameters2, parameters3, parameters4, parameters5, parameters6, parameters7,
        parameters8, parameters9, foo);
    // TODO: we still end up with 1 file which is not what we wanted in the case of SVGs or other file formats
    Approvals.verify(output, options);
  }
  // TODO: do we want to revert the changes here then again?!
  public static <IN1, IN2, IN3, IN4, IN5, IN6, IN7, IN8, IN9> void doForAllCombinations(IN1[] parameters1,
      IN2[] parameters2, IN3[] parameters3, IN4[] parameters4, IN5[] parameters5, IN6[] parameters6,
      IN7[] parameters7, IN8[] parameters8, IN9[] parameters9,
      Action9<IN1, IN2, IN3, IN4, IN5, IN6, IN7, IN8, IN9> action)
  {
    for (IN1 in1 : parameters1)
    {
      for (IN2 in2 : parameters2)
      {
        for (IN3 in3 : parameters3)
        {
          for (IN4 in4 : parameters4)
          {
            for (IN5 in5 : parameters5)
            {
              for (IN6 in6 : parameters6)
              {
                for (IN7 in7 : parameters7)
                {
                  for (IN8 in8 : parameters8)
                  {
                    for (IN9 in9 : parameters9)
                    {
                      action.call(in1, in2, in3, in4, in5, in6, in7, in8, in9);
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }
  public static List<Object> filterEmpty(Object... objects)
  {
    List<Object> list = new ArrayList<>();
    for (Object object : objects)
    {
      if (object != EMPTY_ENTRY)
      {
        list.add(object);
      }
    }
    return list;
  }
}
