# A Gradle plugin for maintaining RPMs in Artifactory

## Purposes

The purpose is to simplify RPM handling in Artifactory.

* Deploy RPMs to Artifactory
* Promote artifacts from one repo to another
* Purge old RPMs to avoid filling the disk in Artifactory

## Usage

Use the tasks described below in your build.

### Tasks

These task are available

* promoteToStageRepo - deploy an rpm to a staging repository
* promoteToUtvRepo - promote the latest artifact from the staging repo to the utv repo. Utv is Swedish for dev.
* promoteToTestRepo - promote the latest artifact from the utv repo to the test repo 
* promoteToProdRepo - promote the latest artifact from the test repo to the production repo 

### Configuration

Get access to the plugin:

```Gradle
apply plugin: 'se.mtm.artifactory-rpm'

buildscript {
    repositories {
        mavenLocal()
    }
    dependencies {
        classpath 'se.mtm.gradle:gradle-artifactory-rpm-plugin:1.0.20'
    }
}
```

The version number, `1.0.20`, is obsolete. Update it with the latest release.

Configure it:

```Gradle
artifactoryRpm {
    packageName = 'filibuster'         
}
```

The properties that can be set are

* repositoryServerUrl - your artifactory host. Defaults to http://artifactory.mtm.se:8081/artifactory
* stageRepo - the repository where an RPM is uploaded first time. Defaults to mtm-staging          
* utvRepo - the repository where an RPM will be promoted to from the stage repo. Defaults to mtm-utv             
* testRepo - the repository where an RPM will be promoted to from the utv repo. Defaults to mtm-test
* prodRepo - the repository where an RPM will be promoted to from the test repo. Defaults to mtm-production
* packageName - the name of the RPM without version, release or architecture. Defaults to the Gradle project name         
* generationsToKeep - The number of generations of the package that will be kept on each promotion. Defaults to 3

The truth is defined in [src/main/java/se/mtm/gradle/extensions/ArtifactoryRpmPluginDefaults](https://github.com/mtmse/gradle-artifactory-rpm-plugin/blob/master/src/main/java/se/mtm/gradle/extensions/ArtifactoryRpmPluginDefaults.java)

### Environment variables

The plugin need access to a user credentials for communicating with Artifactory. These should be set as environment variables.
Set

* ARTIFACTORY_USER
* ARTIFACTORY_PASSWORD

to the user and password you use for uploading artifacts to Artifactory. If you are using Jenkins, then set them
as environment variables in Jenkins. You find the settings by following these menus items:

`Jenkins | Manage Jenkins | Configure System | Environment variables`

### Simplify usage

Usage of a Gradle tasks can be simplified in two ways. Either minimize the available tasks and allow the users to configure the plugin. Or limit the number of ways to a task can be configured and add more tasks that defaults to something resonable. This plugin has been developed with the latter approach. More tasks and less configuration.

## Why a stage repo?

The process forces you to upload to a stage repository and promote the artifacts from there. Whys this rigid default process? The argument is that it will allow you to install the artifact in an envorionemt before you promote the artifact to the utv repository. Suppose that you have some integration test environemt where you want to install the package in before trying to install it in an environment where other users may use it. The integration test installation need a source to read the package from and the stage repository is that source.

## Developing

Build

`./gradlew clean build publishToMavenLocal --daemon`

## Resources

[Artifactory REST Api](http://www.jfrog.com/confluence/display/RTF/Artifactory+REST+API)

[JFrog project examples on GitHub](https://github.com/JFrogDev/project-examples)

[Gradle, Writing Custom Plugins](https://gradle.org/docs/current/userguide/custom_plugins.html)

