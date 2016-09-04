package naomi.me.spotopen.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by naomikoo on 2016-08-14.
 */
public class UWClassWrapper {

    @SerializedName("meta")
    UWClassMeta uwClassMeta;

    @SerializedName("data")
    List<UWClass> uwClassList;

    public List<UWClass> getUwClasses() {
        return uwClassList;
    }
}
