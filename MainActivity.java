package naomi.me.spotopen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import naomi.me.spotopen.Model.UWClass;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.favourited_classes_recycler_view)
    RecyclerView mFavouritedClassesRecyclerView;

    @BindView(R.id.add_more_button)
    ImageView mAddMoreButton;

    private RecyclerView.LayoutManager mLayoutManager;
    private ClassesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ///////////// testing
//        UWClass testClass = new UWClass();
//        testClass.setSubject("CS");
//        testClass.setNumber("370");
//        testClass.setTerm("1139");
//        testClass.setSection("LEC 001");
//        testClass.setTotalCapacity(77);
//        testClass.setTotalEnrolled(78);
//
//        ClassApplication.db.addClass(testClass);

        /////////////////////

        ButterKnife.bind(this);

        mLayoutManager = new LinearLayoutManager(this);
        mFavouritedClassesRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ClassesAdapter();
        mFavouritedClassesRecyclerView.setAdapter(mAdapter);

        populateClasses();

        mAddMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Naomi", "onClick - add more button");
                Intent intent = new Intent(v.getContext(), ChooseClassActivity.class);

                v.getContext().startActivity(intent);
            }
        });


//        TODO: implement firebase and check for empty slots and send pushes server-side
//        Firebase.setAndroidContext(getApplicationContext());
//
//        startService(new Intent(this, FirebasePushService.class));
    }

    private void populateClasses() {

        List<UWClass> classList = ClassApplication.db.getAllClasses();

        for (UWClass uwClass : classList) {
            mAdapter.addToList(uwClass);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        mAdapter.clear();
        populateClasses();
    }
}
