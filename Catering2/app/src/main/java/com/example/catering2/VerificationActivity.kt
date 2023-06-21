package com.example.catering2


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class VerificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)

        val myIntent = Intent(this, LoginActivity::class.java)
        val dbHelper = Database(this)
        val db = dbHelper.writableDatabase
        val name = intent.getStringExtra("name")
        val password = intent.getStringExtra("password")
        val email = intent.getStringExtra("mail")
        val values = ContentValues().apply {
            put(LoginSchema.LoginEntry.COLUMN_NAME_LOGIN, name)
            put(LoginSchema.LoginEntry.COLUMN_NAME_PASSWORD, password)
            put(LoginSchema.LoginEntry.COLUMN_NAME_EMAIL, email)
        }

        val codeinput = findViewById<EditText>(R.id.code)
        val verification = findViewById<Button>(R.id.verificationbutton)
        val login = findViewById<Button>(R.id.loginLink)
        var code = ""
        for (i in 1..6) {
            code += Random.nextInt(0, 9)
        }
        val senderEmail = "app.catering1@gmail.com"
        val senderPassword = "vzaaapfmjfsimfwx"
        val subject = "Application_Catering:Code verification"
        val body = "Hello,thank you for using our app!!\nThis is your verifiaction code:"+code+"\nYour login:"+name+"\nYour password:"+password

        fun sendEmail(senderEmail: String, senderPassword: String, receiverEmail: String, subject: String, body: String) {
            val properties = Properties()
            properties["mail.smtp.host"] = "smtp.gmail.com"
            properties["mail.smtp.port"] = "587"
            properties["mail.smtp.auth"] = "true"
            properties["mail.smtp.starttls.enable"] = "true"
            properties["mail.smtp.ssl.trust"] = "smtp.gmail.com"

            val session = Session.getInstance(properties, object : Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(senderEmail, senderPassword)
                }
            })

            try {
                val mimeMessage = MimeMessage(session)
                mimeMessage.setFrom(InternetAddress(senderEmail))
                mimeMessage.addRecipient(Message.RecipientType.TO, InternetAddress(receiverEmail))
                mimeMessage.subject = subject
                mimeMessage.setText(body)

                Transport.send(mimeMessage)
            } catch (e: MessagingException) {
                e.printStackTrace()
            }
        }

        fun sendEmailInBackground(
            senderEmail: String,
            senderPassword: String,
            receiverEmail: String,
            subject: String,
            body: String
        ) {
            GlobalScope.launch(Dispatchers.IO) {
                sendEmail(senderEmail, senderPassword, receiverEmail, subject, body)
            }
        }
        sendEmailInBackground(senderEmail,senderPassword,email.toString(),subject,body)

        verification.setOnClickListener {

            val codedit = codeinput.text.toString().trim()
            if (code == codedit) {
                val new = db.insert(LoginSchema.LoginEntry.TABLE_NAME, null, values)
                if (new != -1L) {
                    verification.isEnabled = false
                    Toast.makeText(this, "Rejestracja pomyślna", Toast.LENGTH_SHORT).show()
                } else{
                    Toast.makeText(this, "Rejestracja niepowiodła się, spróbuj ponownie", Toast.LENGTH_SHORT).show()

                }
            } else{
                Toast.makeText(this, "Nieprawidłowy kod", Toast.LENGTH_SHORT).show()

            }

        }
        login.setOnClickListener {
            startActivity(myIntent)
        }
    }
}
