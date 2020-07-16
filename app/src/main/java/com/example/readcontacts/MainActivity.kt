package com.example.readcontacts

import android.content.pm.PackageManager
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    var cols = listOf<String>(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone._ID
    ).toTypedArray();
    var to= intArrayOf(android.R.id.text1,android.R.id.text2);
    var cols1= listOf<String>(
        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Phone.NUMBER
    ).toTypedArray();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        permission_checker();
    }



        private fun permission_checker() {
            val permissions = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS);
            if (permissions != PackageManager.PERMISSION_GRANTED) {
                grant_permission();
            } else {
                read_contacts();
            }
        }

        private fun grant_permission() {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_CONTACTS), 11);
        }

        private fun read_contacts() {

            var cursor : Cursor? =contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
            startManagingCursor(cursor);
            var from= arrayOf(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone._ID);
            var to= intArrayOf(android.R.id.text1,android.R.id.text2);
            var my_simple=SimpleCursorAdapter(this,android.R.layout.simple_list_item_2,cursor,from,to);
            contacts_listview.adapter=my_simple;
        }

        override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            if (requestCode == 11 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                read_contacts();
            }

        }

}