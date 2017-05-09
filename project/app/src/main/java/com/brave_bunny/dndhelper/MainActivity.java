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

import java.util.List;

import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        SharedPreferences sharedPref = getSharedPreferences("PREF_COLOR", Context.MODE_PRIVATE);
        int color = sharedPref.getInt(getString(R.string.preference_background_color), R.color.colorPrimary);
        LinearLayout mainContainer = (LinearLayout) findViewById(R.id.activty_main_container);
        mainContainer.setBackgroundColor(color);

        setupCharacterDb();
        setupInProgressDb();
        setup35Library();
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
