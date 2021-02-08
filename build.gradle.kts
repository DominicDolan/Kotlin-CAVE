plugins {
    kotlin("multiplatform") version "1.4.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    jvm()
    mingwX64 {
        binaries {
            executable()
        }

    }
    js(IR) {
        browser()
        nodejs()

        binaries.executable()
    }

    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation("org.joml:joml:+")
            }

        }
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
            }
        }
        all {
            languageSettings.enableLanguageFeature("InlineClasses")
        }
    }
}
