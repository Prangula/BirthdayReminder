package com.example.birthdayreminder1.ui

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.birthdayreminder1.R
import com.example.birthdayreminder1.db.BirthdayDatabase
import com.example.birthdayreminder1.db.BirthdayItem
import com.example.birthdayreminder1.repository.BirthdayRepository
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.birthday_item.*
import java.util.Calendar

class BirthdayCardActivity : AppCompatActivity() {
    private lateinit var viewModel:BirthdayViewModel
    private var time1:String = ""
    private var imageUri: Uri? = null
    private var color:Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.birthday_item)

        toolbar()

        val database = BirthdayDatabase(this)
        val repository = BirthdayRepository(database)
        val factory = ViewModelFactory(repository)
        val viewModel = ViewModelProviders.of(this,factory).get(BirthdayViewModel::class.java)


        item_et_title.addTextChangedListener {

            item_to.text = it.toString()

        }

        item_et_calendar.addTextChangedListener {
            item_time.text = it.toString()
            time1 = it.toString()
        }

        item_et_time.addTextChangedListener {

            item_time.text = time1 + it.toString()

        }


        item_card_main.setCardBackgroundColor(ContextCompat.getColor(this,R.color.red))
        item_et_red.isChecked = true
        item_et_time.visibility = View.GONE
        item_title.text = "Happy Birthday"

        item_et_switch.setOnCheckedChangeListener { compoundButton, isChecked ->

            if (isChecked) {
                item_et_time.visibility = View.VISIBLE
            } else {
                item_et_time.visibility = View.GONE
            }

        }

        item_btn_image.setOnClickListener {

            gallery()
        }

        item_et_calendar.setOnClickListener {

            calendar()
        }

        item_et_time.setOnClickListener {
            time()
        }


        radioGroup()

        item_save.setOnClickListener {
            if(item_title.text.isNotEmpty() && item_to.text.isNotEmpty()
                && imageUri!=null && item_time.text.isNotEmpty()){

                val item = BirthdayItem(
                    "Happy Birthday",
                    item_to.text.toString(),
                    imageUri.toString(),
                    item_time.text.toString(),
                    R.color.red
                )

                viewModel.insert(item)
                finish()



            }
            else{

                Snackbar.make(it, "Please fill in all the fields", Snackbar.LENGTH_LONG).show()



            }
        }






    }

    private fun toolbar(){

        setSupportActionBar(toolbar_card)
        toolbar_card.setNavigationIcon(R.drawable.baseline_arrow_back_24)

        toolbar_card.setNavigationOnClickListener {
            onBackPressed()
        }

    }

    private fun calendar(){

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,{_,year,month,day ->

                item_et_calendar.setText("$day/${month+1}/$year, ")
            },
            year,
            month,
            day
        )

        datePickerDialog.show()

    }

    private fun time(){

        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this, {_,hour,minute->

                val formattedMinute = String.format("%02d", minute)
                val formattedHour = String.format("%02d",hour)
                item_et_time.setText("$formattedHour:$formattedMinute")

            },
            hour,
            minute,
            true
        )

        timePickerDialog.show()


    }

    private fun radioGroup(){

        item_radiogroup_color.setOnCheckedChangeListener { radioGroup, i ->

         when(i){

                R.id.item_et_red -> {
                    item_card_main.setCardBackgroundColor(ContextCompat.getColor(this,R.color.red))
                }

                R.id.item_et_blue -> {
                    item_card_main.setCardBackgroundColor(ContextCompat.getColor(this,R.color.blue))
                }

                R.id.item_et_yellow -> {
                    item_card_main.setCardBackgroundColor(ContextCompat.getColor(this,R.color.yellow))
                }

                R.id.item_et_pink -> {
                    item_card_main.setCardBackgroundColor(ContextCompat.getColor(this,R.color.pink))
                }

                R.id.item_et_green -> {
                    item_card_main.setCardBackgroundColor(ContextCompat.getColor(this,R.color.green))
                }

                R.id.item_et_black -> {
                    item_card_main.setCardBackgroundColor(ContextCompat.getColor(this,R.color.black))
                }

                R.id.item_et_orange -> {
                    item_card_main.setCardBackgroundColor(ContextCompat.getColor(this,R.color.orange))
                }


               else -> {}
           }

        }

    }

    private fun gallery(){

        Dexter.withActivity(this)
            .withPermissions(
                android.Manifest.permission.READ_MEDIA_IMAGES
            ).withListener(object: MultiplePermissionsListener {
                override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                    if(p0!!.areAllPermissionsGranted()){

                        val galleryIntent = Intent(
                            Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        )
                        startActivityForResult(galleryIntent,GALLERY)

                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<PermissionRequest>?,
                    p1: PermissionToken?
                ) {
                    showRationaleDialogForPermissions()
                }

            }).onSameThread().check()


    }

    companion object {

        const val GALLERY = 1

    }

    private fun showRationaleDialogForPermissions(){

        AlertDialog.Builder(this).
        setMessage("turn off")
            .setPositiveButton("GO TO SETTINGS"){
                    _,_ ->
                try {

                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package",packageName,null)
                    intent.data = uri
                    startActivity(intent)
                }catch (e: ActivityNotFoundException){
                    e.printStackTrace()
                }

            }.setNegativeButton("Cancel"){dialog,which ->
                dialog.dismiss()

            }.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == GALLERY && data!!.data!=null){

            imageUri = data!!.data

            Glide
                .with(this)
                .load(imageUri)
                .into(item_image)

        }
    }
}