package com.spun.util.io;

import java.io.File;

public class SimpleDirectoryFilter
  implements java.io.FileFilter
{

  public SimpleDirectoryFilter()
  {
	}
  
  

  public boolean accept(File pathname)
  {
		String name = pathname.getName().toLowerCase();
    boolean accept = false;
  	if (name.equals(".") || name .equals(".."))
  	{
      accept = false;
  	}
		else if (pathname.isDirectory())
    {
      accept = true;
    }
  	else 
    {
      accept = false;
    }
    return accept;
  }


}