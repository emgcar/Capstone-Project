package com.brave_bunny.dndhelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.brave_bunny.dndhelper.create.CreateActivity;
import com.brave_bunny.dndhelper.create.SkillsActivity;
import com.brave_bunny.dndhelper.database.CharacterDbHelper;
import com.brave_bunny.dndhelper.select.SelectActivity;
import com.udacity.brave_bunny.dnd_3_5_library.TrialDbHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        setupCharacterDb();
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

    public void launchSkills(View view) {
        Intent createActivity = new Intent(this, SkillsActivity.class);
        startActivity(createActivity);
    }

    private void setupCharacterDb() {
        new CharacterDbHelper(this);
    }

    private void setup35Library() {
        new TrialDbHelper(this);
    }
}
