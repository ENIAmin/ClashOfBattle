package com.example.clashofbattle.api

import com.example.clashofbattle.models.Player
import com.example.clashofbattle.utils.CapabilityMoshiConverter
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

interface PlayerAPI {

    companion object {
        private const val BASE_URL = "https://firechat-dev-da136-default-rtdb.europe-west1.firebasedatabase.app/"

        private val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(CapabilityMoshiConverter())
            .build()

        private val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .build()


        val service: PlayerAPI by lazy { retrofit.create(PlayerAPI::class.java) }
    }

    @GET("players.json")
    suspend fun getPlayers() : Map<String, Player>

    @PUT("trips/{id}.json ")
    suspend fun updatePlayer(@Path("id") id: String, @Body player: Player) : Player

}