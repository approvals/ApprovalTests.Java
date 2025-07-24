# Release Process

1. Create a release on GitHub (https://github.com/approvals/ApprovalTests.Java/releases/new)

GitHub workflows will do the rest.

## Troubleshooting

- We only deploy `approvaltests` and `approvaltests-util`, the other projects do not need to be deployed

### Checking status

- Check that [the GitHub workflow](https://github.com/approvals/ApprovalTests.Java/actions/workflows/release.yml) succeeded. (Typically ~3 minutes)
- Check that the release is available on Maven Central (https://search.maven.org/artifact/com.approvaltests/approvaltests). (Typically ~30 minutes)
- https://central.sonatype.com/publishing/deployments
  - Note: You must be logged in as Llewellyn.
- https://repo1.maven.org/maven2/com/approvaltests/approvaltests/
- https://central.sonatype.com/artifact/com.approvaltests/approvaltests

### Configuration

- Need a username and password from sonatype account (token)
  - Environment variables `MAVEN_USERNAME_2025` and `MAVEN_PASSWORD_2025`
- Need a GPG key to sign the artifacts
  - Environmental variables `GPG_PRIVATE_KEY` and `GPG_PASSPHRASE`
  - You can test this with `internal_documentation/test_gpg_key.sh`
- You can set versions using 
```sh
mvn versions:set -DnewVersion=100.0.0-SNAPSHOT
```

