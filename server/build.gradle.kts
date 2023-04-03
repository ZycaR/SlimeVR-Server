/*
 * This file was generated by the Gradle "init" task.
 *
 * This generated file contains a sample Java Library project to get you started.
 * For more details take a look at the Java Libraries chapter in the Gradle
 * User Manual available at https://docs.gradle.org/6.3/userguide/java_library_plugin.html
 */
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.ByteArrayOutputStream

plugins {
	kotlin("plugin.serialization") version "1.8.21"
	id("com.diffplug.spotless") version "6.12.0"
	id("com.github.gmazzo.buildconfig") version "4.0.4"

	id("com.android.application") version "7.4.2"
	id("org.jetbrains.kotlin.android") version "1.8.0"
}

kotlin {
	jvmToolchain {
		languageVersion.set(JavaLanguageVersion.of(17))
	}
}
java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(17))
	}
}
tasks.withType<KotlinCompile> {
	kotlinOptions.jvmTarget = "17"
}

// Set compiler to use UTF-8
tasks.withType<JavaCompile> {
	options.encoding = "UTF-8"
}
tasks.withType<Test> {
	systemProperty("file.encoding", "UTF-8")
}
tasks.withType<Javadoc> {
	options.encoding = "UTF-8"
}

tasks
	.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask<*>>()
	.configureEach {
		compilerOptions
			.languageVersion
			.set(
				org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_1_9
			)
	}

allprojects {
	repositories {
		google()
		mavenCentral()
	}
}

dependencies {
	implementation(project(":solarxr-protocol"))

	// This dependency is used internally,
	// and not exposed to consumers on their own compile classpath.
	implementation("com.google.flatbuffers:flatbuffers-java:22.10.26")
	implementation("commons-cli:commons-cli:1.5.0")
	implementation("com.fasterxml.jackson.core:jackson-databind:2.15.1")
	implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.15.1")

	implementation("com.github.jonpeterson:jackson-module-model-versioning:1.2.2")
	implementation("org.apache.commons:commons-math3:3.6.1")
	implementation("org.apache.commons:commons-lang3:3.12.0")
	implementation("org.apache.commons:commons-collections4:4.4")

	implementation("net.java.dev.jna:jna:5.+")
	implementation("net.java.dev.jna:jna-platform:5.+")
	implementation("com.illposed.osc:javaosc-core:0.8")
	implementation("com.fazecast:jSerialComm:2.+")
	implementation("com.google.protobuf:protobuf-java:3.21.12")
	implementation("org.java-websocket:Java-WebSocket:1.+")
	implementation("com.melloware:jintellitype:1.+")
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
	implementation("it.unimi.dsi:fastutil:8.5.12")

	testImplementation(kotlin("test"))
	// Use JUnit test framework
	testImplementation(platform("org.junit:junit-bom:5.9.0"))
	testImplementation("org.junit.jupiter:junit-jupiter")
	testImplementation("org.junit.platform:junit-platform-launcher")

	// Android stuff
	implementation("androidx.appcompat:appcompat:1.6.1")
	implementation("androidx.core:core-ktx:1.9.0")
	implementation("com.google.android.material:material:1.8.0")
	implementation("androidx.constraintlayout:constraintlayout:2.1.4")
	implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
	androidTestImplementation("androidx.test.ext:junit:1.1.5")
	androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
	// For hosting web GUI
	implementation("io.ktor:ktor-server-core:2.2.4")
	implementation("io.ktor:ktor-server-netty:2.2.4")
}

/**
 * The android block is where you configure all your Android-specific
 * build options.
 */

