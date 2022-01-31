package com.spun.util.introspection;

import com.spun.util.ObjectUtils;
import com.spun.util.io.FileUtils;
import com.spun.util.io.filefilters.JavaClassFileFilter;

import java.io.File;
import java.util.ArrayList;

public class ClassGetter
{
  public static ArrayList<Class<?>> getClasses(String packageName, String classSuffix)
  {
    ArrayList<Class<?>> classes = new ArrayList<>();
    File[] testFiles = FileUtils.getRecursiveFileList(new File("."),
        new JavaClassFileFilter(packageName, classSuffix));
    String pathHead = packageName.substring(0, packageName.indexOf('.'));
    for (int i = 0; i < testFiles.length; i++)
    {
      Class<?> clazz = ObjectUtils.loadClass(getJavaName(testFiles[i], pathHead));
      classes.add(clazz);
    }
    return classes;
  }
  private static String getJavaName(File file, String pathHead)
  {
    String path = file.getAbsolutePath();
    String extension = ".class";
    String fullName = path.substring(path.indexOf(File.separator + pathHead + File.separator) + 1,
        path.length() - extension.length());
    return fullName.replace(File.separatorChar, '.');
  }
}
