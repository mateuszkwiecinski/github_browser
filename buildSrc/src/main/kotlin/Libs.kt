private const val DAGGER_VERSION = "2.28.3"
private const val OK_HTTP_VERSION = "4.8.0"

object Libs {
    const val dagger = "com.google.dagger:dagger:$DAGGER_VERSION"
    const val daggerAndroid = "com.google.dagger:dagger-android:$DAGGER_VERSION"
    const val daggerAndroidSupport = "com.google.dagger:dagger-android-support:$DAGGER_VERSION"

    const val rxJava = "io.reactivex.rxjava2:rxjava:2.2.19"
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:2.1.1"
    const val rxKotlin = "io.reactivex.rxjava2:rxkotlin:2.4.0"

    const val appCompat = "androidx.appcompat:appcompat:1.1.0"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.0-rc1"
    const val materialDesign = "com.google.android.material:material:1.2.0-rc01"

    private const val navVersion = "2.3.0"
    const val archNavigationFragment = "androidx.navigation:navigation-fragment-ktx:$navVersion"
    const val archNavigation = "androidx.navigation:navigation-ui-ktx:$navVersion"

    private const val lifecycleVersion = "2.2.0"
    const val archExtensions = "androidx.lifecycle:lifecycle-extensions:$lifecycleVersion"
    const val archViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
    const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion"

    const val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"

    private const val pagingVersion = "2.1.2"
    const val pagingCommon = "androidx.paging:paging-common-ktx:$pagingVersion"
    const val pagingRuntime = "androidx.paging:paging-runtime-ktx:$pagingVersion"
    const val pagingRxJava = "androidx.paging:paging-rxjava2-ktx:$pagingVersion"

    private const val apolloVersion = "2.2.3"
    const val apolloGraphql = "com.apollographql.apollo:apollo-runtime:$apolloVersion"
    const val apolloCache = "com.apollographql.apollo:apollo-http-cache:$apolloVersion"
    const val okHttp = "com.squareup.okhttp3:okhttp:$OK_HTTP_VERSION"
}

object TestLibs {
    const val junit4 = "junit:junit:4.13"
    const val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0"
    const val mockito = "org.mockito:mockito-core:3.4.6"
    const val assertJ = "org.assertj:assertj-core:3.16.1"
    const val archCoreTesting = "androidx.arch.core:core-testing:2.1.0"
    const val archCoreKtx = "androidx.test:core-ktx:1.3.0-rc02"
    const val archRules = "androidx.test:rules:1.3.0-rc02"
    const val mockWebServer = "com.squareup.okhttp3:mockwebserver:$OK_HTTP_VERSION"
}

object Kapt {
    const val daggerCompiler = "com.google.dagger:dagger-compiler:$DAGGER_VERSION"
    const val daggerAndroidCompiler = "com.google.dagger:dagger-android-processor:$DAGGER_VERSION"
}
