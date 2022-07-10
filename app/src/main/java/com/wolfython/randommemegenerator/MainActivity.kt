package com.wolfython.randommemegenerator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.wolfython.randommemegenerator.Api.RetrofitBuilder
import com.wolfython.randommemegenerator.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
//    late initialization for viewbinding
    lateinit var Mainbinding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         Mainbinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(Mainbinding.root)


        getMemes()
        Mainbinding.progress.visibility= View.VISIBLE


     /*   calling this api to generate new memes*/
        Mainbinding.button.setOnClickListener { getMemes() }

    }


  /*  getting memes */
    @OptIn(DelicateCoroutinesApi::class)
    private fun getMemes(){
      GlobalScope.launch(Dispatchers.IO) {
            val response = RetrofitBuilder.apiService.MemeGet()
            withContext(Dispatchers.Main) {
                try {
                    if(response.code() == 200)
                    {
                      val imagememe = response.body()!!.image

                        Glide.with(this@MainActivity)
                            .load(imagememe)
                            .fitCenter()
                            .into(Mainbinding.imageView)

                        val textmeme = response.body()!!.caption

                        Mainbinding.textView.setText(textmeme)

                        Log.e("TAG", "getMemes: "+response.body().toString() )
                        Mainbinding.progress.visibility= View.GONE

                    }
                    else if (response.code() == 401) {
                        Toast.makeText(
                           this@MainActivity,
                            "Try Again",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }
                catch (e:Exception){
                    e.printStackTrace()
                    Toast.makeText(this@MainActivity, "Something Went Wrong", Toast.LENGTH_SHORT).show()
                    Log.d("MainActivity", "Exception: $e")
                }
            }
        }

    }
}