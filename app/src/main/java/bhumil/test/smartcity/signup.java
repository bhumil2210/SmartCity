package bhumil.test.smartcity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class signup extends AppCompatActivity {

    private EditText nameText;
    private EditText noText;
    private EditText addText;
    private EditText dateText;
    private RadioButton mRadio;
    private RadioButton fRadio;
    private RadioGroup rGroup;
    private Button sButton;
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;
    private ProgressDialog mProgress;
    private EditText mStall;
    private EditText mPeople;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        nameText = (EditText) findViewById(R.id.nameText);
        noText = (EditText) findViewById(R.id.noText);
        addText = (EditText) findViewById(R.id.addText);
        dateText = (EditText) findViewById(R.id.dateText);
        sButton = (Button) findViewById(R.id.sButton);
        rGroup = (RadioGroup) findViewById(R.id.rGroup);
        mProgress = new ProgressDialog(this);
        mStall = (EditText) findViewById(R.id.mStall);
        mPeople = (EditText) findViewById(R.id.mPeople);


        mRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://smart-city-84093.firebaseio.com/");
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(mAuth.getCurrentUser() == null){
                    Intent loginIntent = new Intent(signup.this,login.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);

                }
            }
        };


        sButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startAdding();
                final String name = nameText.getText().toString().trim();
                final String no = noText.getText().toString().trim();
                final String address = addText.getText().toString();
                final String date = dateText.getText().toString();
                final String stall = mStall.getText().toString();
                final String people = mPeople.getText().toString();
                RadioButton radiovalue = (RadioButton) findViewById(rGroup.getCheckedRadioButtonId());
                final String gender = radiovalue.getText().toString();
                String uid = mAuth.getCurrentUser().getUid().toString();
                final DatabaseReference mUsers = mDatabase.child(uid);
                mUsers.child("Name").setValue(name);
                mUsers.child("Number").setValue(no);
                mUsers.child("Address").setValue(address);
                mUsers.child("DOB").setValue(date);
                mUsers.child("Gender").setValue(gender);
                mUsers.child("Stall").setValue(stall);
                mUsers.child("People").setValue(people);
                Intent bIntent = new Intent(signup.this,BarCode.class);
                bIntent.putExtra("Name",name);
                bIntent.putExtra("Number",no);
                bIntent.putExtra("Address",address);
                bIntent.putExtra("DOB",date);
                bIntent.putExtra("Gender",gender);
                bIntent.putExtra("Stall",stall);
                bIntent.putExtra("People",people);
                bIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(bIntent);


            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_scan){
            startActivity(new Intent(signup.this,Reader.class));
        }
        return super.onOptionsItemSelected(item);
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
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

}