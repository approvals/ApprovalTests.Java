package org.lambda.functions;

import com.spun.util.ObjectUtils;
import org.lambda.actions.Action9;
import org.lambda.actions.Action9WithException;
import org.lambda.functions.*;

public class Functions
{
  public static <In, Out> Function1<In, Out> unchecked(Function1WithException<In, Out> function)
  {
    return i -> ObjectUtils.throwAsError(() -> function.call(i));
  }
  public static <In1, In2, Out> Function2<In1, In2, Out> unchecked(Function2WithException<In1, In2, Out> function)
  {
    return (p1, p2) -> ObjectUtils.throwAsError(() -> function.call(p1, p2));
  }
  public static <In1, In2, In3, Out> Function3<In1, In2, In3, Out> unchecked(
      Function3WithException<In1, In2, In3, Out> function)
  {
    return (p1, p2, p3) -> ObjectUtils.throwAsError(() -> function.call(p1, p2, p3));
  }
  public static <In1, In2, In3, In4, Out> Function4<In1, In2, In3, In4, Out> unchecked(
      Function4WithException<In1, In2, In3, In4, Out> function)
  {
    return (p1, p2, p3, p4) -> ObjectUtils.throwAsError(() -> function.call(p1, p2, p3, p4));
  }
  public static <In1, In2, In3, In4, In5, Out> Function5<In1, In2, In3, In4, In5, Out> unchecked(
      Function5WithException<In1, In2, In3, In4, In5, Out> function)
  {
    return (p1, p2, p3, p4, p5) -> ObjectUtils.throwAsError(() -> function.call(p1, p2, p3, p4, p5));
  }
  public static <In1, In2, In3, In4, In5, In6, Out> Function6<In1, In2, In3, In4, In5, In6, Out> unchecked(
      Function6WithException<In1, In2, In3, In4, In5, In6, Out> function)
  {
    return (p1, p2, p3, p4, p5, p6) -> ObjectUtils.throwAsError(() -> function.call(p1, p2, p3, p4, p5, p6));
  }
  public static <In1, In2, In3, In4, In5, In6, In7, Out> Function7<In1, In2, In3, In4, In5, In6, In7, Out> unchecked(
      Function7WithException<In1, In2, In3, In4, In5, In6, In7, Out> function)
  {
    return (p1, p2, p3, p4, p5, p6, p7) -> ObjectUtils
        .throwAsError(() -> function.call(p1, p2, p3, p4, p5, p6, p7));
  }
  public static <In1, In2, In3, In4, In5, In6, In7, In8, Out> Function8<In1, In2, In3, In4, In5, In6, In7, In8, Out> unchecked(
      Function8WithException<In1, In2, In3, In4, In5, In6, In7, In8, Out> function)
  {
    return (p1, p2, p3, p4, p5, p6, p7, p8) -> ObjectUtils
        .throwAsError(() -> function.call(p1, p2, p3, p4, p5, p6, p7, p8));
  }
  public static <In1, In2, In3, In4, In5, In6, In7, In8, In9, Out> Function9<In1, In2, In3, In4, In5, In6, In7, In8, In9, Out> unchecked(
      Function9WithException<In1, In2, In3, In4, In5, In6, In7, In8, In9, Out> function)
  {
    return (p1, p2, p3, p4, p5, p6, p7, p8, p9) -> ObjectUtils
        .throwAsError(() -> function.call(p1, p2, p3, p4, p5, p6, p7, p8, p9));
  }
}
