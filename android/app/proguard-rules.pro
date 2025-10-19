# Basic ProGuard rules for Android
-dontobfuscate
-dontoptimize

# Keep all classes that might be accessed via reflection
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.Application

# Keep all public and protected methods that could be used via reflection
-keepclassmembers class * {
    public protected *;
}