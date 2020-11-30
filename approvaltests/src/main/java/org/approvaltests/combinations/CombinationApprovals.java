package org.approvaltests.combinations;

import org.approvaltests.core.Options;
import org.approvaltests.legacycode.LegacyApprovals;
import org.lambda.functions.Function1;
import org.lambda.functions.Function2;
import org.lambda.functions.Function3;
import org.lambda.functions.Function4;
import org.lambda.functions.Function5;
import org.lambda.functions.Function6;
import org.lambda.functions.Function7;
import org.lambda.functions.Function8;
import org.lambda.functions.Function9;

public class CombinationApprovals
{
  static Object[] EMPTY = CombinationsHelper.EMPTY;
  public static void verifyAllCombinations(Object call, String method, Object[]... parametersVariations)
  {
    LegacyApprovals.LockDown(call, method, parametersVariations);
  }
  /**
   * Use SkipCombination exception for invalid combinations
   */
  public static <IN1, OUT> void verifyAllCombinations(Function1<IN1, OUT> call, IN1[] parameters1)
  {
    verifyAllCombinations((n1, n2, n3, n4, n5, n6, n7, n8, n9) -> call.call(n1), parameters1, EMPTY, EMPTY, EMPTY,
        EMPTY, EMPTY, EMPTY, EMPTY, EMPTY);
  }
  /**
   * Use SkipCombination exception for invalid combinations
   */
  public static <IN1, IN2, OUT> void verifyAllCombinations(Function2<IN1, IN2, OUT> call, IN1[] parameters1,
      IN2[] parameters2)
  {
    verifyAllCombinations((n1, n2, n3, n4, n5, n6, n7, n8, n9) -> call.call(n1, n2), parameters1, parameters2,
        EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY);
  }
  /**
   * Use SkipCombination exception for invalid combinations
   */
  public static <IN1, IN2, IN3, OUT> void verifyAllCombinations(Function3<IN1, IN2, IN3, OUT> call,
      IN1[] parameters1, IN2[] parameters2, IN3[] parameters3)
  {
    verifyAllCombinations((n1, n2, n3, n4, n5, n6, n7, n8, n9) -> call.call(n1, n2, n3), parameters1, parameters2,
        parameters3, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY);
  }
  /**
   * Use SkipCombination exception for invalid combinations
   */
  public static <IN1, IN2, IN3, IN4, OUT> void verifyAllCombinations(Function4<IN1, IN2, IN3, IN4, OUT> call,
      IN1[] parameters1, IN2[] parameters2, IN3[] parameters3, IN4[] parameters4)
  {
    verifyAllCombinations((n1, n2, n3, n4, n5, n6, n7, n8, n9) -> call.call(n1, n2, n3, n4), parameters1,
        parameters2, parameters3, parameters4, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY);
  }
  /**
   * Use SkipCombination exception for invalid combinations
   */
  public static <IN1, IN2, IN3, IN4, IN5, OUT> void verifyAllCombinations(
      Function5<IN1, IN2, IN3, IN4, IN5, OUT> call, IN1[] parameters1, IN2[] parameters2, IN3[] parameters3,
      IN4[] parameters4, IN5[] parameters5)
  {
    verifyAllCombinations((n1, n2, n3, n4, n5, n6, n7, n8, n9) -> call.call(n1, n2, n3, n4, n5), parameters1,
        parameters2, parameters3, parameters4, parameters5, EMPTY, EMPTY, EMPTY, EMPTY);
  }
  /**
   * Use SkipCombination exception for invalid combinations
   */
  public static <IN1, IN2, IN3, IN4, IN5, IN6, OUT> void verifyAllCombinations(
      Function6<IN1, IN2, IN3, IN4, IN5, IN6, OUT> call, IN1[] parameters1, IN2[] parameters2, IN3[] parameters3,
      IN4[] parameters4, IN5[] parameters5, IN6[] parameters6)
  {
    verifyAllCombinations((n1, n2, n3, n4, n5, n6, n7, n8, n9) -> call.call(n1, n2, n3, n4, n5, n6), parameters1,
        parameters2, parameters3, parameters4, parameters5, parameters6, EMPTY, EMPTY, EMPTY);
  }
  /**
   * Use SkipCombination exception for invalid combinations
   */
  public static <IN1, IN2, IN3, IN4, IN5, IN6, IN7, OUT> void verifyAllCombinations(
      Function7<IN1, IN2, IN3, IN4, IN5, IN6, IN7, OUT> call, IN1[] parameters1, IN2[] parameters2,
      IN3[] parameters3, IN4[] parameters4, IN5[] parameters5, IN6[] parameters6, IN7[] parameters7)
  {
    verifyAllCombinations((n1, n2, n3, n4, n5, n6, n7, n8, n9) -> call.call(n1, n2, n3, n4, n5, n6, n7),
        parameters1, parameters2, parameters3, parameters4, parameters5, parameters6, parameters7, EMPTY, EMPTY);
  }
  /**
   * /**
   * Use SkipCombination exception for invalid combinations
   */
  public static <IN1, IN2, IN3, IN4, IN5, IN6, IN7, IN8, OUT> void verifyAllCombinations(
      Function8<IN1, IN2, IN3, IN4, IN5, IN6, IN7, IN8, OUT> call, IN1[] parameters1, IN2[] parameters2,
      IN3[] parameters3, IN4[] parameters4, IN5[] parameters5, IN6[] parameters6, IN7[] parameters7,
      IN8[] parameters8)
  {
    verifyAllCombinations((n1, n2, n3, n4, n5, n6, n7, n8, n9) -> call.call(n1, n2, n3, n4, n5, n6, n7, n8),
        parameters1, parameters2, parameters3, parameters4, parameters5, parameters6, parameters7, parameters8,
        EMPTY);
  }
  public static void verifyAllCombinations(Options options, Object call, String method, Object[]... parametersVariations)
  {
    LegacyApprovals.LockDown(options, call, method, parametersVariations);
  }
  /**
   * Use SkipCombination exception for invalid combinations
   */
  public static <IN1, OUT> void verifyAllCombinations(Function1<IN1, OUT> call, IN1[] parameters1, Options options)
  {
    verifyAllCombinations((n1, n2, n3, n4, n5, n6, n7, n8, n9) -> call.call(n1), parameters1, EMPTY, EMPTY, EMPTY,
        EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, options);
  }
  /**
   * Use SkipCombination exception for invalid combinations
   */
  public static <IN1, IN2, OUT> void verifyAllCombinations(Function2<IN1, IN2, OUT> call, IN1[] parameters1,
      IN2[] parameters2, Options options)
  {
    verifyAllCombinations((n1, n2, n3, n4, n5, n6, n7, n8, n9) -> call.call(n1, n2), parameters1, parameters2,
        EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, options);
  }
  /**
   * Use SkipCombination exception for invalid combinations
   */
  public static <IN1, IN2, IN3, OUT> void verifyAllCombinations(Function3<IN1, IN2, IN3, OUT> call,
      IN1[] parameters1, IN2[] parameters2, IN3[] parameters3, Options options)
  {
    verifyAllCombinations((n1, n2, n3, n4, n5, n6, n7, n8, n9) -> call.call(n1, n2, n3), parameters1, parameters2,
        parameters3, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, options);
  }
  /**
   * Use SkipCombination exception for invalid combinations
   */
  public static <IN1, IN2, IN3, IN4, OUT> void verifyAllCombinations(Function4<IN1, IN2, IN3, IN4, OUT> call,
      IN1[] parameters1, IN2[] parameters2, IN3[] parameters3, IN4[] parameters4, Options options)
  {
    verifyAllCombinations((n1, n2, n3, n4, n5, n6, n7, n8, n9) -> call.call(n1, n2, n3, n4), parameters1,
        parameters2, parameters3, parameters4, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, options);
  }
  /**
   * Use SkipCombination exception for invalid combinations
   */
  public static <IN1, IN2, IN3, IN4, IN5, OUT> void verifyAllCombinations(
      Function5<IN1, IN2, IN3, IN4, IN5, OUT> call, IN1[] parameters1, IN2[] parameters2, IN3[] parameters3,
      IN4[] parameters4, IN5[] parameters5, Options options)
  {
    verifyAllCombinations((n1, n2, n3, n4, n5, n6, n7, n8, n9) -> call.call(n1, n2, n3, n4, n5), parameters1,
        parameters2, parameters3, parameters4, parameters5, EMPTY, EMPTY, EMPTY, EMPTY, options);
  }
  /**
   * Use SkipCombination exception for invalid combinations
   */
  public static <IN1, IN2, IN3, IN4, IN5, IN6, OUT> void verifyAllCombinations(
      Function6<IN1, IN2, IN3, IN4, IN5, IN6, OUT> call, IN1[] parameters1, IN2[] parameters2, IN3[] parameters3,
      IN4[] parameters4, IN5[] parameters5, IN6[] parameters6, Options options)
  {
    verifyAllCombinations((n1, n2, n3, n4, n5, n6, n7, n8, n9) -> call.call(n1, n2, n3, n4, n5, n6), parameters1,
        parameters2, parameters3, parameters4, parameters5, parameters6, EMPTY, EMPTY, EMPTY, options);
  }
  /**
   * Use SkipCombination exception for invalid combinations
   */
  public static <IN1, IN2, IN3, IN4, IN5, IN6, IN7, OUT> void verifyAllCombinations(
      Function7<IN1, IN2, IN3, IN4, IN5, IN6, IN7, OUT> call, IN1[] parameters1, IN2[] parameters2,
      IN3[] parameters3, IN4[] parameters4, IN5[] parameters5, IN6[] parameters6, IN7[] parameters7, Options options)
  {
    verifyAllCombinations((n1, n2, n3, n4, n5, n6, n7, n8, n9) -> call.call(n1, n2, n3, n4, n5, n6, n7),
        parameters1, parameters2, parameters3, parameters4, parameters5, parameters6, parameters7, EMPTY, EMPTY, options);
  }
  /**
   * /**
   * Use SkipCombination exception for invalid combinations
   */
  public static <IN1, IN2, IN3, IN4, IN5, IN6, IN7, IN8, OUT> void verifyAllCombinations(
      Function8<IN1, IN2, IN3, IN4, IN5, IN6, IN7, IN8, OUT> call, IN1[] parameters1, IN2[] parameters2,
      IN3[] parameters3, IN4[] parameters4, IN5[] parameters5, IN6[] parameters6, IN7[] parameters7,
      IN8[] parameters8, Options options)
  {
    verifyAllCombinations((n1, n2, n3, n4, n5, n6, n7, n8, n9) -> call.call(n1, n2, n3, n4, n5, n6, n7, n8),
        parameters1, parameters2, parameters3, parameters4, parameters5, parameters6, parameters7, parameters8,
        EMPTY, options);
  }
  /**
   * Use SkipCombination exception for invalid combinations
   */
  public static <IN1, IN2, IN3, IN4, IN5, IN6, IN7, IN8, IN9, OUT> void verifyAllCombinations(
      Function9<IN1, IN2, IN3, IN4, IN5, IN6, IN7, IN8, IN9, OUT> call, IN1[] parameters1, IN2[] parameters2,
      IN3[] parameters3, IN4[] parameters4, IN5[] parameters5, IN6[] parameters6, IN7[] parameters7,
      IN8[] parameters8, IN9[] parameters9)
  {
    verifyAllCombinations(call, parameters1, parameters2, parameters3, parameters4, parameters5, parameters6, parameters7, parameters8, parameters9, new Options());
  }
  /**
   * Use SkipCombination exception for invalid combinations
   */
  public static <IN1, IN2, IN3, IN4, IN5, IN6, IN7, IN8, IN9, OUT> void verifyAllCombinations(
      Function9<IN1, IN2, IN3, IN4, IN5, IN6, IN7, IN8, IN9, OUT> call, IN1[] parameters1, IN2[] parameters2,
      IN3[] parameters3, IN4[] parameters4, IN5[] parameters5, IN6[] parameters6, IN7[] parameters7,
      IN8[] parameters8, IN9[] parameters9, Options options)
  {
    CombinationsHelper.verifyAllCombinations(call, parameters1, parameters2, parameters3, parameters4, parameters5, parameters6, parameters7, parameters8, parameters9, options);
  }

}
