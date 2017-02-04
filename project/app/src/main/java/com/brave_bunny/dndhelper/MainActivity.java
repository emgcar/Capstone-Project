package com.brave_bunny.dndhelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.brave_bunny.dndhelper.create.CreateActivity;
import com.brave_bunny.dndhelper.database.character.CharacterDbHelper;
import com.brave_bunny.dndhelper.database.edition35.RulesDbHelper;
import com.brave_bunny.dndhelper.database.inprogress.InProgressDbHelper;
import com.brave_bunny.dndhelper.select.SelectActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

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
