package com.example.soundboard

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaRecorder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.MediaController
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.yield
import org.jetbrains.annotations.NotNull
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    private var recordAudioPermission: Int = 1
    private var alertDialog: AlertDialog? = null
    private lateinit var categoryViewModel: CategoryViewModel

    private var xPositionDelta = 0
    private var yPositionDelta = 0

    private lateinit var root: ViewGroup

    companion object {
        private const val CATEGORY_ID: Int = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        root = findViewById(R.id.main_activity)

        categoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)

        val categoriesAdapter = CategoryListAdapter(this, categoryViewModel)
        val rvCategories : RecyclerView = findViewById<RecyclerView>(R.id.rv_categories)
        rvCategories.adapter = categoriesAdapter
        rvCategories.layoutManager = LinearLayoutManager(this)

        categoryViewModel.allCategories.observe(this, Observer { categories ->
            // Update the cached copy of the words in the adapter
            categories?.let { categoriesAdapter.setCategories(it) }
        })

        val categoryDialogView: View = layoutInflater.inflate(R.layout.new_category_dialog, null)

        alertDialog = AlertDialog.Builder(this)
            .setView(categoryDialogView)
            .setTitle(R.string.title_add_category)
            .setPositiveButton(R.string.add) { _, _ ->
                val editTextCategoryName = categoryDialogView.findViewById<EditText>(R.id.add_category)
                val categoryName = editTextCategoryName.text.toString()
                val category = Category(CATEGORY_ID, categoryName)
                categoryViewModel.insert(category)
                editTextCategoryName.text.clear()
            }
            .setNegativeButton(R.string.cancel) { _, _ -> }
            .create()

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.RECORD_AUDIO), this.recordAudioPermission)
        } else {

            start()

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

                    start()

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

    @SuppressLint("ClickableViewAccessibility")
    private fun start() {

        val btnOpenSoundboard : FloatingActionButton = findViewById(R.id.btn_record)
        val mediaRecorder = MediaRecorder()
        val soundBiteRecorder = SoundBiteRecorder(mediaRecorder)

//        btnOpenSoundboard.setOnTouchListener(object: View.OnTouchListener {
//            override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
//                val xPosition: Int = motionEvent.rawX.toInt()
//                val yPosition: Int = motionEvent.rawY.toInt()
//
//                when(motionEvent.action + MotionEvent.ACTION_MASK) {
//                    MotionEvent.ACTION_DOWN -> {
//                        val layoutParams: ConstraintLayout.LayoutParams = view.layoutParams as ConstraintLayout.LayoutParams
//                        xPositionDelta = xPosition - layoutParams.leftMargin
//                        yPositionDelta = yPosition - layoutParams.topMargin
//                    }
//                    MotionEvent.ACTION_MOVE -> {
//                        val layoutParams: ConstraintLayout.LayoutParams = view.layoutParams as ConstraintLayout.LayoutParams
//                        layoutParams.leftMargin = xPosition - xPositionDelta
//                        layoutParams.topMargin = yPosition - yPositionDelta
//                        layoutParams.rightMargin = -250
//                        layoutParams.bottomMargin = -250
//                        view.layoutParams = layoutParams
//                    }
//                }
//
//                root.invalidate()
//                return true
//            }
//        })

//        btnOpenSoundboard.setOnTouchListener { view, motionEvent ->
//
//            val xPosition: Int = motionEvent.rawX.toInt()
//            val yPosition: Int = motionEvent.rawY.toInt()
//
//            when(motionEvent.action + MotionEvent.ACTION_MASK) {
//                MotionEvent.ACTION_DOWN -> {
//                    val layoutParams: ConstraintLayout.LayoutParams = view.layoutParams as ConstraintLayout.LayoutParams
//                    xPositionDelta = xPosition - layoutParams.leftMargin
//                    yPositionDelta = yPosition - layoutParams.topMargin
//                }
//                MotionEvent.ACTION_MOVE -> {
//                    val layoutParams: ConstraintLayout.LayoutParams = view.layoutParams as ConstraintLayout.LayoutParams
//                    layoutParams.leftMargin = xPosition - xPositionDelta
//                    layoutParams.topMargin = yPosition - yPositionDelta
//                    layoutParams.rightMargin = -250
//                    layoutParams.bottomMargin = -250
//                    view.layoutParams = layoutParams
//                }
//            }
//
//            this.findViewById<ConstraintLayout>(R.id.main_activity).invalidate()
//
//            true
//        }

        btnOpenSoundboard.setOnClickListener() {


            val draggableView = Intent(this, DraggableView::class.java)
            startActivity(draggableView)

//            if (!soundBiteRecorder.isRecording) {
//
//                val audioPath = this.getExternalFilesDir(null)?.absolutePath
//                soundBiteRecorder.start(audioPath + "test.mp4")
//                btnOpenSoundboard.setImageResource(R.drawable.ic_mic_24px)
//
//            } else {
//
//                soundBiteRecorder.stop()
//                btnOpenSoundboard.setImageResource(R.drawable.ic_mic_none_24px)
//
//            }
        }
    }

    fun slideView(view: View, motionEvent: MotionEvent) {

    }

//        btnOpenSoundboard.setOnTouchListener { view, motionEvent ->
//            Log.d("ALREADY RECORDING:", soundBiteRecorder.isRecording.toString())
//            if (motionEvent.action == MotionEvent.ACTION_UP) {
//                if(soundBiteRecorder.isRecording) {
//                    soundBiteRecorder.stop()
//                }
//            }
//            true
//        }
//    }
}
