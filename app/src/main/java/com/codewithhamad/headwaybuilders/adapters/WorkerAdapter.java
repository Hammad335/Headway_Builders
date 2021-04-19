package com.codewithhamad.headwaybuilders.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.codewithhamad.headwaybuilders.R;
import com.codewithhamad.headwaybuilders.models.WorkerModel;

import java.util.ArrayList;

public class WorkerAdapter extends RecyclerView.Adapter<WorkerAdapter.ViewHolder> {

    Context context;
    ArrayList<WorkerModel> workers;

    public WorkerAdapter(Context context, ArrayList<WorkerModel> workers){
        this.context= context;
        this.workers= workers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample_worker, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WorkerModel workerModel= workers.get(position);

        if(workerModel != null) {
            holder.workerId.setText(workerModel.getWorkerId() + "");
            holder.workerName.setText(workerModel.getWorkerName());
        }
        else{
            Toast.makeText(context, "worker model is null", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return workers.size();
    }

    // all the views of analyst_sample_worker layout are initialized here
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView workerId, workerName;
        CardView parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            workerId= itemView.findViewById(R.id.workerIdTxt);
            workerName= itemView.findViewById(R.id.workerNameTxt);
            parent= itemView.findViewById(R.id.workerParentCardView);

        }
    }
}
