@file:Suppress("DEPRECATION")

package com.nahal.developer.family.nahal.helper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.os.BatteryManager
import android.os.Build
import android.os.Debug
import android.os.Environment
import android.os.StatFs
import android.telephony.TelephonyManager
import android.util.Log
import com.nahal.developer.family.nahal.entity.CrashModel
import com.nahal.developer.family.nahal.event.Event
import java.io.File
import java.text.DecimalFormat
import java.util.Locale
import kotlin.math.log10
import kotlin.math.pow

object BugReportHelper {
    private fun isTablet(context: Context): Boolean {
        val xlarge = (context.resources.configuration.screenLayout
                and Configuration.SCREENLAYOUT_SIZE_MASK) == 4
        val large = (context.resources.configuration.screenLayout
                and Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE
        return xlarge || large
    }

    private fun getAppVersion(context: Context): String {
        val manager = context.packageManager
        var info: PackageInfo? = null
        try {
            info = manager.getPackageInfo(context.packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e("" + context.packageName, "Name not found Exception")
        }
        return info!!.versionName
    }

    private fun getScreenOrientation(context: Activity): String {
        val getOrient = context.windowManager.defaultDisplay
        return if (getOrient.width == getOrient.height) {
            "Square"
        } else {
            if (getOrient.width < getOrient.height) {
                "Portrait"
            } else {
                "Landscape"
            }
        }
    }

    private val brand: String
        get() = Build.BRAND
    private val device: String
        get() = Build.DEVICE
    private val model: String
        get() = Build.MODEL
    private val product: String
        get() = Build.PRODUCT
    private val sdk: String
        get() = Build.VERSION.SDK
    private val release: String
        get() = Build.VERSION.RELEASE
    private val incremental: String
        get() = Build.VERSION.INCREMENTAL

    private fun getHeightPixels(context: Context): Int {
        return context.resources.displayMetrics.heightPixels
    }

    private fun getWidthPixels(context: Context): Int {
        return context.resources.displayMetrics.widthPixels
    }

    private fun getScreenLayout(context: Activity): String {
        return when (context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) {
            Configuration.SCREENLAYOUT_SIZE_LARGE -> "Large Screen"
            Configuration.SCREENLAYOUT_SIZE_NORMAL -> "Normal Screen"
            Configuration.SCREENLAYOUT_SIZE_SMALL -> "Small Screen"
            else -> "Screen size is neither large, normal or small"
        }
    }

    private fun convertSize(size: Long): String {
        if (size <= 0) return "0"
        val units = arrayOf("B", "kB", "MB", "GB", "TB")
        val digitGroups = (log10(size.toDouble()) / log10(1024.0)).toInt()
        return DecimalFormat("#,##0.#").format(
            size / 1024.0.pow(digitGroups.toDouble())
        ) + " " + units[digitGroups]
    }

    private val vm_heap_size: String
        get() = convertSize(Runtime.getRuntime().totalMemory())
    private val allocated_vm_size: String
        get() = convertSize(Runtime.getRuntime().freeMemory())
    private val vm_max_heap_size: String
        get() = convertSize(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())
    private val vm_free_heap_size: String
        get() = convertSize(Runtime.getRuntime().maxMemory())
    private val native_allocated_size: String
        get() = convertSize(Debug.getNativeHeapAllocatedSize())

    private fun getBatteryPercentage(context: Context): Int {
        return context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
            ?.getIntExtra(BatteryManager.EXTRA_LEVEL, 0) ?: 100
    }

    private fun getBatteryChargingMode(act: Activity): String {
        val plugged = act.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
            ?.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)
        if (plugged == BatteryManager.BATTERY_PLUGGED_AC) return "AC" else if (plugged == BatteryManager.BATTERY_PLUGGED_USB) return "USB" else if (plugged == BatteryManager.BATTERY_PLUGGED_WIRELESS) return "WireLess"
        return "نامشخص"
    }

    private val sdCardStatus: String
        get() {
            val isSDPresent = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
            return if (isSDPresent) "Mounted" else "Not mounted"
        }

    @get:Suppress("deprecation")
    private val availableInternalMemorySize: String
        get() {
            val path = Environment.getDataDirectory()
            val stat = StatFs(path.path)
            val blockSize = stat.blockSize.toLong()
            val availableBlocks = stat.availableBlocks.toLong()
            return convertSize(availableBlocks * blockSize)
        }

    @get:Suppress("deprecation")
    private val totalInternalMemorySize: String
        get() {
            val path = Environment.getDataDirectory()
            val stat = StatFs(path.path)
            val blockSize = stat.blockSize.toLong()
            val totalBlocks = stat.blockCount.toLong()
            return convertSize(totalBlocks * blockSize)
        }

    @get:Suppress("deprecation")
    private val availableExternalMemorySize: String
        get() = if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            val path = Environment.getExternalStorageDirectory()
            val stat = StatFs(path.path)
            val blockSize = stat.blockSize.toLong()
            val availableBlocks = stat.availableBlocks.toLong()
            convertSize(availableBlocks * blockSize)
        } else {
            "SDCard not present"
        }

