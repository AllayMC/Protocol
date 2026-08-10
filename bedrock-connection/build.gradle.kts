dependencies {
    api(project(":bedrock-codec"))
    api(libs.netty.transport.raknet)
    api(libs.snappy)

    testImplementation(libs.junit)
    testRuntimeOnly(libs.junit.platform.launcher)
}
