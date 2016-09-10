package naomi.me.spotopen;

import java.util.List;

import naomi.me.spotopen.Model.UWClass;

/**
 * Created by naomikoo on 2016-09-09.
 */
public interface ClassDownloadCallback {

    void onDownloadFinish(List<UWClass> classList);
}
