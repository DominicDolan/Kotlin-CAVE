plugins {
    kotlin("multiplatform") version "2.2.0"
}

group = "com.cave.library"
version = "0.2"

repositories {
    mavenCentral()
}
dependencies {
    commonTestImplementation(kotlin("test"))
}

kotlin {

    jvmToolchain(22)

    jvm()
    // Linux library target for consumers such as Mechanica.
    linuxX64()

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

