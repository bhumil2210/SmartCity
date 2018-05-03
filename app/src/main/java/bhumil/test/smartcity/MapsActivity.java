package bhumil.test.smartcity;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.media.Image;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseReference fd;
    private DatabaseReference locality;
    private final List<String> s = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {
        Geocoder coder = new Geocoder(this);
        List<Address> address;
        LatLng l = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null)
                return null;
            Log.e("address",""+address);
            Address location = address.get(0);
            Log.e("loc",""+location);
            location.getLatitude();
            location.getLongitude();

            l = new LatLng((double) (location.getLatitude()), (double) (location.getLongitude()));

        } catch (Exception e) {
            System.out.println("EXCEPTION");
        }
        return l;

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
//                MarkerOptions markerOptions = new MarkerOptions();
//                custon_infowindow adapter = new custon_infowindow(MapsActivity.this);
//                mMap.setInfoWindowAdapter(adapter);
//                Marker marker = mMap.addMarker(markerOptions);
//                marker.showInfoWindow();

            }
        });
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent i = new Intent(MapsActivity.this,Details.class);
                Log.e("marler",""+marker.getId());
                Log.e("pos",""+marker.getPosition());
                i.putExtra("lat",marker.getPosition().latitude);
                i.putExtra("lng",marker.getPosition().longitude);
                i.putExtra("id",marker.getId());
                startActivity(i);
                return true;
            }
        });

//        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
//                   @Override
//                   public View getInfoWindow(Marker marker) {
//                       return null;
//                   }
//
//                   @Override
//                   public View getInfoContents(Marker marker) {
//                       View v = getLayoutInflater().inflate(R.layout.custom_infowindow, null);
//                       LatLng latLng = marker.getPosition();
//                       TextView t = (TextView)v.findViewById(R.id.addr);
//                       ImageView I = (ImageView)v.findViewById(R.id.img1);
//                       t.setText("hello");
//
//                       return v;
//                   }
//               });

            fd = FirebaseDatabase.getInstance().getReference();
            locality = fd.child("smart-city-e91e4").child("locality");
            Log.e("test", "locality" + locality);
            Log.e("hi", "hello");
            // Add a marker in Sydney and move the camera
            fd.child("locality").addValueEventListener(new ValueEventListener() {
            int i = 1;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                while(items.hasNext()){
                    DataSnapshot item = items.next();
                    Log.e("d-", "" + dataSnapshot.getValue());
                    // DatabaseReference f = ds.child("field");
                    Log.e("item",""+item.getValue());
                    String address = item.child("address").getValue(String.class);
                    s.add(address);
                    Log.e("i",""+i);
                    Log.e("list", "" + s);
                    Log.e("addr", "address" + address);
                    i++;
                    Log.e("new i",""+i);
                    Log.e("we","dk");
                }

                for(int j=0;j<s.size();j++){
                    LatLng addr = getLocationFromAddress(MapsActivity.this,s.get(j));
                    Log.e("latlng", "" + addr);
                    Log.e("s-", "" + s);
                    mMap.addMarker(new MarkerOptions().position(addr)

                    );
                    Log.e("map","hi map");
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(addr));

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

