package com.rdc.gduthelper.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AlertDialog;

import com.rdc.gduthelper.R;
import com.rdc.gduthelper.app.GDUTHelperApp;
import com.rdc.gduthelper.utils.Settings;
import com.rdc.gduthelper.utils.database.ScheduleDBHelper;

/**
 * Created by seasonyuu on 16/1/21.
 */
public class ScheduleSettingsFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {
	private Preference mPrefChooseTerm;
	private Preference mPrefChooseWeek;

	private Preference mPrefScheBackgroud;
	private Preference mPrefScheCardColors;

	private Settings mSettings;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.schedule_settings);

		mSettings = GDUTHelperApp.getSettings();

		mPrefChooseTerm = findPreference(getResources().getString(R.string.schedule_choose_term_key));
		mPrefChooseTerm.setSummary(mSettings.getScheduleChooseTerm());
		mPrefChooseTerm.setTitle(R.string.schedule_choose_term);
		mPrefChooseTerm.setOnPreferenceClickListener(this);

		mPrefChooseWeek = findPreference(getResources().getString(R.string.schedule_current_week_key));
		mPrefChooseWeek.setTitle(R.string.schedule_current_week);
		String week = mSettings.getScheduleCurrentWeek();
		if (week != null)
			mPrefChooseWeek.setSummary(week);
		else {
			//默认为第一周
			mPrefChooseWeek.setSummary("1");
			mSettings.setScheduleCurrentWeek("1");
		}
		mPrefChooseWeek.setOnPreferenceClickListener(this);

		mPrefScheBackgroud = findPreference(getResources().getString(R.string.schedule_background_key));
		mPrefScheBackgroud.setTitle(R.string.schedule_background);

		mPrefScheCardColors = findPreference(getResources().getString(R.string.schedule_card_colors_key));
		mPrefScheCardColors.setTitle(R.string.schedule_card_colors);
		mPrefScheCardColors.setSummary(R.string.schedule_card_colors_tips);
	}

	@Override
	public boolean onPreferenceClick(Preference preference) {
		switch (preference.getKey()) {
			case Settings.SCHEDULE_CHOOSE_TERM:
				ScheduleDBHelper helper = new ScheduleDBHelper(getActivity());
				final String[] terms = helper.getOptionalTerms();
				new AlertDialog.Builder(getActivity())
						.setItems(terms, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								mSettings.setScheduleChooseTerm(terms[which]);
								mPrefChooseTerm.setSummary(terms[which]);
							}
						})
						.setTitle(R.string.choose_terms)
						.setPositiveButton(R.string.cancel, null)
						.show();
				break;
			case Settings.SCHEDULE_CURRENT_WEEK:
				final String[] weeks = new String[22];
				weeks[0] = "0(若还未进入学期则当前周为0)";
				for (int i = 1; i < weeks.length; i++) {
					weeks[i] = i + "";
				}
				new AlertDialog.Builder(getActivity())
						.setItems(weeks, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								mSettings.setScheduleCurrentWeek(which + "");
								mPrefChooseWeek.setSummary(weeks[which]);
							}
						})
						.setTitle(R.string.choose_terms)
						.setPositiveButton(R.string.cancel, null)
						.setNegativeButton(R.string.schedule_choose_first_week, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {

							}
						})
						.show();

		}
		return false;
	}


}
