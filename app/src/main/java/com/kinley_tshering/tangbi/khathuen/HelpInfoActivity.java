package com.kinley_tshering.tangbi.khathuen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class HelpInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_info);

        //Make the links clickable
        TextView help = (TextView) findViewById(R.id.help);
        help.setMovementMethod(LinkMovementMethod.getInstance());

        TextView credit = (TextView) findViewById(R.id.credit);
        credit.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
