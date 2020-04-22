package com.example.soundboard

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var recordAudioPermission: Int = 1
    private var alertDialog: AlertDialog? = null

    private lateinit var categoryViewModel: CategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val categoriesAdapter = CategoryListAdapter(this)
        val rvCategories : RecyclerView = findViewById<RecyclerView>(R.id.rv_categories)
        rvCategories.adapter = categoriesAdapter
        rvCategories.layoutManager = LinearLayoutManager(this)

        categoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        categoryViewModel.allCategories.observe(this, Observer { categories ->
            // Update the cached copy of the words in the adapter
            categories?.let { categoriesAdapter.setCategories(it) }
        })

        val categoryDialogView: View = layoutInflater.inflate(R.layout.new_category_dialog, null)

        alertDialog = AlertDialog.Builder(this)
            .setView(categoryDialogView)
            .setTitle(R.string.title_add_category)
            .setPositiveButton(R.string.add) { _, _ ->
                val categoryName = categoryDialogView.findViewById<EditText>(R.id.add_category)
                    .text.toString()
                val category = Category(0, categoryName)
                categoryViewModel.insert(category)
            }
            .setNegativeButton(R.string.cancel) { _, _ -> }
            .create()

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

    override fun onStart() {
        super.onStart()

        // TODO: insert test categories
        categoryViewModel.insert(Category(0, "TEST1"))
        categoryViewModel.insert(Category(1, "TEST2"))
        categoryViewModel.insert(Category(2, "TEST3"))
        categoryViewModel.insert(Category(3, "TEST4"))
        categoryViewModel.insert(Category(4, "TEST5"))
        categoryViewModel.insert(Category(5, "TEST6"))
        categoryViewModel.insert(Category(6, "TEST7"))
        categoryViewModel.insert(Category(7, "TEST8"))
        categoryViewModel.insert(Category(8, "TEST9"))
        categoryViewModel.insert(Category(9, "TEST10"))
        categoryViewModel.insert(Category(10, "TEST11"))
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
