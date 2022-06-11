package shape.edu.hkmilkteaapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class MapsFragment : Fragment(), OnMapReadyCallback, LocationListener {

    private lateinit var locationManager: LocationManager
    private lateinit var mMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        getLocation()
    }

    private fun getLocation() {
        locationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        //check to see if permission is granted
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // request for location permission if it is not granted
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
        }
    }
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val lfy = LatLng(22.28271409, 114.1537416)
        mMap.addMarker(MarkerOptions().position(lfy).title("Lan Fong Yuen").icon(
            BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)))

        val bkcd = LatLng(22.27843621, 114.1923237)
        mMap.addMarker(MarkerOptions().position(bkcd).title("Bing Kee Cha Dong").icon(
            BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)))

        val kfr = LatLng(22.27547869, 114.1726058)
        mMap.addMarker(MarkerOptions().position(kfr).title("Kam Fung Restaurant").icon(
            BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)))

        val kwcb = LatLng(22.32228942, 114.169707)
        mMap.addMarker(MarkerOptions().position(kwcb).title("Kam Wah Cafe & Bakery").icon(
            BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)))

        val kpc = LatLng(22.33433864, 114.1966145)
        mMap.addMarker(MarkerOptions().position(kpc).title("Kam Po Cafe").icon(
            BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)))

        val skc = LatLng(22.28615313, 114.1497718)
        mMap.addMarker(MarkerOptions().position(skc).title("Shui Kee Coffee").icon(
            BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)))

        val ly = LatLng(22.32945503, 114.1889161)
        mMap.addMarker(MarkerOptions().position(ly).title("Lok Yuen").icon(
            BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)))

        val hcs = LatLng(22.27753151, 114.1739539)
        mMap.addMarker(MarkerOptions().position(hcs).title("Honolulu Coffee Shop").icon(
            BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)))

        val hhr = LatLng(22.27742431, 114.1764434)
        mMap.addMarker(MarkerOptions().position(hhr).title("Ho Hah Restaurant").icon(
            BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)))

        val swr = LatLng(22.33874434, 114.1561029)
        mMap.addMarker(MarkerOptions().position(swr).title("Sun Wah Restaurant").icon(
            BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)))

        val bkc = LatLng(22.32207287, 114.2165272)
        mMap.addMarker(MarkerOptions().position(bkc).title("Bak Kee Cafe").icon(
            BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)))

        val cc = LatLng(22.28511647, 114.1415766)
        mMap.addMarker(MarkerOptions().position(cc).title("Cross Cafe").icon(
            BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)))

        val ckc = LatLng(22.32872183, 114.1658877)
        mMap.addMarker(MarkerOptions().position(ckc).title("Chung Kee Cafe").icon(
            BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)))

        val ttc = LatLng(22.33312706, 114.1640199)
        mMap.addMarker(MarkerOptions().position(ttc).title("Tung Tin Cafe").icon(
            BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)))

        val wnc = LatLng(22.32962491, 114.1604592)
        mMap.addMarker(MarkerOptions().position(wnc).title("Wah Nam Cafe").icon(
            BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)))
    }

    override fun onLocationChanged(location: Location) {
        moveCamera(LatLng(location.latitude,location.longitude), 15f)
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        mMap.isMyLocationEnabled = true
    }
    private fun moveCamera(latlng : LatLng, zoom : Float){
        CoroutineScope(Main).launch {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, zoom))
        }
    }
}