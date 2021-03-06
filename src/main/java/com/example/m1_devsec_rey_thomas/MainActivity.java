package com.example.m1_devsec_rey_thomas;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.app.KeyguardManager;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;

import javax.crypto.KeyGenerator;

@RequiresApi(api = Build.VERSION_CODES.M)
public class MainActivity extends AppCompatActivity {

    private Button clickRefresh;

    private static final String KEY_NAME = "myKey";
    private KeyStore keyStore;
    private KeyGenerator keyGenerator;
    private KeyguardManager keyguardManager;

    public static TextView welcome;
    public static TextView accounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clickRefresh = (Button) findViewById(R.id.buttonRefresh);
        accounts = (TextView) findViewById(R.id.accountsView);
        clickRefresh.setEnabled(false);

        keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

        if (!keyguardManager.isKeyguardSecure()) {
            // If the user hasn’t secured their lockscreen with a PIN password or pattern, then display the following text
            welcome.setText("Please enable lockscreen security in your device's Settings");
        }
        else
        {
            //We are free to create our Crypto Key
            CreateKey();
            StartAuthenticationScreen();
        }
        clickRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accounts.setText("Authenticate to display your accounts");
                ShowAuthenticationScreen();
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1)
        {
            if (resultCode == RESULT_OK)
            {
                clickRefresh.setEnabled(true);
                accounts.setText("Welcome !\n\nREFRESH to display your accounts");
            }
            else
            {
                StartAuthenticationScreen();
            }
        }
        else if(requestCode == 2)
        {
            // Credentials entered successfully!
            if (resultCode == RESULT_OK)
            {
                FetchData process = new FetchData();
                process.execute();
            }
            else
            {
                // The user canceled or didn’t complete the lock screen
                // operation. Go to error/cancellation flow.
            }
        }
    }
    private void CreateKey() {
        try {
            // Obtain a reference to the Keystore using the standard Android keystore container identifier (“AndroidKeystore”)
            keyStore = KeyStore.getInstance("AndroidKeyStore");

            //Generate the key
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");

            //Initialize an empty KeyStore//
            keyStore.load(null);

            //Initialize the KeyGenerator//
            keyGenerator.init(new

                    //Specify the operation(s) this key can be used for
                    KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)

                    //Configure this key so that the user has to confirm their identity with a fingerprint each time they want to use it
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());

            //Generate the key
            keyGenerator.generateKey();

        } catch (KeyStoreException
                | NoSuchAlgorithmException
                | NoSuchProviderException
                | InvalidAlgorithmParameterException
                | CertificateException
                | IOException exc) {
            exc.printStackTrace();
        }
    }
    private void StartAuthenticationScreen()
    {
        Intent intent = keyguardManager.createConfirmDeviceCredentialIntent((String) null, (String) null);
        if (intent != null)
        {
            startActivityForResult(intent, 1);
        }
    }
    private void ShowAuthenticationScreen()
    {
        Intent intent = keyguardManager.createConfirmDeviceCredentialIntent((String) null, (String) null);
        if (intent != null)
        {
            startActivityForResult(intent, 2);
        }
    }

}