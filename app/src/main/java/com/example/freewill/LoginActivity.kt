package com.example.freewill

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBar
import com.example.freewill.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.security.AccessController

class LoginActivity : AppCompatActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient

    //ViewBinding
    private lateinit var binding: ActivityLoginBinding

    //ActionBar
//    private lateinit var actionBar: ActionBar

    //Firebase Authorization
    private lateinit var firebaseAuth: FirebaseAuth

    //Launcher for Google SignIn
//    private lateinit var launcher: ActivityResultLauncher<Intent>

    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = Firebase.auth

        // [START config_signin]
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        // [END config_signin]
        //if no account open SingUpActivity
        binding.noAccountText.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        //if login successfully open main app
        binding.loginButton.setOnClickListener {
            validateUser()
        }

        binding.loginWithoutPasswordButton.setOnClickListener {
            startActivity(Intent(this, ScheduleActivity::class.java))
        }

        binding.loginWithGoogle.setOnClickListener {
//            signInWithGoogle()
            signIn()
//            extraDataDialog()
        }
    }

    //new realisation
    // [START on_start_check_user]
//    override fun onStart() {
//        super.onStart()
//        // Check if user is signed in (non-null) and update UI accordingly.
//    }
//    // [END on_start_check_user]

    // [START onactivityresult]
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Toast.makeText(this, "firebaseAuthWithGoogle:" + account.id, Toast.LENGTH_SHORT).show()
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, "Google sign in failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
    // [END onactivityresult]

    // [START auth_with_google]
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        val user = firebaseAuth.currentUser
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    if(user!!.isEmailVerified){
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(this, "User is verified", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(this, "User is not verified", Toast.LENGTH_SHORT).show()
                    }

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "signInWithCredential:failure", Toast.LENGTH_SHORT).show()
                }
            }
    }
    // [END auth_with_google]

    // [START signin]
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    // [END signin]

    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }

    private fun validateUser(){
        email = binding.emailEditText.text.toString()
        password = binding.passwordEditText.text.toString().trim()

        //check whether user's data is correct
        if(TextUtils.isEmpty(email))
        {
            binding.emailEditText.error = "Please enter your nickname"
        }
        else if(TextUtils.isEmpty(password))
        {
            binding.passwordEditText.error = "Please enter your password"
        }
        else
        {
            loginToFirebase()
        }
    }

    private fun loginToFirebase(){
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                firebaseAuth = FirebaseAuth.getInstance()
                val firebaseUser = firebaseAuth.currentUser
                if(firebaseUser!!.isEmailVerified){
                    //user successfully signed in
                    val email = firebaseUser.email
                    Toast.makeText(this, "Logged as $email", Toast.LENGTH_SHORT).show()

                    //move to main app activity
                    startActivity(Intent(this, ScheduleActivity::class.java))
                    finish()
                }
                else{
                    Toast.makeText(this, "User's email is not verified", Toast.LENGTH_SHORT).show()
                    val user = firebaseAuth.currentUser
                    user!!.sendEmailVerification()
                        .addOnSuccessListener {
                            Toast.makeText(this, "Verification link was sent", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Failed to send a verification link", Toast.LENGTH_SHORT).show()
                        }
                }

            }
            .addOnFailureListener{ e->
                //user not signed in
                Toast.makeText(this, "Please, sign up firstly", Toast.LENGTH_SHORT).show()
            }
    }

//    private fun isVerified() {
//        firebaseAuth = Firebase.auth
//        val user = firebaseAuth.currentUser
//        if(user!!.isEmailVerified)
//        {
//            startActivity(Intent(this, ScheduleActivity::class.java))
//        }
//        else{
//            Toast.makeText(this, "User's email is not verified", Toast.LENGTH_SHORT).show()
//        }


//    private fun extraDataDialog(){
//        val builder: AlertDialog.Builder = android.app.AlertDialog.Builder(this)
//        builder.setTitle("Title")
//
//// Set up the input
//        val input = EditText(this)
//// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
//        input.setHint("Enter Text")
//        input.inputType = InputType.TYPE_CLASS_TEXT
//        builder.setView(input)
//
//// Set up the buttons
//        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
//            // Here you get get input text from the Edittext
//            var m_Text = input.text.toString()
//        })
//        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
//
//        builder.show()
//    }
}