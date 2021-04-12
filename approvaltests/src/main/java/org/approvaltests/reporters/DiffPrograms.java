package org.approvaltests.reporters;

import static org.approvaltests.reporters.intellij.Edition.Community;
import static org.approvaltests.reporters.intellij.Edition.Silicon;
import static org.approvaltests.reporters.intellij.Edition.Ultimate;

import java.util.List;

import org.approvaltests.reporters.intellij.IntelliJPathResolver;

import com.spun.util.ArrayUtils;

public class DiffPrograms
{
  private static final List<String> TEXT           = GenericDiffReporter.TEXT_FILE_EXTENSIONS;
  private static final List<String> IMAGE          = GenericDiffReporter.IMAGE_FILE_EXTENSIONS;
  private static final List<String> TEXT_AND_IMAGE = ArrayUtils.combine(TEXT, IMAGE);
  public static class Mac
  {
    public static DiffInfo DIFF_MERGE         = new DiffInfo(
        "/Applications/DiffMerge.app/Contents/MacOS/DiffMerge", "--nosplash %s %s ", TEXT);
    public static DiffInfo BEYOND_COMPARE     = new DiffInfo(
        "/Applications/Beyond Compare.app/Contents/MacOS/bcomp", TEXT);
    public static DiffInfo KALEIDOSCOPE       = new DiffInfo(
        "/Applications/Kaleidoscope.app/Contents/MacOS/ksdiff", TEXT_AND_IMAGE);
    public static DiffInfo KDIFF3             = new DiffInfo("/Applications/kdiff3.app/Contents/MacOS/kdiff3",
        "%s %s -m", TEXT);
    public static DiffInfo P4MERGE            = new DiffInfo("/Applications/p4merge.app/Contents/MacOS/p4merge",
        TEXT_AND_IMAGE);
    public static DiffInfo TK_DIFF            = new DiffInfo("/Applications/TkDiff.app/Contents/MacOS/tkdiff",
        TEXT);
    public static DiffInfo VISUAL_STUDIO_CODE = new DiffInfo(
        "/Applications/Visual Studio Code.app/Contents/Resources/app/bin/code", "-d %s %s", TEXT);
  }
  public static class Windows
  {
    public static DiffInfo BEYOND_COMPARE_3    = new DiffInfo("{ProgramFiles}Beyond Compare 3\\BCompare.exe",
        TEXT_AND_IMAGE);
    public static DiffInfo BEYOND_COMPARE_4    = new DiffInfo("{ProgramFiles}Beyond Compare 4\\BCompare.exe",
        TEXT_AND_IMAGE);
    public static DiffInfo TORTOISE_IMAGE_DIFF = new DiffInfo("{ProgramFiles}TortoiseSVN\\bin\\TortoiseIDiff.exe",
        "/left:%s /right:%s", IMAGE);
    public static DiffInfo TORTOISE_TEXT_DIFF  = new DiffInfo("{ProgramFiles}TortoiseSVN\\bin\\TortoiseMerge.exe",
        TEXT);
    public static DiffInfo WIN_MERGE_REPORTER  = new DiffInfo("{ProgramFiles}WinMerge\\WinMergeU.exe",
        TEXT_AND_IMAGE);
    public static DiffInfo ARAXIS_MERGE        = new DiffInfo("{ProgramFiles}Araxis\\Araxis Merge\\Compare.exe",
        TEXT);
    public static DiffInfo CODE_COMPARE        = new DiffInfo(
        "{ProgramFiles}Devart\\Code Compare\\CodeCompare.exe", TEXT);
    public static DiffInfo KDIFF3              = new DiffInfo("{ProgramFiles}KDiff3\\kdiff3.exe", TEXT);
    public static DiffInfo VISUAL_STUDIO_CODE  = new DiffInfo("{ProgramFiles}Microsoft VS Code\\Code.exe",
        "-d %s %s", TEXT);
  }
  public static class All
  {
    public static final DiffInfo INTELLIJ_MAC_SILICON = new DiffInfo(new IntelliJPathResolver(Silicon).findIt(),
        "diff %s %s", TEXT);
    public static DiffInfo       INTELLIJ_C           = new DiffInfo(new IntelliJPathResolver(Community).findIt(),
        "diff %s %s", TEXT);
    public static DiffInfo       INTELLIJ_U           = new DiffInfo(new IntelliJPathResolver(Ultimate).findIt(),
        "diff %s %s", TEXT);
  }
  public static class Linux
  {
    public static DiffInfo DIFF_MERGE = new DiffInfo("/usr/bin/diffmerge", "--nosplash %s %s ", TEXT);
    public static DiffInfo MELD_MERGE = new DiffInfo("/usr/bin/meld", "%s %s ", TEXT);
  }
}
