package com.spun.util.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * A static class of convence functions for Files
 **/
public class ZipUtils
{
  
  public static File zipDirectory(String directory, String zipFileName) throws IOException
  {
    return zipDirectory(new File(directory), new File(zipFileName));
  }
  
  public static File zipDirectory(File directory, File zipFileName) throws IOException
  {
    return doCreateZipFile(FileUtils.getRecursiveFileList(directory), zipFileName);
  }
  
  public static File doCreateZipFile(File[] files, File zipFile) throws IOException
  {
    byte[] buf = new byte[1024];
    zipFile.getParentFile().mkdirs();
    FileOutputStream fileOut = new FileOutputStream(zipFile);
    ZipOutputStream out = new ZipOutputStream(fileOut);
    // Compress the files
    for (int i = 0; i < files.length; i++)
    {
      FileInputStream in = new FileInputStream(files[i]);
      out.putNextEntry(new ZipEntry(files[i].getName()));
      // Transfer bytes from the file to the ZIP file
      int len;
      while ((len = in.read(buf)) > 0)
      {
        out.write(buf, 0, len);
      }
      // Complete the entry
      out.closeEntry();
      in.close();
    }
    // Complete the ZIP file
    out.close();
    fileOut.close();
    return zipFile;
  }
  
  public static File[] doUnzip(File destination, File zipFile) throws IOException
  {
    ArrayList<File> list = new ArrayList<File>();
    byte[] buf = new byte[1024];
    FileInputStream fileIn = new FileInputStream(zipFile);
    ZipInputStream in = new ZipInputStream(fileIn);
    // DeCompress the files
    ZipEntry entry = in.getNextEntry();
    while (entry != null)
    {
      File file = new File(destination, entry.getName());
      if (entry.isDirectory())
      {
        file.mkdirs();
      }
      else
      {
        list.add(file);
        FileOutputStream out = new FileOutputStream(file);
        // Transfer bytes from the file to the ZIP file
        int len;
        while ((len = in.read(buf)) > 0)
        {
          out.write(buf, 0, len);
        }
        // Complete the entry
        in.closeEntry();
        out.close();
      }
      entry = in.getNextEntry();
    }
    // Complete the ZIP file
    in.close();
    fileIn.close();
    return list.toArray(new File[0]);
  }
  
  public static void main(String args[]) throws IOException
  {
    zipDirectory("c:\\t", "c:\\t\\t.zip");
  }
  /************************************************************************/
  /************************************************************************/
}