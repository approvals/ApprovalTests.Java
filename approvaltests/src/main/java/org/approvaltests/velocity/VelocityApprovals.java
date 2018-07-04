package org.approvaltests.velocity;

import org.approvaltests.Approvals;
import org.approvaltests.namer.ApprovalNamer;

import com.spun.util.io.FileUtils;
import com.spun.util.velocity.ContextAware;
import com.spun.util.velocity.VelocityParser;

public class VelocityApprovals
{
  public static void verify(ContextAware context)
  {
    verify(context, ".txt");
  }
  public static void verify(ContextAware context, String fileExtentsionWithDot)
  {
    ApprovalNamer namer = Approvals.createApprovalNamer();
    String file = namer.getSourceFilePath() + namer.getApprovalName() + ".template" + fileExtentsionWithDot;
    FileUtils.createIfNeeded(file);
    String text = VelocityParser.parseFile(file, context);
    Approvals.verify(text, fileExtentsionWithDot.substring(1));
  }
}
