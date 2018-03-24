package com.spun.util.io.xml;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.lambda.functions.implementations.S1;
import org.lambda.query.Query;
import org.w3c.dom.Node;

import com.spun.util.ClassUtils;
import com.spun.util.ObjectUtils;
import com.spun.util.database.DatabaseObject;
import com.spun.util.database.Syncable;
import com.spun.util.database.TemporaryCache;
import com.spun.util.database.XmlDatabaseMapExtractor;
import com.spun.util.filters.ClassFilter;
import com.spun.util.filters.FilterUtils;
import com.spun.util.io.XMLUtils;

/***********************************************************************/
public class DatabaseObjectXMLUtils
{
  /***********************************************************************/
  public static DatabaseObject[] extract(String xml, XmlMap[] xmlMaps)
  {
    try
    {
      DatabaseObject objects[] = extract(XMLUtils.parseXML(xml), xmlMaps);
      TemporaryCache cache = new TemporaryCache(objects);
      cache.forceGenericObjectType();
      for (int i = 0; i < objects.length; i++)
      {
        ((Syncable) objects[i]).sync(cache);
      }
      return Query.orderBy(objects, new S1<DatabaseObject>(DatabaseObject.Null)
      {
        {
          ret(a.getPkey());
        }
      });
    }
    catch (Throwable t)
    {
      throw ObjectUtils.throwAsError(t);
    }
  }
  /***********************************************************************/
  private static DatabaseObject[] extract(Node node, XmlMap[] xmlMaps) throws Exception
  {
    ArrayList<DatabaseObject> list = new ArrayList<DatabaseObject>();
    XmlExtractorUtil.extractAndTranslateForNode(XmlExtractorUtil.traverseToTag("data", node), list,
        XmlMapTranslator.get(ArrayList.class, xmlMaps));
    return list.toArray(new DatabaseObject[list.size()]);
  }
  /***********************************************************************/
  public static XmlMap map(Class<?> clazz)
  {
    return new XmlMap(ClassUtils.getClassName(clazz), "add", new XmlDatabaseMapExtractor(clazz));
  }
  /***********************************************************************/
  /***********************************************************************/
  public static <T extends DatabaseObject> T[] extractClass(Class<T> clazz, DatabaseObject[] databaseObjects)
  {
    List<DatabaseObject> list = FilterUtils.retainExtracted(databaseObjects, new ClassFilter(clazz));
    return list.toArray((T[]) Array.newInstance(clazz, list.size()));
  }
  public static void mockOld(DatabaseObject[] objects)
  {
    for (DatabaseObject object : objects)
    {
      object.setNew(false);
    }
  }
}
