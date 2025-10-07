package com.spun.util.io;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class FileMonitor
{
  private Timer             timer;
  private Map<File, Long>   files;
  private Set<FileListener> listeners;
  public FileMonitor(long pollingInterval)
  {
    files = new HashMap<File, Long>();
    listeners = new HashSet<FileListener>();
    timer = new Timer(true);
    timer.schedule(new FileMonitorNotifier(), 0, pollingInterval);
  }

  public void stop()
  {
    timer.cancel();
  }

  public void addFile(File file)
  {
    if (!files.containsKey(file))
    {
      files.put(file, (file.exists() ? file.lastModified() : -1L));
    }
  }

  public void removeFile(File file)
  {
    files.remove(file);
  }

  public void addListener(FileListener fileListener)
  {
    listeners.add(fileListener);
  }

  public void removeListener(FileListener fileListener)
  {
    listeners.remove(fileListener);
  }
  private class FileMonitorNotifier extends TimerTask
  {
    public void run()
    {
      for (File file : files.keySet())
      {
        long lastModifiedTime = ((Long) files.get(file)).longValue();
        long newModifiedTime = file.exists() ? file.lastModified() : -1;
        if (newModifiedTime != lastModifiedTime)
        {
          files.put(file, newModifiedTime);
          for (FileListener listener : listeners)
          {
            listener.fileChanged(file);
          }
        }
      }
    }
  }
  public interface FileListener
  {
    void fileChanged(File file);
  }
}
