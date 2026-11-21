<a id="top"></a>

# Upgrade from 31.2.0 to 31.3.0

31.3.0 changes no behavior. It deprecates the APIs that 32.0.0 will remove, and adds the replacements.
Everything below still compiles and runs on 31.3.0, so you can migrate one call site at a time.
After every change in this guide, approved files keep their names. You do not need to re-approve anything.

## For AI coding agents

Apply the sections below in order. For each section:

1. Run its search command from the root of the project.
2. Rewrite each match as shown in **After**.
3. Build and run the tests. They should pass without new `.received.` files.

When you are done, compile with deprecation warnings on (`-Xlint:deprecation` for javac).
There should be no deprecation warnings left that come from `org.approvaltests`. Then you are ready for 32.0.0.

## Contents

1. [NamerFactory and NamedEnvironment](#namerfactory-and-namedenvironment)
2. [Machine specific images](#machine-specific-images)
3. [Custom file names: withBaseName and withName](#custom-file-names-withbasename-and-withname)
4. [NamerWrapper](#namerwrapper)
5. [Reading the name or directory from an ApprovalNamer](#reading-the-name-or-directory-from-an-approvalnamer)
6. [Implementing ApprovalNamer](#implementing-approvalnamer)
7. [Approvals.namerCreater](#approvalsnamercreater)

## NamerFactory and NamedEnvironment

`NamerFactory` kept naming information in a thread-local inside a `try` block. `Options` now carries it with each `verify` call.

Search:

```sh
rg -n "NamerFactory\.|NamedEnvironment|MultipleFilesLabeller|ApprovalResults" --type java
```

| Before | After |
|---|---|
| `try (NamedEnvironment en = NamerFactory.withParameters(a, b)) { Approvals.verify(r); }` | `Approvals.verify(r, Approvals.NAMES.withParameters(a, b));` |
| `try (NamedEnvironment en = NamerFactory.asOsSpecificTest()) { ... }` | `Approvals.verify(r, Approvals.NAMES.asOsSpecificTest());` |
| `try (NamedEnvironment en = NamerFactory.asMachineNameSpecificTest()) { ... }` | `Approvals.verify(r, Approvals.NAMES.asMachineNameSpecificTest());` |
| `NamerFactory.asMachineSpecificTest("label")` | `new Options().forFile().withAdditionalInformation("label")` |
| `NamerFactory.asMachineSpecificTest(labeller)` | `new Options().forFile().withAdditionalInformation(labeller.call())` |
| `NamerFactory.setAdditionalInformation(info)` | `new Options().forFile().withAdditionalInformation(info)` |
| `NamerFactory.getAdditionalInformation()` | `options.forFile().getAdditionalInformation()` |
| `NamerFactory.isEmpty()` | `options.forFile().getAdditionalInformation().isEmpty()` |
| `NamerFactory.useMultipleFiles()` / `NamerFactory.ApprovalResults` | `FileCounter counter = Approvals.NAMES.useMultipleFiles();` then `Approvals.verify(r, counter.next());` for each file |
| `if (!environment.isCurrentEnvironmentValidFor("MACHINE")) return;` | `assumeTrue(SystemUtils.getComputerName().equals("MACHINE"));` |

Remove the `try` block and pass the `Options` to `verify`. If you already pass `Options`, chain from it: `options.forFile().withAdditionalInformation(...)`.

More examples: [How to update from NamerFactory to Options](UpdateFromNamerFactoryToOptions.md#top).

`NamerFactory.getSubdirectory()` and `NamerFactory.getApprovalBaseDirectory()` are not deprecated.

## Machine specific images

Search:

```sh
rg -n "ImageApprovalWriter\.asJreAware\(\)" --type java
```

Before:

```java
try (NamedEnvironment en = ImageApprovalWriter.asJreAware())
{
  AwtApprovals.verify(image);
}
```

After:

```java
AwtApprovals.verify(image, ImageApprovalWriter.asJreAware(new Options()));
```

## Custom file names: withBaseName and withName

Use a templated namer. The template fields are listed in [TemplatedCustomNamer](../reference/Options.md#file-name).

Search:

```sh
rg -n "withBaseName\(|withName\(" --type java
```

Before:

```java
new Options().forFile().withBaseName("my_name")
new Options().forFile().withName("my_name", ".json")
```

After:

```java
String template = "{test_source_directory}/{approvals_subdirectory}/my_name.{approved_or_received}.{file_extension}";
new Options().forFile().withNamer(TemplatedCustomNamer.create(template))
new Options().forFile().withNamer(TemplatedCustomNamer.create(template)).forFile().withExtension(".json")
```

Call `TemplatedCustomNamer.create` from inside the test method, because it reads the test name from the call stack.

## NamerWrapper

Search:

```sh
rg -n "NamerWrapper" --type java
```

- `new NamerWrapper(namer)` used as a pass-through: use `namer` directly.
- `new NamerWrapper(() -> "name", namer)` to change the name: use a templated namer, as in [Custom file names](#custom-file-names-withbasename-and-withname).
- `namerWrapper.getApprovalFile(ext)`: use `getApprovedFile(ext)`.

## Reading the name or directory from an ApprovalNamer

`ApprovalNamer.getApprovalName()` and `ApprovalNamer.getSourceFilePath()` are deprecated, along with the `GetApprovalName` and `GetSourceFilePath` interfaces.
In 32.0.0 an `ApprovalNamer` has only `getApprovedFile` and `getReceivedFile`.

Search:

```sh
rg -n "\.getApprovalName\(\)|\.getSourceFilePath\(\)|GetApprovalName|GetSourceFilePath" --type java
```

| Before | After |
|---|---|
| `namer.getApprovalName()` | `NamerUtils.getApprovalName(namer)` |
| `namer.getSourceFilePath()` | `NamerUtils.getSourcePath(namer)` (ends with a file separator) |
| `namer.getSourceFilePath() + "MyTest.java"` | `new File(namer.getApprovedFile(".txt").getParentFile(), "MyTest.java")` |
| `assertEquals("MyTest.myMethod", namer.getApprovalName())` | `assertEquals("MyTest.myMethod.approved.txt", namer.getApprovedFile(".txt").getName())` |

## Implementing ApprovalNamer

If you have your own class that `implements ApprovalNamer`, keep `getApprovedFile` and `getReceivedFile`.
`getApprovalName` and `getSourceFilePath` are still required on 31.3.0, but nothing in ApprovalTests calls them any more.
Make sure no code of yours calls them (see the section above). In 32.0.0, delete them and their `@Override` annotations.

If your namer only changes the file name, replace it with `TemplatedCustomNamer.create(template)`.

## Approvals.namerCreater

The global `Approvals.namerCreater` is deprecated. Set the namer creator on `Options` instead, so it applies only where you pass it.

Search:

```sh
rg -n "namerCreater" --type java
```

Before:

```java
Approvals.namerCreater = info -> new MyNamer(info);
Approvals.verify(result);
```

After:

```java
Approvals.verify(result, new Options().forFile().withNamer(info -> new MyNamer(info)));
```

The creator gets the additional information from the `Options` (for example `".param1.param2"`) and returns a new namer for that `verify`.

---

[Back to User Guide](/approvaltests/docs/README.md#top)
