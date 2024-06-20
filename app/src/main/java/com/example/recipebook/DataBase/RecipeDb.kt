package com.example.recipebook.DataBase

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.room.Database
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.recipebook.Recipe.Ingredient
import com.example.recipebook.Recipe.Step
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream


@Database (entities = [Recipe::class, RecipeTitle::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class RecipeDb: RoomDatabase() {

    abstract fun getDao(): Dao

    companion object {
        @Volatile
        private var INSTANCE: RecipeDb? = null

        fun getDb(context: Context): RecipeDb {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, RecipeDb::class.java, "dish.db").build()
                INSTANCE = instance
                return instance
            }
        }
    }
}


@Entity (tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    var title: String,
    var imageOfRecipe: Uri?,

    @Embedded
    var cpfc: CPFC,

    var ingredientList: List<Ingredient>,

    var stepList: List<Step>
)

@Entity (tableName = "recipeTitles")
data class RecipeTitle(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    var title: String,
    var imageOfRecipe: Uri?,
)


class Converters{
    @TypeConverter
    fun fromIngredientList(ingredientList: List<Ingredient>): String {
        return Gson().toJson(ingredientList)
    }
    @TypeConverter
    fun toIngredientList(json: String): List<Ingredient> {
        return Gson().fromJson(json, Array<Ingredient>::class.java).toList()
    }

    @TypeConverter
    fun fromStepList(stepList: List<Step>): String {
        return Gson().toJson(stepList)
    }

    @TypeConverter
    fun toStepList(json: String): List<Step> {
        return Gson().fromJson(json, Array<Step>::class.java).toList()
    }

    @TypeConverter
    fun fromUri(uri: Uri): String{
        return uri.toString()
    }
    @TypeConverter
    fun toUri(string: String): Uri?{
        return Uri.parse(string)
    }
}



data class CPFC(val calories: String, val proteins: String, val fats: String, val carbohydrates: String)