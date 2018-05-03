package bhumil.test.smartcity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class BarCode extends AppCompatActivity {
    private ImageView mImage;
    private TextView mText;
    String text2qr;
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;
    private DatabaseReference mData;
    String mGameState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_code);

        mRef = FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();
        mImage = (ImageView) findViewById(R.id.mImage);
        Bundle bundle = getIntent().getExtras();
        final String name = bundle.getString("Name");
        final String no = bundle.getString("Number");
        final String add = bundle.getString("Address");
        final String gender = bundle.getString("Gender");
        final String dob = bundle.getString("DOB");
        final String stall = bundle.getString("Stall");
        final String people = bundle.getString("People");



       String uid = mAuth.getCurrentUser().getUid().toString();
       mData = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
       mData.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
              // if(dataSnapshot.hasChild("Approved")){
                   text2qr = name +"%"+ no +"%"+ add +"%"+ gender +"%"+ dob +"%"+ stall +"%"+ people;
                   MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                   try {
                       BitMatrix bitMatrix = multiFormatWriter.encode(text2qr, BarcodeFormat.QR_CODE, 200, 200);
                       BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                       Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);

                       mImage.setImageBitmap(bitmap);
                   } catch (WriterException e) {
                       e.printStackTrace();
                   }

               //} else {
                 //  Toast.makeText(BarCode.this, "Registration Pending", Toast.LENGTH_SHORT).show();
               //}
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean("MyBoolean", true);
        savedInstanceState.putDouble("myDouble", 1.9);
        savedInstanceState.putInt("MyInt", 1);
        savedInstanceState.putString("MyString", "Welcome back to Android");

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        boolean myBoolean = savedInstanceState.getBoolean("MyBoolean");
        double myDouble = savedInstanceState.getDouble("myDouble");
        int myInt = savedInstanceState.getInt("MyInt");
        String myString = savedInstanceState.getString("MyString");
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastActivity", getClass().getName());
        editor.commit();
    }

    @Override
    public void onBackPressed() {
        return;
    }
}
