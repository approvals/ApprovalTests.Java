import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class UpdateVersion {

  public static void main(String[] args) throws IOException {
    final String version = args[0];
    replaceTextInFile("README.md", "<version>\\d+.\\d+.\\d+</version>",
        "<version>" + version + "</version>");
  }

  private static void replaceTextInFile(String fileName, String regex, String replacement)
      throws IOException {
    final Path path = Path.of(fileName);
    final String text = Files.readString(path);
    final String newContent = text.replaceFirst(regex, replacement);
    Files.writeString(path, newContent);
  }
}
