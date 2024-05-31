plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
    id("kotlin-parcelize")
    alias(libs.plugins.ktlint)
}

android {
    namespace = "com.kom.skyfly"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.kom.skyfly"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    flavorDimensions += "env"
    productFlavors {
        create("production") {
            buildConfigField(
                type = "String",
                name = "BASE_URL",
                value = "\"https://backend-skyfly-c1.vercel.app/\"",
            )
            buildConfigField(
                type = "String",
                name = "API_TOKEN",
                value = "\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6Ijc1YjBjYTcwLWQ4N2YtNDAwOS04YzVmLWU4N2ZjMjg3NjE4ZCIsInVzZXJuYW1lIjoic2kgYWRtaW4iLCJyb2xlIjoiYWRtaW4iLCJlbWFpbCI6ImFkbWluQG1haWwuY29tIiwiY29tcGFueUlkIjoiMzk5NzU2OGQtMWMxZi00ZGE5LTkwYTgtMzUyMGUyN2E0OWJhIiwiaWF0IjoxNzEzMzYwNTcwLCJleHAiOjE3MTMzNjQxNzB9.ayp8KKV8L2Y45Z-RR1wrEqM14XoAitLn20XGaJhgwNA\"",
            )
        }
        create("integration") {
            buildConfigField(
                type = "String",
                name = "BASE_URL",
                value = "\"https://backend-skyfly-c1.vercel.app/\"",
            )
            buildConfigField(
                type = "String",
                name = "API_TOKEN",
                value = "\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6Ijc1YjBjYTcwLWQ4N2YtNDAwOS04YzVmLWU4N2ZjMjg3NjE4ZCIsInVzZXJuYW1lIjoic2kgYWRtaW4iLCJyb2xlIjoiYWRtaW4iLCJlbWFpbCI6ImFkbWluQG1haWwuY29tIiwiY29tcGFueUlkIjoiMzk5NzU2OGQtMWMxZi00ZGE5LTkwYTgtMzUyMGUyN2E0OWJhIiwiaWF0IjoxNzEzMzYwNTcwLCJleHAiOjE3MTMzNjQxNzB9.ayp8KKV8L2Y45Z-RR1wrEqM14XoAitLn20XGaJhgwNA\"",
            )
        }
    }
}
tasks.getByPath("preBuild").dependsOn("ktlintFormat")

ktlint {
    android.set(false)
    ignoreFailures.set(true)
    reporters {
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
    }
    kotlinScriptAdditionalPaths {
        include(fileTree("scripts/"))
    }
    filter {
        exclude("**/generated/**")
        include("**/kotlin/**")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.recyclerview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

// Image loader
    implementation(libs.coil)

//    Navigation
    implementation(libs.lifecycle.livedata)
    implementation(libs.lifecycle.runtime)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.fragment.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    //  Local storage
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    // Coroutine
    implementation(libs.coroutine.core)
    implementation(libs.coroutine.android)

    // Dependency injection
    implementation(libs.koin.android)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Http Request
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp)
    implementation(libs.http.logging)

    // Unit Testing
    testImplementation(libs.mockk.agent)
    androidTestImplementation(libs.mockk.android)
    testImplementation(libs.coroutine.test)
    testImplementation(libs.turbine)
    testImplementation(libs.core.testing)

    // otp view
    implementation(libs.otp.view)

    // SeatBook View
    implementation(libs.seat.book)

    // App intro
    implementation(libs.app.intro)

    // Calendar View
    implementation(libs.calendar.view)

    // Core Library Desugaring
    coreLibraryDesugaring(libs.core.library.desugaring)

    // Groupie
    implementation(libs.groupie.view)
    implementation(libs.groupie.view.binding)

    implementation("androidx.webkit:webkit:1.8.0")
}
