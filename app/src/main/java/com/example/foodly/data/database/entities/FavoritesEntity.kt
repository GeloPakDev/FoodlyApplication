package com.example.foodly.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodly.models.Result
import com.example.foodly.util.Constants

@Entity(tableName = Constants.FAVORITE_RECIPES_TABLE)
class FavoritesEntity(@PrimaryKey(autoGenerate = true) var id: Int, var result: Result)
