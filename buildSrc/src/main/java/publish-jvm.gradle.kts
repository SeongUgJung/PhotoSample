plugins {
    kotlin("jvm")
    id("publish-base-lib")
}

tasks.create<Jar>("sourceJar") {
    archiveClassifier.set("sources")
        from(sourceSets.main.get().allSource)
}