android {
	/**
	 * The app's namespace. Used primarily to access app resources.
	 */

	namespace = "dev.slimevr"

	/**
	 * compileSdk specifies the Android API level Gradle should use to
	 * compile your app. This means your app can use the API features included in
	 * this API level and lower.
	 */

	compileSdk = 33

	/**
	 * The defaultConfig block encapsulates default settings and entries for all
	 * build variants and can override some attributes in main/AndroidManifest.xml
	 * dynamically from the build system. You can configure product flavors to override
	 * these values for different versions of your app.
	 */

	packagingOptions {
		resources.excludes.add("META-INF/*")
	}

	defaultConfig {

		// Uniquely identifies the package for publishing.
		applicationId = "dev.slimevr.server"

		// Defines the minimum API level required to run the app.
		minSdk = 26

		// Specifies the API level used to test the app.
		targetSdk = 33

		// Defines the version number of your app.
		versionCode = 2

		// Defines a user-friendly version name for your app.
		versionName = "0.7.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}

	/**
	 * The buildTypes block is where you can configure multiple build types.
	 * By default, the build system defines two build types: debug and release. The
	 * debug build type is not explicitly shown in the default build configuration,
	 * but it includes debugging tools and is signed with the debug key. The release
	 * build type applies ProGuard settings and is not signed by default.
	 */

	buildTypes {

		/**
		 * By default, Android Studio configures the release build type to enable code
		 * shrinking, using minifyEnabled, and specifies the default ProGuard rules file.
		 */

		getByName("release") {
			isMinifyEnabled = true // Enables code shrinking for the release build type.
			proguardFiles(
				getDefaultProguardFile("proguard-android.txt"),
				"proguard-rules.pro"
			)
		}
	}

	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}
	kotlinOptions {
		jvmTarget = "17"
	}
}

fun String.runCommand(currentWorkingDir: File = file("./")): String {
	val byteOut = ByteArrayOutputStream()
	project.exec {
		workingDir = currentWorkingDir
		commandLine = this@runCommand.split("\\s".toRegex())
		standardOutput = byteOut
	}
	return String(byteOut.toByteArray()).trim()
}

buildConfig {
	val gitCommitHash = "git rev-parse --verify --short HEAD".runCommand().trim()
	val gitVersionTag = "git --no-pager tag --points-at HEAD".runCommand().trim()
	val gitClean = "git status --porcelain".runCommand().trim().isEmpty()
	useKotlinOutput { topLevelConstants = true }
	packageName("dev.slimevr")

	buildConfigField("String", "GIT_COMMIT_HASH", "\"${gitCommitHash}\"")
	buildConfigField("String", "GIT_VERSION_TAG", "\"${gitVersionTag}\"")
	buildConfigField("boolean", "GIT_CLEAN", gitClean.toString())
}

configure<com.diffplug.gradle.spotless.SpotlessExtension> {
	// optional: limit format enforcement to just the files changed by this feature branch
	// ratchetFrom "origin/main"

	format("misc") {
		// define the files to apply `misc` to
		target("*.gradle", "*.md", ".gitignore")

		// define the steps to apply to those files
		trimTrailingWhitespace()
		endWithNewline()
		indentWithTabs()
	}
	// format "yaml", {
	// 	target "*.yml", "*.yaml",

	// 	trimTrailingWhitespace()
	// 	endWithNewline()
	// 	indentWithSpaces(2)  // YAML cannot contain tabs: https://yaml.org/faq.html
	// }

	// .editorconfig doesn't work so, manual override
	// https://github.com/diffplug/spotless/issues/142
	val editorConfig =
		mapOf(
			"indent_size" to 4,
			"indent_style" to "tab",
// 			"max_line_length" to 88,
			"ktlint_experimental" to "enabled",
			"ij_kotlin_packages_to_use_import_on_demand" to
				"java.util.*,kotlin.math.*,dev.slimevr.autobone.errors.*,io.github.axisangles.ktmath.*,kotlinx.atomicfu.*",
			"ij_kotlin_allow_trailing_comma" to true
		)
	val ktlintVersion = "0.47.1"
	kotlinGradle {
		target("*.gradle.kts") // default target for kotlinGradle
		ktlint(ktlintVersion)
			.setUseExperimental(true)
			.editorConfigOverride(editorConfig)
	}
	kotlin {
		targetExclude("build/**/**.kt")
		ktlint(ktlintVersion)
			.setUseExperimental(true)
			.editorConfigOverride(editorConfig)
	}
	java {
		targetExclude("**/BuildConfig.java")

		removeUnusedImports()
		// Use eclipse JDT formatter
		eclipse().configFile("spotless.xml")
	}
}

tasks.getByName("run", JavaExec::class) {
	standardInput = System.`in`
	args = listOf("run")
}
