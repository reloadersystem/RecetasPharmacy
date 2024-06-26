package com.example.recetaspharmacy;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.GoogleApiAvailabilityCache;

import static com.example.recetaspharmacy.R.*;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
private GoogleApiClient googleApiClient;
private SignInButton signInButton;
public static final int SIGN_IN_CODE=777;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(layout.activity_login2);


        //aqui va el video
        //aqui va la configurcion del boton de google play
        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
               .requestEmail()
                .build();


        googleApiClient =new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();



        signInButton =(SignInButton)findViewById(id.signInButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent,SIGN_IN_CODE)
                ;


            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    if(requestCode==SIGN_IN_CODE){
    GoogleSignInResult result=Auth.GoogleSignInApi.getSignInResultFromIntent(data);
handleSignInResult(result);

    }

    }

    private void handleSignInResult(GoogleSignInResult result) {
    if(result.isSuccess()){
goMainScreen();

        }
    else{

            Toast.makeText(this, string.not_log_in, Toast.LENGTH_LONG).show();
        }


    }

    private void goMainScreen() {
        Intent intent =new Intent(this,navigation1.class);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);
    }

}
