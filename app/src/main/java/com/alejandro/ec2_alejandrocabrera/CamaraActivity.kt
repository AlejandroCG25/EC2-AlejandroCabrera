package com.alejandro.ec2_alejandrocabrera

import android.content.Intent
import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.alejandro.ec2_alejandrocabrera.databinding.ActivityCamaraBinding

class CamaraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCamaraBinding
    private lateinit var openCamaraLauncher : ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCamaraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnFoto.setOnClickListener {
            if(permissionValidated()) {
                takePicture()
            }
        }
        openCamaraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result ->
            if (result.resultCode == RESULT_OK){
                val photo = result.data?.extras?.get("data") as Bitmap
                binding.imgFoto.setImageBitmap(photo)
            }
        }
        binding.btnMapa.setOnClickListener {
            /*val addressUri = Uri.parse("geo:0,0?q=-12.1189716,-77.029266")
            val intent = Intent(Intent.ACTION_VIEW, addressUri)
            intent.setPackage("com.google.android.apps.maps")
            intent.resolveActivity(packageManager).let {
                startActivity(intent)

            }*/
            startActivity(Intent(this, MapaActivity::class.java))
        }
    }

    private fun takePicture() {
        val intent = Intent()
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE)
        openCamaraLauncher.launch(intent)
    }

    private fun permissionValidated(): Boolean {
        val cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        val permissionList: MutableList<String> = mutableListOf()
        if (cameraPermission != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.CAMERA)
        }
        if(permissionList.isNotEmpty()){
            ActivityCompat.requestPermissions(this, permissionList.toTypedArray(), 1000)
            false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode){
            1000 ->{
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                    takePicture()
                }
            }
        }
    }
}