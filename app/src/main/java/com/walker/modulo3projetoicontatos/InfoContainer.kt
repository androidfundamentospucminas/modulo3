package com.walker.modulo3projetoicontatos

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class InfoContainer(
    context: Context,
    attributeSet: AttributeSet
): LinearLayout(context, attributeSet) {

    init {
        inflate(context, R.layout.info_container, this@InfoContainer)
        context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.InfoContainer,
            0, 0
        ).apply {

            try {
                findViewById<TextView>(R.id.infoTitle).text = getString(R.styleable.InfoContainer_infoTitle)
                findViewById<TextView>(R.id.infoValue).text = getString(R.styleable.InfoContainer_infoValue)
                findViewById<ImageView>(R.id.infoIcon).setImageResource(getResourceId(R.styleable.InfoContainer_infoIcon, R.drawable.ic_close))
            } finally {
                recycle()
            }

        }
    }

    fun setInfoValue(value: String?) {
        findViewById<TextView>(R.id.infoValue).text = value
    }

}