package com.crisw.dragcamera

import android.Manifest
import android.annotation.TargetApi
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_permission.*

/**
 * Created by wt on 2018/9/7.
 */
class PermissionUI : AppCompatActivity() {
    //权限
    var granted = false

    private val REQUESTCODE_PERMISSION = 101 //权限申请码
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)
        btn_go.setOnClickListener {
            if (granted) {
                startActivity(Intent(this@PermissionUI, CameraUI::class.java))
            }
        }
        getPermissions()
    }


    /**
     * 获取权限
     */
    private fun getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                //具有权限
                granted = true
            } else {
                //不具有获取权限，需要进行权限申请
                ActivityCompat.requestPermissions(this@PermissionUI, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA), REQUESTCODE_PERMISSION)
                granted = false
            }
        }
    }

    @TargetApi(23)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUESTCODE_PERMISSION) {
            var size = 0
            if (grantResults.size >= 1) {
                val writeResult = grantResults[0]
                //读写内存权限
                val writeGranted = writeResult == PackageManager.PERMISSION_GRANTED//读写内存权限
                if (!writeGranted) {
                    size++
                }
                //录音权限
                val recordPermissionResult = grantResults[1]
                val recordPermissionGranted = recordPermissionResult == PackageManager.PERMISSION_GRANTED
                if (!recordPermissionGranted) {
                    size++
                }
                //相机权限
                val cameraPermissionResult = grantResults[2]
                val cameraPermissionGranted = cameraPermissionResult == PackageManager.PERMISSION_GRANTED
                if (!cameraPermissionGranted) {
                    size++
                }
                if (size == 0) {
                    granted = true
                } else {
                    Toast.makeText(this, "先打开权限", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}
