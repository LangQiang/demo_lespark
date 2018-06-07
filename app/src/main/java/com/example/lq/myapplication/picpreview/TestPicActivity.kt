package com.example.lq.myapplication.picpreview

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.lq.myapplication.R
import kotlinx.android.synthetic.main.activity_test_pic.*

class TestPicActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_pic)

        for (i in 0 .. root_view.childCount - 1) {
            root_view.getChildAt(i).setOnClickListener({
                var mInfos = arrayListOf(PicViewInfo(R.drawable.aaa,iv1.x,iv1.y,iv1.width,iv1.height,i == 0),
                        PicViewInfo(R.drawable.bbb,iv2.x,iv2.y,iv2.width,iv2.height,i == 1),
                        PicViewInfo(R.drawable.ccc,iv3.x,iv3.y,iv3.width,iv3.height,i == 2),
                        PicViewInfo(R.drawable.ddd,iv4.x,iv4.y,iv4.width,iv4.height,i == 3))
                var intent = Intent(this,PicPreviewActivity :: class.java)
                intent.putParcelableArrayListExtra("pic_info",mInfos)
                intent.putExtra("current_pos",i)
                startActivity(intent)
                Log.e("drawable","${iv1.drawable.intrinsicWidth} ${iv1.drawable.intrinsicHeight}")
                Log.e("drawable", "${root_view.height}")
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

