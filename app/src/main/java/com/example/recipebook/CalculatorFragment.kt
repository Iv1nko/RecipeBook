package com.example.recipebook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import com.example.recipebook.databinding.FragmentCalculatorBinding


class CalculatorFragment : Fragment() {

    private var _binding: FragmentCalculatorBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalculatorBinding.inflate(inflater, container, false)
        val view = binding.root

        with(binding){
            buttonMale.setOnClickListener {
                if (buttonMale.isChecked) {
                    buttonFemale.isChecked = false
                }
            }

            buttonFemale.setOnClickListener {
                if (buttonFemale.isChecked) {
                    buttonMale.isChecked = false
                }
            }

            buttonDecreaseWeight.setOnClickListener {
                if (buttonDecreaseWeight.isChecked) {
                    buttonSupportWeight.isChecked = false
                    buttonIncreaseWeight.isChecked = false
                }
            }

            buttonIncreaseWeight.setOnClickListener {
                if (buttonIncreaseWeight.isChecked) {
                    buttonSupportWeight.isChecked = false
                    buttonDecreaseWeight.isChecked = false
                }
            }

            buttonSupportWeight.setOnClickListener {
                if (buttonSupportWeight.isChecked) {
                    buttonDecreaseWeight.isChecked = false
                    buttonIncreaseWeight.isChecked = false
                }
            }

            buttonCount.setOnClickListener {
                if (buttonMale.isChecked || buttonFemale.isChecked) {
                    if(buttonIncreaseWeight.isChecked || buttonDecreaseWeight.isChecked || buttonSupportWeight.isChecked) {
                        if (editAge.text.toString().length != 0 && editHeight.text.toString().length != 0 && editWeight.text.toString().length != 0) {
                            invisibleParams(); visibleResult()
                            val aim: Double = if (buttonDecreaseWeight.isChecked) 0.85
                            else if (buttonSupportWeight.isChecked) 1.0
                            else 1.15

                            if (buttonMale.isChecked) {
                                val calories =
                                    (88.362 + (13.397 * textToInt(editWeight.text) + (4.799 * textToInt(
                                        editHeight.text
                                    )) - (5.677 * textToInt(editAge.text)))) * aim * 1.2
                                textCalories.text = String.format("%.0f", calories) + " калорий"
                            } else {
                                val calories =
                                    (447.593 + (9.247 * textToInt(editWeight.text) + (3.098 * textToInt(
                                        editHeight.text
                                    )) - (4.33 * textToInt(editAge.text)))) * aim * 1.2
                                textCalories.text = String.format("%.0f", calories) + " калорий"
                            }

                            //            TODO("Сохранение состояния")
                            //            if (!(binding.buttonMale.isChecked and binding.buttonFemale.isChecked)) {
                            //                Toast.makeText(context, "Выберите пол", Toast.LENGTH_SHORT).show() }
                            //                else if (binding.buttonDecreaseWeight
                            //                        .isChecked == false and
                            //                    binding.buttonIncreaseWeight
                            //                        .isChecked == false and
                            //                    binding.buttonSupportWeight
                            //                        .isChecked == false
                            //                ) {
                            //                    Toast.makeText(getContext(), "Выберите цель", Toast.LENGTH_SHORT).show()
                            //                }
                        } else Toast.makeText(context, "Введите ваш возраст, рост, вес", Toast.LENGTH_SHORT).show()
                    }else Toast.makeText(context, "Выберите вашу цель", Toast.LENGTH_SHORT).show()
                }else Toast.makeText(context, "Выберите пол", Toast.LENGTH_SHORT).show()
            }

            buttonRecount.setOnClickListener {
                invisibleResult(); visibleParams()
            }
        }

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun invisibleParams() {
        with(binding) {
            textGeneral.visibility = GONE
            buttonMale.visibility = GONE
            buttonFemale.visibility = GONE
            textAge.visibility = GONE
            editAge.visibility = GONE
            textHeight.visibility = GONE
            editHeight.visibility = GONE
            textWeight.visibility = GONE
            editWeight.visibility = GONE
            textAim.visibility = GONE
            buttonDecreaseWeight.visibility = GONE
            buttonSupportWeight.visibility = GONE
            buttonIncreaseWeight.visibility = GONE
            buttonCount.visibility = GONE
      }
    }

    private fun visibleParams() {
        with(binding) {
            textGeneral.visibility = VISIBLE
            buttonMale.visibility = VISIBLE
            buttonFemale.visibility = VISIBLE
            textAge.visibility = VISIBLE
            editAge.visibility = VISIBLE
            textHeight.visibility = VISIBLE
            editHeight.visibility = VISIBLE
            textWeight.visibility = VISIBLE
            editWeight.visibility = VISIBLE
            textAim.visibility = VISIBLE
            buttonDecreaseWeight.visibility = VISIBLE
            buttonSupportWeight.visibility = VISIBLE
            buttonIncreaseWeight.visibility = VISIBLE
            buttonCount.visibility = VISIBLE
        }
    }

    private fun visibleResult() {
        binding.textQuota.visibility = VISIBLE
        binding.textCalories.visibility = VISIBLE
        binding.buttonRecount.visibility = VISIBLE
    }

    private fun invisibleResult() {
        binding.textQuota.visibility = GONE
        binding.textCalories.visibility = GONE
        binding.buttonRecount.visibility = GONE
    }
}

fun textToInt(a: Any): Int {
    return Integer.parseInt(a.toString())
}