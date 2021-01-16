package com.example.helloworld;

import com.example.helloworld.MainActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import static androidx.core.content.ContextCompat.startActivity;

@RequiresApi(api = Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    private Context context;


    public FingerprintHandler(Context context) {
        this.context = context;
    }

    public void startAuth(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject){

        CancellationSignal cancellationSignal = new CancellationSignal();
        fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }
    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString){

        this.update("There was an Auth Error. " + errString, false);
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        this.update("Error: " + helpString, false);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        this.update("You can now access the app", true);
        context.startActivity(new Intent(context, password_page.class));
    }

    private void update(String s, boolean b){
        TextView instructLabel = (TextView) ((Activity)context).findViewById(R.id.InstructLabel);
        ImageView imageView = (ImageView) ((Activity)context).findViewById(R.id.imageView);

        instructLabel.setText(s);
        if (b == false) {
            instructLabel.setTextColor(ContextCompat.getColor(context, R.color.design_default_color_on_primary));
        } else {
            instructLabel.setTextColor(ContextCompat.getColor(context, R.color.design_default_color_on_primary));
            imageView.setImageResource(R.mipmap.action_done);
        }
    }
}
