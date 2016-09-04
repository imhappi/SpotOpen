package naomi.me.spotopen;

import java.util.List;

import naomi.me.spotopen.Api.UWApiInterface;
import naomi.me.spotopen.Model.UWClass;
import naomi.me.spotopen.Model.UWClassWrapper;
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
}
