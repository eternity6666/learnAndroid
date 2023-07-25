plugins {    id("com.android.application")    id("kotlin-android")    id("kotlin-kapt")}android {    compileSdk = 33    defaultConfig {        applicationId = "com.yzh.demoapp"        minSdk = 24        targetSdk = 33        versionCode = 1        versionName = "1.0"        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"        vectorDrawables {            useSupportLibrary = true        }    }    buildTypes {        getByName("release") {            proguardFiles(                getDefaultProguardFile("proguard-android-optimize.txt"),                "proguard-rules.pro"            )        }    }    buildFeatures {        compose = true        viewBinding = true    }    composeOptions {        kotlinCompilerExtensionVersion = "1.5.0"    }    compileOptions {        sourceCompatibility = JavaVersion.VERSION_1_8        targetCompatibility = JavaVersion.VERSION_1_8    }    kotlinOptions {        jvmTarget = "1.8"    }    packaging {        resources {            excludes += "/META-INF/{AL2.0,LGPL2.1}"        }    }    namespace = "com.yzh.demoapp"}dependencies {    implementation(libs.androidxAppCompat)    implementation(libs.androidxConstraintLayout)    implementation(libs.activityCompose)    implementation(libs.composeUI)    implementation(libs.composeUIUnit)    implementation(libs.composeMaterial3)    implementation(libs.cameraxCore)    implementation(libs.cameraxCamera2)    implementation(libs.cameraxLifecycle)    implementation(libs.cameraxVideo)    implementation(libs.cameraxView)    implementation(libs.cameraxExtensions)    implementation(libs.kotlinStdlib)    implementation(libs.material)    implementation(libs.kotlinCore)    implementation(libs.flexboxlayout)    implementation(libs.recyclerview)    implementation(libs.okhttp)    implementation(libs.retrofit)    implementation(libs.converterGson)    implementation(libs.navigationCompose)    implementation(libs.composeFoundation)    implementation(libs.lifecycleRuntimeKtx)    implementation(libs.coil)    implementation(project(":data"))    implementation(project(":program:calculator"))    implementation(project(":demo:custom-view"))    implementation(project(":demo:card-view"))    implementation(project(":demo:recycler-view"))    implementation(project(":base:extension"))    implementation(project(":base:resources"))    implementation(project(":base:annotation"))    testImplementation(libs.junit)    androidTestImplementation(libs.extJunit)    androidTestImplementation(libs.espressoCore)    androidTestImplementation(libs.composeUiTestJunit4)    debugImplementation(libs.composeUiTooling)    debugImplementation(libs.composeUiToolingPreview)    implementation(kotlin("reflect"))}