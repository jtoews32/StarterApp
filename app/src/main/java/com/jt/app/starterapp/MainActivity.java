package com.jt.app.starterapp;

import android.accounts.AccountManager;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.drive.Drive;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openSceneOne(View view) {
        Intent intent = new Intent(this, SceneOneActivity.class);

        // EditText editText = (EditText) findViewById(R.id.edit_message);
        // String message = editText.getText().toString();
        // intent.putExtra(INCOMING_MESSAGE, message);

        startActivity(intent);
    }

    public void openSceneTwo(View view) {
        Intent intent = new Intent(this, SceneTwoActivity.class);
        startActivity(intent);
    }

    public void openSceneThree(View view) {
        Intent intent = new Intent(this, SceneTwoActivity.class);
        startActivity(intent);
    }



    // StarterAppKey: Android's API key
    // keytool -list -v -keystore /home/jtoews/.android/debug.keystore
    static final String startAppKey = "AIzaSyBk8qH8NElXPr8M1zyH7fJb7vjmI_6q8P4";

    // StarterAppOauthKey -- named this in Google Console
    // keytool -exportcert -alias androiddebugkey -keystore /home/jtoews/.android/debug.keystore -list -v
    // password: android
    // sha1: 71:E5:F7:E8:F7:0E:4D:3F:FA:96:D7:DA:13:17:91:4B:C1:89:F8:8C
    static final String starterAppOauthClientID = "908365666466-g7bau343ld8eidn7hik9dktc7k7876ac.apps.googleusercontent.com";

    static final int REQUEST_CODE_OPENER = 1001;
    static final int REQUEST_CODE_PICK_ACCOUNT = 1000;
    String mEmail; // Received from newChooseAccountIntent(); passed to getToken()

    public void openSceneFour(View view) {
        try {
            String[] accountTypes = new String[]{"com.google", "com.google.android.legacyimap"}; // com.google"};
            Intent intent = AccountPicker.newChooseAccountIntent(null, null, accountTypes, false, null, null, null, null);

            startActivityForResult(intent, REQUEST_CODE_PICK_ACCOUNT);

        } catch (Exception e) {
            Exception d = e;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_ACCOUNT) {
            // Receiving a result from the AccountPicker
            if (resultCode == RESULT_OK) {
                mEmail = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                // With the account name acquired, go get the auth token
                // getUsername();
                Toast.makeText(this, R.string.found_account, Toast.LENGTH_SHORT).show();

            } else if (resultCode == RESULT_CANCELED) {
                // The account picker dialog closed without selecting an account.
                // Notify users that they must pick an account to proceed.
                Toast.makeText(this, R.string.pick_account, Toast.LENGTH_SHORT).show();
            } else if (resultCode == REQUEST_CODE_OPENER) {

            }
        }
        // Handle the result from exceptions
    }




    public void openSceneFive(View view) {
        int rc = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);


        /*
        IntentSender intentSender = Drive.DriveApi
                .newOpenFileActivityBuilder()
                .setMimeType(new String[] { "text/plain", "text/html" })
                .build(getGoogleApiClient());


        try {
            startIntentSenderForResult(
                    intentSender, REQUEST_CODE_OPENER, null, 0, 0, 0);
        } catch (IntentSender.SendIntentException e) {
          //  Log.w(TAG, "Unable to send intent", e);
        }*/


    }

}
