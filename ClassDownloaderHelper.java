package naomi.me.spotopen;

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

    public static UWClass downloadClassesAndReturnClass(String term, String subject, String number, final String section) {
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

        return classList.get(0); // hacky; how do I assign something in the onResponse since the type is void and it's an inner class?
    }

    public static List<String> getListOfTerms(final AdapterCallback callback) {

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
                callback.notifyDatasetChanged();
            }

            @Override
            public void onFailure(Call<UWTermWrapper> call, Throwable t) {

            }
        });

        return terms;
    }

    public static void getListOfClasses(final String term, final AdapterCallback callback) {

        final List<UWClass> courses = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UWApiInterface service = retrofit.create(UWApiInterface.class);

        Call<UWClassWrapper> call = service.getCourses(term, API_KEY);
        call.enqueue(new Callback<UWClassWrapper>() {
            @Override
            public void onResponse(Call<UWClassWrapper> call, Response<UWClassWrapper> response) {
                if (response != null && response.body() != null) {
                    List<UWClass> classList = response.body().getUwClasses();
                    for (UWClass uwClass : classList) {
                        courses.add(uwClass);
                    }
                }
                callback.setupSubjectSpinner(courses, term);
            }

            @Override
            public void onFailure(Call<UWClassWrapper> call, Throwable t) {

            }
        });
    }
}
