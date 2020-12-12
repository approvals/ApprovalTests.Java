package org.approvaltests;

import org.approvaltests.core.Options;
import org.approvaltests.writers.ApprovalTextWriter;

public class ApprovalsBuilder {
  private Options options;
  private ApprovalTextWriter approver;

  public ApprovalsBuilder() {
    this.options = new Options();
  }

  public ApprovalsBuilder withOptions(final Options options) {
    this.options = options;
    return this;
  }

  public ApprovalsBuilder withResponse(final String response) {
    this.approver = new ApprovalTextWriter(response, this.options);
    return this;
  }

  public void verify() {
    Approvals.verify(this.approver, this.options);
  }
}
