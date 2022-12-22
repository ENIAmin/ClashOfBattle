package com.example.clashofbattle.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.clashofbattle.DAO.PlayerDAO
import com.example.clashofbattle.api.PlayerAPI
import com.example.clashofbattle.models.Player
import com.example.clashofbattle.utils.CapabilityRoomConverter
import com.example.democlashofbattle.utils.toListOfPlayers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Player::class), version = 1)
@TypeConverters(CapabilityRoomConverter::class)
abstract class PlayerDatabase : RoomDatabase() {
    abstract fun playerDAO(): PlayerDAO

    companion object {
        var INSTANCE: PlayerDatabase? = null

        fun init(context: Context){
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                PlayerDatabase::class.java,
                "players_database"
            )
                .fallbackToDestructiveMigration()
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val dao = INSTANCE?.playerDAO();
                        GlobalScope.launch {
                            val players = PlayerAPI.service.getPlayers().toListOfPlayers()
                            dao?.insertAll(players)
                        }
                    }
                })
                .build()
        }
    }
}