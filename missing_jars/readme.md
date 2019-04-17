
If you have trouble with the "mrunit" package which is listed on Maven central but doesn't seem to download, install it locally with this command:

	mvn install:install-file -Dfile=missing_jars/mrunit-0.9.0-incubating-hadoop1.jar -DgroupId=org.apache.mrunit -DartifactId=mrunit -Dversion=0.9.0-incubating -Dpackaging=jar
