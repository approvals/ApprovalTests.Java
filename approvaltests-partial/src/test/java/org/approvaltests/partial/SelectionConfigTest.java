package org.approvaltests.partial;

import org.approvaltests.JsonApprovals;
import org.junit.jupiter.api.Test;

import java.io.File;

public class SelectionConfigTest
{
  @Test
  void loadStarterProjectSelection()
  {
    SelectionConfig config = SelectionConfig
        .load(new File("src/test/resources/starter-project-selection.json"));
    JsonApprovals.verifyAsJson(config);
  }
}
