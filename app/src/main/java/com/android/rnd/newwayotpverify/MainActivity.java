package com.android.rnd.newwayotpverify;

import android.content.Intent;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppSignatureHelper signatureHelper = new AppSignatureHelper(this);

//        addPreferencesFromResource(R.xml.pref_main);
//
//        PreferenceScreen screen = getPreferenceScreen();

        ArrayList<String> appSignatures = signatureHelper.getAppSignatures();
//        Preference pref = screen.findPreference("app_signature");
//        if (!appSignatures.isEmpty() && pref != null) {
//            pref.setSummary(appSignatures.get(0));
//        }
        Log.e("OTP appSignature: ", appSignatures.get(0));
    }

    public void onClickSendOTP(View view) {
        Intent intent = new Intent(this, SendOTPActivity.class);
        startActivity(intent);
    }
}
