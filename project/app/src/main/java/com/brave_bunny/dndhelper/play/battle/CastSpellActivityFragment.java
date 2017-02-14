package com.brave_bunny.dndhelper.play.battle;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brave_bunny.dndhelper.R;

import static com.brave_bunny.dndhelper.database.character.CharacterUtils.CharacterDomainsUtil.setDomainList;
import static com.brave_bunny.dndhelper.database.character.CharacterUtils.CharacterFeatsUtil.setFeatList;
import static com.brave_bunny.dndhelper.database.character.CharacterUtils.CharacterSkillsUtil.setSkillList;
import static com.brave_bunny.dndhelper.database.character.CharacterUtils.CharacterSpellsUtil.setSpellList;
import static com.brave_bunny.dndhelper.play.UseAbilityListAdapter.TYPE_DOMAIN;
import static com.brave_bunny.dndhelper.play.UseAbilityListAdapter.TYPE_FEAT;
import static com.brave_bunny.dndhelper.play.UseAbilityListAdapter.TYPE_SKILL;
import static com.brave_bunny.dndhelper.play.UseAbilityListAdapter.TYPE_SPELL;

/**
 * A placeholder fragment containing a simple view.
 */
public class CastSpellActivityFragment extends Fragment {
    public static final String ROW_INDEX = "row_index";

    private View mRootView;
    static long rowIndex;
    static int mListType;

    public CastSpellActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_dnd_list, container, false);

        Bundle extras = getActivity().getIntent().getExtras();
        rowIndex = (long) extras.get(CastSpellActivity.indexValue);
        mListType = (int) extras.get(CastSpellActivity.listType);

        switch (mListType) {
            case TYPE_DOMAIN:
                setDomainList(getContext(), mRootView, rowIndex);
                break;
            case TYPE_FEAT:
                setFeatList(getContext(), mRootView, rowIndex);
                break;
            case TYPE_SKILL:
                setSkillList(getContext(), mRootView, rowIndex);
                break;
            case TYPE_SPELL:
                setSpellList(getContext(), mRootView, rowIndex);
                break;
            default:
                break;
        }

        return mRootView;
    }
}
