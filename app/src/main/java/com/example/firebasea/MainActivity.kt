package com.example.firebasea

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Evento analytics
        val analytics:FirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle=Bundle()
        bundle.putString("mensa","Integración completada")
        analytics.logEvent("PantallaInicial",bundle)
        Toast.makeText(this,"Evento analytic Creado",Toast.LENGTH_SHORT).show()

        setup()
        

    }


    private fun setup() {

        buttonCerrar.isEnabled=false


        buttonRegistrar.setOnClickListener {
            if (editTextEmail.text.isNotEmpty() && editTextTextPassword.text.isNotEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    editTextEmail.text.toString(),
                    editTextTextPassword.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        showAlert("Ok","Creado correctamente")
                        buttonCerrar.isEnabled=true
                    }
                    else{
                        showAlert("Error","Error de creación")
                        buttonCerrar.isEnabled=false
                    }

                }

            }

        }


        buttonAcceder.setOnClickListener {
            if (editTextEmail.text.isNotEmpty() && editTextTextPassword.text.isNotEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    editTextEmail.text.toString(),
                    editTextTextPassword.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        showAlert("Ok","Autenticado correctamente")
                        buttonCerrar.isEnabled=true
                    }
                    else{
                        showAlert("Error","Error de autenticado")
                        buttonCerrar.isEnabled=false
                    }

                }

            }
        }


        buttonCerrar.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            buttonCerrar.isEnabled=false
        }
    }


    private fun showAlert(title:String,message:String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("Aceptar",null)
        val dialog:AlertDialog=builder.create()
        dialog.show()
    }
}