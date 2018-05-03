package bhumil.test.smartcity;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import java.net.URL;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by bhumil on 1/4/18.
 */

public class Details  extends AppCompatActivity {

    private DatabaseReference loc;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);

        Bundle marker = getIntent().getExtras();
        if(marker!=null) {
            String m = marker.getString("id");
            Double lat = marker.getDouble("lat");
            Double lng = marker.getDouble("lng");
//            Log.e("m", "" + m);
//            Log.e("lat", "" + lat);
//            Log.e("l", "" + l);
              Character name = m.charAt(1);
            loc = FirebaseDatabase.getInstance().getReference();
            loc = loc.child("smart-city-e91e4").child("locality");
            Log.e("loc",""+loc);
            //Image img1 = loc.child("field"+name);


        }
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
}
