package naomi.me.spotopen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import naomi.me.spotopen.Model.UWClass;

/**
 * Created by naomikoo on 2016-09-06.
 */
public class PickClassSectionActivity extends AppCompatActivity implements ClassDownloadCallback {

    @BindView(R.id.class_section_recycler_view)
    RecyclerView mRecyclerView;

    ClassesAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_choose_class_section);
        ButterKnife.bind(this);

        mAdapter = new ClassesAdapter();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        Intent intent = getIntent();

        String subject = intent.getStringExtra(ClassDescriptionActivity.SUBJECT);
        String number = intent.getStringExtra(ClassDescriptionActivity.NUMBER);
        String term = intent.getStringExtra(ClassDescriptionActivity.TERM);

        ClassDownloaderHelper.downloadClassesAndReturnClasses(term, subject, number, this);
    }


    @Override
    public void onDownloadFinish(List<UWClass> classList) {
        for (UWClass uwClass : classList) {
            mAdapter.addToList(uwClass);
        }
    }
}
