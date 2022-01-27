package org.approvaltests;

import com.google.gson.GsonBuilder;
import com.spun.util.*;
import com.spun.util.persistence.ExecutableCommand;
import com.spun.util.persistence.Loader;
import com.spun.util.persistence.SqlLoader;
import org.approvaltests.approvers.ApprovalApprover;
import org.approvaltests.approvers.FileApprover;
import org.approvaltests.core.ApprovalFailureReporter;
import org.approvaltests.core.ApprovalWriter;
import org.approvaltests.core.Options;
import org.approvaltests.core.VerifyResult;
import org.approvaltests.namer.ApprovalNamer;
import org.approvaltests.namer.MasterDirectoryNamer;
import org.approvaltests.namer.StackTraceNamer;
import org.approvaltests.reporters.ExecutableQueryFailure;
import org.approvaltests.writers.ApprovalXmlWriter;
import org.lambda.actions.Action0;
import org.lambda.functions.Function1;
import org.lambda.query.Query;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.sql.ResultSet;
import java.util.*;

/**
 * Requires (GSON)[https://mvnrepository.com/artifact/com.google.code.gson/gson]
 */
public class JsonApprovals
{
  public static void verifyJson(String json)
  {
    verifyJson(json, new Options());
  }
  public static void verifyJson(String json, Options options)
  {
    Approvals.verify(JsonUtils.prettyPrint(json), options.forFile().withExtension(".json"));
  }
  public static void verifyAsJson(Object o)
  {
    verifyAsJson(o, new Options());
  }
  public static void verifyAsJson(Object o, Function1<GsonBuilder, GsonBuilder> gsonBuilder)
  {
    verifyAsJson(o, gsonBuilder, new Options());
  }
  public static void verifyAsJson(Object o, Function1<GsonBuilder, GsonBuilder> gsonBuilder, Options options)
  {
    Approvals.verify(JsonUtils.asJson(o, gsonBuilder), options.forFile().withExtension(".json"));
  }
  public static void verifyAsJson(Object o, Options options)
  {
    Approvals.verify(JsonUtils.asJson(o), options.forFile().withExtension(".json"));
  }
}
