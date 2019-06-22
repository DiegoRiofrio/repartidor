package es.upm.miw.repartidor;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonRegister, buttonSingIn;
    EditText fieldEmail, fieldPassword;

    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        buttonSingIn = (Button) findViewById(R.id.buttonSignIn);
        fieldEmail = findViewById(R.id.fieldEmail);
        fieldPassword = findViewById(R.id.fieldPassword);

        buttonRegister.setOnClickListener(this);
        buttonSingIn.setOnClickListener(this);

        mAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user =firebaseAuth.getCurrentUser();
                if (user != null){
                    //CharSequence username = user.getDisplayName();
                    Toast.makeText(MainActivity.this, getString(R.string.firebase_user_fmt, user.getEmail()), Toast.LENGTH_LONG).show();
                    Log.i("SESION", "sesion iniciada con el email:" + user.getEmail());
                }else {
                    Log.i("SESION","sesion cerrada");
                }
            }
        };

    }
    private void registrar(String email, String pass){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.i("SESION","usuario creado correctamente");
                }else {
                    Toast.makeText(getApplicationContext(), R.string.logKo, Toast.LENGTH_LONG).show();
                    Log.e("SESION",task.getException().getMessage()+"");
                }
            }
        });
    }
    private void iniciarSesion(String email, String pass){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.i("SESION","sesion iniciada");
                }else {
                    Log.e("SESION",task.getException().getMessage()+"");
                }
            }
        });
    }
    public void onClick (View view){
        switch (view.getId()){
            case R.id.buttonSignIn:
                String emailInicio = fieldEmail.getText().toString();
                String passInicio = fieldPassword.getText().toString();
                iniciarSesion(emailInicio,passInicio);
                break;
            case  R.id.buttonRegister:
                String emailReg = fieldEmail.getText().toString();
                String passReg = fieldPassword.getText().toString();
                registrar(emailReg,passReg);
                break;
        }
    }
    protected void onStart(){
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
    }
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
        }
    }
}

