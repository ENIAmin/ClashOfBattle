package com.example.clashofbattle.DAO

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.clashofbattle.models.Player
import playerName

@Dao
interface PlayerDAO {

    @Query("SELECT * FROM Player WHERE name = :id")
    fun getMainPlayer(id: String): LiveData<Player>

    @Query("SELECT * FROM Player WHERE name = :id")
    suspend fun getMainPlayerForCombat(id: String): Player

    @Query("SELECT * FROM Player WHERE id = :id")
    suspend fun getById(id: Long): Player

    @Query("SELECT * FROM Player WHERE name != :id ORDER BY name")
    fun getAll(id: String): LiveData<List<Player>>

    @Update
    suspend fun update(player: Player)

    @Insert
    suspend fun insertAll(trips: List<Player>)

    @Query("DELETE FROM Player")
    suspend fun clear()

    @Transaction
    suspend fun replace(trips: List<Player>) {
        clear()
        insertAll(trips)
    }
}