package com.aoc4456.othellokotlin.board

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.aoc4456.othellokotlin.R
import kotlin.random.Random

class BlackOrWhiteDialog : DialogFragment() {

    private lateinit var dialogListener: BlackOrWhiteDialogListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dialogListener = context as BlackOrWhiteDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(
                ("$context must implement BaseDialogListener")
            )
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let { it ->
            val builder = AlertDialog.Builder(it)
            builder.setItems(R.array.black_or_white) { _, index ->
                when(index){
                    0 -> {
                        dialogListener.onClickButtonInDialog(true)
                    }
                    1 -> {
                        dialogListener.onClickButtonInDialog(false)
                    }
                    else -> {
                        dialogListener.onClickButtonInDialog(Random.nextBoolean())
                    }
                }
            }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}

interface BlackOrWhiteDialogListener {
    fun onClickButtonInDialog(isBlack: Boolean)
}
