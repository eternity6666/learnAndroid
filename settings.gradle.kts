enableFeaturePreview("VERSION_CATALOGS")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven("https://maven.aliyun.com/repository/public")
        maven("https://maven.aliyun.com/repository/google")
        maven("https://maven.aliyun.com/repository/gradle-plugin")
        maven("https://maven.aliyun.com/repository/spring")
        maven("https://maven.aliyun.com/repository/central")
        maven("https://maven.aliyun.com/repository/spring-plugin")
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
    ":base:annotation",
)
