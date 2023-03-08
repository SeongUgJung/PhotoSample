plugins {
    id("lib-kotlin-android")
    id("lib-compose")
}

android {
    namespace = "io.androidalatan.lifecycle.template.valueholder"
}

dependencies {
    implementation(libs.alatan.lifecycle.router.api)
    implementation(libs.compose.runtime)
}