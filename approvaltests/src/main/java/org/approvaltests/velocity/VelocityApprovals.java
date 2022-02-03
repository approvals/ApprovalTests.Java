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
    ApprovalNamer namer = Approvals.createApprovalNamer();
    String fileExtentsionWithDot = options.forFile().getFileExtension();
    String file = namer.getSourceFilePath() + namer.getApprovalName() + ".template" + fileExtentsionWithDot;
    FileUtils.createIfNeeded(file);
    String text = VelocityParser.parseFile(file, context);
    Approvals.verify(text, options);
  }
  /**
   * @deprecated use {@code Options.forFile().withExtension(fileExtensionWithDot) }
   */
  @Deprecated
  public static void verify(ContextAware context, String fileExtentsionWithDot)
  {
    verify(context, fileExtentsionWithDot, new Options());
  }
  /**
   * @deprecated use {@code Options.forFile().withExtension(fileExtensionWithDot) }
   */
  @Deprecated
  public static void verify(ContextAware context, String fileExtentsionWithDot, Options options)
  {
    options = options.forFile().withExtension(fileExtentsionWithDot);
    verify(context, options);
  }
}
