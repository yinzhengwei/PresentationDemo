package com.yzw.presentation

import android.content.Context
import android.hardware.display.DisplayManager
import android.media.MediaRouter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_send.setOnClickListener {

            showExist()
        }
    }

    private fun show() {
        val service = getSystemService(Context.MEDIA_ROUTER_SERVICE) as MediaRouter
        val route = service.getSelectedRoute(MediaRouter.ROUTE_TYPE_LIVE_VIDEO)
        if (route != null) {
            val display = route.presentationDisplay
            if (display != null) {
                val presentation = MyPresentation(this, display)
                presentation.show()
            }
        }
    }

    var current = 0
    private fun showExist() {
        val displayManager = getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
        val displays = displayManager.getDisplays(DisplayManager.DISPLAY_CATEGORY_PRESENTATION)
        Log.d("displaysSize", displays.size.toString())
        if (displays.isNotEmpty()) {
            val display = displays[current]
            if (display != null) {
                val presentation = MyPresentation(this, display)
                presentation.show()
            }
            if (current == displays.size - 1)
                current = 0
            else
                current++
        } else {
            Toast.makeText(this, "没有搜索到任何设备", Toast.LENGTH_SHORT).show()
        }
    }

}
