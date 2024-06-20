package com.example.recipebook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recipebook.DataBase.RecipeDb
import com.example.recipebook.DataBase.RecipeViewModel
import com.example.recipebook.Recipe.RecipeAdapter
import com.example.recipebook.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val binding get() = _binding!!
    private var _binding: FragmentMainBinding? = null

    private val recipeAdapter = RecipeAdapter()
    private lateinit var rViewModel: RecipeViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        init()


        return binding.root
    }

    private fun init(){
        binding.apply{
            rViewModel = ViewModelProvider(this@MainFragment).get(RecipeViewModel::class.java)
            rViewModel.getAllRecipeTitle.observe(viewLifecycleOwner, Observer {
                recipeAdapter.setRecipeTitle(it)
            })
            rcView.layoutManager = GridLayoutManager(activity, 2)
            rcView.adapter = recipeAdapter

        }
    }
}