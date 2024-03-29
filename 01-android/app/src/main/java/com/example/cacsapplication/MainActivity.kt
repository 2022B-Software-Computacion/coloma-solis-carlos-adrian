package com.example.cacsapplication

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.cacsapplication.IFirebaseUIAuth

class MainActivity : AppCompatActivity() {
    val contenidoIntentExplicito = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result ->
        if(result.resultCode == Activity.RESULT_OK){
            if(result.data != null){
                val data = result.data
                val toast = Toast.makeText(this, "${data?.getStringExtra("nombreModificado")}", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, toast.xOffset / 2, toast.yOffset / 2)
                toast.show()
                Log.i("intent-epn", "${data?.getStringExtra("nombreModificado")}")
            }
        }
    }

    val contenidoIntentImplicito = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        result ->
            if(result.resultCode == RESULT_OK){
                if(result.data != null){
                    if(result.data!!.data != null){
                        val uri: Uri = result.data!!.data!!
                        val cursor = contentResolver.query(
                            uri,
                            null,
                            null,
                            null,
                            null,
                            null
                        )

                        cursor?.moveToFirst()
                        val indiceTelefono = cursor?.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                        )

                        val telefono = cursor?.getString(
                            indiceTelefono!!
                        )

                        cursor?.close()

                        Toast.makeText(this, "Telefono ${telefono}", Toast.LENGTH_SHORT).show()
                        Log.i("intent-epn", "Telefono ${telefono}")


                    }
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        EBaseDeDatos.tablaEntrenador = ESqliteHelperEntrenador(this)
        var path : String = EBaseDeDatos.tablaEntrenador!!.writableDatabase.path
        Toast.makeText(this, path, Toast.LENGTH_LONG).show()
        val botonCicloVida = findViewById<Button>(R.id.btn_ciclo_vida)
        botonCicloVida.setOnClickListener{
            irActividad(ACicloVida::class.java)
        }

        val botonListView = findViewById<Button>(R.id.btn_ir_list_view)
        botonListView.setOnClickListener { irActividad(BListView::class.java) }

        val botonIntentImplicito = findViewById<Button>(R.id.btn_ir_intent_implicito)
        botonIntentImplicito.setOnClickListener{
            val intentConRespuesta = Intent(
                Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI
            )

            contenidoIntentImplicito.launch(intentConRespuesta)
        }

        val botonIntent = findViewById<Button>(R.id.btn_intent)
        botonIntent
            .setOnClickListener {
                abrirActividadConParametros(CIntentExplicitoParametros::class.java)
            }

        findViewById<Button>(R.id.btn_crud).setOnClickListener {
            irActividad(ECrudEntrenador::class.java)
        }

        val botonRView = findViewById<Button>(R.id.btn_recycler_view)
        botonRView.setOnClickListener {
            irActividad(GRecyclerView::class.java)
        }

        val botonMaps = findViewById<Button>(R.id.btn_ir_maps)
        botonMaps.setOnClickListener {
            irActividad(HGoogleMapsActivity::class.java)
        }

        val botonFirebaseUI = findViewById<Button>(R.id.btn_intent_firebase_ui)
        botonFirebaseUI.setOnClickListener {
            irActividad(IFirebaseUIAuth::class.java)
        }

        val botonFirestore = findViewById<Button>(R.id.btn_intent_firestore)
        botonFirestore.setOnClickListener {
            irActividad(JFirebaseStore::class.java)
        }

    }

    fun irActividad(clase: Class<*>){
        val intent = Intent(this, clase)
        startActivity(intent)
    }

    fun abrirActividadConParametros(clase: Class<*>){
        val intentExplicito = Intent(this, clase)

        intentExplicito.putExtra("nombre", "Adrian")
        intentExplicito.putExtra("apellido", "Eguez")
        intentExplicito.putExtra("edad", 32)
        intentExplicito.putExtra("entrenadorPrincipal", BEntrenador(1, "Adrian", "Paleta"))

        contenidoIntentExplicito.launch(intentExplicito)
    }

}