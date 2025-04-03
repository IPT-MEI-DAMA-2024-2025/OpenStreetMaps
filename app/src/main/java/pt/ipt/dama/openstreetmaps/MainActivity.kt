package pt.ipt.dama.openstreetmaps

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.config.Configuration

class MainActivity : AppCompatActivity() {

    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize osmdroid configuration
        // import org.osmdroid.config.Configuration
        Configuration.getInstance()
            .load(this, applicationContext.getSharedPreferences("osmdroid", MODE_PRIVATE))

        // Find the MapView and set its properties
        mapView = findViewById(R.id.mapView)
        mapView.setMultiTouchControls(true)
        mapView.controller.setZoom(16.0)
        val startPoint =
            GeoPoint(39.59963776764394, -8.391843590532233) // Coordinates da sala O103, IPT
        mapView.controller.setCenter(startPoint)


        // Add a marker to the map
        val marker = Marker(mapView)
        marker.position = startPoint
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        marker.title = "IPT"
        mapView.overlays.add(marker)

        Log.d("MainActivity", "Map initialized and centered on room O103 (IPT)")
    }



    override fun onResume() {
        super.onResume()
        mapView.onResume() // needed for compass, my location overlays, v6.0.0 and up
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause() // needed for compass, my location overlays, v6.0.0 and up
    }

}