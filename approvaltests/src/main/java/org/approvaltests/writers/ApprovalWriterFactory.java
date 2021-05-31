package org.approvaltests.writers;

import org.approvaltests.core.ApprovalWriter;
import org.approvaltests.core.Options;

public interface ApprovalWriterFactory
{
  public ApprovalWriter create(Object content, Options options);
}
