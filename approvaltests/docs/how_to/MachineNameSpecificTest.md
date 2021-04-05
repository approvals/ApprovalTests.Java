<a id="top"></a>

# How to use NamerFactory.asMachineNameSpecificTest() in Parameterized Tests

<!-- toc -->
<!-- endToc -->

Sometimes you will want tests to only run on certain machines. You can do this by prefixing your
test with

snippet: runOnlyOnSpecificMachines

**please note:** this will also append the machine name to the ApprovalTest so that each specific
machine gets its own `.approved` file.
