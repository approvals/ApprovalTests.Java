<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Contents**

- [Developer notes](#developer-notes)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->


# Developer notes

To build with Maven:

	mvn install

If you see test failures and want to carry on anyway (Some tests are machine or locale dependent unfortunately):

	mvn install -DskipTests

To avoid locale dependent test failures, you may execute tests with the en_US locale. 
This is one way to do it (in approvaltests-tests/pom.xml and approvaltests-util-tests/pom.xml):

    <plugin>
        <artifactId>maven-surefire-plugin</artifactId>
            <version>3.0.0-M5</version>
            <configuration>
                <argLine>-Duser.language=en -Duser.region=US</argLine>
            </configuration>
    </plugin>


If you have trouble with the "mrunit" package which is listed on Maven central but doesn't seem to download, install it locally with this command:

	mvn install:install-file -Dfile=missing_jars/mrunit-0.9.0-incubating-hadoop1.jar -DgroupId=org.apache.mrunit -DartifactId=mrunit -Dversion=0.9.0-incubating -Dpackaging=jar
