package com.example.foodly.data.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodly.models.FoodJoke
import com.example.foodly.util.Constants

@Entity(tableName = Constants.FOOD_JOKE_TABLE)
class FoodJokeEntity(@Embedded var foodJoke: FoodJoke) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0

}