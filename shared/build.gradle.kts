plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.sqlDelightGradle)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "MultiPlatformLibrary"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            //put your multiplatform dependencies here
            implementation(libs.kotlinx.datetime)
            implementation(libs.androidXCoroutines)

            //koin[add to version controll but something with the versions cause problems espcially in the compose one]
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation("io.insert-koin:koin-compose")
           // implementation(libs.koin.androidx.compose)

            //SqlDelight
            implementation(libs.sqlDelight.runtime)
            implementation(libs.sqlDelight.coroutines)

            //moko
            implementation(libs.moko.compose)
            implementation(libs.moko.core)
            implementation(libs.moko.flow)
            implementation(libs.moko.flow.compose)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
    task("testClasses")
}

android {
    namespace = "com.example.deliveryguyincomeanalyzer"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    dependencies{
        implementation(libs.sqlDelight.android)
    }
}
sqldelight{
    database("WorkData") {
        packageName = "com.example.deliveryguyincomeanalyzer.database"
        sourceFolders = listOf("sqldelight")
    }
}



