package shape.edu.hkmilkteaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private lateinit var  auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // create and initialize FirebaseAuth
        auth= FirebaseAuth.getInstance()
    }

    // register with email and password that input by user
    fun register(view: View){
        val editTextEmailAddress = findViewById<EditText>(R.id.editTextEmailAddress)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        // trimming off empty spaces
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

                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
                    if(task.isSuccessful){

                        // keeping the result as firebase user
                        val firebaseUser = task.result!!.user!!
                        intent.putExtra("user_id", firebaseUser.uid)
                        intent.putExtra("email_id",email)

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

    // redirect to Login page if user have an account
    fun goToLogin(view: View){
        val intent= Intent(this,LoginActivity::class.java)
        startActivity(intent)
    }
}