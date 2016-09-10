package naomi.me.spotopen;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

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

    public static void downloadAndUpdateDB(String term, String subject, String number, final String section, final Context context) {
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
                        ClassApplication.db.updateClassCapacity(uwclass);
                        if (uwclass.getTotalEnrolled() < uwclass.getTotalCapacity()) {
                            NotificationManager notificationManager = (NotificationManager)
                                    context.getSystemService(Context.NOTIFICATION_SERVICE);

                            Intent notificationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://quest.uwaterloo.ca"));
                            PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

                            Notification n  = new Notification.Builder(context)
                                    .setContentTitle("Spot open in " + uwclass.getSubject() + uwclass.getNumber())
                                    .setContentText(uwclass.getSection())
                                    .setContentIntent(contentIntent)
                                    .setPriority(Notification.PRIORITY_HIGH)
                                    .setAutoCancel(true).build();

                            notificationManager.notify(289, n);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<UWClassWrapper> call, Throwable t) {

            }
        });
    }

    public static void downloadClassesAndReturnClasses(String term, String subject, String number, final ClassDownloadCallback classDownloadCallback) {
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
                    if (uwclass.getSection().contains("LEC")) {
                        classList.add(uwclass);
                    }
                }

                classDownloadCallback.onDownloadFinish(classList);

            }

            @Override
            public void onFailure(Call<UWClassWrapper> call, Throwable t) {

            }
        });
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
