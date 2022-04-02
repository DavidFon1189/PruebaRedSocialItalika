package com.example.redsocialapp.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.redsocialapp.R
import com.example.redsocialapp.databinding.SignUpActivityBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class SignupActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: SignUpActivityBinding
    private lateinit var auth: FirebaseAuth
    private val dataBase = Firebase.database
    private val myRef = dataBase.getReference("usuario")
    val REQUEST_TAKE_PHOTO = 1
    lateinit var currentPhotoPath: String
    private var id: String = ""
    private var uri: Uri? = null
    private var photoURI: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignUpActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.btnSignUpMain.setOnClickListener(this)
        binding.imgSelfie.setOnClickListener(this)
        auth = Firebase.auth
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_sign_up_main -> {
                val name = binding.edtNameAccount.text.toString().trim()
                val lastName = binding.edtAppAccount.text.toString().trim()
                val random = Random()
                val randomDigits = random.nextInt(999999)
                val formatDigits = String.format("%06d", randomDigits)
                id = name.substring(0, 2).uppercase(Locale.getDefault()) + lastName.substring(0, 2)
                        .uppercase(Locale.getDefault()) + formatDigits
                Log.d("TAG", "NUMER: $id")
                fileUpLoad()
                auth.createUserWithEmailAndPassword(
                    binding.edtUserLogin.text.toString(),
                    binding.edtPass.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        startActivity(Intent(this, LoginActivity::class.java))
                    } else {
                        val buildDialog = AlertDialog.Builder(this)
                        buildDialog.setTitle("ERROR")
                        buildDialog.setMessage("Error de autenticacion")
                        buildDialog.setPositiveButton("Aceptar", null)
                        val dialog: AlertDialog = buildDialog.create()
                        dialog.show()
                    }
                }
            }
            R.id.img_selfie -> {
                dispatchTakePictureIntent()
            }
        }
    }

    private fun fileUpLoad(){
        val folder: StorageReference = FirebaseStorage.getInstance().reference.child(id)
        val path = uri?.lastPathSegment.toString()
        val fileName: StorageReference = folder.child(path.substring(path.lastIndexOf('/') + 1))

        fileName.putFile(uri!!).addOnSuccessListener {
            fileName.downloadUrl.addOnSuccessListener {
                val hasMap = HashMap<String, String>()
                hasMap["link"] = it.toString()
                myRef.child(myRef.push().key.toString()).setValue(hasMap)
                Toast.makeText(this, "Imagen guardada en Storage", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener{
            Toast.makeText(this, "No se guardo la imagen en Storage", Toast.LENGTH_SHORT).show()
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    photoURI = FileProvider.getUriForFile(
                        this,
                        "com.example.android.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }


    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                uri = photoURI
                Log.d("TAG", "SELFIE URI: $uri")
            }

        }
    }
}