    @get:Suppress("deprecation")
    private val totalExternalMemorySize: String
        get() = if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            val path = Environment.getExternalStorageDirectory()
            val stat = StatFs(path.path)
            val blockSize = stat.blockSize.toLong()
            val totalBlocks = stat.blockCount.toLong()
            convertSize(totalBlocks * blockSize)
        } else {
            "SDCard not present"
        }
    private val isRooted: Boolean
        get() {
            var found = false
            val places = arrayOf(
                "/sbin/", "/system/bin/", "/system/xbin/", "/data/local/xbin/",
                "/data/local/bin/", "/system/sd/xbin/", "/system/bin/failsafe/", "/data/local/"
            )
            for (where in places) {
                if (File(where + "su").exists()) {
                    found = true
                    break
                }
            }
            return found
        }

    private fun getNetworkMode(act: Activity): String {
        val connMgr = act.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        val mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        if (wifi!!.isConnectedOrConnecting) {
            return "Wifi"
        } else if (mobile!!.isConnectedOrConnecting) {
            if (mobile.subtype == TelephonyManager.NETWORK_TYPE_1xRTT) return "1xRTT"
            if (mobile.subtype == TelephonyManager.NETWORK_TYPE_CDMA) return "CDMA"
            if (mobile.subtype == TelephonyManager.NETWORK_TYPE_EDGE) return "EDGE"
            if (mobile.subtype == TelephonyManager.NETWORK_TYPE_EVDO_0) return "EVDO 0"
            if (mobile.subtype == TelephonyManager.NETWORK_TYPE_EVDO_A) return "EVDO A"
            if (mobile.subtype == TelephonyManager.NETWORK_TYPE_GPRS) return "GPRS"
            if (mobile.subtype == TelephonyManager.NETWORK_TYPE_HSDPA) return "HSDPA"
            if (mobile.subtype == TelephonyManager.NETWORK_TYPE_HSPA) return "HSPA"
            if (mobile.subtype == TelephonyManager.NETWORK_TYPE_HSUPA) return "HSUPA"
            if (mobile.subtype == TelephonyManager.NETWORK_TYPE_UMTS) return "UMTS"
            if (mobile.subtype == TelephonyManager.NETWORK_TYPE_EHRPD) return "EHRPD"
            if (mobile.subtype == TelephonyManager.NETWORK_TYPE_IDEN) return "IDEN"
            if (mobile.subtype == TelephonyManager.NETWORK_TYPE_EVDO_B) return "EVDO B"
            if (mobile.subtype == TelephonyManager.NETWORK_TYPE_LTE) return "LTE"
            if (mobile.subtype == TelephonyManager.NETWORK_TYPE_HSPAP) return "HSPAP"
            if (mobile.subtype == TelephonyManager.NETWORK_TYPE_UNKNOWN) return "UNKNOWN"
        } else {
            return "No Network"
        }
        return "NULL"
    }

    private fun isSimSupport(context: Context): Boolean {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return tm.simState != TelephonyManager.SIM_STATE_ABSENT
    }

    private fun getLocale(context: Context): String {
        return Locale("", context.resources.configuration.locale.country).displayCountry
    }

    private fun getBatteryStatus(act: Activity): String {
        val status = act.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
            ?.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
        return when (status) {
            BatteryManager.BATTERY_STATUS_CHARGING -> "Charging"
            BatteryManager.BATTERY_STATUS_DISCHARGING -> "Discharging"
            BatteryManager.BATTERY_STATUS_FULL -> "Full"
            else -> "نامشخص"
        }
    }

    fun GetCrashModel(activity: Activity): CrashModel {
        val crashModel = CrashModel()
        crashModel.Package_Name = activity.packageName
        crashModel.Brand = brand
        crashModel.Device = device
        crashModel.Model = model
        crashModel.Product = product
        crashModel.SDK = sdk
        crashModel.Release = release
        crashModel.Incremental = incremental
        crashModel.Height = getHeightPixels(activity)
        crashModel.Width = getWidthPixels(activity)
        crashModel.App_Version = getAppVersion(activity)
        crashModel.Tablet = isTablet(activity)
        crashModel.Device_Orientation = getScreenOrientation(activity)
        crashModel.Screen_Layout = getScreenLayout(activity)
        crashModel.VM_Heap_Size = vm_heap_size
        crashModel.Allocated_VM_Size = allocated_vm_size
        crashModel.VM_Max_Heap_Size = vm_max_heap_size
        crashModel.VM_free_Heap_Size = vm_free_heap_size
        crashModel.Native_Allocated_Size = native_allocated_size
        crashModel.Battery_Percentage = getBatteryPercentage(activity)
        crashModel.Battery_Charging_Status = getBatteryStatus(activity)
        crashModel.Battery_Charging_Via = getBatteryChargingMode(activity)
        crashModel.SDCard_Status = sdCardStatus
        crashModel.Internal_Memory_Size = availableInternalMemorySize
        crashModel.External_Memory_Size = totalExternalMemorySize
        crashModel.Internal_Free_Space = availableInternalMemorySize
        crashModel.External_Free_Space = availableExternalMemorySize
        crashModel.Device_IsRooted = isRooted
        crashModel.Network_Mode = getNetworkMode(activity)
        crashModel.Country = getLocale(activity)
        crashModel.PersonId = Event.getCurrentUserId()
        return crashModel
    }
}