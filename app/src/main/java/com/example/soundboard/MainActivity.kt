package com.example.soundboard

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var recordAudioPermission: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val categories : Array<String> = resources.getStringArray()
        val categoriesAdapter : ArrayAdapter<String> = ArrayAdapter(this, R.layout.lv_categories, R.id.tv_category)
        val lvCategories : ListView = findViewById<ListView>(R.id.lv_categories)


        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.RECORD_AUDIO), this.recordAudioPermission)
        } else {
            val btnOpenSoundboard : FloatingActionButton = findViewById(R.id.btn_record)
            btnOpenSoundboard.setOnClickListener {
                val intent = Intent(this, SoundBoard::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode) {

            this.recordAudioPermission -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    val btnOpenSoundboard : FloatingActionButton = findViewById(R.id.btn_record)
                    btnOpenSoundboard.setOnClickListener {
                        val intent = Intent(this, SoundBoard::class.java)
                        startActivity(intent)
                    }
                } else {

                    Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show()

                }

                return
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
}
