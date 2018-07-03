package com.example.lq.myapplication.picpreview

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.lq.myapplication.R
import com.example.lq.myapplication.global.App
import kotlinx.android.synthetic.main.activity_test_pic.*

class TestPicActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_pic)
        for (i in 0 .. root_view.childCount - 1) {
            root_view.getChildAt(i).setOnClickListener({
//                App.getInstance().invokeTestFun()
                val arr = IntArray(2)
                var mInfos = arrayListOf<PicViewInfo>()
                iv1.getLocationOnScreen(arr)
                val picViewInfo1 = PicViewInfo(R.drawable.aaa,arr[0].toFloat(),arr[1].toFloat(),iv1.width,iv1.height,i == 0)
                mInfos.add(picViewInfo1)

                iv2.getLocationOnScreen(arr)
                val picViewInfo2 = PicViewInfo(R.drawable.bbb,arr[0].toFloat(),arr[1].toFloat(),iv2.width,iv2.height,i == 0)
                mInfos.add(picViewInfo2)

                iv3.getLocationOnScreen(arr)
                val picViewInfo3 = PicViewInfo(R.drawable.ccc,arr[0].toFloat(),arr[1].toFloat(),iv3.width,iv3.height,i == 0)
                mInfos.add(picViewInfo3)

                iv4.getLocationOnScreen(arr)
                val picViewInfo4 = PicViewInfo(R.drawable.ddd,arr[0].toFloat(),arr[1].toFloat(),iv4.width,iv4.height,i == 0)
                mInfos.add(picViewInfo4)
                var intent = Intent(this,PicPreviewActivity :: class.java)
                intent.putParcelableArrayListExtra("pic_info",mInfos)
                intent.putExtra("current_pos",i)
                startActivity(intent)
                Log.e("drawable","${iv1.drawable.intrinsicWidth} ${iv1.drawable.intrinsicHeight}")
                iv4.getLocationOnScreen(arr)
                Log.e("drawable", "${root_view.height} ${iv4.x} ${iv4.y} ${arr[0]} ${arr[1]}")
            })
        }
    }

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        intent?.let {
            if (intent.component.className.equals(PicPreviewActivity :: class.java.name)) {
                overridePendingTransition(0,0)
            }
        }

    }
}

