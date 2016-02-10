package com.jt.app.starterapp;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ImportDataActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Uri data = getIntent().getData();
        if (data != null) {
            getIntent().setData(null);
            try {
                //       importData(data);
            } catch (Exception e) {
                // warn user about bad data here
                finish();
                return;
            }

            // launch home Activity (with FLAG_ACTIVITY_CLEAR_TOP) here…
        }
    }
}
/*
    private void importData(Uri data) {
        final String scheme = data.getScheme();

        if(ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            try {
                ContentResolver cr = context.getContentResolver();
                InputStream is = cr.openInputStream(data);
                if(is == null) return;

                StringBuffer buf = new StringBuffer();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String str;
                if (is!=null) {
                    while ((str = reader.readLine()) != null) {
                        buf.append(str + "\n" );
                    }
                }
                is.close();

                JSONObject json = new JSONObject(buf.toString());

                // perform your data import here…

            }
        }*/

/*
 *
 String recipient = "",
  subject = "Sharing example",
  message = "";

final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
emailIntent.setType("message/rfc822");

emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{recipient});
emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);

// create attachment
String filename = "example.mytype";

File file = new File(getExternalCacheDir(), filename);
FileOutputStream fos = new FileOutputStream(file);
byte[] bytes = json.toString().getBytes();
fos.write(bytes);
fos.close();

if (!file.exists() || !file.canRead()) {
  Toast.makeText(this, "Problem creating attachment",
      Toast.LENGTH_SHORT).show();
  return;
}

Uri uri = Uri.parse("file://" + file.getAbsolutePath());
emailIntent.putExtra(Intent.EXTRA_STREAM, uri);

startActivityForResult(Intent.createChooser(emailIntent,
        "Email custom data using..."),
        REQUEST_SHARE_DATA);*/