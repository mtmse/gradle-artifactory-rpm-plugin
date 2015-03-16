# A Gradle plugin for maintaining RPMs in Artifactory

## Purposes

There are two purposes with this plugin.

* Simplify uploading RPMs to Artifactory
* Purge old RPMs from Artifactory

Uploading RPMs should be done as an integrated part of the build. The solutions suggested from JFrog will upload the
artifacts with a group, an artifact name and a version in a Maven style. We want to upload the RPMs to a flat structure.

We also want to keep the number of RPMs down somewhat in order to preserve disk on the repository manager. Old RPMs
will be purged regularly keeping a few generations back but not all history.

## Usage

To be documented

## Developing

Build

`./gradlew build install`

## Resources

[Artifactory REST Api](http://www.jfrog.com/confluence/display/RTF/Artifactory+REST+API)

[JFrog project examples on GitHub](https://github.com/JFrogDev/project-examples)
