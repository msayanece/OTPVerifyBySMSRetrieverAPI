package com.android.rnd.newwayotpverify;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.rnd.newwayotpverify.newwayotpverifysdk.GeneralResponse;
import com.android.rnd.newwayotpverify.newwayotpverifysdk.InterceptorHTTPClientCreator;
import com.android.rnd.newwayotpverify.newwayotpverifysdk.NewWayOTPVerifySdk;
import com.android.rnd.newwayotpverify.newwayotpverifysdk.Service;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendOTPActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final int RESOLVE_HINT = 50005;
    TextView phoneText;
    Button mVerifyButton;
    private GoogleApiClient mCredentialsApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        phoneText = findViewById(R.id.phoneText);
        mVerifyButton = findViewById(R.id.verifyButton);

        mCredentialsApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .enableAutoManage(this, this)
                .addApi(Auth.CREDENTIALS_API)
                .build();

    }

    // Construct a request for phone numbers and show the picker
    private void requestHint() {
        HintRequest hintRequest = new HintRequest.Builder()
                .setPhoneNumberIdentifierSupported(true)
                .build();

        PendingIntent intent = Auth.CredentialsApi.getHintPickerIntent(
                mCredentialsApiClient, hintRequest);
        try {
            startIntentSenderForResult(intent.getIntentSender(),
                    RESOLVE_HINT, null, 0, 0, 0);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    // Obtain the phone number from the result
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESOLVE_HINT) {
            if (resultCode == RESULT_OK) {
                Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
                String id = credential.getId();//<-- will need to process phone number string
                phoneText.setText(id);
            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        requestHint();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void onClickVerify(View view) {
        String s = phoneText.getText().toString();
        if (s.isEmpty()) {
            Toast.makeText(this, "Please enter your phone number.", Toast.LENGTH_SHORT).show();
        } else {
            sendOTP(s);
        }
    }

    private void sendOTP(String phoneNumber) {
        InterceptorHTTPClientCreator.createInterceptorHTTPClient(getApplicationContext());
        Service service = new NewWayOTPVerifySdk.Builder().build(getApplicationContext()).getService();
        service.sendOTPRnD("https://desideals.co.in/App/user/process.comn.php?act=sendOTPRnD&deviceId=1&mobile="+ phoneNumber)
                .enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                boolean isFailed = false;
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("success")) {
                            getReadyToRetrieveSMS();
                        } else {
                            isFailed = true;
                        }
                    } else {
                        isFailed = true;
                    }
                } else {
                    isFailed = true;
                }
                if (isFailed)
                    Toast.makeText(SendOTPActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(SendOTPActivity.this, "OTP sent successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                Toast.makeText(SendOTPActivity.this, "Unable to connect to the internet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getReadyToRetrieveSMS() {
        SmsRetrieverClient client = SmsRetriever.getClient(this);
        Task<Void> task = client.startSmsRetriever();
        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(SendOTPActivity.this, "Waiting for the OTP to be retrieved...", Toast.LENGTH_SHORT).show();
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
                Toast.makeText(SendOTPActivity.this, "Unable to start listening to OTP SMS", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
