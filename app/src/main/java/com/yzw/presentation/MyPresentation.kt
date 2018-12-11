package com.yzw.presentation

import android.app.Presentation
import android.content.Context
import android.os.Bundle
import android.view.Display

/**
 * Create by yinzhengwei on 2018/12/10
 * @Function
 */
class MyPresentation(context: Context, display: Display) : Presentation(context, display) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_presentation)
    }

}