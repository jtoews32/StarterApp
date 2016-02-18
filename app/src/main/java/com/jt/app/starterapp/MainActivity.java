package com.jt.app.starterapp;

import android.Manifest;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AppKeyPair;
import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.GooglePlayServicesUtil;

import java.io.File;
import java.io.FileOutputStream;



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

    private static final int BOX_REQUEST_AUTH_CODE = 1002;
    static final int GOOGLE_REQUEST_CODE_OPENER = 1001;
    static final int GOOGLE_REQUEST_CODE_PICK_ACCOUNT = 1000;

    String mEmail; // Received from newChooseAccountIntent(); passed to getToken()

    // google Drive
    public void openSceneFour(View view) {
        try {
            String[] accountTypes = new String[]{"com.google", "com.google.android.legacyimap"}; // com.google"};
            Intent intent = AccountPicker.newChooseAccountIntent(null, null, accountTypes, false, null, null, null, null);

            startActivityForResult(intent, GOOGLE_REQUEST_CODE_PICK_ACCOUNT);

        } catch (Exception e) {
            Exception d = e;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case BOX_REQUEST_AUTH_CODE:


            break;
            case GOOGLE_REQUEST_CODE_PICK_ACCOUNT:
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
                } else if (resultCode == GOOGLE_REQUEST_CODE_OPENER) {

                }
            break;

        }


    }



    // Another Shot at google drive
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


  //  final static private Session.AccessType ACCESS_TYPE = Session.AccessType.INSERT_APP_ACCESS_TYPE;
    private static final String TOKEN = "token";
    private static final String PREF_NAME = "dropbox";
    final static private String APP_KEY = "o8h251y2hy64iy6";
    final static private String APP_SECRET = "rdm2j4qcteh6oom";
    private DropboxAPI<AndroidAuthSession> mDBApi;

    // Dropbox
    public void openSceneSix(View view) {

        try {
            AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
            AndroidAuthSession session = new AndroidAuthSession(appKeys);
            mDBApi = new DropboxAPI<AndroidAuthSession>(session);

            // MyActivity below should be your activity class name
            mDBApi.getSession().startOAuth2Authentication(MainActivity.this);
        } catch (Exception e) {

        }
    }

    /*
    public void storeOauth2AccessToken(String secret){
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(TOKEN, secret);
        editor.commit();
    }

    public AndroidAuthSession loadAndroidAuthSession() {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        String token = preferences.getString(TOKEN, null);
        if (token != null) {
            AppKeyPair appKeys = new AppKeyPair(APPKEY, APPKEYSECRET);
            return new AndroidAuthSession(appKeys,token);
        } else {

            return null;
        }
    }

    public boolean hasLoadAndroidAuthSession() {
        return loadAndroidAuthSession() != null;
    }*/


    protected void onResume() {
        super.onResume();
        /*
        if (mDBApi.getSession().authenticationSuccessful()) {
            try {
                // Required to complete auth, sets the access token on the session
                mDBApi.getSession().finishAuthentication();

                String accessToken = mDBApi.getSession().getOAuth2AccessToken();
            } catch (IllegalStateException e) {
                // Log.i("DbAuthLog", "Error authenticating", e);
            }
        }*/
    }

    private static final String BOX_APP_KEY = "005ondupkgds3aohptzajhn63kekdrb6";
    private static final String BOX_APP_SECRET =   "0YMCONrgDol5knImxR1x5YwsRLYfZ8VR";
    private static final String BOX_REDIRECT_URI = "starterapp://localhost";

    // Box
    public void openSceneSeven(View view){
        //Intent authIntent = OAuthActivity.createOAuthActivityIntent(MainActivity.this, BOX_APP_KEY, BOX_APP_SECRET, BOX_REDIRECT_URI, true);
        //startActivityForResult(authIntent, BOX_REQUEST_AUTH_CODE);

    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    public void openSceneEight(View view) {
        String envExternalStorageDirectory = Environment.getExternalStorageDirectory().getPath().toString();

        boolean externalStorageWritable = isExternalStorageWritable();
        boolean externalStorageReadable = isExternalStorageReadable();

        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }

        externalStorageWritable = isExternalStorageWritable();
        externalStorageReadable = isExternalStorageReadable();

        envExternalStorageDirectory = Environment.getExternalStorageDirectory().getPath().toString();

        File mediaDir = new File(envExternalStorageDirectory);
        if (!mediaDir.exists()){
            mediaDir.mkdir();
        }
        String string = "hello world!";

        try {

            File resolveMeSDCard = new File(envExternalStorageDirectory + "/hello_file.txt");
            resolveMeSDCard.createNewFile();
            FileOutputStream fos = new FileOutputStream(resolveMeSDCard);
            fos.write(string.getBytes());
            fos.close();
        } catch (Exception e) {
            Exception d = e;
        }

    }

        /*
        try {
            String FILENAME = "hello_file.txt";
            String string = "hello world!";

            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(string.getBytes());
            fos.close();
        } catch (Exception e) {
            Exception d = e;

        }*/

     /*
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BOX_REQUEST_AUTH_CODE) {

            switch (resultCode) {
                case Activity.RESULT_OK:
                    // An authenticated oath token object is returned upon success.
                    Bundle bundle = data.getExtras();

                    BoxAuthentication.BoxAuthenticationInfo authInfo =
                            (BoxAuthentication.BoxAuthenticationInfo) bundle.get(OAuthActivity.AUTH_INFO);

                    mBoxAPI = new BoxAPIConnection(
                            Constants.BOX_APP_KEY, Constants.BOX_APP_SECRET,
                            authInfo.accessToken(),
                            authInfo.refreshToken());

                    // If we are editing a source and relink it's important to reset the tokens on the source.
                    if (editSource != null) {
                        try {
                            editSource.tokenaccess = authInfo.accessToken();
                            editSource.tokensecret = authInfo.refreshToken();

                            SourceData.getInstance().updateSource(editSource);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }

                    new PopulateBoxAccountInfo().execute((Void) null);
                    break;

                case Activity.RESULT_CANCELED:
                default:
                    String failMessage = "";
                    if (data == null) {
                        failMessage = "Failed to authenticate with box (no error message).";
                    } else {
                        failMessage = "Another type of failure"; // data.getStringExtra(OAuthActivity.ERROR_MESSAGE);
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Error Authenticating Box")
                            .setMessage(failMessage)
                            .setPositiveButton(" Ok ", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create().show();
                    break;
            }

        }*/

    public void openSceneNine(View view) {
        // String[] listOfFiles = getActivity().getFilesDir().list();
      //  File root = new File("/");
     //   traverse(root);
        // new File("/sdcard/").listFiles()        File root = Environment.getExternalStorageDirectory();
        // File file = new File("/home/jtoews/Desktop/"); //boyermooretest/");
        //
        //
        File file = new File("/home/jtoews/Desktop/boyermooretest/thefile4.txt");
        String pattern = "Font";
       // BoyerMoore.searchDirectory(root, pattern);

    }

    public void openSceneTen(View view) {
        Intent intent = new Intent(this, SceneTenListViewActivity.class);
        startActivity(intent);
    }

}
