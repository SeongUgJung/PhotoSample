pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenLocal()
        google()
        mavenCentral()
        maven {
            setUrl("https://jitpack.io")
            content {
                includeGroupByRegex("com\\.github\\.android-alatan.*")
            }
        }

    }
}
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "PhotoSample"
include(":app")
include(":main")
include(":foobar", ":foobar-api")
include(":value-holder")

