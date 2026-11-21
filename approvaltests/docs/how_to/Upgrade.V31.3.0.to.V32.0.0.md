<a id="top"></a>

# Upgrade from 31.3.0 to 32.0.0

32.0.0 removes the APIs that were deprecated in 31.3.0. There are no other behavior changes.
After every change in this guide, approved files keep their names. You do not need to re-approve anything.

## For AI coding agents

1. **Do this while still on 31.3.0.** Apply [Upgrade from 31.2.0 to 31.3.0](Upgrade.V31.2.0.to.V31.3.0.md#top) first. On 31.3.0 the old APIs still compile, and the compiler's deprecation warnings point at every call site.
2. Compile with `-Xlint:deprecation`. Continue only when there are no deprecation warnings from `org.approvaltests`.
3. Change the dependency version to `32.0.0`.
4. Compile. Use the table below to fix any remaining errors.
5. Run the tests. They should pass without new `.received.` files.

## Removed

| Removed | Use instead | Section in the 31.3.0 guide |
|---|---|---|
| `NamerFactory.withParameters(...)` | `Approvals.NAMES.withParameters(...)` | [NamerFactory and NamedEnvironment](Upgrade.V31.2.0.to.V31.3.0.md#namerfactory-and-namedenvironment) |
| `NamerFactory.asOsSpecificTest()` | `Approvals.NAMES.asOsSpecificTest()` | same |
| `NamerFactory.asMachineNameSpecificTest()` | `Approvals.NAMES.asMachineNameSpecificTest()` | same |
| `NamerFactory.asMachineSpecificTest(...)` | `new Options().forFile().withAdditionalInformation(...)` | same |
| `NamerFactory.setAdditionalInformation`, `getAdditionalInformation`, `getAndClearAdditionalInformation`, `isEmpty` | `options.forFile().withAdditionalInformation(...)` / `getAdditionalInformation()` | same |
| `NamerFactory.useMultipleFiles()`, `NamerFactory.ApprovalResults`, `ApprovalResults`, `MultipleFilesLabeller` | `Approvals.NAMES.useMultipleFiles()` and `counter.next()` | same |
| `NamedEnvironment` and `isCurrentEnvironmentValidFor` | Pass `Options` to `verify`; use `assumeTrue(...)` to skip | same |
| `ImageApprovalWriter.asJreAware()` | `ImageApprovalWriter.asJreAware(options)` | [Machine specific images](Upgrade.V31.2.0.to.V31.3.0.md#machine-specific-images) |
| `Options.forFile().withBaseName(name)` | `withNamer(TemplatedCustomNamer.create(template))` | [Custom file names](Upgrade.V31.2.0.to.V31.3.0.md#custom-file-names-withbasename-and-withname) |
| `Options.forFile().withName(name, ext)` | `withNamer(TemplatedCustomNamer.create(template)).forFile().withExtension(ext)` | same |
| `NamerWrapper` | The namer itself, or `TemplatedCustomNamer.create(template)` | [NamerWrapper](Upgrade.V31.2.0.to.V31.3.0.md#namerwrapper) |
| `ApprovalNamer.getApprovalName()`, `GetApprovalName` | `NamerUtils.getApprovalName(namer)` | [Reading the name or directory](Upgrade.V31.2.0.to.V31.3.0.md#reading-the-name-or-directory-from-an-approvalnamer) |
| `ApprovalNamer.getSourceFilePath()`, `GetSourceFilePath` | `NamerUtils.getSourcePath(namer)` | same |
| `Approvals.namerCreater` | `Options.forFile().withNamer(info -> namer)` | [Approvals.namerCreater](Upgrade.V31.2.0.to.V31.3.0.md#approvalsnamercreater) |

`NamerFactory.getSubdirectory()` and `NamerFactory.getApprovalBaseDirectory()` remain.

## Your own ApprovalNamer

`ApprovalNamer` now has only two methods:

```java
public interface ApprovalNamer
{
  File getApprovedFile(String extensionWithDot);

  File getReceivedFile(String extensionWithDot);
}
```

If your class implements it, delete its `getApprovalName()` and `getSourceFilePath()` methods.
If they are still marked `@Override`, the compiler reports "method does not override or implement a method from a supertype" until you remove them.

## Search for anything left over

Run this from the root of your project. It should find nothing:

```sh
rg -n "NamedEnvironment|MultipleFilesLabeller|ApprovalResults|NamerWrapper|GetApprovalName|GetSourceFilePath|namerCreater|withBaseName\(|withName\(|asJreAware\(\)|NamerFactory\.(with|as|use|set|isEmpty|getAdditional|getAndClear|ApprovalResults)" --type java
```

---

[Back to User Guide](/approvaltests/docs/README.md#top)
