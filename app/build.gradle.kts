plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.google.firebase.crashlytics)
}

android {
    namespace = "com.example.vsp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.vsp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }


    buildFeatures{
        viewBinding = true
    }



}

dependencies {



    implementation ("com.github.bumptech.glide:glide:4.16.0")





    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)
    implementation("com.hbb20:ccp:2.7.0")
    



    implementation(libs.play.services.auth)
    implementation (libs.material.v160)
    implementation (libs.github.glide)
    implementation(libs.firebase.storage)

    implementation (libs.firebase.storage.v2001)


    implementation (libs.glide.v4120)




    implementation(libs.firebase.firestore)
    implementation(libs.firebase.crashlytics)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)




        // Import the BoM for the Firebase platform
        implementation(platform("com.google.firebase:firebase-bom:33.1.2"))

        // Add the dependencies for the App Check libraries
        // When using the BoM, you don't specify versions in Firebase library dependencies
        implementation("com.google.firebase:firebase-appcheck-debug")
    


    implementation ("com.google.firebase:firebase-appcheck-safetynet:16.0.0")
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.16.0")
//
//    implementation ("com.github.bumptech.glide:glide:4.12.0")
//    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")


    implementation ("androidx.lifecycle:lifecycle-common-java8:$2.5.0")


    implementation("androidx.recyclerview:recyclerview:1.3.2") // Use the latest version



    implementation ("com.google.android.gms:play-services-maps:19.0.0")

    implementation("com.google.android.libraries.places:places:3.5.0")


    implementation ("com.google.firebase:firebase-database:20.0.5") // Or latest version
     // Or latest version










}