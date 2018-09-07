package com.crisw.dragcamera

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.crisw.dragcamera.listener.CameraListener
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream

class CameraUI : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dataDir = Environment.getExternalStorageDirectory()
        val temp = dataDir.path + "/DCIM/Camera/"
        cameraView.setCameraLisenter(object : CameraListener {

            override fun captureSuccess(bitmap: Bitmap) {
                val file1 = File(temp, "camera_pic_" + System.currentTimeMillis() + ".png")
                //将bitmap保存到本地，然后发送出去
                try {
                    val bos = BufferedOutputStream(FileOutputStream(file1))
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
                    bos.flush()
                    bos.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                this@CameraUI.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).setData(Uri.fromFile(file1)))
            }
        })
        //左边按钮点击事件
        cameraView.setLeftClickListener {
            finish()
        }
    }


    override fun onStart() {
        super.onStart()
        //全屏显示
        if (Build.VERSION.SDK_INT >= 19) {
            val decorView = window.decorView
            decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        } else {
            val decorView = window.decorView
            val option = View.SYSTEM_UI_FLAG_FULLSCREEN
            decorView.systemUiVisibility = option
        }

    }
}
