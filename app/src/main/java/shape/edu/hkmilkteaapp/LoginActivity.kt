package shape.edu.hkmilkteaapp

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.util.concurrent.Executor

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // create and initialize FirebaseAuth
        auth= FirebaseAuth.getInstance()

        executor = ContextCompat.getMainExecutor(this)

        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int,
                                                   errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(applicationContext,
                        "Authentication error: $errString", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)

                    val email = sharedPreferences.getString("email","")
                    val password = sharedPreferences.getString("password","")

                    auth.signInWithEmailAndPassword(email.toString(),password.toString()).addOnCompleteListener{ task ->
                        if(task.isSuccessful){

                            // keeping the result as firebase user
                            val firebaseUser : FirebaseUser = task.result!!.user!!
                            intent.putExtra("user_id", firebaseUser.uid)
                            intent.putExtra("email_id",firebaseUser.email)

                            // clear activities
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK

                            // redirect to MainActivity if successfully register
                            val intent= Intent(applicationContext,MainActivity::class.java)

                            startActivity(intent)
                            finish()
                        }
                    }.addOnFailureListener { exception ->
                        Toast.makeText(applicationContext,exception.localizedMessage,Toast.LENGTH_LONG).show()
                    }

                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(applicationContext, "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use account password")
            .build()

        val bioLogin = findViewById<ImageView>(R.id.imageViewBioFinger)
        bioLogin.setOnClickListener{
            biometricPrompt.authenticate(promptInfo)
        }

        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE)
        val isLogged = sharedPreferences.getBoolean("isLogged", false)
        if (isLogged) {
            val email = sharedPreferences.getString("email","")
            val password = sharedPreferences.getString("password","")

            bioLogin.visibility = View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        val biometricManager = BiometricManager.from(this)
        when (biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS ->
                Toast.makeText(applicationContext,"App can authenticate using biometrics.",Toast.LENGTH_SHORT).show()
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                Toast.makeText(applicationContext,"No biometric features available on this device.",Toast.LENGTH_SHORT).show()
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
            Toast.makeText(applicationContext,"Biometric features are currently unavailable.",Toast.LENGTH_SHORT).show()
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED ->
                // Prompts the user to create credentials that your app accepts.
            Toast.makeText(applicationContext,"Biometric features are not enrolled.",Toast.LENGTH_SHORT).show()
        }
    }

    // login with email and password that input by user
    fun login(view: View){
        val editTextEmailAddress = findViewById<EditText>(R.id.editTextEmailAddress)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        //trimming off empty spaces
        val email=editTextEmailAddress.text.toString().trim { it <= ' ' }
        val password=editTextPassword.text.toString().trim { it <= ' ' }

        // checking if the user input anything before clicking the button
        when {
            TextUtils.isEmpty(email) -> {
                Toast.makeText(applicationContext,"Please input your E-mail.", Toast.LENGTH_SHORT).show()
            }
            TextUtils.isEmpty(password) -> {
                Toast.makeText(applicationContext,"Please input your password.", Toast.LENGTH_SHORT).show()
            }
            else -> {

                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->
                    if(task.isSuccessful){

                        // keeping the result as firebase user
                        val firebaseUser : FirebaseUser = task.result!!.user!!
                        intent.putExtra("user_id", firebaseUser.uid)
                        intent.putExtra("email_id",firebaseUser.email)

                        // saving the login detail to share preferences
                        val sPEditor : SharedPreferences.Editor = getSharedPreferences("data",
                            MODE_PRIVATE).edit()
                        sPEditor.putString("email", email)
                        sPEditor.putString("password", password)
                        sPEditor.putBoolean("isLogged", true)
                        sPEditor.apply()

                        // clear activities
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK

                        // redirect to MainActivity if successfully register
                        val intent= Intent(this,MainActivity::class.java)

                        startActivity(intent)
                        finish()
                    }
                }.addOnFailureListener { exception ->
                    Toast.makeText(applicationContext,exception.localizedMessage,Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    // redirect to Register page if user do not have an account
    fun goToRegister(view: View){
        val intent= Intent(this,RegisterActivity::class.java)
        startActivity(intent)
    }
}