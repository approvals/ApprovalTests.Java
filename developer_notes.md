<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Contents**

- [ApprovalTests.Java](#approvaltestsjava)
  - [What can it be used for?](#what-can-it-be-used-for)
  - [Documentation](#documentation)
  - [Getting started](#getting-started)
  - [How to get it](#how-to-get-it)
  - [Video Tutorials](#video-tutorials)
  - [Podcasts](#podcasts)
  - [Examples](#examples)
  - [Approved File Artifacts](#approved-file-artifacts)
  - [More Info](#more-info)
  - [LICENSE](#license)
  - [Questions?](#questions)
  - [Developer notes](#developer-notes)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->


# Developer notes

To build with Maven:

	mvn install

If you see test failures and want to carry on anyway (Some tests are machine or locale dependent unfortunately):

	mvn install -DskipTests

If you have trouble with the "mrunit" package which is listed on Maven central but doesn't seem to download, install it locally with this command:

	mvn install:install-file -Dfile=missing_jars/mrunit-0.9.0-incubating-hadoop1.jar -DgroupId=org.apache.mrunit -DartifactId=mrunit -Dversion=0.9.0-incubating -Dpackaging=jar
