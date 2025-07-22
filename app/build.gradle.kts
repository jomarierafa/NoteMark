import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.jvrcoding.notemark"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.jvrcoding.notemark"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        val userEmail = gradleLocalProperties(rootDir, providers).getProperty("USER_EMAIL")
        debug {
            buildConfigField("String", "USER_EMAIL", "\"$userEmail\"")
            buildConfigField("String", "BASE_URL", "\"https://notemark.pl-coding.com\"")
        }

        release {
            buildConfigField("String", "USER_EMAIL", "\"$userEmail\"")
            buildConfigField("String", "BASE_URL", "\"https://notemark.pl-coding.com\"")

            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11

        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //splash screen
    implementation(libs.androidx.core.splashscreen)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.security.crypto.ktx)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.timber)

    implementation(libs.bundles.koin)
    implementation(libs.bundles.ktor)

    coreLibraryDesugaring(libs.desugar.jdk.libs)

    //room
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    implementation(libs.room.paging)
    ksp(libs.room.compiler)

    //paging
    implementation(libs.paging.runtime)
    implementation(libs.paging.compose)

    // work
    implementation(libs.androidx.work)

    //datastore
    implementation(libs.androidx.datastore.preferences)
}