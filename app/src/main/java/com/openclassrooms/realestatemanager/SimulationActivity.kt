package com.openclassrooms.realestatemanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.material.slider.RangeSlider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.openclassrooms.realestatemanager.utils.Utils
import com.openclassrooms.realestatemanager.utils.Utils.Companion.arrondiNumber
import kotlin.math.pow

class SimulationActivity : AppCompatActivity() {
    private lateinit var mMonthlyPaymentextField: TextView
    private lateinit var mAmountTextField: TextInputLayout
    private lateinit var mApportTextField: TextInputLayout
    private lateinit var mTauxTextField: TextInputLayout
    private lateinit var mDuration: RangeSlider
    private lateinit var mButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simulation)
        mMonthlyPaymentextField= findViewById(R.id.monthlyPaymentextField)
        mAmountTextField=findViewById(R.id.amountTextField)
        mApportTextField=findViewById(R.id.apportTextField)
        mTauxTextField=findViewById(R.id.tauxTextField)
        mDuration=findViewById(R.id.duration)
        mButton=findViewById(R.id.buttonEstimate)
        mButton.setOnClickListener(){
            println(mMonthlyPaymentextField?.text)
            var mAount= mAmountTextField.editText?.text.toString().toFloat()
            var mApport=mApportTextField.editText?.text.toString().toFloat()
            var mTaux=mTauxTextField.editText?.text.toString().toFloat()
           var mDuration=mDuration.values[0] as Float
           var monthlyPayement= calculeMonthlyPayment(mAount,mTaux,mApport,mDuration)
            mMonthlyPaymentextField.setText(getString(R.string.monthlyAmount)+ arrondiNumber(monthlyPayement)+getString(R.string.money))
            mMonthlyPaymentextField.visibility= View.VISIBLE
        }
    }
    //m = [C × t/12]/[1−(1 + t/12)−n].
    fun calculeMonthlyPayment(amount:Float,taux:Float,apport:Float,duration:Float):Float{
       var reste=amount-apport
       var m= (reste*taux/12)/(1-(1+taux/12).pow(-duration))
        return m
    }
}