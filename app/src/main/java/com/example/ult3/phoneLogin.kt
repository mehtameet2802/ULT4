package com.example.ult3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class phoneLogin : AppCompatActivity() {

    private var num:String="0"
    private lateinit var forceResendingToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var mCallback: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var mVerification: String
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_login)

        val resend = findViewById<TextView>(R.id.textView2)
        val otpview = findViewById<EditText>(R.id.otp)
        val verify = findViewById<Button>(R.id.verifybtn)
        val otpbtn = findViewById<Button>(R.id.otpbtn)
        val number=findViewById<EditText>(R.id.num)


        mAuth = FirebaseAuth.getInstance()


        otpbtn.setOnClickListener {
            num = number.text.trim().toString()
            if (num.isNotEmpty()) {
                num = "+91$num"
                sendVerificationCode(num)
            } else {
                Toast.makeText(this, "Enter Number", Toast.LENGTH_SHORT).show()
            }
        }

        mCallback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(Credential: PhoneAuthCredential) {
                signInWithPhoneAuthCredential(Credential)

            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(this@phoneLogin, "Invalid OTP", Toast.LENGTH_SHORT).show()

            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                mVerification = verificationId
                forceResendingToken = token
            }

        }


        verify.setOnClickListener {
            val otpnum=otpview.text.toString()
            if (otpnum.isNotEmpty()) {
                val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(
                     mVerification,otpnum
                )
                signInWithPhoneAuthCredential(credential)
            } else {
                Toast.makeText(this, "Enter OTP", Toast.LENGTH_SHORT).show()

            }
        }

        resend.setOnClickListener {
                resendVerificationCode(num,forceResendingToken)
                Toast.makeText(this, "Resent OTP", Toast.LENGTH_SHORT).show()
            }

        
    }

    private fun sendVerificationCode(number: String) {

        val options = PhoneAuthOptions
            .newBuilder(mAuth)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(mCallback) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun resendVerificationCode(number: String,token: PhoneAuthProvider.ForceResendingToken) {
        val options = PhoneAuthOptions
            .newBuilder(mAuth)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(mCallback) // OnVerificationStateChangedCallbacks
            .setForceResendingToken(token)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }


    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this,"Signed in as $num",Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show()
                }

            }
    }
}


