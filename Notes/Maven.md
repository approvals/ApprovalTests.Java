Deploy to maven:
* Deploy snapshot
 * Run
 ```
 ./publish_maven.sh
 ```
   * Password is in 1Password (search approvaltests)
 * Check on Maven
  * https://oss.sonatype.org/content/repositories/snapshots/com/approvaltests
  * https://mvnrepository.com/artifact/com.approvaltests/approvaltests/
* Deploy release  
 * Remove -SNAPSHOT in all the pox.xml Files
 ``` bash
 mvn versions:set -DnewVersion=6.0.1-SNAPSHOT 
 ```

* Run
```
./publish_maven.sh
```
  * check here: https://oss.sonatype.org/content/repositories/releases/com/approvaltests/approvaltests/
  * update sample code for maven in README.md
  * update the starter project
  * update koans (turn to maven please)
  * Update to next version
  * bump version and add -SNAPSHOT in all the pox.xml Files

