Deploy to maven:
* Deploy snapshot
 * Run publish_maven.sh
   * Password is in 1Password
 * Check on Maven
  * https://oss.sonatype.org/content/repositories/snapshots/com/approvaltests
  * https://mvnrepository.com/artifact/com.approvaltests/approvaltests/
* Deploy release  
 * Remove -SNAPSHOT in all the pox.xml Files
  * approvaltests-hadoop/pom.xml
  * approvaltests-util/pom.xml
  * approvaltests/pom.xml
  * counter_display/pom.xml
  * html_locker/pom.xml
  * pom.xml
* Run publish_maven.sh
  * check here: https://oss.sonatype.org/content/repositories/releases/com/approvaltests/approvaltests/
  * update sample code for maven in README.md
* Update to next version
 * bump version and add -SNAPSHOT in all the pox.xml Files
