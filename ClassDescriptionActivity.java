package naomi.me.spotopen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import naomi.me.spotopen.Model.UWClass;

/**
 * Created by naomikoo on 2016-08-31.
 */
public class ClassDescriptionActivity extends AppCompatActivity implements ClassDownloadCallback {

    public static final String SUBJECT = "subject";
    public static final String NUMBER = "number";
    public static final String TERM = "term";
    public static final String SECTION = "section";
    UWClass mUwClass;
    private String mSection;
    private String mSubject;
    private String mNumber;
    private String mTerm;

    private boolean mIsFollowed;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        Intent intent = getIntent();

        if (intent.getExtras() != null) {
            Bundle extras = intent.getExtras();

            if (extras.containsKey(SUBJECT) && extras.containsKey(NUMBER) && extras.containsKey(TERM) && extras.containsKey(SECTION)) {
                mSubject = extras.getString(SUBJECT);
                mNumber = extras.getString(NUMBER);
                mTerm = extras.getString(TERM);
                mSection = extras.getString(SECTION);

                mUwClass = ClassApplication.db.getClass(mSubject, mNumber, mTerm, mSection);

                mIsFollowed = true;

                if (mUwClass == null) {
                    ClassDownloaderHelper.downloadClassesAndReturnClasses(mTerm, mSubject, mNumber, this);
                    mIsFollowed = false;
                }

            }
        }

        setContentView(R.layout.activity_class_details);

        updateUI();
    }

    private void updateUI() {
        TextView courseName = (TextView) findViewById(R.id.class_details_course_name);
        TextView section = (TextView) findViewById(R.id.class_details_course_section);

        if (courseName != null && mUwClass != null && section != null) {
            courseName.setText(mUwClass.getSubject() + mUwClass.getNumber());
            section.setText(mUwClass.getSection());
        }

        final Button followUnfollowButton = (Button) findViewById(R.id.follow_unfollow);

        if (followUnfollowButton != null && mUwClass != null) {

            if (mIsFollowed) {
                followUnfollowButton.setText("Unfollow");
                followUnfollowButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        unfollowToFollow(followUnfollowButton);
                    }
                });
            } else {
                followUnfollowButton.setText("Follow");
                followUnfollowButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        followToUnfollow(followUnfollowButton);
                    }
                });
            }
        }
    }

    private void unfollowToFollow(final Button followUnfollowButton) {
        ClassApplication.db.deleteClass(mUwClass);
        followUnfollowButton.setText("Follow");
        followUnfollowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClassApplication.db.addClass(mUwClass);
                followToUnfollow(followUnfollowButton);
            }
        });
    }

    private void followToUnfollow(final Button followUnfollowButton) {
        ClassApplication.db.addClass(mUwClass);
        followUnfollowButton.setText("Unfollow");
        followUnfollowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClassApplication.db.deleteClass(mUwClass);
                unfollowToFollow(followUnfollowButton);
            }
        });
    }

    @Override
    public void onDownloadFinish(List<UWClass> classList) {
        for (UWClass uwClass : classList) {
            if (uwClass.getSection().equals(mSection)) {
                mUwClass = uwClass;
            }
        }

        updateUI();
    }
}
