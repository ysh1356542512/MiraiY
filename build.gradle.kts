plugins {
    kotlin("jvm") version "1.5.31"
    "java"
    "application"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

//application{
//    mainClass = "MainKt"
//}

tasks.register<Jar>("qqJar"){
    manifest {
        attributes("Main-Class" to "MainKt")
    }
    exclude("META-INF/**")
    archiveFileName.set("${rootProject.name}.jar")
    from(sourceSets.main.get().output)
    from(configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) })
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.6.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
    implementation(group="org.apache.poi", name= "poi", version= "3.17")
    implementation(group= "org.apache.poi", name="poi-ooxml", version="3.17")
    implementation(group = "org.quartz-scheduler", name="quartz", version= "2.3.0")
    implementation("com.google.code.gson:gson:2.9.0:")
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("net.mamoe:mirai-core:2.9.2")

}


tasks.getByName<Test>("test") {
    useJUnitPlatform()
}