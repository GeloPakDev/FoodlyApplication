package com.example.foodly.viewmodels

import android.app.Application
import android.widget.FilterQueryProvider
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.foodly.data.DataStoreRepository
import com.example.foodly.util.Constants.Companion.API_KEY
import com.example.foodly.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.example.foodly.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.example.foodly.util.Constants.Companion.DEFAULT_RECIPES_NUMBER
import com.example.foodly.util.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.example.foodly.util.Constants.Companion.QUERY_API_KEY
import com.example.foodly.util.Constants.Companion.QUERY_DIET
import com.example.foodly.util.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.example.foodly.util.Constants.Companion.QUERY_NUMBER
import com.example.foodly.util.Constants.Companion.QUERY_SEARCH
import com.example.foodly.util.Constants.Companion.QUERY_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {

    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE

    var networkStatus = false
    var backOnline = false

    val readMealAndDietType = dataStoreRepository.readMealAndDietType
    val readBackOnline = dataStoreRepository.readBackOnline.asLiveData()

    fun saveMealAndDataType(mealType: String, mealTypeId: Int, dietType: String, dietTypeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveMealAndDataType(mealType, mealTypeId, dietType, dietTypeId)
        }
    }

    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        viewModelScope.launch {
            readMealAndDietType.collect { value ->
                mealType = value.selectedMealType
                dietType = value.selectedDietType
            }
        }

        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = DEFAULT_MEAL_TYPE
        queries[QUERY_DIET] = DEFAULT_DIET_TYPE
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }

    fun applySearchQuery(searchQuery: String): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[QUERY_SEARCH] = searchQuery
        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"
        return queries
    }

    fun showNetworkStatus() {
        if (!networkStatus) {
            Toast.makeText(getApplication(), "No Internet Connection", Toast.LENGTH_SHORT)
                .show()
            saveBackOnLine(true)
        } else if (networkStatus) {
            if (backOnline) {
                Toast.makeText(getApplication(), "we are back online", Toast.LENGTH_SHORT)
                    .show()
                saveBackOnLine(false)
            }
        }
    }

    private fun saveBackOnLine(backOnline: Boolean) {
        viewModelScope.launch {
            dataStoreRepository.saveBackOnline(backOnline)
        }

    }


}