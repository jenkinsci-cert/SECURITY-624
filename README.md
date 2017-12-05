# About

See: https://jenkins.io/security/advisory/2017-12-05/

# Building
Building requires maven 3.1.0+. You can download a binary at https://maven.apache.org/download.cgi if you cannot install this via your pacakage manager. If you use a binary, simply issue `apache-maven-<VERSION>/bin/mvn` instead below.

```
git clone https://github.com/jenkinsci-cert/security624
cd security624

mvn clean package
<truncated>
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 01:13 min
[INFO] Finished at: 2017-12-05T08:09:35-05:00
[INFO] Final Memory: 58M/749M
[INFO] ------------------------------------------------------------------------
<truncated>
```

Once the package is built, you will see the .hpi plugin in the target folder that you can use to install manually via the WebUI or via $JENKINS_HOME.

```
$ ls target/*.hpi
target/tool-name-filter.hpi
```
