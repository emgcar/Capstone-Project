package com.brave_bunny.dndhelper.data;

/**
 * Created by Jemma on 8/9/2016.
 */
import android.content.UriMatcher;
import android.net.Uri;
import android.test.AndroidTestCase;

import com.brave_bunny.dndhelper.database.character.CharacterContract;
import com.brave_bunny.dndhelper.database.character.CharacterProvider;

/*
    Uncomment this class when you are ready to test your UriMatcher.  Note that this class utilizes
    constants that are declared with package protection inside of the UriMatcher, which is why
    the test must be in the same data package as the Android app code.  Doing the test this way is
    a nice compromise between data hiding and testability.
 */
public class TestCharacterUriMatcher extends AndroidTestCase {

    // content://com.example.android.sunshine.app/weather"
    private static final Uri TEST_CHARACTER_DIR = CharacterContract.CharacterEntry.CONTENT_URI;

    /*
        Students: This function tests that your UriMatcher returns the correct integer value
        for each of the Uri types that our ContentProvider can handle.  Uncomment this when you are
        ready to test your UriMatcher.
     */
    public void testUriMatcher() {
        UriMatcher testMatcher = CharacterProvider.buildUriMatcher();

        assertEquals("Error: The CHARACTER URI was matched incorrectly.",
                testMatcher.match(TEST_CHARACTER_DIR), CharacterProvider.CHARACTER);
    }
}