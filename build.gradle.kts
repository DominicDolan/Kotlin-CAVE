plugins {
    kotlin("multiplatform") version "1.9.0"
}

group = "com.cave.library"
version = "0.1"

repositories {
    mavenCentral()
}
dependencies {
    commonTestImplementation(kotlin("test"))
}

kotlin {
    jvm {
        jvmToolchain(8)
        withJava()
    }
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
        val jvmTest by getting {
            dependencies {
                implementation("org.joml:joml:+")
                implementation(kotlin("test-junit"))
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
