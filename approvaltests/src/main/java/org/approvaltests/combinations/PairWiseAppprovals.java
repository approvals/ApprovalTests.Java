package org.approvaltests.combinations;

import org.approvaltests.Approvals;
import org.approvaltests.combinations.pairwise.Case;
import org.approvaltests.combinations.pairwise.Pairwise;
import org.approvaltests.combinations.pairwise.Parameter;
import org.lambda.functions.*;
import org.lambda.query.Query;

import java.util.ArrayList;
import java.util.List;

public class PairWiseAppprovals {

    static Object EMPTY[] = CombinationApprovals.EMPTY;

    public static <IN1,  OUT> void verifyBestCoveringPairs(Function1<IN1,   OUT> call,
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
                EMPTY);
    }public static <IN1, IN2,  OUT> void verifyBestCoveringPairs(Function2<IN1, IN2,  OUT> call,
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
                EMPTY);
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
                EMPTY);
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
                EMPTY);
    }

    public static <IN1, IN2, IN3, IN4, IN5, IN6, IN7, IN8, IN9, OUT> void verifyBestCoveringPairs(
            Function9<IN1, IN2, IN3, IN4, IN5, IN6, IN7, IN8, IN9, OUT> call, IN1[] parameters1, IN2[] parameters2,
            IN3[] parameters3, IN4[] parameters4, IN5[] parameters5, IN6[] parameters6, IN7[] parameters7,
            IN8[] parameters8, IN9[] parameters9) {
        Pairwise pairwise = toPairWise(parameters1, parameters2, parameters3, parameters4, parameters5, parameters6, parameters7, parameters8, parameters9);

        final List<Case> cases = pairwise.getCases();

        StringBuffer output = new StringBuffer();
        int totalPosisbleSize = 1;
        for (Parameter<?> parameter : pairwise.getParameters()) {
            totalPosisbleSize *= parameter.size();
        }
        output.append(String.format("Testing an optimized %s/%s scenarios:\n\n", cases.size(), totalPosisbleSize));
        for (Case params : cases) {
            String result;
            final IN1 in1 = (IN1) params.get("p1");
            final IN2 in2 = (IN2) params.get("p2");
            final IN3 in3 = (IN3) params.get("p3");
            final IN4 in4 = (IN4) params.get("p4");
            final IN5 in5 = (IN5) params.get("p5");
            final IN6 in6 = (IN6) params.get("p6");
            final IN7 in7 = (IN7) params.get("p7");
            final IN8 in8 = (IN8) params.get("p8");
            final IN9 in9 = (IN9) params.get("p9");
            try {
                result = "" + call.call(in1, in2, in3, in4, in5, in6, in7, in8, in9);
            } catch (SkipCombination e) {
                continue;
            } catch (Throwable t) {
                result = String.format("%s: %s", t.getClass().getName(), t.getMessage());
            }
            output.append(String.format("%s => %s \n",
                    CombinationApprovals.extracted(in1, in2, in3, in4, in5, in6, in7, in8, in9), result));
        }
        Approvals.verify(output);

    }

    private static Pairwise toPairWise(Object[]... parameters) {
        ArrayList list = new ArrayList();
        for (int i = 0; i < parameters.length; i++) {
            list.add(new Parameter<>("p" + (i + 1), parameters[i]));
        }
        return new Pairwise.Builder().withParameters(list).build();
    }
}