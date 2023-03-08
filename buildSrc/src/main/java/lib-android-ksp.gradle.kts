plugins {
    id("com.android.library")
    id("com.google.devtools.ksp")
    kotlin("android")
}

afterEvaluate {
    android.libraryVariants.forEach { variant ->
        kotlin.sourceSets {
            val name = variant.name
            getByName(name) {
                kotlin.srcDir("$buildDir/generated/ksp/$name/kotlin")
            }
        }
    }
}

ksp {
    arg("moduleName", project.name)
}
