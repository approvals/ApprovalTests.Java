package org.approvaltests.combinations;

import org.approvaltests.combinations.pairwise.PairwiseHelper;
import org.approvaltests.core.Options;
import org.lambda.functions.Function1;
import org.lambda.functions.Function2;
import org.lambda.functions.Function3;
import org.lambda.functions.Function4;
import org.lambda.functions.Function5;
import org.lambda.functions.Function6;
import org.lambda.functions.Function7;
import org.lambda.functions.Function8;
import org.lambda.functions.Function9;

public class PairWiseApprovals {

    static Object[] EMPTY = CombinationsHelper.EMPTY;

    public static <IN1, OUT> void verifyBestCoveringPairs(Function1<IN1, OUT> call,
                                                          IN1[] parameters1) {
        verifyBestCoveringPairs((n1, n2, n3, n4, n5, n6, n7, n8, n9) -> call.call(n1),
                parameters1,
                EMPTY,
                EMPTY,
                EMPTY,
                EMPTY,
                EMPTY,
                EMPTY,
                EMPTY,
                EMPTY,
                new Options());
    }

    public static <IN1, OUT> void verifyBestCoveringPairs(Function1<IN1, OUT> call,
                                                          IN1[] parameters1, Options options) {
        verifyBestCoveringPairs((n1, n2, n3, n4, n5, n6, n7, n8, n9) -> call.call(n1),
                parameters1,
                EMPTY,
                EMPTY,
                EMPTY,
                EMPTY,
                EMPTY,
                EMPTY,
                EMPTY,
                EMPTY,
                options);
    }

    public static <IN1, IN2, OUT> void verifyBestCoveringPairs(Function2<IN1, IN2, OUT> call,
                                                               IN1[] parameters1, IN2[] parameters2) {
        verifyBestCoveringPairs((n1, n2, n3, n4, n5, n6, n7, n8, n9) -> call.call(n1, n2),
                parameters1,
                parameters2,
                EMPTY,
                EMPTY,
                EMPTY,
                EMPTY,
                EMPTY,
                EMPTY,
                EMPTY,
                new Options());
    }

    public static <IN1, IN2, OUT> void verifyBestCoveringPairs(Function2<IN1, IN2, OUT> call,
                                                               IN1[] parameters1, IN2[] parameters2, Options options) {
        verifyBestCoveringPairs((n1, n2, n3, n4, n5, n6, n7, n8, n9) -> call.call(n1, n2),
                parameters1,
                parameters2,
                EMPTY,
                EMPTY,
                EMPTY,
                EMPTY,
                EMPTY,
                EMPTY,
                EMPTY,
                options);
    }

    public static <IN1, IN2, IN3, OUT> void verifyBestCoveringPairs(Function3<IN1, IN2, IN3, OUT> call,
                                                                    IN1[] parameters1, IN2[] parameters2, IN3[] parameters3) {
        verifyBestCoveringPairs((n1, n2, n3, n4, n5, n6, n7, n8, n9) -> call.call(n1, n2, n3),
                parameters1,
                parameters2,
                parameters3,
                EMPTY,
                EMPTY,
                EMPTY,
                EMPTY,
                EMPTY,
                EMPTY,
                new Options());
    }

    public static <IN1, IN2, IN3, OUT> void verifyBestCoveringPairs(Function3<IN1, IN2, IN3, OUT> call,
                                                                    IN1[] parameters1, IN2[] parameters2, IN3[] parameters3, Options options) {
        verifyBestCoveringPairs((n1, n2, n3, n4, n5, n6, n7, n8, n9) -> call.call(n1, n2, n3),
                parameters1,
                parameters2,
                parameters3,
                EMPTY,
                EMPTY,
                EMPTY,
                EMPTY,
                EMPTY,
                EMPTY,
               options);
    }

