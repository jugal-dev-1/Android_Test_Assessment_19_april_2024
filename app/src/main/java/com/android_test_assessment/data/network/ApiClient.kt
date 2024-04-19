package com.android_test_assessment.data.network


import android.content.Context
import com.android_test_assessment.data.network.common.BASE_URL
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit


class ApiClient {

    fun getClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createDefaultOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(NullOnEmptyConverterFactory())
            .build()

    }

    private fun createDefaultOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient().newBuilder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
//            .addInterceptor(TokenInterceptor())
            .retryOnConnectionFailure(true)
            .build()
    }

    class TokenInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
//            Log.e("###", SharedPref[USER_TOKEN, ""].toString())
            val newRequest: Request = chain.request().newBuilder()
//                .header("Authorization", "Bearer ${SharedPref[USER_TOKEN, ""]}")
                .addHeader("Connection", "close")
                .build()
            return chain.proceed(newRequest)
        }
    }

    fun gotoLogin(context: Context) {
//        val intent = Intent(context, LoginActivity::class.java)
//        context.startActivity(intent)
    }

    class NullOnEmptyConverterFactory : Converter.Factory() {

        override fun responseBodyConverter(
            type: Type?,
            annotations: Array<Annotation>?,
            retrofit: Retrofit?
        ): Converter<ResponseBody, *>? {
            val delegate = retrofit!!.nextResponseBodyConverter<Any>(this, type!!, annotations!!)
            return Converter<ResponseBody, Any> { body ->
                if (body.contentLength() == 0L) null else delegate.convert(
                    body
                )
            }
        }
    }

    fun getApiInstance(): ApiInterface {
        return getClient().create(ApiInterface::class.java)
    }


}