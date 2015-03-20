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

Get access to the plugin:

`apply plugin: 'se.mtm.artifactory-rpm'`

Add it to the build script:

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

Configure it:

```Gradle
artifactoryRpm {
    developmentRepo = "mtm-utv"
    purgeRepos = ["mtm-test", "mtm-dev"]
}
```

The properties that can be set are

* String repositoryServerUrl
* String developmentRepo
* String promotionRepo
* String[] purgeRepos
* String distributionDir
* int generationsToKeep

The truth is defined in `src/main/java/se/mtm/gradle/defaults/ArtifactoryRpmPluginDefaults`

## Developing

Build

`./gradlew build install`

## Resources

[Artifactory REST Api](http://www.jfrog.com/confluence/display/RTF/Artifactory+REST+API)

[JFrog project examples on GitHub](https://github.com/JFrogDev/project-examples)

[Gradle, Writing Custom Plugins](https://gradle.org/docs/current/userguide/custom_plugins.html)

