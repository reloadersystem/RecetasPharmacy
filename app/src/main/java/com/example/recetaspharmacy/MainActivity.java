package com.example.recetaspharmacy;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.OptionalPendingResultImpl;

import org.w3c.dom.Text;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
private ImageView PhotoImageView;
private TextView NameTextView;
private TextView EmailTextView;
private TextView idTextView;
private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PhotoImageView =(ImageView)findViewById(R.id.PhotoImageView);
        NameTextView=(TextView) findViewById(R.id.nameTextView);
        EmailTextView=(TextView) findViewById(R.id.EmailTextView);
        idTextView=(TextView)findViewById(R.id.idTextView);

        //login silencioso
       GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                   .requestEmail()
                                    .build();

        googleApiClient=new GoogleApiClient.Builder(this)
        .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr=Auth.GoogleSignInApi.silentSignIn(googleApiClient);
if(opr.isDone()){
    GoogleSignInResult result=opr.get();
    handleSigInResult(result);

}else{
    opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
        @Override
        public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
            handleSigInResult(googleSignInResult);
        }
    });
} }

    private void handleSigInResult(GoogleSignInResult result) {
    if(result.isSuccess()){
        GoogleSignInAccount account= result.getSignInAccount();
        NameTextView.setText(account.getDisplayName());
        EmailTextView.setText(account.getEmail());
        idTextView.setText(account.getId());
        Glide.with(this).load(account.getPhotoUrl()).into(PhotoImageView);
        //Log.d("RecetasPharmacy",account.getPhotoUrl().toString());

    }else{
     goLogInScreem();
    }
    }



    public void  goLogInScreem() {
        Intent intent=new Intent(this,LoginActivity.class );
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void logOut(View view) {

        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
if(status.isSuccess()){
    goLogInScreem();
}else{
    Toast.makeText(getApplicationContext(),"No se pudo cerrar seccion ",Toast.LENGTH_SHORT).show();
} }});

    }

    public void revoke(View view) {
        Auth.GoogleSignInApi.revokeAccess(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if(status.isSuccess()){
                    goLogInScreem();

                }else{
Toast.makeText(getApplicationContext(),"No se pudo revokar el acceso",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
