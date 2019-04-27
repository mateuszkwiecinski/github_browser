private const val daggerVersion = "2.22.1"

object Libs {
    val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${KotlinVersion.CURRENT}"

    const val dagger = "com.google.dagger:dagger:$daggerVersion"
    const val daggerAndroid = "com.google.dagger:dagger-android:$daggerVersion"
    const val daggerAndroidSupport = "com.google.dagger:dagger-android-support:$daggerVersion"
}

object TestLibs {
    const val junit4 = "junit:junit:4.12"
    const val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:2.1.0"
    const val mockito = "org.mockito:mockito-core:2.27.0"
    const val assertJ = "org.assertj:assertj-core:3.12.2"
}

object Kapt {
    const val daggerCompiler = "com.google.dagger:dagger-compiler:$daggerVersion"
    const val daggerAndroidCompiler = "com.google.dagger:dagger-android-processor:$daggerVersion"
}
