package org.approvaltests.velocity;

import com.spun.util.io.FileUtils;
import com.spun.util.velocity.ContextAware;
import com.spun.util.velocity.VelocityParser;
import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.approvaltests.namer.ApprovalNamer;

public class VelocityApprovals
{
  public static void verify(ContextAware context)
  {
    verify(context, new Options());
  }
  public static void verify(ContextAware context, Options options)
  {
    verify(context, ".txt", options);
  }
  public static void verify(ContextAware context, String fileExtentsionWithDot)
  {
    verify(context, fileExtentsionWithDot, new Options());
  }
  public static void verify(ContextAware context, String fileExtentsionWithDot, Options options)
  {
    ApprovalNamer namer = Approvals.createApprovalNamer();
    String file = namer.getSourceFilePath() + namer.getApprovalName() + ".template" + fileExtentsionWithDot;
    FileUtils.createIfNeeded(file);
    String text = VelocityParser.parseFile(file, context);
    options = options.forFile().withExtension(fileExtentsionWithDot);
    Approvals.verify(text, options);
  }
}
