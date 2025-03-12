plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.navigation.safe.args)
    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.example.e_commercenativexml"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.e_commercenativexml"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }


    packaging {
        resources {
            excludes += listOf(
                "META-INF/gradle/incremental.annotation.processors",
                "META-INF/DEPENDENCIES",
                "META-INF/LICENSE",
                "META-INF/LICENSE.txt",
                "META-INF/NOTICE",
                "META-INF/NOTICE.txt",
                "META-INF/AL2.0",
                "META-INF/LGPL2.1"
            )
        }
    }




}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    //hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    //room
    implementation(libs.androidx.room.runtime)
    implementation(libs.play.services.analytics.impl)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    //retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)

    //Lottie
    implementation(libs.lottie)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


}