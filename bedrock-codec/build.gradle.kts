dependencies {
    api(project(":common"))
    api(platform(libs.fastutil.bom))
    api(libs.netty.buffer)
    api(libs.fastutil.long.common)
    api(libs.fastutil.long.`object`.maps)
    api(libs.jose4j)
    api(libs.nbt)
    implementation(libs.jackson.annotations)

    // Tests
    testImplementation(libs.junit)
    testRuntimeOnly(libs.junit.platform.launcher)
}
