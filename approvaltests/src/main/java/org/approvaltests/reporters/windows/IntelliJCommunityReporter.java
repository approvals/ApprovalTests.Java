package org.approvaltests.reporters.windows;

import org.approvaltests.reporters.GenericDiffReporter;
import org.approvaltests.reporters.macosx.DiffPrograms.Windows;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

import static java.lang.Integer.parseInt;

public class IntelliJCommunityReporter extends GenericDiffReporter {
    public static final IntelliJCommunityReporter INSTANCE = new IntelliJCommunityReporter();

    public IntelliJCommunityReporter() {
        super(Windows.INTELLIJ_C);
    }

    public static class IntelliJPathResolver {
        private final String channelsPath;

        public IntelliJPathResolver(Edition edition) {
            String appData = System.getenv("LOCALAPPDATA");
            String toolboxPath = appData + "/JetBrains/Toolbox";
            this.channelsPath = toolboxPath + "/apps/" + edition.getDirectory() + "/ch-0/";
        }

        public String findIt() {
            String notPresentPath = "C:\\Intelli-not-present.exe";
            try {
                return getIntellJPath().map(Objects::toString).orElse(notPresentPath);
            } catch (IOException e) {
                return notPresentPath;
            }
        }

        private Optional<Path> getIntellJPath() throws IOException {
            return Files.walk(Paths.get(channelsPath), 1, FileVisitOption.FOLLOW_LINKS)
                    .map(Path::getFileName)
                    .map(Objects::toString)
                    .filter(Version::isVersionFile)
                    .map(Version::new).min(Comparator.reverseOrder())
                    .map(this::getPath);
        }

        private Path getPath(Version version) {
            String runtimeSuffix = "/bin/idea64.exe";
            return Paths.get(channelsPath + version.version + runtimeSuffix).toAbsolutePath();
        }

        public enum Edition {
            Community("IDEA-C"), Ultimate("IDEA-U");
            private final String directory;

            Edition(String directory) {
                this.directory = directory;
            }

            public String getDirectory() {
                return directory;
            }
        }
    }

    private static class Version implements Comparable<Version> {
        final String version;

        Version(String version) {
            this.version = version;
        }

        private static boolean isVersionFile(String version) {
            return version.matches("[0-9]+(\\.[0-9]+)*");
        }

        @Override
        public int compareTo(Version other) {
            String[] thisParts = version.split("\\.");
            String[] thatParts = other.version.split("\\.");
            int length = Math.max(thisParts.length, thatParts.length);
            for (int i = 0; i < length; i++) {
                int thisPart = (i < thisParts.length) ? parseInt(thisParts[i]) : 0;
                int thatPart = (i < thatParts.length) ? parseInt(thatParts[i]) : 0;
                if (thisPart < thatPart) {
                    return -1;
                }
                if (thisPart > thatPart) {
                    return 1;
                }
            }
            return 0;
        }
    }

}
