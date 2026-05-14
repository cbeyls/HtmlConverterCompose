plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "be.digitalia.compose.htmlconverter.sample"
    compileSdk = 36

    defaultConfig {
        applicationId = "be.digitalia.compose.htmlconverter.sample"
        minSdk = 21
        targetSdk = 36
        versionCode = 1
        versionName = "1.0.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"))
        }
        packaging {
            resources {
                excludes += listOf(
                    "DebugProbesKt.bin",
                    "kotlin-tooling-metadata.json",
                    "kotlin/**",
                    "META-INF/*.version"
                )
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(project(":sample"))

    implementation(compose.preview)
    implementation(libs.androidx.activity.compose)
    debugImplementation(compose.uiTooling)
}
