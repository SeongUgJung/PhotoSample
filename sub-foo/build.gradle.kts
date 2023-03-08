plugins {
    id("lib-kotlin-android")
    id("lib-compose")
    kotlin("kapt")
}

android {
    namespace = "io.androidalatan.lifecycle.template.foobar.sub.foo"
}

dependencies {
    implementation(platform(libs.alatan.lifecycle.compose.holder))

    implementation(libs.compose.material)
    implementation(libs.compose.runtimeLiveData)


    implementation(libs.dagger.android)
    implementation(libs.dagger.base)
    implementation(libs.common.javaX)
    kapt(libs.dagger.androidCompiler)
    kapt(libs.dagger.baseCompiler)

}