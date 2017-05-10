package com.brave_bunny.dndhelper.widget;

/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.brave_bunny.dndhelper.R;
import com.brave_bunny.dndhelper.Utility;
import com.brave_bunny.dndhelper.database.character.CharacterContract;
import com.brave_bunny.dndhelper.database.character.CharacterUtils.CharacterUtil;

import java.util.concurrent.ExecutionException;

/**
 * RemoteViewsService controlling the data being shown in the scrollable weather detail widget
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class CharacterWidgetIntentService extends RemoteViewsService {
    public final String LOG_TAG = CharacterWidgetIntentService.class.getSimpleName();

    private static final String[] CHARACTER_COLUMNS = {
            CharacterContract.CharacterEntry.TABLE_NAME + CharacterContract.CharacterEntry._ID,
            CharacterContract.CharacterEntry.COLUMN_NAME,
            CharacterContract.CharacterEntry.COLUMN_TOTAL_LEVEL
    };
    // these indices must match the projection
    private static final int INDEX_CHARACTER_ID = 0;
    private static final int INDEX_NAME = 1;
    private static final int INDEX_LEVEL = 2;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {
            private Cursor data = null;

            @Override
            public void onCreate() {
                // Nothing to do
            }

            @Override
            public void onDataSetChanged() {
                if (data != null) {
                    data.close();
                }
                // This method is called by the app hosting the widget (e.g., the launcher)
                // However, our ContentProvider is not exported so it doesn't have access to the
                // data. Therefore we need to clear (and finally restore) the calling identity so
                // that calls use our process and permission
                final long identityToken = Binder.clearCallingIdentity();
                Uri characterUri = CharacterContract.CharacterEntry.buildCharacterUri();
                data = getContentResolver().query(characterUri,
                        CHARACTER_COLUMNS,
                        null,
                        null,
                        CharacterContract.CharacterEntry.COLUMN_NAME + " ASC");
                Binder.restoreCallingIdentity(identityToken);
            }

            @Override
            public void onDestroy() {
                if (data != null) {
                    data.close();
                    data = null;
                }
            }

            @Override
            public int getCount() {
                Log.d(LOG_TAG, "getCount called");
                return data == null ? 0 : data.getCount();
            }

            @Override
            public RemoteViews getViewAt(int position) {
                Log.d(LOG_TAG, "getViewAt called");
                if (position == AdapterView.INVALID_POSITION ||
                        data == null || !data.moveToPosition(position)) {
                    return null;
                }
                RemoteViews views = new RemoteViews(getPackageName(),
                        R.layout.list_item_character);

                Context context = CharacterWidgetIntentService.this;

                ContentValues values = Utility.cursorRowToContentValues(data);
                String name = CharacterUtil.getCharacterName(values);
                int charLevel = CharacterUtil.getCharacterLevel(values);
                String level = context.getString(R.string.total_level, charLevel);
                views.setTextViewText(R.id.name_text, name);
                views.setTextViewText(R.id.level_text, level);

                final Intent fillInIntent = new Intent();
                Uri characterUri = CharacterContract.CharacterEntry.buildCharacterUri();
                fillInIntent.setData(characterUri);
                views.setOnClickFillInIntent(R.id.character_layout, fillInIntent);
                return views;
            }

            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(), R.layout.list_item_character);
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                if (data.moveToPosition(position)) {
                    ContentValues values = Utility.cursorRowToContentValues(data);
                    return CharacterUtil.getId(values);
                }
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }
}