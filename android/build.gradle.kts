//val libs = libraries
//val versionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")


plugins {
    // Plugins usados nos submódulos (apply false)
    id("com.android.application") version "8.5.2" apply false
    id("com.android.library") version "8.5.2" apply false
    id("com.android.test") version "8.5.2" apply false

    id("org.jetbrains.kotlin.android") version "1.9.25" apply false
    id("de.undercouch.download") version "5.6.0" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
