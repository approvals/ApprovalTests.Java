package org.approvaltests.namer;

import com.spun.util.AnnotationUtils;
import com.spun.util.ClassUtils;

public class ApprovalNamerFactory
{
  /**
   * Returns instance of {@link ApprovalNamer} specified in {@link ApprovalsNamer} annotation.<br>
   * If {@link ApprovalsNamer} annotation is not used, {@link JUnitStackTraceNamer} instance is returned.
   * 
   * @return {@link ApprovalNamer} instance
   */
  public static ApprovalNamer getApprovalNamer()
  {
    ApprovalsNamer approvalsNamer = AnnotationUtils.getAnnotationFromStackTrace(ApprovalsNamer.class);
    if (approvalsNamer == null || approvalsNamer.value() == null) { return new JUnitStackTraceNamer(); }
    return ClassUtils.create(approvalsNamer.value());
  }
}
