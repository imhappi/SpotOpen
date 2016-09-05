package naomi.me.spotopen;

import android.util.Log;

import com.squareup.okhttp.ResponseBody;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import naomi.me.spotopen.Api.UWApiInterface;
import naomi.me.spotopen.Model.UWClass;
import naomi.me.spotopen.Model.UWClassWrapper;
import naomi.me.spotopen.Model.UWTermData;
import naomi.me.spotopen.Model.UWTermWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by naomikoo on 2016-09-04.
 */
public class ClassDownloaderHelper {
    public static final String BASE_URL = "https://api.uwaterloo.ca/v2/"; //todo
    private static String API_KEY = "c17f04337a20f48f2644be97c63cba74"; //todo

    public static void downloadAndInsertIntoDB(String term, String subject, String number, final String section) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UWApiInterface service = retrofit.create(UWApiInterface.class);

        Call<UWClassWrapper> call = service.getClass(term, subject, number, API_KEY); //todo replace subjects with db stuff
        call.enqueue(new Callback<UWClassWrapper>() {

            @Override
            public void onResponse(Call<UWClassWrapper> call, Response<UWClassWrapper> response) {
                UWClassWrapper uwClassWrapper = response.body();
                List<UWClass> list = uwClassWrapper.getUwClasses();

                // can you specify sections? research // todo: check if you can specify section in api
                for (UWClass uwclass : list) {
                    if (uwclass.getSection().equals(section)) { //todo: replace section with db section
                        ClassApplication.db.addClass(uwclass);
                    }
                }
            }

            @Override
            public void onFailure(Call<UWClassWrapper> call, Throwable t) {

            }
        });
    }

    public static List<UWClass> downloadClassesAndReturnClassList(String term, String subject, String number, final String section) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UWApiInterface service = retrofit.create(UWApiInterface.class);

        final List<UWClass> classList = new ArrayList<>();

        Call<UWClassWrapper> call = service.getClass(term, subject, number, API_KEY); //todo replace subjects with db stuff
        call.enqueue(new Callback<UWClassWrapper>() {

            @Override
            public void onResponse(Call<UWClassWrapper> call, Response<UWClassWrapper> response) {
                UWClassWrapper uwClassWrapper = response.body();
                List<UWClass> list = uwClassWrapper.getUwClasses();

                // can you specify sections? research // todo: check if you can specify section in api
                for (UWClass uwclass : list) {
                    if (uwclass.getSection().equals(section)) { //todo: replace section with db section
                        classList.add(uwclass);
                    }
                }
            }

            @Override
            public void onFailure(Call<UWClassWrapper> call, Throwable t) {

            }
        });

        return classList; // kind of worried about this since local variable;
        // the value of classList will change because of async network call, but will stack be overwritten by the time it's used? not sure how android works
    }

    public static List<String> getListOfTerms() {

        final List<String> terms = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UWApiInterface service = retrofit.create(UWApiInterface.class);

        Call<UWTermWrapper> call = service.getTerms(API_KEY);
        call.enqueue(new Callback<UWTermWrapper>() {
            @Override
            public void onResponse(Call<UWTermWrapper> call, Response<UWTermWrapper> response) {
                UWTermWrapper wrapper = response.body();
                UWTermData data = wrapper.getTermData();
                terms.add(Integer.toString(data.getPreviousTerm()));
                terms.add(Integer.toString(data.getCurrentTerm()));
                terms.add(Integer.toString(data.getNextTerm()));
            }

            @Override
            public void onFailure(Call<UWTermWrapper> call, Throwable t) {

            }
        });

        Log.d("Naomi", "terms is: " + terms.size());

        return terms;
    }
}
