package com.dhiman.fetch_mobile_numbers

import java.io.StringWriter
import android.util.JsonWriter
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import androidx.core.app.ActivityCompat
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.EventChannel.EventSink
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.RequestPermissionsResultListener
import android.util.Log

//import com.google.gson.Gson


/** FetchMobileNumbersPlugin */
class FetchMobileNumbersPlugin : FlutterPlugin, MethodCallHandler, ActivityAware,
  RequestPermissionsResultListener {
  private lateinit var context: Context
  private lateinit var channel: MethodChannel
  private var methodChannelName = "getMobileNumbers"

  private val result: Result? = null
  private val permissionEvent: EventSink? = null


  override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    context = flutterPluginBinding.applicationContext
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "fetch_mobile_numbers")
    channel.setMethodCallHandler(this)
  }

  override fun onMethodCall(call: MethodCall, result: Result) {
    if (call.method == methodChannelName) {
      result.success(getSimNumbers())
    } else {
      result.notImplemented()
    }
  }


  fun getSimNumbers(): MutableList<Map<String, Any>>? {
    val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    val mobileNumbers = mutableListOf<Map<String, Any>>()

    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {

      val subscriptionManager = SubscriptionManager.from(context)
      if (subscriptionManager != null) {
        val activeSubscriptionInfoList = subscriptionManager.activeSubscriptionInfoList
        if (activeSubscriptionInfoList != null) {
          for (subscriptionInfo in activeSubscriptionInfoList) {
            val slotIndex = subscriptionInfo.simSlotIndex.toString()
            val carrierName = subscriptionInfo.carrierName.toString()
            val countryIso = subscriptionInfo.countryIso.toString()
            val number = subscriptionInfo.number.toString()
            Log.e("Dhiman", "${subscriptionInfo.number}")
            val sim:Map<String, Any> = mapOf(
              "slotIndex" to slotIndex,
              "carrierName" to carrierName,
              "countryISO" to countryIso,
              "number" to number
            )
            mobileNumbers.add(sim)
          }
          Log.e("Dhiman", "$mobileNumbers")
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


  override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }

  override fun onAttachedToActivity(binding: ActivityPluginBinding) {
    context = binding.activity
    binding.addRequestPermissionsResultListener(this)
  }

  override fun onDetachedFromActivityForConfigChanges() {
    TODO("Not yet implemented")
  }

  override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
    context = binding.activity
    binding.addRequestPermissionsResultListener(this)
  }

  override fun onDetachedFromActivity() {
    TODO("Not yet implemented")
  }

  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<out String>,
    grantResults: IntArray
  ): Boolean {
    // If request is cancelled, the result arrays are empty.
    if (requestCode == 0) {
      if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        permissionEvent?.success(true)
        getSimNumbers()
        return true
      } else {
        permissionEvent?.success(false)
      }
    }
    result?.error("PERMISSION", "onRequestPermissionsResult is not granted", null)
    return false
  }

}
