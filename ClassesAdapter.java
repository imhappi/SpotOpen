package naomi.me.spotopen;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import naomi.me.spotopen.Model.UWClass;

/**
 * Created by naomikoo on 2016-08-09.
 */
public class ClassesAdapter extends RecyclerView.Adapter<ClassViewHolder> {

    private List<UWClass> classList;

    public ClassesAdapter() {
        classList = new ArrayList<>();
    }

    @Override
    public ClassViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.class_layout, parent, false);
        return new ClassViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ClassViewHolder holder, int position) {
        UWClass uwClass = classList.get(position);
        // set subject, number, capacity, etc
        holder.setSubjectNumberView(uwClass.getSubject(), uwClass.getNumber());
        holder.setEnrollmentResultsView(uwClass.getTotalEnrolled() + "/" + uwClass.getTotalCapacity());
        holder.setTermView(uwClass.getTerm());
        holder.setNameView(uwClass.getName());
    }

    @Override
    public int getItemCount() {
        return classList.size();
    }

    public void addToList(UWClass uwClass) {
        classList.add(uwClass);
        notifyDataSetChanged();
    }
}
