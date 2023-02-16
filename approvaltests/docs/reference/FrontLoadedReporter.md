<a id="top"></a>
# Front Loaded Reporters
<!-- toc -->
## Contents

* [Front Loaded Re](#verifying-a-variety-of-inputs)<!-- endToc -->

## What and Why

Front loaded reporters allow you to block all normal reporting behaviour depending on the environment.
This is useful in situations like running on a CI Machine, where you wouldn't want a reporter to open.

The `FrontLoadedReporter` is the mechanism to do that.
`FrontLoadedReporter`s implement `EnvironmentAwareReporter`, which means they are aware of the environment.
If they are in the correct environment, they override all other reporters, otherwise they pass through.

## The `DefaultFrontLoadedReporter`

Currently, the DefaultFrontLoadedReporter that comes with ApprovalTests is set up to handle:

snippet: default_front_loaded_reporter

## Links

* [Customize using package settings](PackageSettings.md#top)

