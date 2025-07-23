package com.app.reelsapp.data.local.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.reelsapp.data.local.database.dto.ProductDto
import com.app.reelsapp.data.local.database.dto.ProductOwnerDto

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(vararg product: ProductDto)

    @Query("SELECT * FROM product")
    fun getProducts(): PagingSource<Int, ProductDto>

    @Query("SELECT * FROM product WHERE id = :id")
    suspend fun getProductById(id: Int): ProductDto?

    @Query("DELETE FROM product")
    suspend fun clearProduct()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductOwner(vararg owner: ProductOwnerDto)

    @Query("SELECT * FROM product_owner WHERE id = :id")
    suspend fun getProductOwnerById(id: Int): ProductOwnerDto?


    @Query("SELECT * FROM product_owner")
    fun getProductOwners(): PagingSource<Int, ProductOwnerDto>

    @Query("DELETE FROM product_owner")
    suspend fun clearProductOwner()

}

