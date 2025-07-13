package com.app.reelsapp.data.local

import android.content.Context
import android.util.Log
import com.app.reelsapp.data.LocalDataSource
import com.app.reelsapp.data.local.dto.ProductDto
import com.app.reelsapp.data.local.dto.UserContentDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : LocalDataSource {

    private val gson = Gson()

    private suspend fun loadJsonFromAssets(fileName: String): String {
        return withContext(Dispatchers.IO) {
            context.assets.open(fileName).bufferedReader().use { it.readText() }
        }
    }

    override suspend fun getProducts(page: Int, size: Int): List<ProductDto> {
        return withContext(Dispatchers.IO) {
            val jsonString = loadJsonFromAssets("products.json")
            val productType = object : TypeToken<List<ProductDto>>() {}.type
            val allProducts: List<ProductDto> = gson.fromJson(jsonString, productType)

            val startIndex = (page - 1) * size
            val endIndex = minOf(startIndex + size, allProducts.size)

            if (startIndex >= allProducts.size) {
                emptyList()
            } else {
                allProducts.subList(startIndex, endIndex)
            }
        }
    }

    override suspend fun getUserContent(page: Int, size: Int): List<UserContentDto> {
        return withContext(Dispatchers.IO) {
            val jsonString = loadJsonFromAssets("user_content.json")
            val userContentType = object : TypeToken<List<UserContentDto>>() {}.type
            val allUserContent: List<UserContentDto> = gson.fromJson(jsonString, userContentType)

            val startIndex = (page - 1) * size
            val endIndex = minOf(startIndex + size, allUserContent.size)

            if (startIndex >= allUserContent.size) {
                emptyList()
            } else {
                allUserContent.subList(startIndex, endIndex)
            }
        }
    }
}
