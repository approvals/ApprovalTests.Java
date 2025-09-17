package org.approvaltests.ci;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.jayway.jsonpath.JsonPath;
import com.spun.util.ClassUtils;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JavaVersionTest
{
  @Test
  public void testSupportedJavaVersions() throws IOException, InterruptedException
  {
    var supportedJavaVersions = getSupportedJavaVersions();
    // 11 is past regular support but gets free extended premier support; regardless, it doesn't work with our current dependencies
    supportedJavaVersions.remove("11");
    // We still support Java 8, but it's tested separately
    supportedJavaVersions.remove("8");

    var testedJavaVersions = getTestedJavaVersions();
    assertEquals(testedJavaVersions, supportedJavaVersions,
        "Please update the tested Java versions in GitHub Actions");
    // Java 8 GitHub Action is separate from the normal matrixed CI, so hard-code it here.
    Approvals.verify("Works on JDK 1.8+ (tested on 1.8, " + String.join(", ", testedJavaVersions) + ").");
  }
  List<String> getTestedJavaVersions() throws IOException
  {
    var testYmlPath = new File(ClassUtils.getProjectRootPath() + "/../.github/workflows/test.yml");
    Object yaml = new ObjectMapper(new YAMLFactory()).readValue(testYmlPath, Object.class);
    List<String> javaVersions = JsonPath.read(yaml, "$.jobs.build.strategy.matrix.java");
    return javaVersions;
  }
  List<String> getSupportedJavaVersions() throws IOException, InterruptedException
  {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("https://java.oraclecloud.com/javaVersions?isSupportedVersion=true")).build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    Object json = new ObjectMapper().readValue(response.body(), Object.class);
    List<String> javaVersions = JsonPath.read(json, "$.items[*].jdkVersion");
    return javaVersions;
  }
}
