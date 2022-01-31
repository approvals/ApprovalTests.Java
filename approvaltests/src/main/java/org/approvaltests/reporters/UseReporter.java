package org.approvaltests.reporters;

import org.approvaltests.core.ApprovalFailureReporter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface UseReporter {
  Class<? extends ApprovalFailureReporter>[] value();
}
