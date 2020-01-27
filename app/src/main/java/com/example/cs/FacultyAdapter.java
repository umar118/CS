package com.example.cs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FacultyAdapter extends RecyclerView.Adapter<FacultyAdapter.FacultyViewHolder> {

        Context context;
        ArrayList<Faculty> faculties;
public FacultyAdapter(Context context,ArrayList<Faculty> faculties){
    this.context=context;
    this.faculties=faculties;
    }
    @NonNull
    @Override
    public FacultyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.list_of_faculty,parent,false);
        return new FacultyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FacultyViewHolder holder, int position) {
       holder.profile.setImageResource(faculties.get(position).getProfile());
       holder.teachername.setText(faculties.get(position).getTeacher());
       holder.teacherscale.setText(faculties.get(position).getScale());
       holder.teacheremail.setText(faculties.get(position).getEmail());
       holder.teachereducation.setText(faculties.get(position).getEducation());
    }

    @Override
    public int getItemCount() {
        return faculties.size();
    }

    public class FacultyViewHolder extends RecyclerView.ViewHolder{

        public TextView teachername,teacherscale,teacheremail,teachereducation;
        CircleImageView profile;

        public FacultyViewHolder(@NonNull View itemView) {
            super(itemView);

            profile = itemView.findViewById(R.id.profile);
            teachername=   itemView.findViewById(R.id.teacherid);
            teacherscale=   itemView.findViewById(R.id.teaching_scale);
            teacheremail=   itemView.findViewById(R.id.teach_email);
            teachereducation=   itemView.findViewById(R.id.teach_qualification);
        }
    }


}
