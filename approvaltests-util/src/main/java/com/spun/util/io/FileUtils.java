package com.spun.util.io;

import com.spun.util.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.channels.FileChannel;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A static class of convenience functions for Files
 **/
public class FileUtils
{
  public static File createTempDirectory()
  {
    File tempFile = ObjectUtils.throwAsError(() -> File.createTempFile("TEMP", null));
    tempFile.delete();
    tempFile.mkdirs();
    return tempFile;
  }
  public static void deleteDirectory(File directory)
  {
    // delete all directory
    File[] directories = directory.listFiles(new SimpleDirectoryFilter());
    for (int i = 0; i < directories.length; i++)
    {
      deleteDirectory(directories[i]);
    }
    // Delete all Files.
    File[] files = directory.listFiles(new SimpleFileFilter());
    for (int i = 0; i < files.length; i++)
    {
      files[i].delete();
    }
    // delete self.
    directory.delete();
  }
  public static String readFromClassPath(Class<?> clazz, String string)
  {
    final InputStream resourceAsStream = clazz.getResourceAsStream(string);
    if (resourceAsStream == null)
    {
      String message = String.format("Could not find %s from %s", string, clazz.getName());
      throw new RuntimeException(message);
    }
    String resource = FileUtils.readStream(resourceAsStream);
    return resource;
  }
  public static File[] getRecursiveFileList(File directory)
  {
    return getRecursiveFileList(directory, new SimpleFileFilter());
  }
  public static File[] getRecursiveFileList(File directory, FileFilter filter)
  {
    ArrayList<File> list = new ArrayList<File>();
    if (!directory.isDirectory())
    { throw new Error("File is not a directory: " + directory.getName()); }
    File[] directories = directory.listFiles(new SimpleDirectoryFilter());
    directories = directories == null ? new File[0] : directories;
    for (int i = 0; i < directories.length; i++)
    {
      ArrayUtils.addArray(list, getRecursiveFileList(directories[i], filter));
    }
    File[] files = directory.listFiles(filter);
    ArrayUtils.addArray(list, files);
    return list.toArray(new File[list.size()]);
  }
  public static void copyFile(File in, File out)
  {
    try
    {
      out.getParentFile().mkdirs();
      try (FileInputStream sIn = new FileInputStream(in))
      {
        try (FileChannel inChannel = sIn.getChannel())
        {
          try (FileOutputStream sOut = new FileOutputStream(out))
          {
            try (FileChannel outChannel = sOut.getChannel())
            {
              outChannel.transferFrom(inChannel, 0, inChannel.size());
            }
          }
        }
      }
    }
    catch (Exception e)
    {
      ObjectUtils.throwAsError(e);
    }
  }
  public static void copyStream(InputStream in, OutputStream out)
  {
    try
    {
      byte[] buf = new byte[1024];
      int i = 0;
      while ((i = in.read(buf)) != -1)
      {
        out.write(buf, 0, i);
      }
      in.close();
      out.close();
    }
    catch (Throwable t)
    {
      throw ObjectUtils.throwAsError(t);
    }
  }
  public static void redirectInputToFile(String fileName, InputStream in)
  {
    try
    {
      FileOutputStream fos = new FileOutputStream(new File(fileName), false);
      copyStream(in, fos);
    }
    catch (Throwable t)
    {
      throw ObjectUtils.throwAsError(t);
    }
  }
  public static void copyFileToDirectory(String file, File tempDir)
  {
    try
    {
      File in = new File(file);
      File out = new File(tempDir, in.getName());
      copyFile(in, out);
    }
    catch (Throwable t)
    {
      throw ObjectUtils.throwAsError(t);
    }
  }
  public static void appendToFile(File file, String text)
  {
    try
    {
      Asserts.assertNotNull("Writing to file: " + file, text);
      file.getCanonicalFile().getParentFile().mkdirs();
      try (BufferedWriter out = Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8,
          StandardOpenOption.APPEND))
      {
        out.write(text);
      }
    }
    catch (Throwable t)
    {
      throw ObjectUtils.throwAsError(t);
    }
  }
  public static void writeFile(File file, String text)
  {
    try
    {
      Asserts.assertNotNull("Writing to file: " + file, text);
      file.getCanonicalFile().getParentFile().mkdirs();
      try (BufferedWriter out = Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8))
      {
        out.write(text);
      }
    }
    catch (Throwable t)
    {
      throw ObjectUtils.throwAsError(t);
    }
  }
  public static void writeFileQuietly(File file, String text)
  {
    writeFile(file, text);
  }
  public static void writeFile(File file, CharSequence data)
  {
    try
    {
      Asserts.assertNotNull("Writing to file: " + file, data);
      file.getCanonicalFile().getParentFile().mkdirs();
      try (DataOutputStream writer = new DataOutputStream(new FileOutputStream(file)))
      {
        for (int i = 0; i < data.length(); i++)
        {
          writer.write(data.charAt(i));
        }
      }
    }
    catch (Throwable t)
    {
      throw ObjectUtils.throwAsError(t);
    }
  }
  public static void writeFile(File file, InputStream data)
  {
    try
    {
      Asserts.assertNotNull("Writing to file: " + file, data);
      file.getCanonicalFile().getParentFile().mkdirs();
      copyStream(data, new FileOutputStream(file));
    }
    catch (Throwable t)
    {
      throw ObjectUtils.throwAsError(t);
    }
  }
  public static String readFile(String absolutePath)
  {
    return readFile(absolutePath, true);
  }
  public static String readFile(String absolutePath, boolean ensureTrailingNewline)
  {
    return readFile(new File(absolutePath), ensureTrailingNewline);
  }
  public static String readFile(File file)
  {
    return readFile(file, true);
  }
  public static String readFile(File file, boolean ensureTrailingNewline)
  {
    try
    {
      if (!file.exists())
      { throw new RuntimeException("Invalid file '" + getResolvedPath(file) + "'"); }
      CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder();
      decoder.onMalformedInput(CodingErrorAction.IGNORE);
      Reader reader = new InputStreamReader(Files.newInputStream(file.toPath()), decoder);
      BufferedReader in = new BufferedReader(reader);
      String output = readBuffer(in);
      output = output.replaceAll("\r\n", "\n");
      if (ensureTrailingNewline)
      {
        output = StringUtils.ensureEnding(output, "\n");
      }
      return output;
    }
    catch (Throwable t)
    {
      throw ObjectUtils.throwAsError(t);
    }
  }
  public static String readBuffer(BufferedReader in)
  {
    try
    {
      StringBuilder sb = new StringBuilder();
      int ch;
      while ((ch = in.read()) != -1)
      {
        sb.append((char) ch);
      }
      return sb.toString();
    }
    catch (Throwable t)
    {
      throw ObjectUtils.throwAsError(t);
    }
  }
  public static String readFileWithSuppressedExceptions(File databaseFile)
  {
    return FileUtils.readFile(databaseFile);
  }
  public static File saveToFile(String prefix, Reader input)
  {
    try
    {
      File file = File.createTempFile(prefix, null);
      try (BufferedWriter bw = Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8))
      {
        try (BufferedReader inputReader = new BufferedReader(input))
        {
          String thisLine;
          while ((thisLine = inputReader.readLine()) != null)
          {
            bw.write(thisLine);
            bw.newLine();
          }
        }
      }
      return file;
    }
    catch (IOException e)
    {
      throw new FormattedException("Failed to save file (prefix, message): %s, %s", prefix, e.getMessage());
    }
  }
  public static String getDirectoryFriendlyName(String name)
  {
    if (name == null)
    { return ""; }
    StringBuffer result = new StringBuffer();
    for (int i = 0; i < name.length(); i++)
    {
      char c = name.charAt(i);
      switch (c)
      {
        case '.' :
          break;
        default :
          result.append(c);
          break;
      }
    }
    return result.toString();
  }
  public static String getExtensionWithDot(String filename)
  {
    int p = filename.lastIndexOf('.');
    return filename.substring(p);
  }
  public static String getExtensionWithoutDot(String filename)
  {
    return getExtensionWithDot(filename).substring(1);
  }
  public static void createIfNeeded(String file)
  {
    try
    {
      File f = new File(file);
      if (!f.exists())
      {
        if (isImage(file))
        {
          createEmptyImage(f);
        }
        else
        {
          writeFile(f, "");
        }
      }
    }
    catch (Throwable e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
  private static void createEmptyImage(File file)
  {
    try
    {
      BufferedImage image = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
      ImageIO.write(image, "png", file);
    }
    catch (IOException e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
  private static boolean isImage(String file)
  {
    return file.endsWith(".png");
  }
  public static String readStream(InputStream resourceAsStream)
  {
    BufferedReader reader = new BufferedReader(new InputStreamReader(resourceAsStream, StandardCharsets.UTF_8));
    return FileUtils.readBuffer(reader);
  }
  public static char[] loadResourceFromClasspathAsBytes(Class<?> clazz, String name)
  {
    return extractBytes(clazz.getResourceAsStream(name));
  }
  public static char[] extractBytes(final InputStream resourceAsStream)
  {
    try
    {
      ArrayList<Character> data = new ArrayList<Character>();
      int b = resourceAsStream.read();
      while (b != -1)
      {
        data.add(new Character((char) b));
        b = resourceAsStream.read();
      }
      return FileUtils.toChars(data);
    }
    catch (Throwable t)
    {
      throw ObjectUtils.throwAsError(t);
    }
  }
  public static char[] toChars(List<Character> data)
  {
    char[] out = new char[data.size()];
    for (int i = 0; i < out.length; i++)
    {
      out[i] = data.get(i);
    }
    return out;
  }
  public static boolean isNonEmptyFile(String approved)
  {
    File file = new File(approved);
    return file.exists() && file.length() > 0;
  }
  public static void ensureParentDirectoriesExist(File file)
  {
    File dir = file.getParentFile();
    if (!dir.exists())
    {
      dir.mkdirs();
    }
  }
  public static String readFile(File file, String defaultText)
  {
    try
    {
      return readFile(file);
    }
    catch (Throwable e)
    {
      return defaultText;
    }
  }
  public static String getCurrentDirectory()
  {
    try
    {
      return new File(".").getCanonicalPath();
    }
    catch (Throwable e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
  public static void delete(String filePath)
  {
    delete(new File(filePath));
  }
  public static void delete(File file)
  {
    if (file.exists())
    {
      file.delete();
    }
  }
  public static Stream<Path> walkPath(String channelsPath, int maxDepth)
  {
    try
    {
      return Files.walk(Paths.get(channelsPath), maxDepth, FileVisitOption.FOLLOW_LINKS);
    }
    catch (IOException e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
  public static String getResolvedPath(File file)
  {
    try
    {
      return file.getCanonicalFile().getAbsolutePath();
    }
    catch (IOException e)
    {
      throw ObjectUtils.throwAsError(e);
    }
  }
}
