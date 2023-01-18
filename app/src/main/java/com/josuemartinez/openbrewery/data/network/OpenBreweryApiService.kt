package com.josuemartinez.openbrewery.data.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private const val BASE_URL = "https://api.openbrewerydb.org/"

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()



interface OpenBreweryApiService {

    @GET("breweries")
    suspend fun getProperties(@Query("filter") type: String): List<OpenBreweryData>
    fun getProperties():
            Call<String>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object OpenBreweryApi {
    val retrofitService : OpenBreweryApiService by lazy {
        retrofit.create(OpenBreweryApiService::class.java)
    }
}
