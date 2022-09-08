package kr.hs.dgsw.noepa_ls.util

import android.content.Context
import android.telephony.SmsManager
import android.widget.Toast


class NoepaUtil {
    companion object {

        const val NoepaUtil = "NoepaUtil"

        fun sendSMS(phoneNo:String, msg:String, context: Context?) {
            // Do something
            try {
                val smsManager: SmsManager = SmsManager.getDefault()// context!!.getSystemService(SmsManager::class.java)

                // on below line we are sending text message.
                smsManager.sendTextMessage(phoneNo, null, msg, null, null)

                // on below line we are displaying a toast message for message send,
                Toast.makeText(context, "Message Sent", Toast.LENGTH_LONG).show()


            } catch (e:Exception) {
                Toast.makeText(
                    context,
                    "Please enter all the data.." + e.message.toString(),
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        }
    }
}