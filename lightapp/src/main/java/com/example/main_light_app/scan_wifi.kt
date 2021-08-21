package com.example.main_light_app

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.*
import android.net.wifi.ScanResult
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.net.wifi.WifiNetworkSpecifier
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.service.autofill.Validators.not
import android.util.Log
import android.util.Log.ASSERT
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
//import com.example.add_device2.myBroadcast2.Companion.getBroadcastAddress
import org.w3c.dom.Text
import java.util.concurrent.TimeUnit



class scan_wifi : AppCompatActivity() {
    lateinit var wifiManager:WifiManager
    lateinit var resultList : ArrayList<ScanResult>
    lateinit var listv : ListView
    lateinit var list: ArrayList<String>
    lateinit var toolbar: Toolbar

    lateinit var passtext: EditText
    lateinit var AP_text: TextView
    lateinit var button_login: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_wifi)
        listv = findViewById(R.id.listview) as ListView
        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        passtext= findViewById(R.id.editTextTextPassword) as EditText
        AP_text= findViewById(R.id.editTextTextPersonName) as TextView
        button_login= findViewById(R.id.button_login) as Button
        passtext.visibility= View.INVISIBLE
        AP_text.visibility= View.INVISIBLE
        button_login.visibility= View.INVISIBLE


        button_login.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                passtext.visibility = View.INVISIBLE
                AP_text.visibility = View.INVISIBLE
                button_login.visibility = View.INVISIBLE
                listv.visibility = View.VISIBLE

                val sharedPreference =  this@scan_wifi.getSharedPreferences("PREFERENCE_NAME",Context.MODE_PRIVATE)

                var editor = sharedPreference.edit()
                wifiPassword=passtext.text.toString()
                editor.putString(wifiSSID,wifiPassword)
                editor.commit()


                tryconnection()
            }
        })


        toolbar=findViewById(R.id.toolbar2)
        setSupportActionBar(toolbar)
        title = "Wifi Access Points"
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)

        Scaneverything()
        

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.getItemId() === android.R.id.home) {
            finish() // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun Scaneverything()
    {

    var REQUEST_CODE: Int =0
    // Here, thisActivity is the current activity
    if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
    != PackageManager.PERMISSION_GRANTED) {

        // Permission is not granted
        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.
        } else {
            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_CODE)

            // REQUEST_CODE is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }
    }



    if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION),
                        REQUEST_CODE)

                // REQUEST_CODE is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        if (ContextCompat.checkSelfPermission(this,
                        android.Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            android.Manifest.permission.INTERNET)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        arrayOf(android.Manifest.permission.INTERNET),
                        REQUEST_CODE)

                // REQUEST_CODE is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }


        if (ContextCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_WIFI_STATE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            android.Manifest.permission.ACCESS_WIFI_STATE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        arrayOf(android.Manifest.permission.ACCESS_WIFI_STATE),
                        REQUEST_CODE)

                // REQUEST_CODE is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        if (ContextCompat.checkSelfPermission(this,
                        android.Manifest.permission.CHANGE_WIFI_STATE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            android.Manifest.permission.CHANGE_WIFI_STATE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        arrayOf(android.Manifest.permission.CHANGE_WIFI_STATE),
                        REQUEST_CODE)

                // REQUEST_CODE is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        startScanning()
    Toast.makeText(this, "Trying to scan all wifi Access Points", Toast.LENGTH_SHORT).show()
}


