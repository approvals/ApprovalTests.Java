package org.lambda.utils;

import com.spun.util.NumberUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OnceTest {
    public static int count = 0;

    @Test
    public void testOnce() {
        increment();
        increment();
        increment();
        increment();
        increment();
        assertEquals(1, count);
    }

    private void increment() {
        var fieldThatForcesLambdaToHaveMultipleInstances = NumberUtils.doRandomPercentage(50);
        Once.run(() -> {
            if (fieldThatForcesLambdaToHaveMultipleInstances)
                OnceTest.count++;
            else
                OnceTest.count++;
        });
    }

    @Test
    public void testFunctionCallOnce() {
        int count = 0;
        count = increment(count);
        count = increment(count);
        count = increment(count);
        count = increment(count);
        count = increment(count);
        assertEquals(1, count);
    }

    private int increment(int count) {
        return Once.run(() -> count + 1);
    }

}
