package com.example.recipebook.Recipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.recipebook.DataBase.RecipeTitle
import com.example.recipebook.R
import com.example.recipebook.databinding.AddIngredientItemBinding
import com.example.recipebook.databinding.IngredientItemBinding

class IngredientAdapter: RecyclerView.Adapter<IngredientAdapter.IngredientHolder>() {

    private var ingredientList = emptyList<Ingredient>()

    class IngredientHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = IngredientItemBinding.bind(item)

        fun bind(ingredient: Ingredient) = with(binding){
            textIngredient.text = ingredient.ingredientName
            textIngredientGramms.text = ingredient.ingredientGramms
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ingredient_item, parent, false)
        return IngredientHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientHolder, position: Int) {
        holder.bind(ingredientList[position])
    }

    override fun getItemCount(): Int {
        return ingredientList.size
    }

    fun setIngredientList(ingredientList: List<Ingredient>) {
        this.ingredientList = ingredientList
        notifyDataSetChanged()
    }
}



data class Ingredient(var ingredientName: String, var ingredientGramms: String)



class AddIngredientAdapter: RecyclerView.Adapter<AddIngredientAdapter.AddIngredientHolder>() {

    var addIngredientList = ArrayList<Ingredient>()

    class AddIngredientHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = AddIngredientItemBinding.bind(item)
        fun bind(ingredient: Ingredient) = with(binding){
            buttonFinishIngredient.setOnClickListener {
                if (etIngredient.text.isNotEmpty() || etIngredientGramms.text.isNotEmpty()) {
                    ingredient.ingredientName = etIngredient.text.toString()
                    ingredient.ingredientGramms = etIngredientGramms.text.toString()
                    Toast.makeText(etIngredient.context, "Описание ингредиента закончено", Toast.LENGTH_SHORT).show()
                } else Toast.makeText(etIngredient.context, "Введите описание ингридиентов", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddIngredientHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.add_ingredient_item, parent, false)
        return AddIngredientHolder(view)
    }

    override fun onBindViewHolder(holder: AddIngredientHolder, position: Int) {
        holder.bind(addIngredientList[position])
    }

    override fun getItemCount(): Int {
        return addIngredientList.size
    }

    fun addIngredient(ingredient: Ingredient){
        addIngredientList.add(ingredient)
        notifyDataSetChanged()
    }

    fun removeIngredient() {
        addIngredientList.removeLast()
        notifyDataSetChanged()
    }
}