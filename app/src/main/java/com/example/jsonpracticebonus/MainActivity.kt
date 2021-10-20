package com.example.jsonpracticebonus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.net.URL

class MainActivity : AppCompatActivity() {
    lateinit var etnumber:EditText
    lateinit var button: Button
    lateinit var textView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etnumber=findViewById(R.id.etnumber)
        button=findViewById(R.id.button)
        textView=findViewById(R.id.textView)

        button.setOnClickListener {
            CallApi()
        }
    }

    private fun CallApi() {
        CoroutineScope(Dispatchers.IO).launch{
            try {
                val api = URL(" https://dojo-recipes.herokuapp.com/contacts/")
                    .readText(Charsets.UTF_8)
                if (api.isNotEmpty()){
                    getData(api)
                }else {
                    Toast.makeText(this@MainActivity,"NULL VALUE", Toast.LENGTH_LONG).show()
                }
            }catch (e: Exception){
                println("Error $e")
            }
        }
    }

    private suspend fun getData(api: String) {
        withContext(Dispatchers.Main){
            val names = JSONArray(api)
            val n=etnumber.text.toString().toInt()-1
            for (i in 0 until names.length()){
                if (n==i && n>=0 && n<13){
                    val name=names.getJSONObject(i).getString("name")
                    textView.text=name
                }
            }
        }
    }
}