package com.spun.util.persistence;

public interface ExecutableCommand
{
  public String getCommand();
  public String executeCommand(String command);
}
