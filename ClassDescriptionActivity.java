package naomi.me.spotopen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import naomi.me.spotopen.Model.UWClass;

/**
 * Created by naomikoo on 2016-08-31.
 */
public class ClassDescriptionActivity extends AppCompatActivity {

    public static final String SUBJECT = "subject";
    public static final String NUMBER = "number";
    public static final String TERM = "term";
    public static final String SECTION = "section";
    UWClass mUwClass;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        Intent intent = getIntent();

        mUwClass = new UWClass();

        if (intent.getExtras() != null) {
            Bundle extras = intent.getExtras();

            if (extras.containsKey(SUBJECT) && extras.containsKey(NUMBER) && extras.containsKey(TERM) && extras.containsKey(SECTION)) {
                String subject = extras.getString(SUBJECT);
                String number = extras.getString(NUMBER);
                String term = extras.getString(TERM);
                String section = extras.getString(SECTION);

                mUwClass = ClassApplication.db.getClass(subject, number, term, section);
            }
        }

        setContentView(R.layout.activity_class_details);

        TextView courseName = (TextView) findViewById(R.id.class_details_course_name);

        if (courseName != null) {
            courseName.setText(mUwClass.getSubject() + mUwClass.getNumber());
        }
    }

}
