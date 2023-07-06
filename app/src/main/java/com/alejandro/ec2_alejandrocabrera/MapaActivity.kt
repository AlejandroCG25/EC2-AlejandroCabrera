package com.alejandro.ec2_alejandrocabrera

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

class MapaActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var map: GoogleMap
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapa)
        val fragmentMap = supportFragmentManager.findFragmentById(R.id.fragment_map) as SupportMapFragment
        fragmentMap.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val idatsjm = LatLng(-12.157033823884738, -76.98108331621332)
        val idatate = LatLng(-12.02372817140898, -76.91307017679975)
        val idatcentro = LatLng(-12.067113813166195, -77.03578932811617)
        map.addMarker(MarkerOptions().title("SJM").position(idatsjm))
        map.addMarker(MarkerOptions().title("ATE").position(idatate))
        map.addMarker(MarkerOptions().title("CENTRO").position(idatcentro))

        val bounds = LatLngBounds.Builder()
        bounds.include(idatsjm)
        bounds.include(idatate)
        bounds.include(idatcentro)

        map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(),150))
    }
}