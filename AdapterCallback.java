package naomi.me.spotopen;

import java.util.List;

import naomi.me.spotopen.Model.UWClass;

/**
 * Created by naomikoo on 2016-09-05.
 */
public interface AdapterCallback {

    void notifyDatasetChanged();

    void setupSubjectSpinner(List<UWClass> courses, String term);
}
