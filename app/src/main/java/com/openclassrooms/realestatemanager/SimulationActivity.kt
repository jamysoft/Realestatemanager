package com.openclassrooms.realestatemanager

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.slider.RangeSlider
import com.google.android.material.textfield.TextInputLayout
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
        var thereIsError: Boolean
        mButton.setOnClickListener(){
            thereIsError = false
            println(mMonthlyPaymentextField?.text)
            var mAount= mAmountTextField.editText?.text
            var mApport=mApportTextField.editText?.text
            var mTaux=mTauxTextField.editText?.text
           var mDuration=mDuration.values[0] as Float

            if (mAount.toString().isEmpty()
                || mTaux.toString().isEmpty()
                || mDuration.toString().isEmpty()) {
                thereIsError = true
            }
            if(!thereIsError) {
                var monthlyPayement= calculeMonthlyPayment(mAount.toString().toFloat(),mTaux.toString().toFloat(),mApport.toString().toFloat(),mDuration.toString().toFloat())
                mMonthlyPaymentextField.setText(
                    getString(R.string.monthlyAmount) + arrondiNumber(
                        monthlyPayement
                    ) + getString(R.string.money)
                )
                mMonthlyPaymentextField.visibility = View.VISIBLE
            }
            else{
                Toast.makeText(this, "Enter  validate  information !", Toast.LENGTH_SHORT).show()
            }
        }
    }
    //m = [C × t/12]/[1−(1 + t/12)−n].
    fun calculeMonthlyPayment(amount:Float,taux:Float,apport:Float,duration:Float):Float{
       var reste=amount-apport
       var m= (reste*taux/12)/(1-(1+taux/12).pow(-duration))
        return m
    }
}