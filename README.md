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

How are you expected to use the plugin?

### Tasks

These task are available

* deployRpm - deploy an rpm to a staging repository
* promoteRpm - promote an rpm from the staging repository
* purgeOldRpms - remove old rpms, defaults to keep the latest

### Configuration

Get access to the plugin:

```Gradle
apply plugin: 'se.mtm.artifactory-rpm'

buildscript {
    repositories {
        mavenLocal()
    }
    dependencies {
        classpath 'se.mtm.gradle:gradle-artifactory-rpm-plugin:1.0.5'
    }
}
```

The version number, `1.0.5`, is obsolete. Update it with the latest release.

Configure it:

```Gradle
artifactoryRpm {
    stagingRepo = "mtm-utv"
    purgeRepos = ["mtm-test", "mtm-dev"]
}
```

The properties that can be set are

* String repositoryServerUrl - your Artifactory host
* String stagingRepo - a staging repository where artifacts that haven't passed automated tests yet calls home
* String promotionRepo - a repository to keep artifacts that have passed automated tests
* String[] purgeRepos - an array of repositories that should be visited when purging old artifacts
* String distributionDir - the directory where the rpm is stored after executing `buildRpm`
* int generationsToKeep - how many old artifacts should be kept when purging old rpm

The truth and default values are defined in [src/main/java/se/mtm/gradle/extensions/ArtifactoryRpmPluginDefaults](https://github.com/mtmse/gradle-artifactory-rpm-plugin/blob/master/src/main/java/se/mtm/gradle/extensions/ArtifactoryRpmPluginDefaults.java)

### Environment variables

The plugin need access to a user credentials for communicating with Artifactory. These should be set as environment variables.
Set

* ARTIFACTORY_USER
* ARTIFACTORY_PASSWORD

to the user and password you use for uploading artifacts to Artifactory. If you are using Jenkins, then set them
as environment variables in Jenkins. You find the settings by following these menus items:

`Jenkins | Manage Jenkins | Configure System | Environment variables`

## Developing

Build

`./gradlew build install`

## Resources

[Artifactory REST Api](http://www.jfrog.com/confluence/display/RTF/Artifactory+REST+API)

[JFrog project examples on GitHub](https://github.com/JFrogDev/project-examples)

[Gradle, Writing Custom Plugins](https://gradle.org/docs/current/userguide/custom_plugins.html)

