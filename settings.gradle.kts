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

rootProject.name = "LifecycleComponentTemplate"
include(":app")
include(":main")
include(":foobar", ":foobar-api", ":sub-foo", ":sub-bar")

