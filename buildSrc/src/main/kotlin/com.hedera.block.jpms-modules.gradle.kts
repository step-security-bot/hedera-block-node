/*
 * Copyright (C) 2016-2024 Hedera Hashgraph, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

plugins {
    id("org.gradlex.jvm-dependency-conflict-resolution")
    id("org.gradlex.extra-java-module-info")
}

// Fix or enhance the metadata of third-party Modules. This is about the metadata in the
// repositories: '*.pom' and '*.module' files.
jvmDependencyConflicts.patch {
    // These compile time annotation libraries are not of interest in our setup and are thus removed
    // from the dependencies of all components that bring them in.
    val annotationLibraries =
        listOf(
            "com.google.android:annotations",
            "com.google.code.findbugs:annotations",
            "com.google.code.findbugs:jsr305",
            "com.google.errorprone:error_prone_annotations",
            "com.google.guava:listenablefuture",
            "org.checkerframework:checker-compat-qual",
            "org.checkerframework:checker-qual",
            "org.codehaus.mojo:animal-sniffer-annotations"
        )

    module("io.grpc:grpc-api") { annotationLibraries.forEach { removeDependency(it) } }
    module("io.grpc:grpc-core") { annotationLibraries.forEach { removeDependency(it) } }
    module("io.grpc:grpc-context") { annotationLibraries.forEach { removeDependency(it) } }
    module("io.grpc:grpc-protobuf") { annotationLibraries.forEach { removeDependency(it) } }
    module("io.grpc:grpc-protobuf-lite") {
        annotationLibraries.forEach { removeDependency(it) }
        removeDependency(/* dependency = */ "com.google.protobuf:protobuf-javalite")
        addApiDependency("com.google.protobuf:protobuf-java")
    }
    module("io.grpc:grpc-services") { annotationLibraries.forEach { removeDependency(it) } }
    module("io.grpc:grpc-stub") { annotationLibraries.forEach { removeDependency(it) } }
    module("io.grpc:grpc-testing") { annotationLibraries.forEach { removeDependency(it) } }
    module("io.grpc:grpc-util") { annotationLibraries.forEach { removeDependency(it) } }
    module("com.google.dagger:dagger-compiler") {
        annotationLibraries.forEach { removeDependency(it) }
    }
    module("com.google.dagger:dagger-producers") {
        annotationLibraries.forEach { removeDependency(it) }
    }
    module("com.google.dagger:dagger-spi") { annotationLibraries.forEach { removeDependency(it) } }
    module("com.google.guava:guava") {
        (annotationLibraries -
                "com.google.code.findbugs:jsr305" -
                "com.google.errorprone:error_prone_annotations" -
                "org.checkerframework:checker-qual")
            .forEach { removeDependency(it) }
    }
    module("com.google.protobuf:protobuf-java-util") {
        annotationLibraries.forEach { removeDependency(it) }
    }
    module("io.prometheus:simpleclient") {
        removeDependency("io.prometheus:simpleclient_tracer_otel")
        removeDependency("io.prometheus:simpleclient_tracer_otel_agent")
    }
    module("org.jetbrains.kotlin:kotlin-stdlib") {
        removeDependency("org.jetbrains.kotlin:kotlin-stdlib-common")
    }
    module("junit:junit") { removeDependency("org.hamcrest:hamcrest-core") }
    module("org.hyperledger.besu:secp256k1") { addApiDependency("net.java.dev.jna:jna") }
}