    public static <IN1, IN2, IN3, IN4, OUT> void verifyBestCoveringPairs(Function4<IN1, IN2, IN3, IN4, OUT> call,
                                                                         IN1[] parameters1, IN2[] parameters2, IN3[] parameters3, IN4[] parameters4) {
        verifyBestCoveringPairs((n1, n2, n3, n4, n5, n6, n7, n8, n9) -> call.call(n1, n2, n3, n4),
                parameters1,
                parameters2,
                parameters3,
                parameters4,
                EMPTY,
                EMPTY,
                EMPTY,
                EMPTY,
                EMPTY,
                new Options());
    }

    public static <IN1, IN2, IN3, IN4, OUT> void verifyBestCoveringPairs(Function4<IN1, IN2, IN3, IN4, OUT> call,
                                                                         IN1[] parameters1, IN2[] parameters2, IN3[] parameters3, IN4[] parameters4, Options options) {
        verifyBestCoveringPairs((n1, n2, n3, n4, n5, n6, n7, n8, n9) -> call.call(n1, n2, n3, n4),
                parameters1,
                parameters2,
                parameters3,
                parameters4,
                EMPTY,
                EMPTY,
                EMPTY,
                EMPTY,
                EMPTY,
                options);
    }

    public static <IN1, IN2, IN3, IN4, IN5, OUT> void verifyBestCoveringPairs(Function5<IN1, IN2, IN3, IN4, IN5, OUT> call,
                                                                              IN1[] parameters1, IN2[] parameters2, IN3[] parameters3, IN4[] parameters4, IN5[] parameters5) {
        verifyBestCoveringPairs((n1, n2, n3, n4, n5, n6, n7, n8, n9) -> call.call(n1, n2, n3, n4, n5),
                parameters1,
                parameters2,
                parameters3,
                parameters4,
                parameters5,
                EMPTY,
                EMPTY,
                EMPTY,
                EMPTY,
                new Options());
    }

    public static <IN1, IN2, IN3, IN4, IN5, OUT> void verifyBestCoveringPairs(Function5<IN1, IN2, IN3, IN4, IN5, OUT> call,
                                                                              IN1[] parameters1, IN2[] parameters2, IN3[] parameters3, IN4[] parameters4, IN5[] parameters5, Options options) {
        verifyBestCoveringPairs((n1, n2, n3, n4, n5, n6, n7, n8, n9) -> call.call(n1, n2, n3, n4, n5),
                parameters1,
                parameters2,
                parameters3,
                parameters4,
                parameters5,
                EMPTY,
                EMPTY,
                EMPTY,
                EMPTY,
                options);
    }

    public static <IN1, IN2, IN3, IN4, IN5, IN6, OUT> void verifyBestCoveringPairs(Function6<IN1, IN2, IN3, IN4, IN5, IN6, OUT> call,
                                                                                   IN1[] parameters1, IN2[] parameters2, IN3[] parameters3, IN4[] parameters4, IN5[] parameters5, IN6[] parameters6) {
        verifyBestCoveringPairs((n1, n2, n3, n4, n5, n6, n7, n8, n9) -> call.call(n1, n2, n3, n4, n5, n6),
                parameters1,
                parameters2,
                parameters3,
                parameters4,
                parameters5,
                parameters6,
                EMPTY,
                EMPTY,
                EMPTY,
                new Options());
    }
    public static <IN1, IN2, IN3, IN4, IN5, IN6, OUT> void verifyBestCoveringPairs(Function6<IN1, IN2, IN3, IN4, IN5, IN6, OUT> call,
                                                                                   IN1[] parameters1, IN2[] parameters2, IN3[] parameters3, IN4[] parameters4, IN5[] parameters5, IN6[] parameters6, Options options) {
        verifyBestCoveringPairs((n1, n2, n3, n4, n5, n6, n7, n8, n9) -> call.call(n1, n2, n3, n4, n5, n6),
                parameters1,
                parameters2,
                parameters3,
                parameters4,
                parameters5,
                parameters6,
                EMPTY,
                EMPTY,
                EMPTY,
                options);
    }

