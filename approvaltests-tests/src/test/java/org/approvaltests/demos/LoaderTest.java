package org.approvaltests.demos;

import com.spun.util.persistence.Loader;
import com.spun.util.persistence.Saver;

import java.util.List;

public class LoaderTest
{
  public static class Step1
  {
    // begin-snippet:separating_loaders_1
    public void reserveItems(List<String> ids)
    {
      Item[] items = getInventory();
      for (Item item : items)
      {
        if (ids.contains(item.id) && item.inventoryCount > 0)
        {
          registerHold(item);
        }
      }
    }
    // end-snippet
  }
  public static class Step2
  {
    // begin-snippet:separating_loaders_2
    public void reserveItems(List<String> ids)
    {
      reserveItems(ids, new InventoryLoader(), new ItemReserver());
    }
    public void reserveItems(List<String> ids, Loader<Item[]> loader, Saver<Item> itemReserver)
    {
      Item[] items = loader.load();
      for (Item item : items)
      {
        if (ids.contains(item.id) && item.inventoryCount > 0)
        {
          itemReserver.save(item);
        }
      }
    }
    // end-snippet
    public static class InventoryLoader implements Loader<Item[]>
    {
      @Override
      public Item[] load()
      {
        return getInventory();
      }
    }
    public static class ItemReserver implements Saver<Item>
    {
      @Override
      public Item save(Item save)
      {
        registerHold(save);
        return save;
      }
    }
  }
  private static void registerHold(Item item)
  {
  }
  private static Item[] getInventory()
  {
    Item[] items = new Item[0];
    return items;
  }
  private class Item
  {
    public String id;
    public int    inventoryCount;
  }
}
