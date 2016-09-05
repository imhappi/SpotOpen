package naomi.me.spotopen.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by naomikoo on 2016-09-05.
 */
public class UWTermData {

    @SerializedName("previous_term")
    int previous_term;

    @SerializedName("current_term")
    int current_term;

    @SerializedName("next_term")
    int next_term;

    public int getCurrentTerm() {
        return current_term;
    }

    public int getNextTerm() {
        return next_term;
    }

    public int getPreviousTerm() {
        return previous_term;
    }
}
