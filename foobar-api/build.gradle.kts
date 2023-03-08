plugins {
    id("lib-kotlin-android")
}

android {
    namespace = "io.androidalatan.lifecycle.template.foobar.api"
}

dependencies {
    implementation(libs.alatan.lifecycle.router.api)
}