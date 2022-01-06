package com.spun.util;

import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ThrowAsErrorExamples {
    @Test
    void examples() {

    }
    Integer whyReturnIsNeeded() {
        // begin-snippet: throw_as_error_does_not_compile
        try {
            return methodThatThrowsCheckedException();
        } catch (Exception e) {
            ObjectUtils.throwAsError(e); // Does not compile. Missing "return" statement
        }
        // end-snippet
        return 0;
    }
    Integer withReturn() {
        // begin-snippet: throw_as_error_with_return
        try {
            return methodThatThrowsCheckedException();
        } catch (Exception e) {
            ObjectUtils.throwAsError(e);
            return null;  // This is never reached because the line above always throws an exception
        }
        // end-snippet
    }
    Integer withThrow() {
        // begin-snippet: throw_as_error_with_throw
        try {
            return methodThatThrowsCheckedException();
        } catch (Exception e) {
            throw ObjectUtils.throwAsError(e);
        }
        // end-snippet
    }

    private Integer methodThatThrowsCheckedException() throws IOException {
        return 1;
    }
}
