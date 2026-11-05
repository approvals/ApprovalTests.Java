package org.approvaltests.partial;

import com.spun.util.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HtmlReport
{
  private static final String[] OURS    = {"com.spun", "org.approvaltests", "org.lambda", "java.", "javax."};
  private static final Pattern  IMPORTS = Pattern.compile("^import\\s+(?:static\\s+)?([\\w.]+)", Pattern.MULTILINE);
  public static class Inputs
  {
    public SelectionConfig     config;
    public File                configFile;
    public File                checkout;
    public Map<String, String> originalSources;
    public JarBuilder.Stats    originalStats;
    public JarBuilder.Stats    partialStats;
    public File                jar;
  }
  private record FileRecord(String path, String packageName, String module, String status, int before, int after,
      Set<String> dependenciesBefore, Set<String> dependenciesAfter)
  {
  }
  public static Map<String, String> snapshot(File checkout)
  {
    Map<String, String> sources = new TreeMap<>();
    for (String root : TreeShaker.SOURCE_ROOTS)
    {
      try (Stream<Path> paths = Files.walk(new File(checkout, root).toPath()))
      {
        paths.filter(p -> p.toString().endsWith(".java"))
            .forEach(p -> sources.put(checkout.toPath().relativize(p).toString(), FileUtils.readFile(p.toFile())));
      }
      catch (IOException e)
      {
        throw new RuntimeException(e);
      }
    }
    return sources;
  }

  public static File write(Inputs inputs, File output)
  {
    FileUtils.writeFile(output, render(inputs));
    return output;
  }

  private static String render(Inputs in)
  {
    List<FileRecord> records = new ArrayList<>();
    for (Map.Entry<String, String> entry : in.originalSources.entrySet())
    {
      String path = entry.getKey();
      String before = entry.getValue();
      File current = new File(in.checkout, path);
      String after = current.exists() ? FileUtils.readFile(current) : "";
      String status = !current.exists() ? "deleted" : before.equals(after) ? "unchanged" : "changed";
      records.add(new FileRecord(path, packageOf(path), path.substring(0, path.indexOf('/')), status, lines(before),
          lines(after), thirdParty(before), thirdParty(after)));
    }
    Map<String, List<String>> diffs = diffs(in.checkout);
    int totalBefore = records.stream().mapToInt(FileRecord::before).sum();
    int totalAfter = records.stream().mapToInt(FileRecord::after).sum();
    long deleted = records.stream().filter(r -> r.status.equals("deleted")).count();
    long changed = records.stream().filter(r -> r.status.equals("changed")).count();
    long unchanged = records.size() - deleted - changed;
    Set<String> dependenciesBefore = new TreeSet<>();
    Set<String> dependenciesAfter = new TreeSet<>();
    records.forEach(r -> {
      dependenciesBefore.addAll(r.dependenciesBefore);
      dependenciesAfter.addAll(r.dependenciesAfter);
    });
    Map<String, List<FileRecord>> packages = records.stream()
        .collect(Collectors.groupingBy(FileRecord::packageName, TreeMap::new, Collectors.toList()));
    SelectionConfig config = in.config;
    String configName = in.configFile.getName();
    StringBuilder h = new StringBuilder();
    h.append("<!doctype html>\n<html lang=\"en\">\n<head>\n<meta charset=\"utf-8\">\n")
        .append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n")
        .append("<title>Tree Shaking Report</title>\n<style>\n").append(CSS).append("</style>\n</head>\n<body>\n<main>\n");
    h.append("<h1>Tree Shaking Report</h1>\n<div class=\"muted\">ApprovalTests ").append(esc(config.approvaltestsVersion))
        .append(" · ").append(config.entryPoints.size()).append(" entry points from <code>").append(esc(configName))
        .append("</code> · original vs. partial</div>\n");
    h.append("<div class=\"tiles\">\n");
    tile(h, "Source files", (records.size() - deleted) + " <span class=\"sub\">/ " + records.size() + "</span>",
        deleted + " deleted · " + changed + " changed · " + unchanged + " untouched");
    tile(h, "Lines of source", pct(totalAfter, totalBefore) + "%",
        String.format("%,d of %,d kept", totalAfter, totalBefore));
    tile(h, "Compiled .class files",
        String.format("%,d <span class=\"sub\">/ %,d</span>", in.partialStats.classes, in.originalStats.classes),
        kb(in.partialStats.classBytes) + " of " + kb(in.originalStats.classBytes) + " ("
            + pct(in.partialStats.classBytes, in.originalStats.classBytes) + "%)");
    tile(h, "Jar size", kb(in.partialStats.jarBytes), "vs. " + kb(in.originalStats.jarBytes) + " for the original jars ("
        + pct(in.partialStats.jarBytes, in.originalStats.jarBytes) + "%)");
    tile(h, "Third-party packages",
        dependenciesAfter.size() + " <span class=\"sub\">/ " + dependenciesBefore.size() + "</span>", "still imported");
    h.append("</div>\n");
    renderInputs(h, in);
    h.append("<h2>By module</h2>\n<div class=\"scroll\"><table>\n")
        .append("<tr><th>Module</th><th>Files</th><th>Kept</th><th>Lines before</th><th>Lines after</th><th>Kept</th><th></th></tr>\n");
    for (String module : JarBuilder.MODULES)
    {
      List<FileRecord> rs = records.stream().filter(r -> r.module.equals(module)).collect(Collectors.toList());
      int b = rs.stream().mapToInt(FileRecord::before).sum();
      int a = rs.stream().mapToInt(FileRecord::after).sum();
      long kept = rs.stream().filter(r -> !r.status.equals("deleted")).count();
      h.append(String.format(
          "<tr><td><code>%s</code></td><td class=\"n\">%d</td><td class=\"n\">%d</td><td class=\"n\">%,d</td><td class=\"n\">%,d</td><td class=\"n\">%d%%</td><td>%s</td></tr>\n",
          module, rs.size(), kept, b, a, pct(a, b), bar(a, b)));
    }
    h.append("</table></div>\n");
    h.append("<h2>By package</h2>\n<div class=\"legend muted\"><span><i class=\"swatch\" style=\"background:var(--kept)\"></i>kept</span>")
        .append("<span><i class=\"swatch\" style=\"background:var(--gone);opacity:.55\"></i>removed</span><span>struck-through = whole package removed</span></div>\n")
        .append("<div class=\"scroll\"><table>\n<tr><th>Package</th><th>Files</th><th>Deleted</th><th>Changed</th><th>Lines before</th><th>Lines after</th><th>Kept</th><th></th></tr>\n");
    for (Map.Entry<String, List<FileRecord>> entry : packages.entrySet())
    {
      List<FileRecord> rs = entry.getValue();
      int b = rs.stream().mapToInt(FileRecord::before).sum();
      int a = rs.stream().mapToInt(FileRecord::after).sum();
      long d = rs.stream().filter(r -> r.status.equals("deleted")).count();
      long c = rs.stream().filter(r -> r.status.equals("changed")).count();
      String state = d == rs.size() ? "gone" : a == b ? "intact" : "partial";
      h.append(String.format(
          "<tr class=\"%s\"><td><code>%s</code></td><td class=\"n\">%d</td><td class=\"n\">%d</td><td class=\"n\">%d</td><td class=\"n\">%,d</td><td class=\"n\">%,d</td><td class=\"n\">%d%%</td><td>%s</td></tr>\n",
          state, esc(entry.getKey()), rs.size(), d, c, b, a, pct(a, b), bar(a, b)));
    }
    h.append("</table></div>\n");
    h.append("<h2>Third-party dependencies</h2>\n<div class=\"scroll\"><table>\n<tr><th>Package (from imports)</th><th>Status</th></tr>\n");
    for (String dependency : dependenciesBefore)
    {
      boolean kept = dependenciesAfter.contains(dependency);
      h.append("<tr class=\"").append(kept ? "intact" : "gone").append("\"><td><code>").append(esc(dependency))
          .append("</code></td><td>").append(kept ? "still referenced" : "removed").append("</td></tr>\n");
    }
    h.append("</table></div>\n");
    List<FileRecord> changedFiles = records.stream().filter(r -> r.status.equals("changed"))
        .sorted(Comparator.comparingInt(r -> r.after - r.before)).collect(Collectors.toList());
    h.append("<h2>Changed files (").append(changedFiles.size())
        .append(")</h2>\n<div class=\"muted\">Sorted by lines removed. Expand for the diff against the original.</div>\n");
    for (FileRecord r : changedFiles)
    {
      h.append("<details><summary><code>").append(esc(r.path.substring(r.path.indexOf("/src/main/java/") + 15)))
          .append("</code> <span class=\"muted\">").append(String.format("%,d → %,d lines (%+,d)", r.before, r.after,
              r.after - r.before))
          .append("</span>").append(bar(r.after, r.before)).append("</summary><pre class=\"diff\">");
      for (String line : diffs.getOrDefault(r.path, List.of()))
      {
        String cls = line.startsWith("+") ? "add" : line.startsWith("-") ? "del" : line.startsWith("@@") ? "hunk" : "";
        h.append("<span class=\"").append(cls).append("\">").append(esc(line)).append("</span>\n");
      }
      h.append("</pre></details>\n");
    }
    h.append("<h2>Deleted files (").append(deleted).append(")</h2>\n");
    for (Map.Entry<String, List<FileRecord>> entry : packages.entrySet())
    {
      List<FileRecord> gone = entry.getValue().stream().filter(r -> r.status.equals("deleted"))
          .collect(Collectors.toList());
      if (gone.isEmpty())
      {
        continue;
      }
      h.append("<details><summary><code>").append(esc(entry.getKey())).append("</code> <span class=\"muted\">")
          .append(gone.size()).append(" files · ")
          .append(String.format("%,d", gone.stream().mapToInt(FileRecord::before).sum()))
          .append(" lines</span></summary><ul class=\"cols\">");
      for (FileRecord r : gone)
      {
        h.append("<li><code>").append(esc(new File(r.path).getName())).append("</code> <span class=\"muted\">")
            .append(String.format("%,d", r.before)).append(" lines</span></li>");
      }
      h.append("</ul></details>\n");
    }
    h.append("</main>\n</body>\n</html>\n");
    return h.toString();
  }

  private static void renderInputs(StringBuilder h, Inputs in)
  {
    SelectionConfig config = in.config;
    String configName = in.configFile.getName();
    h.append("<h2>Inputs</h2>\n<div class=\"inputs\">\n<div class=\"tile\">\n")
        .append("<div class=\"label\">Selection file</div><div><code>").append(esc(configName)).append("</code></div>")
        .append("<div class=\"sub\">").append(esc(in.configFile.getAbsoluteFile().getParent())).append("</div>\n")
        .append("<div class=\"label\" style=\"margin-top:12px\">ApprovalTests version</div><div class=\"value\">")
        .append(esc(config.approvaltestsVersion)).append("</div><div class=\"sub\">")
        .append(config.approvaltestsVersion.startsWith("master@") ? "built with --current, from the latest master"
            : "cloned from the <code>" + esc(config.approvaltestsVersion) + "</code> tag")
        .append(" of approvals/ApprovalTests.Java</div>\n</div>\n");
    h.append("<div class=\"tile\">\n<div class=\"label\">Entry points you selected (").append(config.entryPoints.size())
        .append(")</div><div class=\"sub\">Everything kept is reachable from these. Everything else is deleted.</div>\n")
        .append("<table class=\"plain\"><tr><th>Call</th><th>Kind</th><th>Package</th></tr>");
    for (String entryPoint : config.entryPoints)
    {
      int paren = entryPoint.indexOf('(');
      String head = paren < 0 ? entryPoint : entryPoint.substring(0, paren);
      String owner = head.substring(0, head.lastIndexOf('.'));
      String member = head.substring(head.lastIndexOf('.') + 1);
      if (paren >= 0)
      {
        member += "(" + Stream.of(entryPoint.substring(paren + 1, entryPoint.length() - 1).split(","))
            .map(String::trim).filter(p -> !p.isEmpty()).map(HtmlReport::simpleName).collect(Collectors.joining(", "))
            + ")";
      }
      h.append("<tr><td><code>").append(esc(simpleName(owner))).append(".<b>").append(esc(member))
          .append("</b></code></td><td>").append(paren < 0 ? "field" : "method").append("</td><td><code class=\"muted\">")
          .append(esc(owner.substring(0, owner.lastIndexOf('.')))).append("</code></td></tr>");
    }
    h.append("</table>\n</div>\n<div class=\"tile\">\n<div class=\"label\">Runtime discoveries</div>")
        .append("<table class=\"plain\"><tr><th>Discovery</th><th>Mode</th></tr>");
    Map<String, String> modes = Map.of("keep", "keep — works normally", "remove", "remove — throws if triggered",
        "ignore", "ignore — code removed");
    Map<String, String> discoveries = config.runtimeDiscoveries == null ? Map.of() : config.runtimeDiscoveries;
    for (Map.Entry<String, String> entry : discoveries.entrySet())
    {
      h.append("<tr><td><code>").append(esc(entry.getKey().equals("*") ? "everything" : entry.getKey()))
          .append("</code></td><td>").append(esc(modes.getOrDefault(entry.getValue(), entry.getValue())))
          .append("</td></tr>");
    }
    h.append("</table>\n<div class=\"label\" style=\"margin-top:12px\">Output</div><ul class=\"plainlist\">")
        .append("<li><span class=\"muted\">jar:</span> <code>").append(esc(in.jar.getName())).append("</code></li>")
        .append("<li><span class=\"muted\">directory:</span> <code>").append(esc(in.jar.getParent()))
        .append("</code></li></ul>\n</div>\n</div>\n");
    h.append("<details><summary>Raw <code>").append(esc(configName)).append("</code></summary><pre class=\"diff\">")
        .append(esc(FileUtils.readFile(in.configFile))).append("</pre></details>\n");
  }

  private static Map<String, List<String>> diffs(File checkout)
  {
    List<String> command = new ArrayList<>(List.of("git", "diff", "--no-color", "-U2", "HEAD", "--"));
    command.addAll(List.of(TreeShaker.SOURCE_ROOTS));
    String output = Shell.output(checkout, command.toArray(new String[0]));
    Map<String, List<String>> diffs = new HashMap<>();
    List<String> current = null;
    for (String line : output.split("\n"))
    {
      if (line.startsWith("diff --git a/"))
      {
        String path = line.substring("diff --git a/".length(), line.indexOf(" b/"));
        current = new ArrayList<>();
        diffs.put(path, current);
      }
      else if (current != null && !line.startsWith("index ") && !line.startsWith("--- ") && !line.startsWith("+++ "))
      {
        current.add(line);
      }
    }
    return diffs;
  }

  private static void tile(StringBuilder h, String label, String value, String sub)
  {
    h.append("<div class=\"tile\"><div class=\"label\">").append(label).append("</div><div class=\"value\">")
        .append(value).append("</div><div class=\"sub\">").append(sub).append("</div></div>\n");
  }

  private static String packageOf(String path)
  {
    for (String root : TreeShaker.SOURCE_ROOTS)
    {
      if (path.startsWith(root + "/"))
      {
        String relative = path.substring(root.length() + 1);
        int slash = relative.lastIndexOf('/');
        return slash < 0 ? "(default)" : relative.substring(0, slash).replace('/', '.');
      }
    }
    return "?";
  }

  private static Set<String> thirdParty(String source)
  {
    Set<String> result = new TreeSet<>();
    Matcher matcher = IMPORTS.matcher(source);
    while (matcher.find())
    {
      String name = matcher.group(1);
      if (Stream.of(OURS).noneMatch(name::startsWith))
      {
        String[] parts = name.split("\\.");
        result.add(parts.length < 2 ? name : parts[0] + "." + parts[1]);
      }
    }
    return result;
  }

  private static int lines(String text)
  {
    if (text.isEmpty())
    { return 0; }
    return count(text, "\n") + (text.endsWith("\n") ? 0 : 1);
  }

  private static int count(String text, String part)
  {
    int count = 0;
    for (int i = text.indexOf(part); i >= 0; i = text.indexOf(part, i + part.length()))
    {
      count++;
    }
    return count;
  }

  private static String simpleName(String name)
  {
    return name.substring(name.lastIndexOf('.') + 1);
  }

  private static long pct(long part, long whole)
  {
    return whole == 0 ? 0 : Math.round(100.0 * part / whole);
  }

  private static String kb(long bytes)
  {
    return String.format("%,d KB", Math.round(bytes / 1024.0));
  }

  private static String bar(long after, long before)
  {
    long keep = pct(after, before);
    return "<div class=\"bar\" title=\"" + keep + "% kept\"><span class=\"kept\" style=\"width:" + keep
        + "%\"></span><span class=\"gone\" style=\"width:" + (100 - keep) + "%\"></span></div>";
  }

  private static String esc(String text)
  {
    return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
  }

  private static final String CSS = """
      :root {
        --bg: #fbfaf8; --surface: #ffffff; --text: #1d1d1f; --muted: #6b6b70; --border: #e4e2dd;
        --kept: #3b7d5c; --gone: #c9553f; --gone-soft: #f6e3de; --kept-soft: #e0efe6;
        --code-bg: #f4f2ee;
      }
      @media (prefers-color-scheme: dark) {
        :root:not([data-theme="light"]) {
          --bg: #161617; --surface: #1f1f21; --text: #ececee; --muted: #9a9aa0; --border: #333336;
          --kept: #5fb88a; --gone: #e2775f; --gone-soft: #3a2420; --kept-soft: #1d3327;
          --code-bg: #19191b;
        }
      }
      :root[data-theme="dark"] {
        --bg: #161617; --surface: #1f1f21; --text: #ececee; --muted: #9a9aa0; --border: #333336;
        --kept: #5fb88a; --gone: #e2775f; --gone-soft: #3a2420; --kept-soft: #1d3327;
        --code-bg: #19191b;
      }
      * { box-sizing: border-box; }
      body { margin: 0; background: var(--bg); color: var(--text); font: 15px/1.5 -apple-system, BlinkMacSystemFont, "Segoe UI", sans-serif; }
      main { max-width: 1100px; margin: 0 auto; padding: 32px 16px 64px; }
      h1 { font-size: 28px; margin: 0 0 4px; }
      h2 { font-size: 19px; margin: 40px 0 12px; }
      .muted { color: var(--muted); font-size: 13px; }
      code { font-family: ui-monospace, SFMono-Regular, Menlo, monospace; font-size: 13px; }
      .tiles { display: grid; grid-template-columns: repeat(auto-fit, minmax(170px, 1fr)); gap: 12px; margin-top: 24px; }
      .tile { background: var(--surface); border: 1px solid var(--border); border-radius: 10px; padding: 14px 16px; }
      .tile .label { color: var(--muted); font-size: 13px; }
      .tile .value { font-size: 26px; font-weight: 600; font-variant-numeric: tabular-nums; }
      .tile .sub { color: var(--muted); font-size: 13px; font-variant-numeric: tabular-nums; }
      .scroll { overflow-x: auto; background: var(--surface); border: 1px solid var(--border); border-radius: 10px; }
      table { border-collapse: collapse; width: 100%; }
      th, td { padding: 6px 12px; border-bottom: 1px solid var(--border); text-align: left; white-space: nowrap; }
      th { font-size: 12px; text-transform: uppercase; letter-spacing: .04em; color: var(--muted); font-weight: 600; }
      td.n { text-align: right; font-variant-numeric: tabular-nums; }
      tr.gone td:first-child code { color: var(--gone); text-decoration: line-through; }
      tr.intact td:first-child code { color: var(--kept); }
      .bar { display: inline-flex; width: 120px; height: 8px; border-radius: 4px; overflow: hidden; vertical-align: middle; background: var(--border); }
      .bar .kept { background: var(--kept); } .bar .gone { background: var(--gone); opacity: .55; }
      details { background: var(--surface); border: 1px solid var(--border); border-radius: 8px; margin: 6px 0; }
      summary { cursor: pointer; padding: 8px 12px; display: flex; flex-wrap: wrap; gap: 8px; align-items: center; }
      summary .bar { margin-left: auto; }
      ul.cols { columns: 3 240px; margin: 0; padding: 4px 12px 12px 32px; }
      pre.diff { margin: 0; padding: 12px; background: var(--code-bg); overflow-x: auto; font: 12px/1.45 ui-monospace, Menlo, monospace; border-top: 1px solid var(--border); }
      pre.diff span { display: block; }
      pre.diff .add { background: var(--kept-soft); } pre.diff .del { background: var(--gone-soft); }
      pre.diff .hunk { color: var(--muted); }
      .legend span { display: inline-flex; align-items: center; gap: 6px; margin-right: 16px; }
      .swatch { width: 10px; height: 10px; border-radius: 2px; display: inline-block; }
      .inputs { display: grid; grid-template-columns: minmax(200px, 1fr) minmax(280px, 2fr) minmax(220px, 1fr); gap: 12px; margin-bottom: 8px; }
      @media (max-width: 800px) { .inputs { grid-template-columns: 1fr; } }
      .inputs .tile { min-width: 0; overflow-x: auto; }
      table.plain th, table.plain td { padding: 4px 8px 4px 0; }
      table.plain { margin-top: 8px; }
      ul.plainlist { list-style: none; padding: 0; margin: 4px 0 0; }
      """;
}
