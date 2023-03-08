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
    implementation(projects.valueHolder)

    implementation(libs.alatan.alerts.common.dialog.api)
    implementation(libs.alatan.resourceprovider.api)

    implementation(libs.image.coil)

    implementation(libs.dagger.android)
    implementation(libs.dagger.base)
    implementation(libs.common.javaX)
    kapt(libs.dagger.androidCompiler)
    kapt(libs.dagger.baseCompiler)

    implementation(libs.compose.material)
    implementation(libs.compose.runtime)

    testImplementation(libs.alatan.lifecycle.handler.assertion)
    testImplementation(libs.alatan.lifecycle.viewevent.assertion)
    testImplementation(libs.alatan.lifecycle.router.assertion)
    testImplementation(libs.alatan.lifecycle.resulthandler.assertion)
    testImplementation(libs.alatan.alerts.compose.dialog.assertion)
    testImplementation(libs.test.turbine)
    testImplementation(libs.test.coroutineTest)
    testImplementation(libs.test.assertj.core)
}