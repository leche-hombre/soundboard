package com.example.soundboard

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var recordAudioPermission: Int = 1
    private var alertDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        alertDialog = this.let {
            val builder = AlertDialog.Builder(it)
            val inflater = this.layoutInflater

            builder.setView(inflater.inflate(R.layout.new_category_dialog, null))
                .setTitle(R.string.title_add_category)
                .setPositiveButton(R.string.add) { _, _ ->

                }
                .setNegativeButton(R.string.cancel) { dialog, _ ->
                    dialog.cancel()
                }

            builder.create()
        }

        val categoriesAdapter = CategoryListAdapter(this)
        val rvCategories : RecyclerView = findViewById<RecyclerView>(R.id.rv_categories)
        rvCategories.adapter = categoriesAdapter
        rvCategories.layoutManager = LinearLayoutManager(this)

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add -> {
                this.alertDialog?.show()
                true
            }
            R.id.action_settings -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
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
