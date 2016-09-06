package naomi.me.spotopen.Api;

import com.squareup.okhttp.ResponseBody;

import org.json.JSONArray;
import org.json.JSONObject;

import naomi.me.spotopen.Model.UWClass;
import naomi.me.spotopen.Model.UWClassWrapper;
import naomi.me.spotopen.Model.UWTermWrapper;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by naomikoo on 2016-08-10.
 */
public interface UWApiInterface {

    @GET("terms/{term}/{subject}/{number}/schedule.json")
    Call<UWClassWrapper> getClass(@Path("term") String term, @Path("subject") String subject, @Path("number") String number, @Query("key") String apiKey);

//    @GET("courses/CS/370.json?key=c17f04337a20f48f2644be97c63cba74")
//    Call<UWClassWrapper> getUWClass();

    @GET("terms/list.json")
    Call<UWTermWrapper> getTerms(@Query("key") String apiKey);

    @GET("terms/{term}/courses.json")
    Call<UWClassWrapper> getCourses(@Path("term") String term, @Query("key") String apiKey);

}