    public static <IN1, IN2, IN3, IN4, IN5, IN6, IN7, OUT> void verifyBestCoveringPairs(Function7<IN1, IN2, IN3, IN4, IN5, IN6, IN7, OUT> call,
                                                                                        IN1[] parameters1, IN2[] parameters2, IN3[] parameters3, IN4[] parameters4, IN5[] parameters5, IN6[] parameters6, IN7[] parameters7) {
        verifyBestCoveringPairs((n1, n2, n3, n4, n5, n6, n7, n8, n9) -> call.call(n1, n2, n3, n4, n5, n6, n7),
                parameters1,
                parameters2,
                parameters3,
                parameters4,
                parameters5,
                parameters6,
                parameters7,
                EMPTY,
                EMPTY,
                new Options());
    }
    public static <IN1, IN2, IN3, IN4, IN5, IN6, IN7, OUT> void verifyBestCoveringPairs(Function7<IN1, IN2, IN3, IN4, IN5, IN6, IN7, OUT> call,
                                                                                        IN1[] parameters1, IN2[] parameters2, IN3[] parameters3, IN4[] parameters4, IN5[] parameters5, IN6[] parameters6, IN7[] parameters7, Options options) {
        verifyBestCoveringPairs((n1, n2, n3, n4, n5, n6, n7, n8, n9) -> call.call(n1, n2, n3, n4, n5, n6, n7),
                parameters1,
                parameters2,
                parameters3,
                parameters4,
                parameters5,
                parameters6,
                parameters7,
                EMPTY,
                EMPTY,
                options);
    }
    public static <IN1, IN2, IN3, IN4, IN5, IN6, IN7,IN8, OUT> void verifyBestCoveringPairs(Function8<IN1, IN2, IN3, IN4, IN5, IN6, IN7,IN8, OUT> call,
                                                                                            IN1[] parameters1, IN2[] parameters2, IN3[] parameters3, IN4[] parameters4, IN5[] parameters5, IN6[] parameters6, IN7[] parameters7, IN8[] parameters8) {
        verifyBestCoveringPairs((n1, n2, n3, n4, n5, n6, n7, n8, n9) -> call.call(n1, n2, n3, n4, n5, n6, n7,n8),
                parameters1,
                parameters2,
                parameters3,
                parameters4,
                parameters5,
                parameters6,
                parameters7,
                parameters8,
                EMPTY,
                new Options());
    }

    public static <IN1, IN2, IN3, IN4, IN5, IN6, IN7, IN8, IN9, OUT> void verifyBestCoveringPairs(
            Function9<IN1, IN2, IN3, IN4, IN5, IN6, IN7, IN8, IN9, OUT> call, IN1[] parameters1, IN2[] parameters2,
            IN3[] parameters3, IN4[] parameters4, IN5[] parameters5, IN6[] parameters6, IN7[] parameters7,
            IN8[] parameters8, IN9[] parameters9) {
        verifyBestCoveringPairs(call,
                parameters1,
                parameters2,
                parameters3,
                parameters4,
                parameters5,
                parameters6,
                parameters7,
                parameters8,
                parameters9,
                new Options());
    }

    public static <IN1, IN2, IN3, IN4, IN5, IN6, IN7, IN8, IN9, OUT> void verifyBestCoveringPairs(
            Function9<IN1, IN2, IN3, IN4, IN5, IN6, IN7, IN8, IN9, OUT> call, IN1[] parameters1, IN2[] parameters2,
            IN3[] parameters3, IN4[] parameters4, IN5[] parameters5, IN6[] parameters6, IN7[] parameters7,
            IN8[] parameters8, IN9[] parameters9, Options options) {
        PairwiseHelper.verifyBestCoveringPairs(call, parameters1, parameters2, parameters3, parameters4, parameters5, parameters6, parameters7, parameters8, parameters9, options);
    }

}
