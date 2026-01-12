package org.approvaltests.combinations;

import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.lambda.actions.Action9;
import org.lambda.functions.Function9;
import org.lambda.query.Query;

import java.util.List;

public class CombinationsHelper
{
  public static final Object   EMPTY_ENTRY = new Object();
  public static final Object[] EMPTY       = {EMPTY_ENTRY};
  public static <IN1, IN2, IN3, IN4, IN5, IN6, IN7, IN8, IN9, OUT> void verifyAllCombinations(
      Function9<IN1, IN2, IN3, IN4, IN5, IN6, IN7, IN8, IN9, OUT> call, IN1[] parameters1, IN2[] parameters2,
      IN3[] parameters3, IN4[] parameters4, IN5[] parameters5, IN6[] parameters6, IN7[] parameters7,
      IN8[] parameters8, IN9[] parameters9, Options options)
  {
    verifyAllCombinations(new Labels(), call, parameters1, parameters2, parameters3, parameters4, parameters5,
        parameters6, parameters7, parameters8, parameters9, options);
  }

  public static <IN1, IN2, IN3, IN4, IN5, IN6, IN7, IN8, IN9, OUT> void verifyAllCombinations(Labels labels,
      Function9<IN1, IN2, IN3, IN4, IN5, IN6, IN7, IN8, IN9, OUT> call, IN1[] parameters1, IN2[] parameters2,
      IN3[] parameters3, IN4[] parameters4, IN5[] parameters5, IN6[] parameters6, IN7[] parameters7,
      IN8[] parameters8, IN9[] parameters9, Options options)
  {
    StringBuffer output = new StringBuffer();
    appendHeader(labels, output);
    Action9<IN1, IN2, IN3, IN4, IN5, IN6, IN7, IN8, IN9> addToOutput = (in11, in12, in13, in14, in15, in16, in17,
        in18, in19) -> {
      output.append(getCombinationText(labels, call, in11, in12, in13, in14, in15, in16, in17, in18, in19));
    };
    doForAllCombinations(parameters1, parameters2, parameters3, parameters4, parameters5, parameters6, parameters7,
        parameters8, parameters9, addToOutput);
    Approvals.verify(output, options);
  }

  private static void appendHeader(Labels labels, StringBuffer output)
  {
    if (labels.hasHeader())
    {
      output.append(labels.getHeader()).append("\n\n\n");
    }
  }

  private static <IN1, IN2, IN3, IN4, IN5, IN6, IN7, IN8, IN9, OUT> String getCombinationText(Labels labels,
      Function9<IN1, IN2, IN3, IN4, IN5, IN6, IN7, IN8, IN9, OUT> call, IN1 in11, IN2 in12, IN3 in13, IN4 in14,
      IN5 in15, IN6 in16, IN7 in17, IN8 in18, IN9 in19)
  {
    String result;
    try
    {
      result = "" + call.call(in11, in12, in13, in14, in15, in16, in17, in18, in19);
    }
    catch (SkipCombination e)
    {
      return "";
    }
    catch (Throwable t)
    {
      result = String.format("%s: %s", t.getClass().getName(), t.getMessage());
    }
    String inputText = formatInputs(labels, in11, in12, in13, in14, in15, in16, in17, in18, in19);
    return String.format("%s => %s \n", inputText, result);
  }

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
    return Query.where(objects, o -> o != EMPTY_ENTRY);
  }

  private static String formatInputs(Labels labels, Object... objects)
  {
    List<Object> values = filterEmpty(objects);
    if (!labels.hasLabels())
    { return values.toString(); }
    String[] labelNames = labels.getLabels();
    StringBuilder output = new StringBuilder();
    output.append("[");
    for (int i = 0; i < values.size(); i++)
    {
      if (i > 0)
      {
        output.append(", ");
      }
      String label = i < labelNames.length ? labelNames[i] : null;
      if (label != null && !label.isEmpty())
      {
        output.append(label).append(": ").append(values.get(i));
      }
      else
      {
        output.append(values.get(i));
      }
    }
    output.append("]");
    return output.toString();
  }
}
