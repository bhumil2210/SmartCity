package bhumil.test.smartcity;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by bhumil on 1/4/18.
 */


public class custom_infowindow implements GoogleMap.InfoWindowAdapter {

    private Activity context;

    public custom_infowindow(Activity context){
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = context.getLayoutInflater().inflate(R.layout.custom_infowindow, null);

        TextView tvTitle = (TextView) view.findViewById(R.id.addr);
        ImageView tvSubTitle = (ImageView) view.findViewById(R.id.img1);

        tvTitle.setText(marker.getTitle());
        //tvSubTitle.setText(marker.getSnippet());

        return view;
    }
}