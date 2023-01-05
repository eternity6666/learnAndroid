enableFeaturePreview("VERSION_CATALOGS")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            from(files("libs.versions.toml"))
        }
    }
}
rootProject.name = "learnAndroid"
include(
    ":app",
    ":program",
    ":program:calculator",
    ":demo",
    ":demo:custom-view",
    ":demo:card-view",
    ":demo:recycler_view",
    ":data",
    ":base",
    ":base:extension",
    ":base:resources",
)

