dependencies {
    api(platform(libs.fastutil.bom))
    api(libs.fastutil.`object`.int.maps)
    api(libs.fastutil.int.`object`.maps)
    api(libs.fastutil.long.common)
    api(libs.fastutil.long.`object`.maps)
    api(libs.netty.buffer)
    api(libs.math)
    api(libs.natives)
    api(libs.jose4j)
    api(libs.nbt)
    implementation(libs.jackson.annotations)

    // Tests
    testImplementation(libs.junit)
    testRuntimeOnly(libs.junit.platform.launcher)
}
