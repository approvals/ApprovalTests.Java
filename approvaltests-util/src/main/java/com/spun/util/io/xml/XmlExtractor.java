package com.spun.util.io.xml;

import org.w3c.dom.Node;

public interface XmlExtractor
{
  public Object extractObjectForNode(Node node);
}
