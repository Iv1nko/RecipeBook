package com.example.recipebook.Recipe


import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.room.TypeConverter
import com.example.recipebook.DataBase.RecipeTitle
import com.example.recipebook.R
import com.example.recipebook.databinding.AddStepItemBinding
import com.example.recipebook.databinding.StepItemBinding

class StepAdapter: RecyclerView.Adapter<StepAdapter.StepHolder>() {

    private var stepList = emptyList<Step>()

    class StepHolder(item: View): RecyclerView.ViewHolder(item) {
        private val binding = StepItemBinding.bind(item)

        fun bind(step: Step) = with(binding){
            imageOfStep.setImageURI(Uri.parse(step.uriStringImageOfStep))
            textDescriptionOfStep.text = step.descriptionOfStep
            textStep.text = step.numberOfStep.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.step_item, parent, false)
        return StepHolder(view)
    }

    override fun onBindViewHolder(holder: StepHolder, position: Int) {
        holder.bind(stepList[position])
    }

    override fun getItemCount(): Int {
        return stepList.size
    }

    fun setStepList(stepList: List<Step>) {
        this.stepList = stepList
        notifyDataSetChanged()
    }
}


data class Step(val uriStringImageOfStep: String, var descriptionOfStep: String, val numberOfStep: Int)


class AddStepAdapter: RecyclerView.Adapter<AddStepAdapter.StepHolder>() {

    var addStepList = ArrayList<Step>()



    class StepHolder(item: View): RecyclerView.ViewHolder(item){
        private val binding = AddStepItemBinding.bind(item)

        fun bind(step: Step) = with(binding) {
            textStep.text = step.numberOfStep.toString()
            imageOfStep.setImageURI(Uri.parse(step.uriStringImageOfStep))
            buttonFinishStep.setOnClickListener {
                if (etDescriptionOfStep.text.toString().length != 0) {
                    step.descriptionOfStep = etDescriptionOfStep.text.toString()
                    Toast.makeText(etDescriptionOfStep.context, "Описание шага закончено", Toast.LENGTH_SHORT).show()
                } else Toast.makeText(textStep.context, "Введите описание шага", Toast.LENGTH_SHORT).show()
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.add_step_item, parent, false)
        return StepHolder(view)
    }

    override fun onBindViewHolder(holder: StepHolder, position: Int) {
        holder.bind(addStepList[position])
    }

    override fun getItemCount(): Int {
        return addStepList.size
    }


    fun addStep(addStep: Step){
        addStepList.add(addStep)
        notifyDataSetChanged()
    }
    fun removeStep() {
        addStepList.removeLast()
        notifyDataSetChanged()
    }
}