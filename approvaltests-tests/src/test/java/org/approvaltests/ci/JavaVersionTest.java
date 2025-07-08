package org.approvaltests.ci;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.jayway.jsonpath.JsonPath;
import com.spun.util.ClassUtils;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JavaVersionTest
{
  @Test
  public void testSupportedJavaVersions() throws IOException
  {
    var testYmlPath = new File(ClassUtils.getProjectRootPath() + "/../.github/workflows/test.yml");
    Object yaml = new ObjectMapper(new YAMLFactory()).readValue(testYmlPath, Object.class);
    List<String> javaVersions = JsonPath.read(yaml, "$.jobs.build.strategy.matrix.java");
    // Java 8 GitHub Action is separate from the normal matrixed CI, so hard-code it here.
    Approvals.verify("Supported Java versions: 8, " + String.join(", ", javaVersions));
  }
}
