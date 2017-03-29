package tools

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class CachingInterceptor implements Interceptor {

    public static final String CacheControlHeader = "Cache-Control"
    public static final String ResponseCacheValue = "max-age=600"
    public static final String RequestCacheValue = "public, max-stale=2419200"
    public static final String GetMethod = "GET"

    @Override
    Response intercept(Interceptor.Chain chain) throws IOException {
        final Request request = isGetRequest(chain) ? buildWithCacheControl(chain) : chain.request()
        final Response originalResponse = chain.proceed(request);

        return originalResponse.newBuilder()
            .header(CacheControlHeader, ResponseCacheValue)
            .build();
    }

    private static Request buildWithCacheControl(Interceptor.Chain chain) {
        chain.request().newBuilder()
            .header(CacheControlHeader, RequestCacheValue)
            .build()
    }

    private static boolean isGetRequest(Interceptor.Chain chain) {
        chain.request().method() == GetMethod
    }
}
