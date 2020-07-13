package thegeekmodule.com.readaloud;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CustomizationActivity extends AppCompatActivity {

    private Button mProceed;

    private Spinner mSelectLocale;
    Map<String,Locale> localeMap = new HashMap<>();
    Locale[] locales = new Locale[]{Locale.ENGLISH,Locale.GERMAN,Locale.FRENCH};

    private String msg = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customization);
        mProceed = (Button)findViewById(R.id.proceedBtn);
        mSelectLocale = (Spinner)findViewById(R.id.selectLocale);
        msg = getIntent().getStringExtra("content");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Customizations");
        actionBar.setSubtitle("Enter all the customizations");


        for(int i=0;i<locales.length;i++){
            localeMap.put(locales[i].getDisplayLanguage(),locales[i]);
        }
        String[] items = new String[localeMap.size()];
        int index =0;
        for(Map.Entry<String,Locale> locale : localeMap.entrySet()){
            items[index] = locale.getKey();
            index++;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        mSelectLocale.setAdapter(adapter);


        mProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SpeakActivity.class);
                i.putExtra("content",msg);
                startActivity(i);
            }
        });
    }
}
