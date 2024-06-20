package com.example.recipebook.DataBase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RecipeViewModel(application: Application): AndroidViewModel(application) {
//    val getRecipe: LiveData<Recipe>
    val getAllRecipeTitle: LiveData<List<RecipeTitle>>
    val id: Int? = null



    private val repository: RecipeRepository

    init {
        val recipeDao = RecipeDb.getDb(application).getDao()
        repository = RecipeRepository(recipeDao)
//        getRecipe = recipeDao.getRecipe(id)
        getAllRecipeTitle = recipeDao.getAllRecipeTitles()
    }

    fun insertRecipe(recipe: Recipe){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertRecipe(recipe)
        }
    }
    fun insertRecipeTitle(recipeTitle: RecipeTitle){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertRecipeTitle(recipeTitle)
        }
    }
}