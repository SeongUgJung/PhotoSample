plugins {
    id("lib-kotlin-android")
    id("lib-compose")
    kotlin("kapt")
}

android {
    namespace = "io.androidalatan.lifecycle.template.foobar"
}

dependencies {
    implementation(platform(libs.alatan.lifecycle.compose.activity))
    implementation(libs.alatan.lifecycle.composable.holder)
    implementation(projects.foobarApi)
    implementation(projects.subFoo)
    implementation(projects.subBar)

    implementation(libs.alatan.alerts.common.dialog.api)
    implementation(libs.alatan.resourceprovider.api)

    implementation(libs.dagger.android)
    implementation(libs.dagger.base)
    implementation(libs.common.javaX)
    kapt(libs.dagger.androidCompiler)
    kapt(libs.dagger.baseCompiler)

    implementation(libs.compose.material)
    implementation(libs.compose.runtimeLiveData)
}