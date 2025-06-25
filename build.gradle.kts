// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    id("com.google.devtools.ksp") version "1.5.30-1.0.0"
    id("com.google.dagger.hilt.android") version "2.56.2" apply false
}