/*
 * AuthFirebase : Register/Login
 * Coder - IloGus
 * Website - https://www.ilogus.fr
 * Docs - https://docs.ilogus.fr/firebase-auth
 *
 * Console Firebase : https://console.firebase.google.com
 *
 * For education and personal use.
 */

package fr.ilogus.authfirebase;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        // mAuth.signOut(); // Permet la déconnexion de l'utilisateur actif

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());


                    // Affiche un message avec l'adresse de l'utilisateur actif
                    Toast.makeText(MainActivity.this, "Email :" + user.getEmail(), Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };


        //register("fakemail@google.com", "azertyuiop");
        login("fakemail@google.com", "azertyuiop");

    }


    @Override
    public void onStart() {
        super.onStart();
        // Ajoute le Listener
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        // Supprime le Listener
        mAuth.removeAuthStateListener(mAuthListener);
    }

    /*
     * Enregistrement sur firebase
     * @param: String email - choisi par l'utilisateur
     * @param: String password - Mot de passe choisi par l'utilisateur
     */
    public void register(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        Toast.makeText(MainActivity.this, "Authentication success.",
                                Toast.LENGTH_SHORT).show();

                        if (!task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /*
     * Connexion sur Firebase
     * @param: String email - présent sur firebase
     * @param: String password - présent sur firebase
     */
    public void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // Affiche un message si success
                        Toast.makeText(MainActivity.this, "Authentication success !",
                                Toast.LENGTH_SHORT).show();

                        if (!task.isSuccessful()) {
                            Log.v(TAG, "signInWithEmail", task.getException());

                            // Si il y a une erreur pendant la connexion on affiche un message failed
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}