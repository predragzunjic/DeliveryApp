package com.example.deliveryapp

import com.example.deliveryapp.tables.*
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val deliveryAppDao: DeliveryAppDao
): Repository {
    override suspend fun insertTown(town: Town) = deliveryAppDao.insertTown(town)


    override suspend fun insertClient(client: Client) = deliveryAppDao.insertClient(client)


    override suspend fun insertOrder(order: Order) = deliveryAppDao.insertOrder(order)


    override suspend fun insertOrderElement(orderElement: OrderElement) = deliveryAppDao.insertOrderElement(orderElement)


    override suspend fun insertAddition(addition: Addition) = deliveryAppDao.insertAddition(addition)


    override suspend fun insertRestaurant(restaurant: Restaurant) = deliveryAppDao.insertRestaurant(restaurant)


    override suspend fun insertProduct(product: Product) = deliveryAppDao.insertProduct(product)


    override suspend fun insertCategory(category: Category) = deliveryAppDao.insertCategory(category)


    //override suspend fun getRestaurantWithOrdersAndOrderElementsAndAdditionsAndProducts(id: Int): LiveData<List<RestaurantWithOrdersAndOrderElementsAndAdditionsAndProducts>> {
      //  return db.getDao().getRestaurantWithOrdersAndOrderElementsAndAdditionsAndProducts(id)
    //}

    override fun getTowns() = deliveryAppDao.getTowns()

    override suspend fun deleteTown(town_name: String) = deliveryAppDao.deleteTown(town_name)

    override fun getRestaurants() = deliveryAppDao.getRestaurants()

    override suspend fun getClient(email: String, password: String) = deliveryAppDao.getClient(email, password)

    override suspend fun getRestaurant(email: String?, password: String?) = deliveryAppDao.getRestaurant(email, password)
}