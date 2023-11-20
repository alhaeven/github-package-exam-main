# Github Package Registry Usage

### Reference
[Working with the Gradle registry](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-gradle-registry)

## Requirements
- Spring boot with gradle
- github access token

## Publishing a package

### gradle.properties
- Create gradle.properties with the following content in your Gradle user's home directory for global configuration properties.
  - windows path ; %USERPROFILE%/.gradle/gradle.properties
```properties
gpr.key=<REPLACE GITHUB ACCESS TOKEN>
gpr.user=<REPLACE GITHUB USERNAME>
```

### build.gradle
Set up your build.gradle file for publish
- Add the Gradle plugin listed below in the plugins section
```groovy
plugins {
  id 'java-library'
  id 'maven-publish'
}
```

- Add a publishing section and character configuration
```groovy
tasks.withType(JavaCompile).configureEach {
  options.encoding = "UTF-8"
}

publishing {
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/<OWNER>/<REPOSITORY>")
            credentials {
                username = property('gpr.user')
                password = property('gpr.key')
            }
        }
    }
    publications {
        bootJava(MavenPublication) {
            from components.java
            versionMapping {
                usage('java-api') {
                    fromResolutionOf('runtimeClasspath')
                }
                usage('java-runtime') {
                    fromResolutionResult()
                }
            }
        }
    }
}
```

- To include dependencies, change the 'implementation' to 'api' in the dependencies section.
```groovy
// exmaple
implementation 'org.json:json:20231013' -> api 'org.json:json:20231013'
```

### Publish the package, after gradle build
```text
gradle publish
```


## Installing a package

### build.gradle
Set up your build.gradle file

- Add a repositories section
```groovy
repositories {
  mavenCentral()
  maven {
    url = uri("https://maven.pkg.github.com/<OWNER>/<REPOSITORY>")
    credentials {
      username = project.findProperty("gpr.user") ?: System.getenv("USERNAME")
      password = project.findProperty("gpr.key") ?: System.getenv("TOKEN")
    }
  }
  ...maven blocks
}
```

- (Optional)Add the configuration below for the update package for your SNAPSHOT version
<br>
[Reference](https://docs.gradle.org/current/dsl/org.gradle.api.artifacts.ResolutionStrategy.html)
```groovy
configurations.all {
  // sets the length of time that dynamic versions will be cached.
  // Dynamic versions are those that use a version range or a wildcard to specify the dependency version, such as 2.+ or [1.0, 2.0)
  resolutionStrategy.cacheChangingModulesFor 0, 'seconds'
  // sets the length of time that changing modules will be cached.
  // Changing modules are those that have the same version but different content, such as SNAPSHOT versions or dependencies that are explicitly marked as changing = true
  resolutionStrategy.cacheDynamicVersionsFor 5, 'minutes'
}
```

- Add the package dependencies in the dependencies section
```groovy
dependencies {
  implementation '<groupId>:<artifactId>:<version>'

// example
  implementation 'com.example:github-package-exam-sub:+'
}
```

- Add scanBasePackages option to SpringBootApplication Annotation
```java
@SpringBootApplication(
        scanBasePackages = {
                "<groupId> or <java package path>",
                "com.example"
        }
)
public class Application {
  //...
}
```
