package com.example.kidsdrawingapp

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.get
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_brush_size.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {
    private var mImageButtonCurrentPaint : ImageButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mImageButtonCurrentPaint = ll_paint_colors[1] as ImageButton
        mImageButtonCurrentPaint!!.setImageDrawable(
            ContextCompat.getDrawable(this, R.drawable.pallete_selected)
        )
        drawing_view.setSizeForBrush(20.toFloat())
        ib_brush.setOnClickListener {
            showBrushSizeChooserDialog()
        }

        ib_gallery.setOnClickListener {
            if(isReadStorageAllowedOrNot()) {
                val pickPhotoIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(pickPhotoIntent, GALLERY)
            } else {
                requestStoragePermission()
            }
        }

        ib_undo.setOnClickListener {
            drawing_view.onClickUndo()
        }

        ib_save.setOnClickListener {
            if(isReadStorageAllowedOrNot())
                BitmapAsyncTask(getBitmapFromView(fl_drawing_view_container)).execute()
            else
                requestStoragePermission()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == GALLERY) {
                try {
                    if(data!!.data != null) {
                        iv_background.visibility = View.VISIBLE
                        iv_background.setImageURI(data.data)
                    } else
                        Toast.makeText(this@MainActivity,
                            "Error in parsing the image or its corrupted.",
                            Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun showBrushSizeChooserDialog() {
        val brushDialog = Dialog(this)
        brushDialog.setContentView(R.layout.dialog_brush_size)
        brushDialog.setTitle("Brush Size: ")
        val smallButton = brushDialog.ib_small_brush
        smallButton.setOnClickListener{
            val brushSize = resources.getDimension(R.dimen.dgSmallBrushDimen) / resources.displayMetrics.density
            drawing_view.setSizeForBrush(brushSize)
            brushDialog.dismiss()
        }
        val mediumButton = brushDialog.ib_medium_brush
        mediumButton.setOnClickListener{
            val brushSize = resources.getDimension(R.dimen.dgMediumBrushDimen) / resources.displayMetrics.density
            drawing_view.setSizeForBrush(brushSize)
            brushDialog.dismiss()
        }
        val largeButton = brushDialog.ib_large_brush
        largeButton.setOnClickListener{
            val brushSize = resources.getDimension(R.dimen.dgLargeBrushDimen) / resources.displayMetrics.density
            drawing_view.setSizeForBrush(brushSize)
            brushDialog.dismiss()
        }

        brushDialog.show()
    }

    fun paintClicked(view : View) {
        if(view !== mImageButtonCurrentPaint) {
            val imageButton = view as ImageButton
            val colorTag = imageButton.tag.toString()
            drawing_view.setColor(colorTag)
            imageButton.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.pallete_selected)
            )
            mImageButtonCurrentPaint!!.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.pallete_normal)
            )
            mImageButtonCurrentPaint = view
        }
    }

    private fun requestStoragePermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).toString()))
            Toast.makeText(this, "Need permission to add a background.", Toast.LENGTH_SHORT).show()

        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE), STORAGE_PERMISSION_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == STORAGE_PERMISSION_CODE) {
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "Permission granted, now the app can read the storage.", Toast.LENGTH_SHORT).show()
        } else
            Toast.makeText(this, "Ooops you just denied the permission!", Toast.LENGTH_SHORT).show()
    }

    private fun isReadStorageAllowedOrNot() : Boolean {
        val result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun getBitmapFromView(view: View) : Bitmap {
        val returnedBitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(returnedBitmap)
        val bgDrawable = view.background

        if(bgDrawable != null)
            bgDrawable.draw(canvas)
        else
            canvas.drawColor(Color.WHITE)

        view.draw(canvas)

        return returnedBitmap
    }

    private inner class BitmapAsyncTask(val mBitmap: Bitmap) : AsyncTask<Any, Void, String>() {
        private lateinit var mProgressDialog : Dialog
        private val fileName = "KidsDrawingApp_" + System.currentTimeMillis() / 1000
        private val collection =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
            else
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        override fun onPreExecute() {
            super.onPreExecute()
            showProgressDialog()
        }

        override fun doInBackground(vararg p0: Any?): String {
            var result = ""

            try {
                val imgRoot = File(externalCacheDir!!.absoluteFile.toString() + File.separator)
                imgRoot.mkdirs()
                val file = File(imgRoot, "$fileName.png")
                val fOut = FileOutputStream(file)

                mBitmap.compress(Bitmap.CompressFormat.PNG, 95, fOut)
                fOut.flush()
                fOut.close()

                result = file.absolutePath
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return result
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            // Saving the image on MediaStore too
            val values = ContentValues().apply {
                put(MediaStore.Images.Media.TITLE, fileName)
                put(MediaStore.Images.Media.DESCRIPTION, "My drawing")
                put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
                put(MediaStore.Images.Media.MIME_TYPE, "image/png")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
                    put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/KidsDrawing")
                }
            }

            val resolver = contentResolver
            val uri = resolver.insert(collection, values)
            val stream = resolver.openOutputStream(uri!!)
            mBitmap.compress(Bitmap.CompressFormat.PNG, 95, stream)

            cancelProgressDialog()

            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, uri)
                type = "image/png"
            }
            val shareIntent = Intent.createChooser(sendIntent, "Compartilha, parça.")
            startActivity(shareIntent)
        }

        private fun showProgressDialog() {
            mProgressDialog = Dialog(this@MainActivity)
            mProgressDialog.setContentView(R.layout.dialog_custom_progress)
            mProgressDialog.show()
        }

        private fun cancelProgressDialog() {
            mProgressDialog.dismiss()
        }
    }

    companion object {
        private const val STORAGE_PERMISSION_CODE = 1
        private const val GALLERY = 2
    }
}