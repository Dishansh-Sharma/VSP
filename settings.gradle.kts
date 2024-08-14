pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {

    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()

        mavenCentral()
        maven { url = uri("https://jitpack.io")

            artifactUrls ("http://repo.mycompany.com/jars")
            artifactUrls ("http://repo.mycompany.com/jars2")}
    }
}


rootProject.name = "VISP"
include(":app")
