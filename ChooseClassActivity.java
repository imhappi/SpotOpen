package naomi.me.spotopen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by naomikoo on 2016-09-04.
 */
public class ChooseClassActivity extends AppCompatActivity {

    @BindView(R.id.term_spinner)
    Spinner mTermSpinner;

    @BindView(R.id.subject_spinner)
    Spinner mSubjectSpinner;

    @BindView(R.id.number_spinner)
    Spinner mNumberSpinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("Naomi", "about to look for terms");

        setContentView(R.layout.activity_choose_class);

        ButterKnife.bind(this);

        setupTermSpinner();
    }

    private void setupTermSpinner() {
        final ArrayAdapter<String> termsSpinnerAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, ClassDownloaderHelper.getListOfTerms());

        mTermSpinner.setAdapter(termsSpinnerAdapter);
        mTermSpinner.setSelection(-1);

        mTermSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setupSubjectSpinner(termsSpinnerAdapter.getItem(position)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setupSubjectSpinner(String term) {


    }

    private void setupNumberSpinner() {

    }

}
