package bhumil.test.smartcity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DataActivity extends AppCompatActivity {
    private TextView mName;
    private TextView mNo;
    private TextView mAddr;
    private TextView mDOB;
    private TextView mGen;
    private TextView mGen2;
    private TextView mGen3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        mName = (TextView) findViewById(R.id.mName);
        mNo = (TextView) findViewById(R.id.mNo);
        mAddr = (TextView) findViewById(R.id.mAddr);
        mDOB = (TextView) findViewById(R.id.mDOB);
        mGen = (TextView) findViewById(R.id.mGen);
        mGen2 = (TextView) findViewById(R.id.mGen2);
        mGen3 = (TextView) findViewById(R.id.mGen3);

        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("Text");
        String [] splitstr = message.split("%");
        mName.setText(splitstr[0]);
        mNo.setText(splitstr[1]);
        mAddr.setText(splitstr[2]);
        mGen.setText(splitstr[3]);
        mDOB.setText(splitstr[4]);
        mGen2.setText(splitstr[5]);
        mGen3.setText(splitstr[6]);
    }
}
