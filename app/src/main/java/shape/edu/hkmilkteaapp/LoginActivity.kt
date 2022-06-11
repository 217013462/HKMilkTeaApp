package shape.edu.hkmilkteaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    private lateinit var  auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // create and initialize FirebaseAuth
        auth= FirebaseAuth.getInstance()
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