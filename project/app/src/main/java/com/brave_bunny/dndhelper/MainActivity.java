package com.brave_bunny.dndhelper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.brave_bunny.dndhelper.create.CreateActivity;
import com.brave_bunny.dndhelper.database.character.CharacterDbHelper;
import com.brave_bunny.dndhelper.database.edition35.RulesDbHelper;
import com.brave_bunny.dndhelper.database.inprogress.InProgressDbHelper;
import com.brave_bunny.dndhelper.select.SelectActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.List;

import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity {

    public static final String ACTION_DATA_UPDATED =
            "com.brave_bunny.dndhelper.ACTION_DATA_UPDATED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        SharedPreferences sharedPref = getSharedPreferences("PREF_COLOR", Context.MODE_PRIVATE);
        int color = sharedPref.getInt(getString(R.string.preference_background_color), R.color.colorPrimary);
        LinearLayout mainContainer = (LinearLayout) findViewById(R.id.activty_main_container);
        mainContainer.setBackgroundColor(color);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544~3347511713");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        updateWidgets();

        setupCharacterDb();
        setupInProgressDb();
        setup35Library();
    }

    private void updateWidgets() {
        // Setting the package ensures that only components in our app will receive the broadcast
        Intent dataUpdatedIntent = new Intent(ACTION_DATA_UPDATED)
                .setPackage(getPackageName());
        sendBroadcast(dataUpdatedIntent);
    }

    public void launchSelectCharacter(View view) {
        Intent selectActivity = new Intent(this, SelectActivity.class);
        startActivity(selectActivity);
    }

    public void launchCreateCharacter(View view) {
        Intent createActivity = new Intent(this, CreateActivity.class);
        startActivity(createActivity);
    }

    public void launchCustomize(View view) {
        AmbilWarnaDialog dialog = new AmbilWarnaDialog(this, R.color.colorPrimary,
                new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        // color is the color selected by the user.
                        SharedPreferences sharedPref = getSharedPreferences("PREF_COLOR", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putInt(getString(R.string.preference_background_color), color);
                        editor.commit();

                        LinearLayout mainContainer = (LinearLayout) findViewById(R.id.activty_main_container);
                        mainContainer.setBackgroundColor(color);
                    }

                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {
                        // cancel was selected by the user
                    }
                });
        dialog.show();
    }

    private void setupCharacterDb() {
        new CharacterDbHelper(this);
    }

    private void setupInProgressDb() {
        new InProgressDbHelper(this);
    }

    private void setup35Library() {
        new RulesDbHelper(this);
    }
}
