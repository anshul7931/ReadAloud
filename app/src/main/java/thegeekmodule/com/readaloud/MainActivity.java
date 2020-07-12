package thegeekmodule.com.readaloud;

import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;


//TODO
/*
*Select voice
*Select Accent(Locale)
* Improve UI
*/
public class MainActivity extends AppCompatActivity {

    private TextToSpeech mTTS;
    private EditText mEditText;
    private SeekBar mSeekBarPitch;
    private SeekBar mSeekBarSpeed;
    private Button mButtonSpeak;
    private Spinner mSelectLocale;
    Map<String,Locale> localeMap = new HashMap<>();
    //        Locale[] locales = Locale.getAvailableLocales();
    Locale[] locales = new Locale[]{Locale.ENGLISH,Locale.GERMAN,Locale.FRENCH};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonSpeak = (Button)findViewById(R.id.button_speak);
        mEditText = (EditText)findViewById(R.id.edit_text);
        mSeekBarPitch = (SeekBar)findViewById(R.id.seek_bar_pitch);
        mSeekBarSpeed = (SeekBar)findViewById(R.id.seek_bar_speed);
        mSelectLocale = (Spinner)findViewById(R.id.selectLocale);


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
        //TODO enable visibility
        mSelectLocale.setVisibility(View.INVISIBLE);

        createTextToSpeakObject(Locale.GERMAN);

        mButtonSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speak();
            }
        });
    }

    private void speak() {
        //createTextToSpeakObject(localeMap.get(mSelectLocale.getSelectedItem().toString()));
        String text = mEditText.getText().toString();
        float pitch = (float) mSeekBarPitch.getProgress() / 50;
        if (pitch < 0.1) pitch = 0.1f;
        float speed = (float) mSeekBarSpeed.getProgress() / 50;
        if (speed < 0.1) speed = 0.1f;
        mTTS.setPitch(pitch);
        mTTS.setSpeechRate(speed);
        mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
    @Override
    protected void onDestroy() {
        if (mTTS != null) {
            mTTS.stop();
            mTTS.shutdown();
        }
        super.onDestroy();
    }

    private void createTextToSpeakObject(final Locale localeLanguage){

        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    int result = mTTS.setLanguage(localeLanguage);
                    if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                        Toast.makeText(getApplicationContext(),"Language not supported",Toast.LENGTH_SHORT).show();
                        Log.e("TTS","Language not supported");
                    }else{
                        mButtonSpeak.setEnabled(true);
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Initialization failed",Toast.LENGTH_SHORT).show();
                    Log.e("TTS","Initialization failed");
                }
            }
        });
    }


}
