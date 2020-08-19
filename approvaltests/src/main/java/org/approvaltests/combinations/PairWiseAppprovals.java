package org.approvaltests.combinations;

import org.approvaltests.Approvals;
import org.approvaltests.combinations.pairwise.Case;
import org.approvaltests.combinations.pairwise.Pairwise;
import org.approvaltests.combinations.pairwise.Parameter;
import org.lambda.functions.Function4;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PairWiseAppprovals {

    public static <IN1, IN2, IN3, IN4, OUT> void verifyBestCoveringPairs(Function4<IN1, IN2, IN3, IN4, OUT> call,
                                                                         IN1[] parameters1, IN2[] parameters2, IN3[] parameters3, IN4[] parameters4) {
        Pairwise pairwise = new Pairwise.Builder()
                .withParameters(Arrays.asList(
                        new Parameter<>("p1", parameters1),
                        new Parameter<>("p2", parameters2),
                        new Parameter<>("p3", parameters3),
                        new Parameter<>("p4", parameters4)
                ))
                .build();

        final List<Case> cases = pairwise.getCases();
        StringBuffer output = new StringBuffer();

        for (Case params : cases) {
            String result;
            final IN1 in1 = (IN1) params.get("p1");
            final IN2 in2 = (IN2) params.get("p2");
            final IN3 in3 = (IN3) params.get("p3");
            final IN4 in4 = (IN4) params.get("p4");
            Object in5 = CombinationApprovals.EMPTY_ENTRY;
            Object in6 = CombinationApprovals.EMPTY_ENTRY;
            Object in7 = CombinationApprovals.EMPTY_ENTRY;
            Object in8 = CombinationApprovals.EMPTY_ENTRY;
            Object in9 = CombinationApprovals.EMPTY_ENTRY;
            try {
                result = "" + call.call(in1, in2, in3, in4);
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
}
