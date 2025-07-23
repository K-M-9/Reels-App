package com.app.reelsapp.data.local

import android.content.Context
import com.app.reelsapp.data.LocalDataSource
import com.app.reelsapp.data.local.database.dao.ProductDao
import com.app.reelsapp.data.local.database.dao.UserDao
import com.app.reelsapp.data.local.database.dto.FavoriteDto
import com.app.reelsapp.data.local.database.dto.FollowDto
import com.app.reelsapp.data.local.database.dto.ProductDto
import com.app.reelsapp.data.local.database.dto.ProductOwnerDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val productDao: ProductDao,
    private val userDao: UserDao
) : LocalDataSource {

    private val gson = Gson()

    private suspend fun loadJsonFromAssets(fileName: String): String {
        return withContext(Dispatchers.IO) {
            context.assets.open(fileName).bufferedReader().use { it.readText() }
        }
    }

    override suspend fun getUserProductFavorite(username: String): List<String> =
        userDao.getFavoritesByUsername(username).map { it.productId }


    override suspend fun getUserProductOwnerFollow(username: String): List<String> =
        userDao.getFollowsByUsername(username).map { it.ownerId }

    override suspend fun toggleFavoriteStatusForUserProduct(
        username: String,
        productID: String,
        isFavorite: Boolean
    ) {
        if (isFavorite)
            userDao.insertFavorite(FavoriteDto(username, productID))
        else
            userDao.deleteFavorite(username, productID)
    }

    override suspend fun toggleProductOwnerFollowStatusForUser(
        username: String,
        productOwner: String,
        isFollow: Boolean
    ) {
        if (isFollow)
            userDao.insertFollow(FollowDto(username, productOwner))
        else
            userDao.deleteFollow(username, productOwner)
    }

    override suspend fun getCurrentUsername(): String? = CurrentUserPreferences.getUsername(context)


    override suspend fun getProducts(page: Int, size: Int): List<ProductDto> {
        return withContext(Dispatchers.IO) {
            val jsonString = loadJsonFromAssets("products.json")
            val productType = object : TypeToken<List<ProductDto>>() {}.type
            val allProducts: List<ProductDto> = gson.fromJson(jsonString, productType)

            productDao.clearProduct()
            allProducts.forEach { productDao.insertProduct(it) }

            val startIndex = (page - 1) * size
            val endIndex = minOf(startIndex + size, allProducts.size)

            if (startIndex >= allProducts.size) emptyList()
            else allProducts.subList(startIndex, endIndex)
        }
    }

    override suspend fun getProductOwner(page: Int, size: Int): List<ProductOwnerDto> {
        return withContext(Dispatchers.IO) {
            val jsonString = loadJsonFromAssets("user_content.json")
            val userContentType = object : TypeToken<List<ProductOwnerDto>>() {}.type
            val allUserContent: List<ProductOwnerDto> = gson.fromJson(jsonString, userContentType)

            productDao.clearProductOwner()
            allUserContent.forEach { productDao.insertProductOwner(it) }

            val startIndex = (page - 1) * size
            val endIndex = minOf(startIndex + size, allUserContent.size)

            if (startIndex >= allUserContent.size) emptyList()
            else allUserContent.subList(startIndex, endIndex)
        }
    }
}