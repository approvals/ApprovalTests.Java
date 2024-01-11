package org.approvaltests.bdd;

import org.approvaltests.Approvals;
import org.approvaltests.strings.Printable;
import org.approvaltests.strings.PrintableScenario;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PrintableScenarioTest
{
  @Test
  public void printBDDScenario() throws Exception
  {
    PrintableScenario story = new PrintableScenario("Buy Groceries", "Bob puts two items in the shopping cart");
    User currentUser = new User();
    ShoppingCart shoppingCart = new ShoppingCart(currentUser);
    story.given(new Printable<>(currentUser, UserPrinter::print),
        new Printable<>(shoppingCart, ShoppingCartPrinter::print));
    story.when("Add oranges to the cart", () -> {
      shoppingCart.add("Oranges", 1, BigDecimal.valueOf(5));
      return null;
    });
    story.when("Add apples to the cart", () -> {
      shoppingCart.add("Apples", 3, BigDecimal.valueOf(4));
      return null;
    });
    Approvals.verify(story.then());
  }
}

class User
{
  public String name = "Bob";
}

class Item
{
  public String     name;
  public Integer    quantity;
  public BigDecimal price;
  public BigDecimal amount()
  {
    return price.multiply(BigDecimal.valueOf(quantity));
  }
  public Item(String itemName, int quantity, BigDecimal price)
  {
    this.name = itemName;
    this.quantity = quantity;
    this.price = price;
  }
}

class ShoppingCart
{
  private final User user;
  public List<Item>  articles = new ArrayList<>();
  public ShoppingCart(User currentUser)
  {
    this.user = currentUser;
  }
  public BigDecimal subtotal()
  {
    return articles.stream().map(Item::amount).reduce(BigDecimal.ZERO, BigDecimal::add);
  }
  public BigDecimal shipping()
  {
    if (Objects.equals(user.name, "Bob"))
    { return BigDecimal.ONE; }
    return BigDecimal.ZERO;
  }
  public BigDecimal total()
  {
    return this.subtotal().add(this.shipping());
  }
  public void add(String itemName, int quantity, BigDecimal price)
  {
    this.articles.add(new Item(itemName, quantity, price));
  }
}

class ShoppingCartPrinter
{
  public static String print(ShoppingCart shoppingCart)
  {
    StringBuilder result = new StringBuilder();
    result.append("ShoppingCart:\n");
    result.append("Articles:\n");
    result.append(shoppingCart.articles.stream()
        .map(article -> "    " + article.name + " price: " + article.price + " quantity: " + article.quantity)
        .collect(Collectors.joining("\n")));
    result.append("\n");
    result.append("Subtotal:" + shoppingCart.subtotal() + "\n");
    result.append("Shipping:" + shoppingCart.shipping() + "\n");
    result.append("Total:" + shoppingCart.total() + "\n");
    return result.toString();
  }
}

class UserPrinter
{
  public static String print(User user)
  {
    return "User: " + user.name + "\n";
  }
}