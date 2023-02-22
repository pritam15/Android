package com.example.jettipcalculator.Util

fun calculateTip( totalBillAmount : Double, tipPercentage : Int) : Double{

    return if( totalBillAmount > 1 &&
        totalBillAmount.toString().isNotEmpty() ){

        (totalBillAmount * tipPercentage ) / 100
    }
    else 0.0

}

fun totalPerPerson( totalBillAmount : Double,
                    tipPercentage: Int,
                    spliteBy: Int) : Double{

    var bill = calculateTip(totalBillAmount,tipPercentage) + totalBillAmount

    return (bill / spliteBy)

}