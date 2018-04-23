package com.example.lq.myapplication

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.lq.myapplication.xfermode.TestBugClass
import dalvik.system.DexClassLoader
import kotlinx.android.synthetic.main.activity_hot_fix.*
import java.io.*


class HotFixActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hot_fix)
        val test = TestBugClass()
        execute.setOnClickListener {

        }
        fix.setOnClickListener {
            //fix
        }
        load.setOnClickListener {
            val dexPath = File(getDir("dex", Context.MODE_PRIVATE), "path_dex.jar")
            prepareDex(this,dexPath,"path_dex.jar")
            val dexClassLoader = DexClassLoader(dexPath.absolutePath,getDir("dex", 0).absolutePath,dexPath.absolutePath,classLoader)
            val loadClass = dexClassLoader.loadClass("com.jxnu.hotfixdemo.BugClass")
            val newInstance = loadClass.getConstructor().newInstance()
            val invoke = loadClass.getDeclaredMethod("getInfo").invoke(newInstance)
            Log.e("aaaa",invoke.toString())
            execute.text = invoke.toString()
        }
    }

    fun prepareDex(context: Context, dexInternalStoragePath: File, dex_file: String): Boolean {
        var bis: BufferedInputStream? = null
        var dexWriter: OutputStream? = null

        try {
            bis = BufferedInputStream(context.assets.open(dex_file))
            dexWriter = BufferedOutputStream(FileOutputStream(dexInternalStoragePath))
            val buf = ByteArray(1024)
            var len: Int
            while (true) {
                len = bis.read(buf)
                if (len < 0) {
                    break
                }
                dexWriter.write(buf, 0, len)
            }
            dexWriter.close()
            bis.close()
            return true
        } catch (e: IOException) {
            if (dexWriter != null) {
                try {
                    dexWriter.close()
                } catch (ioe: IOException) {
                    ioe.printStackTrace()
                }

            }
            if (bis != null) {
                try {
                    bis.close()
                } catch (ioe: IOException) {
                    ioe.printStackTrace()
                }

            }
            return false
        }

    }
}
