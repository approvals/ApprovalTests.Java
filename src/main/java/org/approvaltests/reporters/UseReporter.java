package org.approvaltests.reporters;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.approvaltests.core.ApprovalFailureReporter;

@Retention(RetentionPolicy.RUNTIME)
public @interface UseReporter {
  Class<? extends ApprovalFailureReporter>[] value();
}
