package org.approvaltests.partial;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import java.io.File;

public class TreeShakerTest
{
  @Test
  void shakeStarterProject()
  {
    SelectionConfig config = SelectionConfig.load(new File("src/test/resources/starter-project-selection.json"));
    String localRepository = "file://" + new File("..").getAbsoluteFile().toPath().normalize();
    File checkout = SourceCheckout.cloneTag(localRepository, config.approvaltestsVersion,
        new File(".local/test/approvaltests-" + config.approvaltestsVersion));
    JarBuilder.build(checkout);
    Approvals.verify(TreeShaker.shake(checkout, config.entryPoints, JarBuilder.thirdPartyJars(checkout)));
  }
}
