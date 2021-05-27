package org.approvaltests;

import com.spun.util.ClassUtils;
import com.spun.util.StringUtils;
import org.approvaltests.core.Options;
import org.approvaltests.reporters.QuietReporter;
import org.junit.jupiter.api.Test;
import org.lambda.query.Query;
import org.lambda.query.Queryable;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class DocumentHelpers {
    @Test
    void listAllVerifyFunctions()
    {
        Queryable<Method> methods = getAllVerifyFunctionsWithOptions(OptionsTest.getApprovalClasses());
        Queryable<String> lines = methods
                .select(m -> String.format("%s. [%s](%s) (%s)", m.getDeclaringClass().getSimpleName(), m.getName(), getLink(m), showParameters(m)))
                .orderBy(s -> s);

        Approvals.verifyAll("", lines, l -> String.format(" * %s  ", l), new Options().forFile().withExtension(".md").withReporter(QuietReporter.INSTANCE));
    }

    private String showParameters(Method m) {
        return StringUtils.join(Query.select(m.getParameters(), p -> String.format("%s", p.getType().getSimpleName())), ",");
    }

    private String getLink(Method m) {
        String baseUrl = "https://github.com/approvals/ApprovalTests.Java/blob/master/src/";
        String classPath = ClassUtils.getSourceDirectory(m.getDeclaringClass()).toString();
        classPath = classPath.replaceAll("..", "").replace('\\', '/') + m.getDeclaringClass().getSimpleName();
//        var filePath = PathUtilities.GetAdjacentFile($"../../{classPath}.cs");
//        var code = File.ReadAllLines(filePath);
//        var lineNumber = 0;
//        for (var i = 0; i < code.Length; i++)
//        {
//            if (code[i].Contains("void "+ m.Name))
//            {
//                lineNumber = i+1;
//                break;
//            }
//        }
        int lineNumber = 1;
        return String.format("%s%s.java#L%s", baseUrl, classPath, lineNumber);
    }

    private Queryable<Method> getAllVerifyFunctionsWithOptions(List<Class<?>> approvalClasses) {
        Queryable<Method> methods = new Queryable<>(Method.class);
        for (Class c: approvalClasses) {
            Queryable<Method> declaredMethods = Queryable.as(c.getDeclaredMethods());
            Queryable<Method> methodList = declaredMethods.where(m -> m.getName().startsWith("verify")
                    && Modifier.isPublic(m.getModifiers()) && !m.isAnnotationPresent(Deprecated.class));
            List<Method> methodsWithOptions = methodList.where(m -> !OptionsTest.isOptionsPresent(m));
            methods.addAll(methodsWithOptions);
        }
        return methods;
    }
}
