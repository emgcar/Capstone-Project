package com.brave_bunny.dndhelper;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.brave_bunny.dndhelper.create.CreateActivity;
import com.brave_bunny.dndhelper.database.character.CharacterDbHelper;
import com.brave_bunny.dndhelper.database.edition35.RulesDbHelper;
import com.brave_bunny.dndhelper.database.inprogress.InProgressDbHelper;
import com.brave_bunny.dndhelper.select.SelectActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity {

    public static final String ACTION_DATA_UPDATED =
            "com.brave_bunny.dndhelper.ACTION_DATA_UPDATED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        //setUpAds();
        updateWidgets();

        /*Button selectButton = (Button) findViewById(R.id.select_character_button);
        selectButton.setEnabled(false);
        Button createButton = (Button) findViewById(R.id.create_character_button);
        createButton.setEnabled(false);*/

        checkForBackgroundColorPref();
        //new SetUpDatabasesTask().execute();
    }

    private void checkForBackgroundColorPref() {
        SharedPreferences sharedPref = getSharedPreferences("PREF_COLOR", Context.MODE_PRIVATE);
        int color = sharedPref.getInt(getString(R.string.preference_background_color), R.color.colorPrimary);
        LinearLayout mainContainer = (LinearLayout) findViewById(R.id.activty_main_container);
        mainContainer.setBackgroundColor(color);
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

    private void setUpAds() {
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544~3347511713");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void updateWidgets() {
        // Setting the package ensures that only components in our app will receive the broadcast
        Intent dataUpdatedIntent = new Intent(ACTION_DATA_UPDATED)
                .setPackage(getPackageName());
        sendBroadcast(dataUpdatedIntent);
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

    private void setProgressPercent(int progress) {
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.loader_progress);
        progressBar.setProgress(progress);
    }

    private void enableButtons() {
        findViewById(R.id.loader_progress).setVisibility(View.INVISIBLE);
        findViewById(R.id.create_character_button).setEnabled(true);
        findViewById(R.id.select_character_button).setEnabled(true);
    }
}