// Fix or enhance the 'module-info.class' of third-party Modules. This is about the
// 'module-info.class' inside the Jar files. In our full Java Modules setup every
// Jar needs to have this file. If it is missing, it is added by what is configured here.
extraJavaModuleInfo {
    failOnAutomaticModules = true // Only allow Jars with 'module-info' on all module paths

    module("io.grpc:grpc-api", "io.grpc") {
        exportAllPackages()
        requireAllDefinedDependencies()
        requires("java.logging")
    }

    module("io.grpc:grpc-core", "io.grpc.internal")
    module("io.grpc:grpc-context", "io.grpc.context")
    module("io.grpc:grpc-stub", "io.grpc.stub") {
        exportAllPackages()
        requireAllDefinedDependencies()
        requires("java.logging")
    }
    module("io.grpc:grpc-testing", "io.grpc.testing")
    module("io.grpc:grpc-services", "io.grpc.services")
    module("io.grpc:grpc-util", "io.grpc.util")
    module("io.grpc:grpc-protobuf", "io.grpc.protobuf")
    module("io.grpc:grpc-protobuf-lite", "io.grpc.protobuf.lite")
    module("com.github.spotbugs:spotbugs-annotations", "com.github.spotbugs.annotations")
    module("com.google.code.findbugs:jsr305", "java.annotation") {
        exportAllPackages()
        mergeJar("javax.annotation:javax.annotation-api")
    }
    module("com.google.errorprone:error_prone_annotations", "com.google.errorprone.annotations")
    module("com.google.j2objc:j2objc-annotations", "com.google.j2objc.annotations")
    module("com.google.protobuf:protobuf-java", "com.google.protobuf") {
        exportAllPackages()
        requireAllDefinedDependencies()
        requires("java.logging")
    }
    module("com.google.guava:guava", "com.google.common") {
        exportAllPackages()
        requireAllDefinedDependencies()
        requires("java.logging")
    }
    module("com.google.guava:failureaccess", "com.google.common.util.concurrent.internal")
    module("com.google.api.grpc:proto-google-common-protos", "com.google.api.grpc.common")
    module("com.google.dagger:dagger", "dagger")
    module("io.perfmark:perfmark-api", "io.perfmark")
    module("javax.inject:javax.inject", "javax.inject")

    module("commons-codec:commons-codec", "org.apache.commons.codec")
    module("org.apache.commons:commons-math3", "org.apache.commons.math3")
    module("org.apache.commons:commons-collections4", "org.apache.commons.collections4")
    module("com.esaulpaugh:headlong", "headlong")

    module("org.checkerframework:checker-qual", "org.checkerframework.checker.qual")
    module("net.i2p.crypto:eddsa", "net.i2p.crypto.eddsa")
    module("org.jetbrains:annotations", "org.jetbrains.annotations")
    module("org.antlr:antlr4-runtime", "org.antlr.antlr4.runtime")

    // needed for metrics and logging, but also several platform classes
    module("com.goterl:resource-loader", "resource.loader")
    module("com.goterl:lazysodium-java", "lazysodium.java")
    module("org.hyperledger.besu:secp256k1", "org.hyperledger.besu.nativelib.secp256k1")
    module("net.java.dev.jna:jna", "com.sun.jna") {
        exportAllPackages()
        requires("java.logging")
    }
    module("io.prometheus:simpleclient", "io.prometheus.simpleclient")
    module("io.prometheus:simpleclient_common", "io.prometheus.simpleclient_common")
    module("io.prometheus:simpleclient_httpserver", "io.prometheus.simpleclient.httpserver") {
        exportAllPackages()
        requireAllDefinedDependencies()
        requires("jdk.httpserver")
    }

    // Annotation processing only
    module("com.google.auto.service:auto-service-annotations", "com.google.auto.service")
    module("com.google.auto.service:auto-service", "com.google.auto.service.processor")
    module("com.google.auto:auto-common", "com.google.auto.common")
    module("com.google.dagger:dagger-compiler", "dagger.compiler")
    module("com.google.dagger:dagger-producers", "dagger.producers")
    module("com.google.dagger:dagger-spi", "dagger.spi")
    module(
        "com.google.devtools.ksp:symbol-processing-api",
        "com.google.devtools.ksp.symbolprocessingapi"
    )
    module("com.google.errorprone:javac-shaded", "com.google.errorprone.javac.shaded")
    module("com.google.googlejavaformat:google-java-format", "com.google.googlejavaformat")
    module("net.ltgt.gradle.incap:incap", "net.ltgt.gradle.incap")
    module("org.jetbrains.kotlinx:kotlinx-metadata-jvm", "kotlinx.metadata.jvm")

    // Test clients only
    module("com.github.docker-java:docker-java-api", "com.github.dockerjava.api")
    module("com.github.docker-java:docker-java-transport", "com.github.dockerjava.transport")
    module(
        "com.github.docker-java:docker-java-transport-zerodep",
        "com.github.dockerjava.transport.zerodep"
    )
    module("org.slf4j:slf4j-api", "org.slf4j") { patchRealModule() }
    module("io.github.cdimascio:java-dotenv", "io.github.cdimascio")
    module("com.google.protobuf:protobuf-java-util", "com.google.protobuf.util")
    module("com.squareup:javapoet", "com.squareup.javapoet") {
        exportAllPackages()
        requires("java.compiler")
    }
    module("junit:junit", "junit")
    module("org.hamcrest:hamcrest", "org.hamcrest")
    module("org.json:json", "org.json")
    module("org.mockito:mockito-core", "org.mockito")
    module("org.objenesis:objenesis", "org.objenesis")
    module("org.rnorth.duct-tape:duct-tape", "org.rnorth.ducttape")
    module("org.testcontainers:junit-jupiter", "org.testcontainers.junit.jupiter")
    module("org.testcontainers:testcontainers", "org.testcontainers")
    module("org.mockito:mockito-junit-jupiter", "org.mockito.junit.jupiter")
}

// Make 'javax.annotation:javax.annotation-api' discoverable for merging it into
// 'com.google.code.findbugs:jsr305'
dependencies { "javaModulesMergeJars"("javax.annotation:javax.annotation-api:1.3.2") }