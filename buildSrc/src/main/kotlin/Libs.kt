private const val daggerVersion = "2.25.4"
private const val okHttpVersion = "4.3.0"

object Libs {
    const val dagger = "com.google.dagger:dagger:$daggerVersion"
    const val daggerAndroid = "com.google.dagger:dagger-android:$daggerVersion"
    const val daggerAndroidSupport = "com.google.dagger:dagger-android-support:$daggerVersion"

    const val rxJava = "io.reactivex.rxjava2:rxjava:2.2.16"
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:2.1.1"
    const val rxKotlin = "io.reactivex.rxjava2:rxkotlin:2.4.0"

    const val appCompat = "androidx.appcompat:appcompat:1.1.0"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.0-beta4"
    const val materialDesign = "com.google.android.material:material:1.2.0-alpha03"

    private const val navVersion = "2.2.0-rc04"
    const val archNavigationFragment = "androidx.navigation:navigation-fragment-ktx:$navVersion"
    const val archNavigation = "androidx.navigation:navigation-ui-ktx:$navVersion"

    private const val lifecycleVersion = "2.2.0-rc03"
    const val archExtensions = "androidx.lifecycle:lifecycle-extensions:$lifecycleVersion"
    const val archViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
    const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion"

    const val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0-alpha03"

    private const val pagingVersion = "2.1.1"
    const val pagingCommon = "androidx.paging:paging-common-ktx:$pagingVersion"
    const val pagingRuntime = "androidx.paging:paging-runtime-ktx:$pagingVersion"
    const val pagingRxJava = "androidx.paging:paging-rxjava2-ktx:$pagingVersion"

    const val apolloGraphql = "com.apollographql.apollo:apollo-runtime:1.2.2"
    const val apolloCache = "com.apollographql.apollo:apollo-http-cache:1.2.2"
    const val okHttp = "com.squareup.okhttp3:okhttp:$okHttpVersion"
}

object TestLibs {
    const val junit4 = "junit:junit:4.13"
    const val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0"
    const val mockito = "org.mockito:mockito-core:3.2.4"
    const val assertJ = "org.assertj:assertj-core:3.14.0"
    const val archCoreTesting = "androidx.arch.core:core-testing:2.1.0-alpha02"
    const val archCoreKtx = "androidx.test:core-ktx:1.2.0-alpha04"
    const val archRules = "androidx.test:rules:1.2.0-alpha04"
    const val mockWebServer = "com.squareup.okhttp3:mockwebserver:$okHttpVersion"
}

object Kapt {
    const val daggerCompiler = "com.google.dagger:dagger-compiler:$daggerVersion"
    const val daggerAndroidCompiler = "com.google.dagger:dagger-android-processor:$daggerVersion"
}
