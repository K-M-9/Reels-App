package com.app.reelsapp.reels.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.reelsapp.reels.data.local.database.dto.FavoriteDto
import com.app.reelsapp.reels.data.local.database.dto.FollowDto
import com.app.reelsapp.reels.data.local.database.dto.UserDto


@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserDto)

    @Query("SELECT * FROM user WHERE username = :username")
    suspend fun getUserByUsername(username: String): UserDto?

    @Query("DELETE FROM user")
    suspend fun clear()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteDto)

    @Query("SELECT * FROM favorite WHERE username = :username")
    suspend fun getFavoritesByUsername(username: String): List<FavoriteDto>

    @Query("DELETE FROM favorite WHERE username = :username AND productId = :productId")
    suspend fun deleteFavorite(username: String, productId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFollow(follow: FollowDto)

    @Query("SELECT * FROM follow WHERE username = :username")
    suspend fun getFollowsByUsername(username: String): List<FollowDto>

    @Query("DELETE FROM follow WHERE username = :username AND ownerId = :ownerId")
    suspend fun deleteFollow(username: String, ownerId: String)
}
