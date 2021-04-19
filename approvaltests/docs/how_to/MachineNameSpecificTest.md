<a id="top"></a>

# How to run tests only on specific machines

<!-- toc -->
<!-- endToc -->

Sometimes you will want tests to only run on certain machines. You can do this by prefixing your
test with

<!-- snippet: runOnlyOnSpecificMachines -->
<a id='snippet-runonlyonspecificmachines'></a>
```java
try (NamedEnvironment namedEnvironment = NamerFactory.asMachineNameSpecificTest())
{
  if (!namedEnvironment.isCurrentEnvironmentValidFor("Larss-Air.lan", "macbook13", "Larss-MacBook-Air.local"))
  { return; }
  // the rest of your test...
```
<sup><a href='/approvaltests/src/test/java/org/approvaltests/reporters/intellij/IntelliJPathResolverTest.java#L19-L25' title='Snippet source file'>snippet source</a> | <a href='#snippet-runonlyonspecificmachines' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

**please note:** this will also append the machine name to the ApprovalTest so that each specific
machine gets its own `.approved` file.
