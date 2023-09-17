package com.walker.modulo3projetoicontatos

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class ActionButton(
    context: Context,
    attributeSet: AttributeSet,
): LinearLayout(context, attributeSet) {

    init {
        inflate(context, R.layout.action_button, this@ActionButton)
        context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.ActionButton,
            0, 0
        ).apply {

            try {
                findViewById<TextView>(R.id.buttonTitle).text = getString(R.styleable.ActionButton_buttonTitle)
                findViewById<ImageView>(R.id.buttonIcon).setImageResource(getResourceId(R.styleable.ActionButton_buttonIcon, R.drawable.ic_close))
            } finally {
                recycle()
            }

        }
    }

    fun onClickListener(listener: OnClickListener) {
        findViewById<LinearLayout>(R.id.container).setOnClickListener(listener)
    }

}