package org.approvaltests.namer;


import org.lambda.functions.Function0;

public class IdeLabeller implements Function0<String>
{
    @Override
    public String call()
    {
        StackTraceElement[] stackTrace = new RuntimeException().getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            String className = stackTraceElement.getClassName();
            if (className.contains("intellij")) {
                return "intellij";
            } else if (className.contains("eclipse")) {
                return "eclipse";
            }
        }
        return "unknown";
    }


}
