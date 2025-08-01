@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("de.undercouch.download") version "5.6.0" // garante que o plugin já existe

}

// Definições (substitui o ext{})
val AAR_URL = "https://storage.googleapis.com/download.tensorflow.org/models/tflite/generativeai/tensorflow-lite-select-tf-ops.aar"
val AAR_PATH = "$projectDir/libs/tensorflow-lite-select-tf-ops.aar"


// Coloca no extra para o download.gradle acessar
extra["AAR_URL"] = AAR_URL
extra["AAR_PATH"] = AAR_PATH
// Aplica o script que baixa o AAR
//apply {
//    from("download.gradle")
//}
// 🔹 Task que baixa o AAR diretamente (sem download.gradle)
tasks.register("downloadAAR", de.undercouch.gradle.tasks.download.Download::class) {
    src(AAR_URL)
    dest(AAR_PATH)
    overwrite(false)
}
android {
    namespace = "com.google.tensorflowdemo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.google.tensorflowdemo"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
        buildConfig = true
        viewBinding = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15" // versão atual do Compose
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            isDebuggable = false
        }
        debug {
            applicationIdSuffix = ".debug"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    // libs da pasta /libs
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.aar"))))

    // Jetpack Compose (coloquei as dependências diretas)
    val composeVersion = "1.6.8"
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    implementation("androidx.compose.foundation:foundation:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.material:material-icons-core:$composeVersion")
    implementation("androidx.activity:activity-compose:1.9.2")

    // Accompanist (System UI Controller)
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.34.0")

    // Koin
    val koinVersion = "3.5.6"
    implementation("io.insert-koin:koin-core:$koinVersion")
    implementation("io.insert-koin:koin-android:$koinVersion")
    implementation("io.insert-koin:koin-androidx-compose:$koinVersion")

    // Lifecycle
    val lifecycleVersion = "2.8.4"
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion")

    // Logging (Napier)
    implementation("io.github.aakira:napier:2.7.1")

    // Profanity filter (wordfilter)
    implementation("com.github.mediamonks:AndroidWordFilter:1.0.3")

    // TensorFlow Lite
    implementation("org.tensorflow:tensorflow-lite:2.16.1")
    implementation("org.tensorflow:tensorflow-lite-task-text:0.4.4")
// Jetpack Compose Material 3
    implementation("androidx.compose.material3:material3:1.2.1")
// Ícones padrão do Material3
    implementation("androidx.compose.material:material-icons-extended:1.6.7")

// Compose UI e base
//    implementation("androidx.compose.ui:ui:1.6.7")
//
//    debugImplementation("androidx.compose.ui:ui-tooling:1.6.7")
//
//// Compose Foundation
//    implementation("androidx.compose.foundation:foundation:1.6.7")
//
//// Compose Activity
//    implementation("androidx.activity:activity-compose:1.9.1")

    // Unit tests
    testImplementation("junit:junit:4.13.2")
}
