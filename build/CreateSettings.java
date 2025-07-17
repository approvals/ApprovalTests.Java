import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CreateSettings {
  public static void main(String[] args) throws IOException {
    String username = System.getenv("MAVEN_USERNAME_2025");
    String password = System.getenv("MAVEN_PASSWORD_2025");
    if (username == null || password == null) {
      System.err.println("MAVEN_USERNAME_2025 and MAVEN_PASSWORD_2025 must be set as environment variables");
      System.exit(1);
    }
    String xml = """
        <settings>
        <servers>
            <server>
                <id>central</id>
                <username>%s</username>
                <password>%s</password>
            </server>
        </servers>
        </settings>
        """;
    xml = String.format(xml, username, password);    
    Files.writeString(Path.of("settings.xml"), xml);
  }
}

