package naomi.me.spotopen;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by naomikoo on 2016-08-09.
 */
public class ClassViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.subjectnumber)
    TextView subjectNumberView;

    @BindView(R.id.enrollment_results)
    TextView enrollmentResultsView;

    @BindView(R.id.term)
    TextView termView;

    @BindView(R.id.course_name)
    TextView nameView;

    @BindView(R.id.section)
    TextView sectionView;

    private String mCourseNumber;
    private String mEnrollmentResults;
    private String mName;
    private String mTerm;
    private String mCourseSubject;
    private String mSection;

    public ClassViewHolder(View view) {
        super(view);

        ButterKnife.bind(this, view);

//        subjectNumberView = (TextView) view.findViewById(R.id.subjectnumber);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Naomi", "onClick");
                Intent intent = new Intent(v.getContext(), ClassDescriptionActivity.class);
                intent.putExtra(ClassDescriptionActivity.SUBJECT, mCourseSubject);
                intent.putExtra(ClassDescriptionActivity.NUMBER, mCourseNumber);
                intent.putExtra(ClassDescriptionActivity.TERM, mTerm);
                intent.putExtra(ClassDescriptionActivity.SECTION, mSection);
                // TODO should include section number

                v.getContext().startActivity(intent);

            }
        });

    }

    public void setSubjectNumberView(String courseSubject, String courseNumber) {
        mCourseSubject = courseSubject;
        mCourseNumber = courseNumber;
        subjectNumberView.setText(courseSubject + courseNumber);
    }

    public void setSectionView(String section) {
        mSection = section;
        sectionView.setText(section);
    }

    public void setEnrollmentResultsView(String results) {
        mEnrollmentResults = results;
        enrollmentResultsView.setText(results);
    }

    public void setTermView(String term) {
        mTerm = term;
        termView.setText(term);
    }

    public void setNameView(String name) {
        mName = name;
        nameView.setText(name);
    }
}
