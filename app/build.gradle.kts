plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.mpd.pmdm.practicaroommodulos"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.mpd.pmdm.practicaroommodulos"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }
}

dependencies {
    val composeBom = platform("androidx.compose:compose-bom:2023.01.00")
    implementation(composeBom)
    // Material Design 3
    implementation("androidx.compose.material3:material3")
    //Para poder usar composables en Fragments
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    // Optional - Integration with LiveData
    implementation("androidx.compose.runtime:runtime-livedata")





    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.3.0")
    val room_version = "2.6.1"
    val lifecycle_version = "2.7.0"
    val fragment_version = "1.6.2"

    implementation("androidx.room:room-runtime:$room_version")
    // To use Kotlin Symbol Processing (KSP)
    ksp("androidx.room:room-compiler:$room_version")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")
    //Para poder usar coroutines en viewModelScope
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    //Para poder instanciar ViewModel dentro de fragmentos
    implementation("androidx.fragment:fragment-ktx:$fragment_version")

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}