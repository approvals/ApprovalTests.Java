package com.spun.util.velocity;

/*
 * Copyright 2001-2002,2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import com.spun.util.StringUtils;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.loader.ResourceLoader;
import org.apache.velocity.util.ExtProperties;

import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

/**
 * This is a simple resource loader that loads the string as a template
 *
 **/
public class StringResourceLoader extends ResourceLoader
{
  @Override
  public void init(ExtProperties extProperties)
  {
  }
  @Override
  public Reader getResourceReader(String source, String encoding)
  {
    return new StringReader(source);
  }
  public boolean isSourceModified(Resource resource)
  {
    return true;
  }
  public long getLastModified(Resource resource)
  {
    return 0;
  }
}
