# Current Process

1. open the [releases page](https://github.com/approvals/ApprovalTests.Java/releases/new)
2. fill in the information for title and description
3. click "Publish release"
4. GitHub actions do the rest
5. check 
   1. the action succeeded (https://github.com/approvals/ApprovalTests.Java/actions/workflows/release.yml) (this takes ~3 minutes)
   2. the release is available on Maven Central (https://search.maven.org/artifact/com.approvaltests/approvaltests) (takes usually ~30 minutes)
   

## Release requirements

- [ ] Need a username and password from sonatype account (token)
  - Environmental variables MAVEN_USERNAME_2025 and MAVEN_PASSWORD_2025
- [ ] We need a GPG key to sign the artifacts
  - Environmental variables GPG_PRIVATE_KEY and GPG_PASSPHRASE
  - You can test this with `internal_documentation/test_gpg_key.sh`
- You can set versions using 
```
mvn versions:set -DnewVersion=100.0.0-SNAPSHOT
```
- We only deploy `approvaltests` and `approvaltests-util`, the other projects do not need to be deployed
- You can check the status at
  - https://central.sonatype.com/publishing/deployments
    - Note: You must be logged in as Llewellyn.
  - https://repo1.maven.org/maven2/com/approvaltests/approvaltests/
  - https://central.sonatype.com/artifact/com.approvaltests/approvaltests
