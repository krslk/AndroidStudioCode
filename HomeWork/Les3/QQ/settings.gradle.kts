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
        google()
        mavenCentral()
    }
}

rootProject.name = "QQ"
include(":app")
include(":fragment")
include(":fragment2")
include(":activitysendinfotofragment")
include(":fragmentandviewpage")
include(":fragmentandviewpage2")
