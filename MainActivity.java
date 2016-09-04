package naomi.me.spotopen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import naomi.me.spotopen.Api.UWApiInterface;
import naomi.me.spotopen.Model.UWClass;
import naomi.me.spotopen.Model.UWClassWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

//    public static final String BASE_URL = "https://api.uwaterloo.ca/v2/"; //todo
//    private static String API_KEY = "c17f04337a20f48f2644be97c63cba74"; //todo

    @BindView(R.id.favourited_classes_recycler_view)
    RecyclerView mFavouritedClassesRecyclerView;

    private RecyclerView.LayoutManager mLayoutManager;
    private ClassesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mLayoutManager = new LinearLayoutManager(this);
        mFavouritedClassesRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ClassesAdapter();
        mFavouritedClassesRecyclerView.setAdapter(mAdapter);

        populateClasses();


//        TODO: implement firebase and check for empty slots and send pushes server-side
//        Firebase.setAndroidContext(getApplicationContext());
//
//        startService(new Intent(this, FirebasePushService.class));
    }

    private void populateClasses() {

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        UWApiInterface service = retrofit.create(UWApiInterface.class);

        ///////////// testing
        UWClass testClass = new UWClass();
        testClass.setSubject("CS");
        testClass.setNumber("370");
        testClass.setTerm("1139");
        testClass.setSection("LEC 001");
        testClass.setTotalCapacity(77);
        testClass.setTotalEnrolled(78);

        ClassApplication.db.addClass(testClass);

        /////////////////////

//        Call<UWClassWrapper> call = service.getClass("1139", "CS", "370", API_KEY); //todo replace subjects with db stuff
//        call.enqueue(new Callback<UWClassWrapper>() {
//
//            @Override
//            public void onResponse(Call<UWClassWrapper> call, Response<UWClassWrapper> response) {
//                UWClassWrapper uwClassWrapper = response.body();
//                List<UWClass> list = uwClassWrapper.getUwClasses();
//
//                // can you specify sections? research // todo: check if you can specify section in api
//                for (UWClass uwclass : list) {
//                    if (uwclass.getSection().equals("LEC 001")) { //todo: replace section with db section
//                        mAdapter.addToList(uwclass);
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<UWClassWrapper> call, Throwable t) {
//
//            }
//        });
    }
}
