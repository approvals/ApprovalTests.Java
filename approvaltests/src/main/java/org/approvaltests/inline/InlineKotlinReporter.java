package org.approvaltests.inline;

import com.spun.util.StringUtils;
import com.spun.util.io.FileUtils;
import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.core.ApprovalReporterWithCleanUp;
import org.approvaltests.namer.StackTraceNamer;
import org.lambda.functions.Function2;
import org.lambda.functions.Function3;
import java.io.File;

public class InlineKotlinReporter implements ApprovalFailureReporter, ApprovalReporterWithCleanUp {
    private final String sourceFilePath;
    private final StackTraceNamer stackTraceNamer;
    private final Function2<String, String, String> footerCreator;
    private final ApprovalFailureReporter reporter;
    private String additionalLines = null;
    private Function3<String, String, String, String> createNewReceivedFileText;

    public InlineKotlinReporter(ApprovalFailureReporter reporter, Function2<String, String, String> footerCreator) {
        this.reporter = reporter;
        this.stackTraceNamer = new StackTraceNamer();
        this.sourceFilePath = stackTraceNamer.getSourceFilePath();
        this.createNewReceivedFileText = (kotlinSourceCode, actual, methodName) ->
            createNewReceivedFileText(kotlinSourceCode, actual, methodName);
        this.footerCreator = footerCreator != null ? footerCreator : (source, actual) -> "";
    }

    public InlineKotlinReporter(ApprovalFailureReporter reporter) {
        this(reporter, null);
    }

    @Override
    public boolean report(String received, String approved) {
        additionalLines = footerCreator.call(received, approved);
        String sourceFile = sourceFilePath + stackTraceNamer.getInfo().getClassName() + ".kt";
        String newSource = createReceived(FileUtils.readFile(received));
        return reporter.report(newSource, sourceFile);
    }

    public String createReceived(String actual) {
        String file = sourceFilePath + stackTraceNamer.getInfo().getClassName() + ".kt";
        String received = getReceivedFileName();
        String text = FileUtils.readFile(file);
        String fullText = this.createNewReceivedFileText.call(
                text, actual + additionalLines,
                this.stackTraceNamer.getInfo().getMethodName()
        );
        FileUtils.writeFile(new File(received), fullText);
        return received;
    }

    private String getReceivedFileName() {
        return sourceFilePath + stackTraceNamer.getInfo().getClassName() + ".received.txt";
    }

    @Override
    public void cleanUp(String received, String approved) {
        FileUtils.delete(getReceivedFileName());
    }

    public static String createNewReceivedFileText(String kotlinSourceCode, String actual, String methodName) {
        String normalizedSourceCode = kotlinSourceCode.replaceAll("\\r\\n", "\n");
        CodeParts codeParts = CodeParts.splitCode(normalizedSourceCode, methodName);
        if (codeParts.method != null && codeParts.method.contains("expected = \"\"\"")) {
            replaceExpected(codeParts, actual);
        } else {
            addExpected(codeParts, actual);
        }
        return codeParts.getFullCode();
    }

    private static void addExpected(CodeParts codeParts, String actual) {
        int start = codeParts.method.indexOf("{") + 2;
        String before = codeParts.method.substring(0, start);
        String after = codeParts.method.substring(start);
        codeParts.method = before + getExpected(actual, codeParts.tab) + after;
    }

    private static String getExpected(String actual, String tab) {
        return String.format(
                "%s%sval expected = \"\"\"\n%s%s%s%s\"\"\".trimIndent()\n", tab, tab, indent(actual, tab), tab, tab,
                tab
        );
    }

    private static void replaceExpected(CodeParts codeParts, String actual) {
        int start = codeParts.method.indexOf("expected = \"\"\"");
        start = codeParts.method.substring(0, start).lastIndexOf("\n") + 1;
        // Find the closing """ by looking for it at the start of a line (after whitespace)
        int searchPos = start + "expected = \"\"\"".length();
        int end = -1;
        while (searchPos < codeParts.method.length()) {
            int nextTripleQuote = codeParts.method.indexOf("\"\"\"", searchPos);
            if (nextTripleQuote == -1) break;
            // Check if this """ is at the start of a line (preceded only by whitespace)
            int lineStart = codeParts.method.lastIndexOf("\n", nextTripleQuote - 1) + 1;
            String textBeforeQuote = codeParts.method.substring(lineStart, nextTripleQuote);
            if (textBeforeQuote.trim().isEmpty()) {
                end = nextTripleQuote;
                break;
            }
            searchPos = nextTripleQuote + 1;
        }
        if (end == -1) {
            // Fallback to old behavior if we can't find it
            end = codeParts.method.indexOf("\"\"\"", start + "expected = \"\"\"".length());
        }
        end += 3;  // Move past the closing """
        // Check if there's a .trimIndent() after the closing """
        String afterTripleQuote = codeParts.method.substring(end);
        if (afterTripleQuote.startsWith(".trimIndent()")) {
            end += ".trimIndent()".length();
        }
        end = codeParts.method.indexOf("\n", end) + 1;
        String before = codeParts.method.substring(0, start);
        String after = codeParts.method.substring(end);
        codeParts.method = before + getExpected(actual, codeParts.tab) + after;
    }

    public static String indent(String actual, String tab) {
        String[] split = StringUtils.split(actual, "\n");
        String output = "";
        for (String line : split) {
            output += tab + tab + tab + line + "\n";
        }
        return output;
    }
}
