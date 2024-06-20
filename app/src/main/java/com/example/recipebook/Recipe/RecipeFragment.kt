package com.example.recipebook.Recipe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipebook.DataBase.RecipeDb
import com.example.recipebook.DataBase.RecipeTitle
import com.example.recipebook.R
import com.example.recipebook.databinding.FragmentRecipeBinding

class RecipeFragment : Fragment() {

    private var _binding: FragmentRecipeBinding? = null
    private val binding get() = _binding!!


    private val ingredientAdapter = IngredientAdapter()
    private val stepAdapter = StepAdapter()
    private lateinit var db: RecipeDb
//    private lateinit var rViewModel: RecipeViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        init()

        return binding.root
    }

    private fun init() {
        binding.apply {
//            rViewModel = ViewModelProvider(this@RecipeFragment).get(RecipeViewModel::class.java)
            db = RecipeDb.getDb(textTitle.context)
            db.getDao().getRecipe(this@RecipeFragment.arguments?.getInt("key")).observe(viewLifecycleOwner, Observer { recipe ->
                textTitle.text = recipe.title
                imageOfDish.setImageURI(recipe.imageOfRecipe)
                textQuantityOfCalories.text = recipe.cpfc.calories
                textQuantityOfProteins.text = recipe.cpfc.proteins
                textQuantityOfFats.text = recipe.cpfc.fats
                textQuantityOfCarbohydrates.text = recipe.cpfc.carbohydrates
                ingredientAdapter.setIngredientList(recipe.ingredientList)
                stepAdapter.setStepList(recipe.stepList)

                buttonDelete.setOnClickListener {
                    Thread {
                        db.getDao().deleteRecipe(recipe)
                        db.getDao().deleteRecipeTitle(
                            RecipeTitle(
                                this@RecipeFragment.arguments?.getInt("key"),
                                recipe.title,
                                recipe.imageOfRecipe
                            )
                        )
                    }.start()
                    val navController = Navigation.findNavController(it)
                    navController.navigate(R.id.action_navigation_recipe_to_navigation_main)
                    Toast.makeText(context, "Рецепт удален", Toast.LENGTH_SHORT).show()
                }
            })


            rcIngredients.layoutManager = LinearLayoutManager(activity)
            rcIngredients.adapter = ingredientAdapter

            rcStep.layoutManager = LinearLayoutManager(activity)
            rcStep.adapter = stepAdapter
        }
    }
}