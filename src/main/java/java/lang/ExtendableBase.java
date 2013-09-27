package java.lang;

public class ExtendableBase<T> implements Extendable<T>
{
  protected T caller;
  @Override
  public void setCaller(T caller)
  {
    this.caller = caller;
  }
  protected T getCaller()
  {
    return caller;
  }
}
