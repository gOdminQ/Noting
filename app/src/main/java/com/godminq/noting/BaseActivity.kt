package com.godminq.noting

import android.Manifest
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission

abstract class BaseActivity : AppCompatActivity() {

    fun requestPermission() {
        TedPermission.create()
            .setPermissionListener(object : PermissionListener {

                //권한이 허용됐을 때
                override fun onPermissionGranted() {
                    Toast.makeText(this@BaseActivity, "권한이 허가되었습니다", Toast.LENGTH_SHORT).show()
                }

                //권한이 거부됐을 때
                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    Toast.makeText(this@BaseActivity, "권한이 거부되었습니다", Toast.LENGTH_SHORT).show()
                }
            })
            .setDeniedMessage("권한을 허용해야 앱 사용이 가능합니다")// 권한이 없을 때 띄워주는 Dialog Message
            .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)// 얻으려는 권한(여러개 가능)
            .setDeniedCloseButtonText("닫기")
            .setGotoSettingButtonText("설정")
            .check()
    }

    fun tabAdapter() {

    }






}