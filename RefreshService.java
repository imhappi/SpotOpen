package naomi.me.spotopen;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import naomi.me.spotopen.Model.UWClass;

/**
 * Created by naomikoo on 2016-09-09.
 */
public class RefreshService extends IntentService {

    public RefreshService() {
        super(RefreshService.class.getName());
        setIntentRedelivery(true);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        // update db and send notification

        ScheduledExecutorService scheduleTaskExecutor = Executors.newScheduledThreadPool(1);

// This schedule a runnable task every 30 seconds
        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
            public void run() {
                checkForSpots();
            }
        }, 0, 30, TimeUnit.SECONDS);

    }

    private void checkForSpots() {
        List<UWClass> list = ClassApplication.db.getAllClasses();

        for (UWClass uwClass : list) {
            ClassDownloaderHelper.downloadAndUpdateDB(uwClass.getTerm(), uwClass.getSubject(), uwClass.getNumber(), uwClass.getSection(), this);
        }
    }
}
