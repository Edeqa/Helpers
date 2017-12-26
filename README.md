# Helpers

[![](https://jitpack.io/v/edeqa/helpers.svg)](https://jitpack.io/#edeqa/helpers)

Some simple helpers.

## How to add

### Gradle

Step 1. Add the JitPack repository in your root build.gradle at the end of repositories:

    allprojects {
        repositories {
            maven { url "https://jitpack.io" }
        }
    }

Step 2. Add the dependency in the app's build.gradle:

    dependencies {
        compile 'com.github.edeqa:helpers:1.2'
    }

### Maven

Step 1. Add the JitPack repository to your build file:

    <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>
    
Step 2. Add the dependency:

    <dependency>
        <groupId>com.github.edeqa</groupId>
        <artifactId>helpers</artifactId>
        <version>0.1</version>
    </dependency>

## How to use

See the [Javadoc](https://edeqa.github.io/Helpers/) to learn more about the API.

## History

1.2 - Misc.log, Misc.err; HtmlGenerator#build improved; HtmlGenerator.IMG

1.1 - Misc.toStringDeep

1.0 - fixes

0.1 - initial version

## License

Waytous Framework is licensed under an MIT license. See the `LICENSE` file for specifics.
