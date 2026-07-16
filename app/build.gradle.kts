plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    kotlin("plugin.serialization") version "2.0.0"
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.food.delivery"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.food.delivery"
        minSdk = 24
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
    buildToolsVersion = "36.0.0"
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.animation)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.runtime.saveable)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.material3)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.googleid)
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation(libs.firebase.firestore)
    //Glide
    implementation(libs.glide)
    implementation(libs.compose)

    implementation(libs.androidx.compose.material.icons.extended)

    // this ia for hillt dependency injection
//    implementation("com.google.dagger:hilt-android:2.50")
//    kapt("com.google.dagger:hilt-android-compiler:2.50")

//    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
//    kapt("androidx.hilt:hilt-compiler:1.2.0")

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    implementation(libs.hilt.navigation.compose)
    ksp(libs.androidx.hilt.compiler)


    // this is for coil
    implementation(libs.coil.compose)


    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Serialization
    implementation(libs.kotlinx.serialization.json)


    //this is for pager
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)


// this is for payment getway
    implementation(libs.checkout.razor.pay){
        exclude(group = "com.razorpay", module = "core")
        exclude(group = "com.razorpay", module = "standard-core")
    }


    //custem bottom nev bar
    implementation(libs.bottombar)

    //lottie for Place order dialog
    implementation(libs.lottie.compose)

    implementation(libs.accompanist.systemuicontroller)

    //splashScreen
    implementation(libs.androidx.core.splashscreen)


}