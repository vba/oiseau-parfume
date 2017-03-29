package tools

import okhttp3.Cache
import okhttp3.OkHttpClient

import java.nio.file.Files

class CacheableHttpClient {
    public static final int SizeOfCache = 10 * 1024 * 1024
    private final OkHttpClient instance = {
        final cacheDir = Files.createTempDirectory("http").toFile()
        final Cache cache = new Cache(cacheDir, SizeOfCache)
        println "Http get request will be cached in ${cacheDir.getCanonicalPath()}"
        new OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(new CachingInterceptor())
            .build()
    }()

    def get() { instance }
}
