import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        google()
        mavenCentral()
    }
}// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    id("com.android.application") version "8.0.2" apply false
    id("com.android.library") version "8.0.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.20" apply false
    id("com.github.ben-manes.versions") version "0.42.0"
    id("org.jmailen.kotlinter") version "3.15.0" apply false
    id("com.google.devtools.ksp") version "1.8.21-1.0.11" apply false
    id( "com.android.test") version "8.0.2" apply false
    id( "androidx.baselineprofile") version "1.2.0-alpha13" apply false
}

subprojects {
    apply(plugin = "org.jmailen.kotlinter") // Version should be inherited from parent
}

// Enables compose compiler metrics
// Generate them with `./gradlew assembleRelease --rerun-tasks -P com.jerboa.enableComposeCompilerReports=true`
// see https://github.com/androidx/androidx/blob/androidx-main/compose/compiler/design/compiler-metrics.md
subprojects {
    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            if (project.findProperty("com.jerboa.enableComposeCompilerReports") == "true") {

                freeCompilerArgs.addAll(
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=" +
                        project.buildDir.absolutePath + "/compose_metrics"
                )

                freeCompilerArgs.addAll(
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=" +
                        project.buildDir.absolutePath + "/compose_metrics"
                )
            }
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
