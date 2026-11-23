<a id="top"></a>

# How to verify binary data

<!-- toc -->
<!-- endToc -->

Use `Approvals.verifyBinary` to approve raw bytes, such as images, PDFs, or serialized data.
The bytes are written to the received file unchanged and compared byte for byte.

<!-- snippet: verify_binary -->
<a id='snippet-verify_binary'></a>
```java
File image = new File(ClassUtils.getSourceDirectory(getClass()), "reporters/a.png");
byte[] png = Files.readAllBytes(image.toPath());
Approvals.verifyBinary(png, ".png");
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/VerifyBinaryTest.java#L14-L18' title='Snippet source file'>snippet source</a> | <a href='#snippet-verify_binary' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

The second argument is the file extension, with or without the dot. Pick one that lets your diff tool or viewer open the file.

---

[Back to User Guide](../README.md#top)
