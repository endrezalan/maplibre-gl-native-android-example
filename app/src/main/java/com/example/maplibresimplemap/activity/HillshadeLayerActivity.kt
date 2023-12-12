package com.example.maplibresimplemap.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.maplibresimplemap.R
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.layers.HillshadeLayer
import com.mapbox.mapboxsdk.style.layers.PropertyFactory
import com.mapbox.mapboxsdk.style.sources.RasterDemSource

/**
 * Test activity showcasing using HillshadeLayer.
 */
class HillshadeLayerActivity : AppCompatActivity() {
    private lateinit var mapView: MapView
    private lateinit var mapboxMap: MapboxMap
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(this)
        setContentView(R.layout.activity_main)
        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(
            OnMapReadyCallback { map: MapboxMap? ->
                if (map != null) {
                    mapboxMap = map
                }
                val position = CameraPosition.Builder()
                    .target(LatLng(45.8793, 6.8874))
                    .zoom(12.0)
                    .tilt(0.0)
                    .build()
                val rasterDemSource = RasterDemSource(SOURCE_ID, SOURCE_URL)
                val hillshadeLayer = HillshadeLayer(LAYER_ID, SOURCE_ID).apply {
                    setProperties(
                        PropertyFactory.hillshadeExaggeration(1.5f),
                        PropertyFactory.hillshadeHighlightColor("rgba(255,255,255,0.5)"),
                        PropertyFactory.hillshadeShadowColor("rgba(0,0,0,0.5)"),
                        PropertyFactory.hillshadeIlluminationDirection(335f)
                    )
                }
                mapboxMap.setStyle(
                    Style.Builder()
                        .fromUri("https://api.maptiler.com/maps/satellite/style.json?key=YOUR_API_KEY")
                        .withSource(rasterDemSource)
                        .withLayer(hillshadeLayer)
                )

                mapboxMap.cameraPosition = position
                mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 2000)
            }
        )
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    public override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    companion object {
        private const val LAYER_ID = "hillshade"
        private const val LAYER_BELOW_ID = "water_intermittent"
        private const val SOURCE_ID = "terrain-rgb"
        private const val SOURCE_URL = "https://api.maptiler.com/tiles/terrain-rgb-v2/tiles.json?key=YOUR_API_KEY"
    }
}
