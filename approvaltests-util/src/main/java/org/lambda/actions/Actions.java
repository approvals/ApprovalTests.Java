package org.lambda.actions;

import com.spun.util.ObjectUtils;

public class Actions {
    public static <In> Action1<In> unchecked(Action1WithException<In> action) {
        return i -> ObjectUtils.throwAsError(() -> action.call(i));
    }
    public static <In1, In2> Action2<In1, In2> unchecked(Action2WithException<In1, In2> action) {
        return (p1, p2) -> ObjectUtils.throwAsError(() -> action.call(p1, p2));
    }
    public static <In1, In2, In3> Action3<In1, In2, In3> unchecked(Action3WithException<In1, In2, In3> action) {
        return (p1, p2, p3) -> ObjectUtils.throwAsError(() -> action.call(p1, p2, p3));
    }
    public static <In1, In2, In3, In4> Action4<In1, In2, In3, In4> unchecked(Action4WithException<In1, In2, In3, In4> action) {
        return (p1, p2, p3, p4) -> ObjectUtils.throwAsError(() -> action.call(p1, p2, p3, p4));
    }
    public static <In1, In2, In3, In4, In5> Action5<In1, In2, In3, In4, In5> unchecked(Action5WithException<In1, In2, In3, In4, In5> action) {
        return (p1, p2, p3, p4, p5) -> ObjectUtils.throwAsError(() -> action.call(p1, p2, p3, p4, p5));
    }
    public static <In1, In2, In3, In4, In5, In6> Action6<In1, In2, In3, In4, In5, In6> unchecked(Action6WithException<In1, In2, In3, In4, In5, In6> action) {
        return (p1, p2, p3, p4, p5, p6) -> ObjectUtils.throwAsError(() -> action.call(p1, p2, p3, p4, p5, p6));
    }
    public static <In1, In2, In3, In4, In5, In6, In7> Action7<In1, In2, In3, In4, In5, In6, In7> unchecked(Action7WithException<In1, In2, In3, In4, In5, In6, In7> action) {
        return (p1, p2, p3, p4, p5, p6, p7) -> ObjectUtils.throwAsError(() -> action.call(p1, p2, p3, p4, p5, p6, p7));
    }
    public static <In1, In2, In3, In4, In5, In6, In7, In8> Action8<In1, In2, In3, In4, In5, In6, In7, In8> unchecked(Action8WithException<In1, In2, In3, In4, In5, In6, In7, In8> action) {
        return (p1, p2, p3, p4, p5, p6, p7, p8) -> ObjectUtils.throwAsError(() -> action.call(p1, p2, p3, p4, p5, p6, p7, p8));
    }
    public static <In1, In2, In3, In4, In5, In6, In7, In8, In9> Action9<In1, In2, In3, In4, In5, In6, In7, In8, In9> unchecked(Action9WithException<In1, In2, In3, In4, In5, In6, In7, In8, In9> action) {
        return (p1, p2, p3, p4, p5, p6, p7, p8, p9) -> ObjectUtils.throwAsError(() -> action.call(p1, p2, p3, p4, p5, p6, p7, p8, p9));
    }
}
