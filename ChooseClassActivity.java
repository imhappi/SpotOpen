package naomi.me.spotopen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import naomi.me.spotopen.Model.UWClass;

/**
 * Created by naomikoo on 2016-09-04.
 */
public class ChooseClassActivity extends AppCompatActivity implements AdapterCallback {

    @BindView(R.id.term_spinner)
    Spinner mTermSpinner;

    @BindView(R.id.subject_spinner)
    Spinner mSubjectSpinner;

    @BindView(R.id.number_spinner)
    Spinner mNumberSpinner;

    @BindView(R.id.ok_button)
    Button mButton;

    private ArrayAdapter<String> mTermsSpinnerAdapter;
    private ArrayAdapter<String> mSubjectSpinnerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("Naomi", "about to look for terms");

        setContentView(R.layout.activity_choose_class);

        ButterKnife.bind(this);

        mButton.setEnabled(false);

        setupTermSpinner();
    }

    private void setupTermSpinner() {
        mTermsSpinnerAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, ClassDownloaderHelper.getListOfTerms(this));

        mTermSpinner.setAdapter(mTermsSpinnerAdapter);
        mTermSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                startCoursesDownload(mTermsSpinnerAdapter.getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void startCoursesDownload(final String term) {
        ClassDownloaderHelper.getListOfClasses(term, this);
    }

    @Override
    public void notifyDatasetChanged() {
        if (mTermsSpinnerAdapter != null) {
            mTermsSpinnerAdapter.notifyDataSetChanged();
        }

        if (mSubjectSpinnerAdapter != null) {
            mSubjectSpinnerAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void setupSubjectSpinner(final List<UWClass> classes, final String term) {

        Map<String, Void> subjectsMap = new LinkedHashMap<>();

        for (UWClass uwClass : classes) {
            subjectsMap.put(uwClass.getSubject(), null);
        }

        final List<String> subjects = new ArrayList<>(subjectsMap.keySet());

        mSubjectSpinnerAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, subjects);
        mSubjectSpinner.setAdapter(mSubjectSpinnerAdapter);
        mSubjectSpinner.setEnabled(true);

        mSubjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String subject = subjects.get(position);

                final List<String> numbers = new ArrayList<>();

                for (UWClass uwClass : classes) {
                    if (uwClass.getSubject().equals(subject)) {
                        numbers.add(uwClass.getNumber());
                    }
                }
                ArrayAdapter<String> numberSpinnerAdapter = new ArrayAdapter<>(view.getContext(), R.layout.support_simple_spinner_dropdown_item, numbers);
                mNumberSpinner.setEnabled(true);
                mNumberSpinner.setAdapter(numberSpinnerAdapter);

                mNumberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                        mButton.setEnabled(true);

                        // we know the term, subject, and the number. we can look for this class in the classes list and pass that to the activity
                        for (UWClass uwClass : classes) {
                            if (uwClass.getSubject().equals(subject) && uwClass.getNumber().equals(numbers.get(position))) {
                                // go to activity
                                mButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(v.getContext(), PickClassSectionActivity.class);
                                        intent.putExtra(ClassDescriptionActivity.SUBJECT, subject);
                                        intent.putExtra(ClassDescriptionActivity.NUMBER, numbers.get(position));
                                        intent.putExtra(ClassDescriptionActivity.TERM, term);
                                        startActivity(intent);
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
