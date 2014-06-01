package org.approvaltests.namer;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Use this annotation on class or method level to specify custom 
 * {@link ApprovalNamer} to be used.
 * 
 * @author mzagar
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ApprovalsNamer {
  Class<? extends ApprovalNamer> value() default JUnitStackTraceNamer.class;
}
