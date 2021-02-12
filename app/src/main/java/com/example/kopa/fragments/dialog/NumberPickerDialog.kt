package com.example.kopa.fragments.dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.NumberPicker.OnValueChangeListener
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputEditText


class NumberPickerDialog(textViewEditText: TextInputEditText,dialogType:String,textView: TextView,typeT:String) : DialogFragment() {
    val textInput = textViewEditText
    val typeDialog = dialogType
    val type = typeT
    val text = textView
    var displayedValues = mutableListOf<String>().toTypedArray()
    @SuppressLint("SetTextI18n")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val numberPicker = NumberPicker(getActivity())
        val builder: AlertDialog.Builder = AlertDialog.Builder(getActivity())
        when(typeDialog){
            "model"->{
                displayedValues = mutableListOf<String>("nike", "not nike", "not nike nIKEE").toTypedArray()
            }
            "material" -> {
                displayedValues = mutableListOf<String>("Штучна шкіра", "Натуральна шкіра", "Поліесткр").toTypedArray()
            }
            "size" ->{
                var values = mutableListOf<String>()
                for (i in 0..90){
                    val sizeValue:Double = (i+30.0)/2.0
                    values.add(i,sizeValue.toString())
                }
                displayedValues = values.toTypedArray()
            }
            "region" ->{
                displayedValues = mutableListOf<String>("UA", "EU", "GB").toTypedArray()
            }
        }
        when(typeDialog){
            "model"->{
                builder.setTitle("Виберіть модель")
                numberPicker.minValue = 0
                numberPicker.maxValue = 2
                numberPicker.value = 1
                numberPicker.displayedValues = displayedValues
            }
            "material" -> {
                builder.setTitle("Виберіть матеріал")
                numberPicker.minValue = 0
                numberPicker.maxValue = 2
                numberPicker.value = 1
                numberPicker.displayedValues = displayedValues
            }
            "size" ->{
                builder.setTitle("Виберіть розмір")
                numberPicker.minValue = 0
                numberPicker.maxValue = 90
                numberPicker.value = 45
                numberPicker.displayedValues = displayedValues
            }
            "region" ->{
                builder.setTitle("Виберіть систему розмірів")
                numberPicker.minValue = 0
                numberPicker.maxValue = 2
                numberPicker.value = 1
                numberPicker.displayedValues = displayedValues
            }
        }
        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
            if(type == "1"){
                textInput.setText(" ${displayedValues[numberPicker.value]}")
            }else if(type =="2"){
                text.setText(" ${displayedValues[numberPicker.value]}")
            }

        })
        builder.setNegativeButton("CANCEL", DialogInterface.OnClickListener { dialog, which ->
        })
        builder.setView(numberPicker)
        return builder.create()
    }
}