private var wifiSSID:String=""
private var wifiPassword = "12345678"
private var numberofitem=-1

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
private fun CreateListView(list: ArrayList<String>) {


    var password: String  ="XXXXXXX"
    //Create an adapter for the listView and add the ArrayList to the adapter.
    var mHistory: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list)

    listv.setAdapter(mHistory)
    //list.setAdapter(ArrayAdapter<string>(this, android.R.layout.simple_list_item_1, resultList))


    listv.setOnItemClickListener { parent, view, position, id ->
        val element = listv.getItemAtPosition(position) // The item that was clicked
        //Toast.makeText(this, element.toString(), Toast.LENGTH_SHORT).show()
        wifiSSID=  element.toString()

        var firstattemp:Boolean=true




        numberofitem=position

        val sharedPreference =  this.getSharedPreferences("PREFERENCE_NAME",Context.MODE_PRIVATE)


        password = sharedPreference.getString(wifiSSID, "XXXXXXX").toString()



        if ((password.equals("XXXXXXX")))
        {
            AP_text.text=wifiSSID
            passtext.visibility = View.VISIBLE
            AP_text.visibility = View.VISIBLE
            button_login.visibility = View.VISIBLE
            listv.visibility = View.INVISIBLE
        }

        else

        {


            tryconnection()


        }
        //sharedPreference.getLong("l",1L)




    }
}



    fun tryconnection(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){

            WiFiConnectUseCase(this@scan_wifi).invoke()

        } else
        {
            WiFiConnectLegacyUseCase(this@scan_wifi).invoke()
        }


    }


    val broadcastReceiver = object : BroadcastReceiver() {
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun onReceive(contxt: Context?, intent: Intent?) {
            resultList = wifiManager.scanResults as ArrayList<ScanResult>
            //Toast.makeText(this, "onReceive Called", Toast.LENGTH_SHORT).show()
            stopScanning()



        }
    }


    fun startScanning() {
        wifiManager.startScan()
        registerReceiver(broadcastReceiver, IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION))
        //Toast.makeText(this, "onReceive Called", Toast.LENGTH_SHORT).show()
       /* Handler().postDelayed({
            stopScanning()
        }, 10000)*/
    }

    fun stopScanning() {
        unregisterReceiver(broadcastReceiver)
        /*  val axisList = ArrayList<Axis>()*/
        var ll: String

        ll=""
        list = ArrayList<String>()

        for (result in resultList) {
            ll= result.SSID.toString()
            if (("Voda" in ll) or (true)) {
                //+ " " + result.BSSID.toString() + " " + result.level.toString()
                list.add(ll) //result.BSSID + " " + result.level)
            }
        }

        CreateListView(list)
        //Toast.makeText(this, ll, Toast.LENGTH_SHORT).show()

    }





    @Suppress("DEPRECATION")
    inner  class WiFiConnectLegacyUseCase(private val context: Context) {

        private var wifiManager =
                context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        private val connectivityManager =
                context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager



        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        operator fun invoke() {
            connect()
            initNetwork()
        }

        private fun connect(){
                val intentFilter = IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION)
                val receiver = object : BroadcastReceiver() {
                    override fun onReceive(context: Context?, intent: Intent?) {
                        if (isConnectedToCorrectSSID()) {
                            Log.i("TAG","Successfully connected to the device.")
                            //emitter.onComplete()
                        } else {
                            Log.i("TAG","Still not connected to ${wifiSSID}. Waiting a little bit more...")
                        }
                    }
                }
                Log.i("TAG","Registering connection receiver...")
                context.registerReceiver(receiver, intentFilter)
               // emitter.setCancellable {
                   // Log.i("TAG","Unregistering connection receiver...")
                  //  context.unregisterReceiver(receiver)
                //}
                addNetwork()
            }


        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        private fun initNetwork(): Network? {
            Log.i("TAG","Initializing network...")
            var network: Network? =null
            var networks  = connectivityManager.allNetworks
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    for (it_:Network in networks)
                    {
                        var cmc = connectivityManager.getNetworkCapabilities(it_)
                        if (cmc != null) {
                            if (cmc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                                network=it_
                                break
                            }
                        }
                    }
                }
                else
                {
                    for (it_:Network in networks)
                    {
                        if (connectivityManager.getNetworkInfo(it_)!!.extraInfo == wifiSSID)
                        {
                        network = it_
                        break
                        }
                    }
                }

            return network
        }

        private fun addNetwork() {
            Log.i("TAG","Connecting to ${wifiSSID}...")
            val wc = WifiConfiguration()
            wc.SSID = "\"" + wifiSSID + "\""
            wc.preSharedKey = "\"" + wifiPassword + "\""
            wc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK)
            val netId = wifiManager.addNetwork(wc)
            if (netId != -1) {
                if (!wifiManager.enableNetwork(netId, true)) {
                    Log.i("TAG","Failed to connect to the device.")
                    //emitter.tryOnError(IllegalArgumentException("Failed to connect to the device"))
                }
            } else {
                Log.i("TAG","Failed to connect to the device. addNetwork() returned -1")
                //emitter.tryOnError(IllegalArgumentException("Failed to connect to the device. addNetwork() returned -1"))

            }
        }

        private fun isConnectedToCorrectSSID(): Boolean {
            val currentSSID = wifiManager.connectionInfo.ssid ?: return false
            Log.i("TAG","Connected to $currentSSID")

            var res=(currentSSID == "\"${wifiSSID}\"")
            if (res) {
                var list_temp: ArrayList<String> =ArrayList<String>()
                for (el in list)
                {list_temp.add(el)}

                list_temp[numberofitem] = wifiSSID + "--> OK!"
                CreateListView(list_temp)
            }
            else
            {
                var list_temp: ArrayList<String> =ArrayList<String>()
                for (el in list)
                {list_temp.add(el)}


                list_temp[numberofitem] = wifiSSID + "--> Not conn!"
                CreateListView(list_temp)
            }
            return res
        }
    }



    inner class WiFiConnectUseCase(context: Context) {

        private var wifiManager =
                context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        private val connectivityManager =
                context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


        @RequiresApi(api = Build.VERSION_CODES.Q)
        operator fun invoke(): Pair<Network?, ConnectivityManager.NetworkCallback> { //return the NetworkCallback in order to disconnect properly from the device
            return connect()
             //delay(3, TimeUnit.SECONDS) // wait for 3 sec, just to make sure everything is configured on the device
        }

        @RequiresApi(Build.VERSION_CODES.Q)
        private fun connect(): Pair<Network?, ConnectivityManager.NetworkCallback> {

                var networkx:Network?=null

                val specifier = WifiNetworkSpecifier.Builder()
                        .setSsid(wifiSSID)
                        .setWpa2Passphrase(wifiPassword)
                        .build()

                val networkRequest = NetworkRequest.Builder()
                        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                        .removeCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) // as not internet connection is required for this device
                        .setNetworkSpecifier(specifier)
                        .build()

                val networkCallback = object : ConnectivityManager.NetworkCallback() {
                    override fun onAvailable(network: Network) {
                        super.onAvailable(network)
                        Log.i("TAG","connect to WiFi success. Network is available.")
                        //emitter.onSuccess(Pair(network, this))
                        networkx=network
                    }

                    override fun onUnavailable() {
                        super.onUnavailable()
                        Log.i("TAG","connect to WiFi failed. Network is unavailable")
                        //emitter.tryOnError(IllegalArgumentException("connect to WiFi failed. Network is unavailable"))
                    }
                }

                connectivityManager.requestNetwork(networkRequest, networkCallback)


            return Pair(networkx, networkCallback)
        }
    }

}