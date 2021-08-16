package com.spun.util.persistence;

public interface ExecutableCommand
{
  public abstract String getCommand();
  public abstract String executeCommand(String command);
}
