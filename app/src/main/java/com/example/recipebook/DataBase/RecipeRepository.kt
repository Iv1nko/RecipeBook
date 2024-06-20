package com.example.recipebook.DataBase

import androidx.lifecycle.LiveData

class RecipeRepository(val dao: Dao) {
//    val getRecipe: LiveData<Recipe> = dao.getRecipe(id)
    val getAllRecipeTitle: LiveData<List<RecipeTitle>> = dao.getAllRecipeTitles()

    suspend fun insertRecipe(recipe: Recipe){
        dao.insertRecipe(recipe)
    }
    suspend fun insertRecipeTitle(recipeTitle: RecipeTitle){
        dao.insertRecipeTitle(recipeTitle)
    }
}