package org.approvaltests.velocity;

import com.spun.util.io.FileUtils;
import com.spun.util.velocity.ContextAware;
import com.spun.util.velocity.VelocityParser;
import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.approvaltests.namer.ApprovalNamer;
import org.approvaltests.namer.NamerUtils;

public class VelocityApprovals
{
  public static void verify(ContextAware context)
  {
    verify(context, new Options());
  }

  public static void verify(ContextAware context, Options options)
  {
    ApprovalNamer namer = Approvals.createApprovalNamer();
    String fileExtensionWithDot = options.forFile().getFileExtension();
    String file = NamerUtils.getSourcePath(namer) + NamerUtils.getApprovalName(namer) + ".template"
        + fileExtensionWithDot;
    FileUtils.createIfNeeded(file);
    String text = VelocityParser.parseFile(file, context);
    Approvals.verify(text, options);
  }

  /**
   * @deprecated use {@code Options.forFile().withExtension(fileExtensionWithDot) }
   */
  @Deprecated
  public static void verify(ContextAware context, String fileExtensionWithDot)
  {
    verify(context, fileExtensionWithDot, new Options());
  }

  /**
   * @deprecated use {@code Options.forFile().withExtension(fileExtensionWithDot) }
   */
  @Deprecated
  public static void verify(ContextAware context, String fileExtensionWithDot, Options options)
  {
    options = options.forFile().withExtension(fileExtensionWithDot);
    verify(context, options);
  }
}
