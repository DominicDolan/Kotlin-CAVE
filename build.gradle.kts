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
        all {
            languageSettings.enableLanguageFeature("InlineClasses")
        }
    }
}
