package net.oxyce.fabmenu

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FabMenu @JvmOverloads constructor(ctx: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : ConstraintLayout (ctx, attrs, defStyleAttr) {

    private val ids : ArrayList<Int> = arrayListOf()
    private val constraints = ConstraintSet()
    private val mainFAB = FloatingActionButton(ctx)
    private var isMenuOpen = true

    init {
        this.id = View.generateViewId()

        mainFAB.setImageResource(R.drawable.ic_action_outline_add)
        mainFAB.setOnClickListener { triggerFabMenu() }
        mainFAB.id = View.generateViewId()
        ids.add(mainFAB.id)
        addView(mainFAB)

        constraints.constrainWidth(mainFAB.id, ConstraintSet.WRAP_CONTENT)
        constraints.constrainHeight(mainFAB.id, ConstraintSet.WRAP_CONTENT)
        constraints.connect(mainFAB.id, ConstraintSet.END, this.id, ConstraintSet.END, 16)
        constraints.connect(mainFAB.id, ConstraintSet.BOTTOM, this.id, ConstraintSet.BOTTOM, 16)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed) {
            for (i in 1 until childCount) {
                val v: View = getChildAt(i)

                v.id = View.generateViewId()
                ids.add(v.id)

                constraints.constrainWidth(v.id, ConstraintSet.WRAP_CONTENT)
                constraints.constrainHeight(v.id, ConstraintSet.WRAP_CONTENT)
                constraints.connect(v.id, ConstraintSet.END, this.id, ConstraintSet.END, 16)
                constraints.connect(v.id, ConstraintSet.BOTTOM, ids.get(i - 1), ConstraintSet.TOP, 8)
            }
            constraints.applyTo(this)
        }
    }


    private fun triggerFabMenu() {
        val propertyValues = hashMapOf<String, Float>()
        val translationDeltas = Array(childCount) { 0f }

        if (!isMenuOpen) {
            propertyValues["rotation"] = 45f
            propertyValues["alpha"] = 1f
        } else {
            propertyValues["rotation"] = 0f
            propertyValues["alpha"] = 0f

            for (i: Int in 1 until translationDeltas.size) {
                translationDeltas[i] = (mainFAB.top - getChildAt(i).top).toFloat()
            }
        }

        val mainFabAnimator = ObjectAnimator.ofFloat(mainFAB, "rotation", propertyValues["rotation"] as Float).apply { duration = 200 }

        val miniFabsAnimator = AnimatorSet().apply {
            duration = 200
            for (i in 1 until translationDeltas.size) {
                val miniFab = getChildAt(i)

                playTogether(
                        ObjectAnimator.ofFloat(miniFab, "alpha", propertyValues["alpha"] as Float),
                        ObjectAnimator.ofFloat(miniFab, "translationY", translationDeltas[i])
                )
            }
        }

        mainFabAnimator.start()
        miniFabsAnimator.start()

        isMenuOpen = !isMenuOpen
    }
}