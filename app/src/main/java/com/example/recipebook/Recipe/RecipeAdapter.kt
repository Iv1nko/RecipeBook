package com.example.recipebook.Recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.recipebook.DataBase.RecipeTitle
import com.example.recipebook.R
import com.example.recipebook.databinding.RecipeItemBinding


class RecipeAdapter: RecyclerView.Adapter<RecipeAdapter.RecipeHolder>() {

    var recipeTitleList = emptyList<RecipeTitle>()

    class RecipeHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = RecipeItemBinding.bind(item)


        fun bind(recipeTitle: RecipeTitle) = with(binding) {
            imageOfDish.setImageURI(recipeTitle.imageOfRecipe)
            textTitle.text = recipeTitle.title
            imageOfDish.setOnClickListener {
                val navController = Navigation.findNavController(it)
                val bundle = Bundle()
                bundle.putInt("key", recipeTitle.id!!)
                navController.navigate(R.id.action_navigation_main_to_navigation_recipe, bundle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_item, parent, false)
//        view.setOnClickListener {
//            val navController = Navigation.findNavController(it)
//            navController.navigate(R.id.action_navigation_main_to_navigation_recipe, )
//        }
        return RecipeHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeHolder, position: Int) {
        holder.bind(recipeTitleList[position])
    }

    override fun getItemCount(): Int {
        return recipeTitleList.size
    }


    fun setRecipeTitle(recipeTitleList: List<RecipeTitle>) {
        this.recipeTitleList = recipeTitleList
        notifyDataSetChanged()
    }
}

