import org.gradle.api.tasks.bundling.Zip
import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper

buildscript {
    val kotlin_version = "1.0.5-3"
    extra["kotlin_version"] = kotlin_version

    repositories {
        jcenter()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
    }
}

apply {
    plugin<BasePlugin>()
}

subprojects {
    repositories {
        jcenter()
        gradleScriptKotlin()
    }

    apply {
        plugin<JavaPlugin>()
        plugin<KotlinPluginWrapper>()
    }

    dependencies {
        compile(rootProject.fileTree("lib").apply {
            include("*.jar")
        })

        testCompile("junit:junit:4.12")
        testCompile("io.kotlintest:kotlintest:1.3.5")
    }


    task<Zip>("zipSource") {
        from("src/main/java")
        include("*.java")
        destinationDir = rootProject.buildDir
    }

    val sourceSets = the<JavaPluginConvention>().sourceSets
    val main = sourceSets.getByName("main")

    main.java.srcDir("src/provided/java")
}