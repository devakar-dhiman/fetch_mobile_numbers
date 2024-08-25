package com.dhiman.fetch_mobile_numbers

import android.content.Context
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import android.util.Log
import io.flutter.embedding.android.FlutterActivity

class MobileNumbers(private val context: Context):FlutterActivity() {
    fun getSimNumbers(): MutableList<Map<String, Any>>? {
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val mobileNumbers = mutableListOf<Map<String, Any>>()

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {

            val subscriptionManager = SubscriptionManager.from(activity)
            if (subscriptionManager != null) {
                val activeSubscriptionInfoList = subscriptionManager.activeSubscriptionInfoList
                if (activeSubscriptionInfoList != null) {
                    for (subscriptionInfo in activeSubscriptionInfoList) {
                        val slotIndex = subscriptionInfo.simSlotIndex.toString()
                        val carrierName = subscriptionInfo.carrierName.toString()
                        val countryIso = subscriptionInfo.countryIso.toString()
                        val number = subscriptionInfo.number.toString()
                        val sim:Map<String, Any> = mapOf(
                            "slotIndex" to slotIndex,
                            "carrierName" to carrierName,
                            "countryISO" to countryIso,
                            "number" to number
                        )
                        mobileNumbers.add(sim)
                    }
                } else {
                    Log.e("Dhiman", "No active SIM cards found")
                    return null
                }
            } else {
                Log.e("Dhiman", "SubscriptionManager is null")
                return null
            }
        } else {
            val slotIndex = "0"
            val carrierName = telephonyManager.simCarrierIdName.toString()
            val countryIso = telephonyManager.simCountryIso.toString()
            val number = telephonyManager.line1Number.toString()
            val sim: Map<String, Any> = mapOf(
                "slotIndex" to slotIndex,
                "carrierName" to carrierName,
                "countryISO" to countryIso,
                "number" to number
            )
            mobileNumbers.add(sim)
        }

        return if (mobileNumbers.isEmpty()) null else mobileNumbers
    }
}

