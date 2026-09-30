plugins { id("com.android.application"); id("org.jetbrains.kotlin.android") }
android {
    namespace = "br.com.goalstats.routemonitor"
    compileSdk = 35
    defaultConfig {
        applicationId = "br.com.goalstats.routemonitor"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }
}
