package naomi.me.spotopen.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by naomikoo on 2016-09-05.
 */
public class UWTermWrapper {

    @SerializedName("data")
    UWTermData termData;

    public UWTermData getTermData() {
        return termData;
    }
}
