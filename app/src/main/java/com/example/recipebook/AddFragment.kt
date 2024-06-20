package com.example.recipebook

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipebook.DataBase.CPFC
import com.example.recipebook.DataBase.Recipe
import com.example.recipebook.DataBase.RecipeDb
import com.example.recipebook.DataBase.RecipeTitle
import com.example.recipebook.DataBase.RecipeViewModel
import com.example.recipebook.Recipe.AddIngredientAdapter
import com.example.recipebook.Recipe.Step
import com.example.recipebook.Recipe.AddStepAdapter
import com.example.recipebook.Recipe.Ingredient
import com.example.recipebook.databinding.FragmentAddBinding

class AddFragment : Fragment() {

    private val binding get() = _binding!!
    private var _binding: FragmentAddBinding? = null
    private lateinit var rViewModel: RecipeViewModel

    private val addIngredientAdapter = AddIngredientAdapter()
    private val addStepAdapter = AddStepAdapter()
    private var index = 0

    private var imageOfTitle: Uri? = null
    private var imageUri: Uri? = null
    private var determinantImage: Int = 0
    private var determinantIngridient: Boolean = false
    private var determinantStep: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        init()


        return binding.root
    }


    private fun init() {
        binding.apply {
            rcAddIngredients.layoutManager = LinearLayoutManager(activity)
            rcAddIngredients.adapter = addIngredientAdapter

            rcAddStep.layoutManager = LinearLayoutManager(activity)
            rcAddStep.adapter = addStepAdapter


            buttonAddIngredient.setOnClickListener {
                val ingredient = Ingredient("", "")
                addIngredientAdapter.addIngredient(ingredient)
            }
            buttonRemoveIngredient.setOnClickListener {
                if (addIngredientAdapter.addIngredientList.size > 0) {
                    addIngredientAdapter.removeIngredient()
                }
            }
            buttonAddStep.setOnClickListener {
                Toast.makeText(context, "Выберите изображение шага", Toast.LENGTH_LONG).show()
                pickImageFromGalleryToStep()
            }
            buttonRemoveStep.setOnClickListener {
                if (addStepAdapter.addStepList.size > 0) {
                    index--
                    addStepAdapter.removeStep()
                }
            }

            buttonSetImage.setOnClickListener {
                Toast.makeText(context, "Выберите изображение блюда", Toast.LENGTH_LONG).show()
                pickImageFromGalleryToTitle()
            }


            buttonFinishRecipe.setOnClickListener {
                determinantIngridient = true
                determinantStep = true
                if (edTitle.text.isNotEmpty()) {
                    if (imageOfDish.drawable != null) {
                        if (edQuantityOfCalories.text.isNotEmpty() && edQuantityOfCarbohydrates.text.isNotEmpty() && edQuantityOfFats.text.isNotEmpty() && edQuantityOfProteins.text.isNotEmpty()) {
                            if (addIngredientAdapter.addIngredientList.size != 0) {
                                for (ingredient in addIngredientAdapter.addIngredientList) {
                                    if (ingredient.ingredientGramms.isEmpty() || ingredient.ingredientName.isEmpty()) {
                                        determinantIngridient = false
                                    }
                                }
                                if (determinantIngridient) {
                                    if (addStepAdapter.addStepList.isNotEmpty()) {
                                        for (step in addStepAdapter.addStepList) {
                                            if (step.descriptionOfStep.isEmpty())
                                                determinantStep = false
                                        }
                                        if (determinantStep) {

                                            rViewModel = ViewModelProvider(this@AddFragment).get(RecipeViewModel::class.java)
                                            val recipe = Recipe(
                                                null,
                                                edTitle.text.toString(),
                                                imageOfTitle,
                                                CPFC(edQuantityOfCalories.text.toString(), edQuantityOfProteins.text.toString(), edQuantityOfFats.text.toString(), edQuantityOfCarbohydrates.text.toString()),
                                                addIngredientAdapter.addIngredientList,
                                                addStepAdapter.addStepList
                                            )
                                            rViewModel.insertRecipe(recipe)
                                            rViewModel.insertRecipeTitle((RecipeTitle(null, edTitle.text.toString(), imageOfTitle)))
                                            Toast.makeText(context, "Рецепт добавлен", Toast.LENGTH_SHORT).show()
                                            val navController = Navigation.findNavController(edTitle)
                                            navController.navigate(R.id.navigation_main)


                                        } else Toast.makeText(context, "Закончите описание шага", Toast.LENGTH_SHORT).show()
                                    } else Toast.makeText(context, "Добавьте шаги приготовления", Toast.LENGTH_SHORT).show()
                                } else Toast.makeText(context, "Закончите описание ингрединтов", Toast.LENGTH_SHORT).show()
                            } else Toast.makeText(context, "Добавьте ингридиенты", Toast.LENGTH_SHORT).show()
                        } else Toast.makeText(context, "Введите количество калорий, белков, жиров и углеводов", Toast.LENGTH_SHORT).show()
                    } else Toast.makeText(context, "Добавьте изображение блюда", Toast.LENGTH_SHORT).show()
                } else Toast.makeText(context, "Введите название рецепта", Toast.LENGTH_SHORT).show()
            }
        }
    }



    fun fromUri(uri: Uri?): String{
        return uri.toString()
    }

    override fun onResume() {
        super.onResume()
        when (determinantImage) {
            1 -> { binding.imageOfDish.setImageURI(imageUri)
                imageOfTitle = imageUri
                determinantImage = 0
            }

            2 -> {
                index++
                val step = Step(fromUri(imageUri), "", index)
                addStepAdapter.addStep(step)
                determinantImage = 0
            }
        }
    }

    private fun pickImageFromGalleryToTitle() {
        imageUri = null
        determinantImage = 1
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 1)
    }

    private fun pickImageFromGalleryToStep() {
        imageUri = null
        determinantImage = 2
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            imageUri = data?.data
        } else determinantImage = 0
    }
}