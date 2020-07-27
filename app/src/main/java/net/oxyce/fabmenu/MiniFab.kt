package net.oxyce.fabmenu

import android.content.Context
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.lang.Exception

class MiniFab @JvmOverloads constructor(ctx: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : LinearLayoutCompat(ctx, attrs, defStyleAttr) {

    lateinit var miniFAB : FloatingActionButton

    init {
        with(ctx.theme.obtainStyledAttributes(
                attrs, R.styleable.MiniFab, 0, 0)) {
            try {
                val label = getString(R.styleable.MiniFab_miniFabLabel)
                val imgResId = getResourceId(R.styleable.MiniFab_miniFabImage, -1)

                val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
                params.gravity = Gravity.CENTER_VERTICAL

                val labelTV = TextView(ContextThemeWrapper(ctx, R.style.MiniFabLabel))
                if (label != null) {
                    labelTV.layoutParams = params
                    labelTV.text = label
                } else labelTV.visibility = View.GONE


                miniFAB = FloatingActionButton(ctx)
                miniFAB.size = FloatingActionButton.SIZE_MINI

                params.setMargins(16, 16, 16, 16)
                miniFAB.layoutParams = params
                miniFAB.setImageResource(imgResId)

                addView(labelTV)
                addView(miniFAB)
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    override fun setOnClickListener(l: OnClickListener?) = miniFAB.setOnClickListener(l)
}