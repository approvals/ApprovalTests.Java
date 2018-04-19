ant "Publish    ApprovalTests" -buildfile build/build.xml
jar tf target/ApprovalTests.jar
jar tf target/ApprovalTests.020.zip
