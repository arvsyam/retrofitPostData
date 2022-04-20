package com.adl.retrofitpost

import android.app.Activity
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.ActionMode
import android.view.InputQueue
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.adl.retrofitpost.model.GetIjinResponse
import com.adl.retrofitpost.model.IjinItem
import com.adl.retrofitpost.model.PostIjinResponse
import com.adl.retrofitpost.service.RetrofitConfig
import com.github.drjacky.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.btn_add_new
import kotlinx.android.synthetic.main.activity_main.btn_from_date
import kotlinx.android.synthetic.main.activity_main.btn_image
import kotlinx.android.synthetic.main.activity_main.btn_untill_date
import kotlinx.android.synthetic.main.activity_main.et_from_date
import kotlinx.android.synthetic.main.activity_main.et_keterangan
import kotlinx.android.synthetic.main.activity_main.et_perihal
import kotlinx.android.synthetic.main.activity_main.et_untill_date
import kotlinx.android.synthetic.main.activity_main.photo
import kotlinx.android.synthetic.main.activity_main_temp.*
import retrofit2.Call

import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val uri = it.data?.data!!
            // Use the uri to load the image
            photo.setImageURI(uri)

            Log.d("uri image","${uri}")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_temp)

        initComponent()


        RetrofitConfig().getIjin().getAll().enqueue(object : Callback<GetIjinResponse>{
            override fun onResponse(
                call: Call<GetIjinResponse>,
                response: Response<GetIjinResponse>
            ) {
                Log.d("resp","${response.body()}")
            }

            override fun onFailure(call: Call<GetIjinResponse>, t: Throwable) {
                Log.e("error request",t.localizedMessage,t)
            }

        })


    }

    fun selectDate(et : EditText){
        var cal = Calendar.getInstance()
        val dateListener = DatePickerDialog.OnDateSetListener({
                datePicker, i, i2, i3 ->
            cal.set(i,i2,i3)
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            et.setText(sdf.format(cal.time))
        })
        DatePickerDialog(this@MainActivity, dateListener,
            cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
    }

    fun initComponent(){
        btn_from_date.setOnClickListener({
            Log.d("click btn","click")
            selectDate(et_from_date)
        })
        btn_untill_date.setOnClickListener({
            Log.d("click btn","click")
            selectDate(et_untill_date)
        })
        btn_add_new.setOnClickListener({

            RetrofitConfig().getIjin().addNewIjin(spn_kategori.selectedItem.toString(),et_from_date.text.toString(),et_untill_date.text.toString(),et_perihal.text.toString(),et_keterangan.text.toString()).enqueue(object : Callback<PostIjinResponse>{
                override fun onResponse(
                    call: Call<PostIjinResponse>,
                    response: Response<PostIjinResponse>
                ) {

                    if(response.isSuccessful()){
                        Toast.makeText(this@MainActivity,"Saved", Toast.LENGTH_LONG).show()
                    }else{
                        response.body().toString()
                        Toast.makeText(this@MainActivity,"not Saved", Toast.LENGTH_LONG).show()
                    }

                    var data:PostIjinResponse? = response.body()
                    Log.d("resp","${data}")
                }

                override fun onFailure(call: Call<PostIjinResponse>, t: Throwable) {
                    Log.e("error request",t.localizedMessage,t)
                }

            })
        })

        btn_image.setOnClickListener({
            Log.d("button click","btn_image")
            cameraLauncher.launch(
                ImagePicker.with(this)
                    .crop()
                    .cameraOnly()
                    .maxResultSize(480, 800, true)
                    .createIntent()
            )
        })
    }



}