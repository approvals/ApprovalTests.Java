package org.approvaltests.demos;

import com.spun.util.persistence.Loader;
import com.spun.util.persistence.Saver;
import com.spun.util.persistence.test.MockSaver;
import org.approvaltests.reporters.UseReporter;
import org.approvaltests.reporters.macosx.DiffMergeReporter;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@UseReporter(DiffMergeReporter.class)
public class LoaderTest
{
  public static class Step1
  {
    // begin-snippet: separating_loaders_1
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
    // begin-snippet: separating_loaders_2
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
    // begin-snippet: seperating_loaders_test
    @Test
    public void testOnlyAvailableItemsAreReserved()
    {
      Item milk = new Item("M101", "Milk", 2);
      Item missing_item = new Item("W202", "Item not Found", 2);
      Item sold_out_item = new Item("S303", "SuperPopularGame", 0);
      MockSaver<Item> saver = new MockSaver<>();
      reserveItems(Arrays.asList(milk.id, missing_item.id, sold_out_item.id),
          () -> new Item[]{milk, sold_out_item}, saver);
      // Only reserved milk
      Assert.assertArrayEquals(saver.saved.toArray(), new Item[]{milk});
    }
    // end-snippet
  }
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
  private static void registerHold(Item item)
  {
  }
  private static Item[] getInventory()
  {
    Item[] items = new Item[0];
    return items;
  }
  public static class Item
  {
    public String id;
    public int    inventoryCount;
    public String name;
    public Item(String id, String name, int inventoryCount)
    {
      this.id = id;
      this.inventoryCount = inventoryCount;
      this.name = name;
    }
  }
}
