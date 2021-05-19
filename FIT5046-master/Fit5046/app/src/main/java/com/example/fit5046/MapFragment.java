package com.example.fit5046;

import android.app.Fragment;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    View vDisplayMap;
    MapView mMapView;
    private String API_KEY="AIzaSyBDYYeO-iwtYGeRxqp59eSQeI40z8y5mzg";
    private GoogleMap mMap;
    double latitude ;
    double longitude ;
    private String location;
    String address;
    Button daily,hourly;




    Calendar c = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String hours = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
    String formatted_date = sdf.format(c.getTime());

    public MarkerOptions options = new MarkerOptions();



    //ArrayList<AllUserInfo> auiL = new ArrayList<AllUserInfo>();

    //AllUserInfo aui = new AllUserInfo();
    String address5 = "4/10 Repton Road";
    LatLng p1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vDisplayMap = inflater.inflate(R.layout.fragment_map, container, false);
        mMapView = (MapView) vDisplayMap.findViewById(R.id.map);



        return vDisplayMap;
    }
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        if(mMapView != null){
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);


        }

    }
   /* public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;

        LatLng p1 = null;

        try {
            // May throw an IOException

            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            if (address.size() == 0) {
                return null;
            }
            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude());



        } catch (IOException ex) {

            ex.printStackTrace();
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        return p1;
    }

    public void onMapReady(final GoogleMap googleMap)  {
        p1 = getLocationFromAddress(vDisplayMap.getContext(),address5);
        MapsInitializer.initialize(vDisplayMap.getContext());
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        CameraPosition resAddress = CameraPosition.builder().target(p1).zoom(16).bearing(0).tilt(45).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(resAddress));






    }
*/
   @Override
   public void onMapReady(GoogleMap googleMap) {
       mMap = googleMap;
       // String[] latlong =  getLocation().substring(10,getLocation().length()-1).split(",");
       final String[] latlong = {"-37.9","145.1"};
       final double latitude = Double.parseDouble(latlong[0]);
       final double longitude = Double.parseDouble(latlong[1]);
       LatLng home = new LatLng(latitude, longitude);
       mMap.addMarker(new MarkerOptions().position(home).title("Home").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
       mMap.moveCamera(CameraUpdateFactory.newLatLng(home));
       float zoomLevel = 16.0f;
       mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(home, zoomLevel));
       String nearbyPark = "park";
       String url = getUrl(latitude, longitude, nearbyPark);
       Object[] DataTransfer = new Object[2];
       DataTransfer[0] = mMap;
       DataTransfer[1] = url;
       GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
       getNearbyPlacesData.execute(DataTransfer);
       //Toast.makeText(MapsActivity.this,"Nearby parks", Toast.LENGTH_LONG).show();


   }

    private String getUrl(double latitude, double longitude, String nearbyPlace) {
        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=" + latitude + "," + longitude);
        googlePlacesUrl.append("&radius=" + 5000);
        googlePlacesUrl.append("&type=" + nearbyPlace);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + API_KEY);
        return (googlePlacesUrl.toString());
    }
}
