package com.gamzeuysal.artbooksqlite

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.gamzeuysal.artbooksqlite.databinding.ActivityArtBinding
import com.google.android.material.snackbar.Snackbar

class ArtActivity : AppCompatActivity() {

    //View Binding
    private lateinit var binding: ActivityArtBinding
    //Launcher ile izinleri isteme ve yakalama
    private lateinit var permissionLauncher : ActivityResultLauncher<String>
    //galeriye gitmek  için laucher
    private lateinit var  activityResultLauncher: ActivityResultLauncher<Intent>
    var selectedBitmap : Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //launcher initialize
        registerLauncher()
    }
    private fun registerLauncher()
    {
        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback {   resultIntent ->
                //çekme işleminin başarılı gelip gelmediğini kontrol etmem lazım
                val intentFromResult = resultIntent.data
                if(resultIntent.resultCode == RESULT_OK)
                {
                    if(intentFromResult != null)
                    {
                        //image cekme BAŞARILI
                        val imageUri = intentFromResult?.data
                       // binding.imageView.setImageURI(imageUri)
                        if (imageUri != null)
                        {
                            try {
                                if(Build.VERSION.SDK_INT >= 28)
                                {
                                    //image Bitmap'e çevirme
                                    val source = ImageDecoder.createSource(this@ArtActivity.contentResolver,imageUri!!)
                                    selectedBitmap = ImageDecoder.decodeBitmap(source)
                                    binding.imageView.setImageBitmap(selectedBitmap)
                                }else
                                {
                                    //image Bitmap'e çevirme
                                    selectedBitmap = MediaStore.Images.Media.getBitmap(this@ArtActivity.contentResolver,imageUri)
                                    binding.imageView.setImageBitmap(selectedBitmap)

                                }

                            }catch (e: Exception)
                            {
                                e.printStackTrace()
                            }

                        }

                    }

                }else
                {
                    //image cekme BAŞARISIZ
                }
            })

        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission(),
            ActivityResultCallback {  result ->
                if(result)
                {
                    //izin verildi
                    val intentToGallery = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    activityResultLauncher.launch(intentToGallery)
                }
                else
                {
                    //izin verilmedi
                    Toast.makeText(this@ArtActivity,"Permission needed.",Toast.LENGTH_LONG).show()
                }
            })

    }

    fun selectImage(view: View) {
        //API LEVEL 33  ve 33+ -> permission -> android.permission.READ_MEDIA_IMAGES
        //API LEVEL 32-   -> permission -> android.permission.READ_EXTERNAL_STORAGE

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        {
            //API LEVEL 33  ve 33+ -> permission -> android.permission.READ_MEDIA_IMAGES
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_MEDIA_IMAGES
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                //not permission --> IZIN ISTEME
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        android.Manifest.permission.READ_MEDIA_IMAGES
                    )
                ) {
                    //rationale -> nedenini gösterek IZIN ISTEME
                    Snackbar.make(
                        binding.root,
                        "Permission needed for gallery",
                        Snackbar.LENGTH_INDEFINITE
                    )
                        .setAction("Give Permission",View.OnClickListener {
                            //setAction ile  belirlenen butona tıklanırsa ne olacak
                            //request permission
                            //IZIN ISTEME mesaj kutusu -> pop up göster
                            permissionLauncher.launch(android.Manifest.permission.READ_MEDIA_IMAGES)

                        }) .show()

                }
                else{
                    //rationale else
                    //nedenini göstermeden izin iste
                    //request permission
                    //IZIN ISTEME mesaj kutusu -> pop up göster
                    permissionLauncher.launch(android.Manifest.permission.READ_MEDIA_IMAGES)

                }

            } else {
                //permission granted
                //request permission
                //gallery resim çekme
                val intentToGallery = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)

            }


        }else
        {
            //API LEVEL 32-   -> permission -> android.permission.READ_EXTERNAL_STORAGE
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                //not permission --> IZIN ISTEME
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                ) {
                    //rationale -> nedenini gösterek IZIN ISTEME
                    Snackbar.make(
                        binding.root,
                        "Permission needed for gallery",
                        Snackbar.LENGTH_INDEFINITE
                    )
                        .setAction("Give Permission",View.OnClickListener {
                            //setAction ile  belirlenen butona tıklanırsa ne olacak
                            //request permission
                            //IZIN ISTEME mesaj kutusu -> pop up göster
                            permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)

                        }) .show()

                }
                else{
                    //rationale else
                    //nedenini göstermeden izin iste
                    //request permission
                    //IZIN ISTEME mesaj kutusu -> pop up göster
                    permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)

                }

            } else {
                //permission granted
                //request permission
                //gallery resim çekme
                val intentToGallery = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)

            }


        }



    }


    fun save (view : View)
    {

    }

}