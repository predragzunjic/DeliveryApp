package com.example.deliveryapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.deliveryapp.repositories.LoggedInRepository
import com.example.deliveryapp.tables.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class LoggedInViewModel @Inject constructor(
    private val repository: LoggedInRepository
) : ViewModel() {
    private lateinit var restaurants: LiveData<List<Restaurant>>
    private lateinit var categories: LiveData<List<Category>>
    private lateinit var restaurantWithCategoryWithProducts:
            LiveData<List<RestaurantWithCategoriesWithProducts?>>
    private var restaurantWithCategoriesWithProductsRB:
            RestaurantWithCategoriesWithProducts? = null
    private lateinit var restaurantsInTown: LiveData<List<TownWithRestaurants?>>
    private var nameTown: String? = null

    fun getRestaurantWithCategoryWithProducts(idRestaurant: Int?)
    : LiveData<List<RestaurantWithCategoriesWithProducts?>>{
        privateGetRestaurantWithCategoryWithProducts(idRestaurant)

        return restaurantWithCategoryWithProducts
    }

    fun getRestaurants(): LiveData<List<Restaurant>>{
        privateGetRestaurants()

        return restaurants
    }

    fun geRestaurantsInTown(nameTown: String?): LiveData<List<TownWithRestaurants?>>{
        restaurantsInTown = repository.getRestaurantsInTown(nameTown).asLiveData()

        return restaurantsInTown
    }

    fun getRestaurantWithCategoryWithProductsBoolean(idRestaurant: Int?, idCategory: Int?): Boolean?{
        return repository.getRestaurantWithCategoriesWithProductsBoolean(idRestaurant, idCategory)
    }

    fun getTownWithClient(idClient: Int?): String?{
        runBlocking {
            val job = launch {
                nameTown = repository.getTownWithClient(idClient)
            }
            job.join()
        }

        return nameTown
    }

    suspend fun insertProduct(product: Product) = CoroutineScope(Dispatchers.Main).launch {
        repository.insertProduct(product)
    }

    private fun privateGetRestaurants(){
        restaurants = repository.getRestaurants().asLiveData()
    }

    private fun privateGetRestaurantWithCategoryWithProducts(idRestaurant: Int?){
        restaurantWithCategoryWithProducts =
            repository.getRestaurantWithCategoriesWithProducts(idRestaurant).asLiveData()
    }

    fun insertCategory(category: Category) = CoroutineScope(Dispatchers.Main).launch {
        repository.insertCategory(category)
    }

    fun deleteAllCategories() = CoroutineScope(Dispatchers.Main).launch {
        repository.deleteAllCategories()
    }
}