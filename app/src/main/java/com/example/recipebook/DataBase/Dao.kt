package com.example.recipebook.DataBase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert(entity = Recipe::class)
    fun insertRecipe(recipe: Recipe)
    @Insert(entity = RecipeTitle::class)
    fun insertRecipeTitle(recipeTitle: RecipeTitle)


    @Delete
    fun deleteRecipe(recipe: Recipe)
    @Delete
    fun deleteRecipeTitle(recipeTitle: RecipeTitle)


    @Query ("SELECT * FROM recipes WHERE id = (:id)")
    fun getRecipe(id: Int?): LiveData<Recipe>
    @Query ("SELECT * FROM recipeTitles")
    fun getAllRecipeTitles(): LiveData<List<RecipeTitle>>
}