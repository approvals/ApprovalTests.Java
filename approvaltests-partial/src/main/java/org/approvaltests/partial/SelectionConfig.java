package org.approvaltests.partial;

import com.google.gson.Gson;
import com.spun.util.io.FileUtils;

import java.io.File;
import java.util.List;
import java.util.Map;

public class SelectionConfig
{
  public String              approvaltestsVersion;
  public List<String>        entryPoints;
  public Map<String, String> runtimeDiscoveries;
  public Output              output;
  public static class Output
  {
    public String  directory;
    public String  version;
    public boolean pom;
    public boolean sources;
    public String  install;
  }
  public static SelectionConfig load(File file)
  {
    return parse(FileUtils.readFile(file));
  }

  public static SelectionConfig parse(String json)
  {
    return new Gson().fromJson(json, SelectionConfig.class);
  }
}
