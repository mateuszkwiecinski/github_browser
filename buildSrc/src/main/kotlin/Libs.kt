private const val daggerVersion = "2.22.1"

object Libs {
    val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${KotlinVersion.CURRENT}"

    const val dagger = "com.google.dagger:dagger:$daggerVersion"
    const val daggerAndroid = "com.google.dagger:dagger-android:$daggerVersion"
    const val daggerAndroidSupport = "com.google.dagger:dagger-android-support:$daggerVersion"

    const val rxJava = "io.reactivex.rxjava2:rxjava:2.2.8"
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:2.1.1"

    const val appCompat = "androidx.appcompat:appcompat:1.0.2"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.0-alpha5"
    const val materialDesign = "com.google.android.material:material:1.1.0-alpha05"

    private const val navVersion = "2.1.0-alpha02"
    const val archNavigationFragment = "androidx.navigation:navigation-fragment-ktx:$navVersion"
    const val archNavigation = "androidx.navigation:navigation-ui-ktx:$navVersion"

    private const val lifecycleVersion = "2.1.0-alpha04"
    const val archExtensions = "androidx.lifecycle:lifecycle-extensions:$lifecycleVersion"
    const val archViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
    const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion"

    private const val pagingVersion = "2.1.0"
    const val pagingCommon = "androidx.paging:paging-common-ktx:$pagingVersion"
    const val pagingRuntime = "androidx.paging:paging-runtime-ktx:$pagingVersion"
    const val pagingRxJava = "androidx.paging:paging-rxjava2-ktx:$pagingVersion"

    const val apolloGraphql = "com.apollographql.apollo:apollo-runtime:1.0.1-SNAPSHOT"
    const val okHttp = "com.squareup.okhttp3:okhttp:3.14.1"
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
