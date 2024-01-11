<a id="top"></a>

# How to make a BDD Scenario using Approvals
<!-- toc -->
## Contents

* [Introduction](#introduction)
* [Sample Code](#sample-code)<!-- endToc -->

## Introduction
The idea is that you will end up with a descriptive approved file that reads like a BDD Scenario. You can show this to your less-technical collaborators, and they should be able to understand what scenario is being tested without needing to read the test sourcecode.

It doesn't use Gherkin strictly, but you still get a scenario with a name, description, and Given, When, Then steps.

## Sample Code

This test case uses a PrintableScenario:

<!-- snippet: bdd_test -->

<!-- endSnippet -->

* Construct the scenario at the beginning of the test with a descriptive name and summary.
* When you are done with the code for the "given" step of the test, call story.given() with your list of Printables. Use one printable to wrap each object you have created which you think might change state during the test and that you want to verify.
* In each "When" step of your test, call when() with a descriptive name for what the user is doing. The second argument is a lambda that contains code to actually do the when step. This is optional, you can write it in your test as normal code before the call to 'when' if you prefer.
* The "Then" step is a call to then() which will verify the state of all the Printables as it changed throughout the course of the test.

This produces a feature file like this one: [[PrintableScenarioTest.printBDDScenario.approved.txt](../../../Fapprovaltests-tests/src/test/javavorg/approvaltests/bdd/PrintableScenarioTest.printBDDScenario.approved.txt)]

Note - if you don't want to use the BDD terminology you don't have to. You can equally well use 'arrange', 'act', 'print' instead of 'given' 'when' 'then'

