<a id="top"></a>

# Upgrade from 32.0.0 to 32.1.0

32.1.0 adds one method to `ApprovalWriter`. There are no behavior changes.
Approved files keep their names. You do not need to re-approve anything.

## For AI coding agents

1. Change the dependency version to `32.1.0`.
2. Compile. Nothing should break: the new method has a default.
3. If you have your own `ApprovalWriter`, read [Your own ApprovalWriter](#your-own-approvalwriter) and decide whether to override `cleanUpReceived`.
4. Run the tests. They should pass without new `.received.` files.

## Your own ApprovalWriter

`ApprovalWriter` now has three methods:

```java
public interface ApprovalWriter
{
  File writeReceivedFile(File received);

  String getFileExtensionWithDot();

  default void cleanUpReceived(File received)
  {
    received.delete();
  }
}
```

After a test passes, ApprovalTests calls `writer.cleanUpReceived(received)` instead of deleting the received file itself.
The default deletes the file, as before, so you do not have to change anything.

Override it when the default is wrong for your writer, for example when `writeReceivedFile` returns a file you do not want deleted:

```java
@Override
public void cleanUpReceived(File received)
{
  // keep the file
}
```

---

[Back to User Guide](/approvaltests/docs/README.md#top)
