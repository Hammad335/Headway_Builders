package com.codewithhamad.headwaybuilders.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.codewithhamad.headwaybuilders.OnRefreshWorkerFragmentInterface;
import com.codewithhamad.headwaybuilders.R;
import com.codewithhamad.headwaybuilders.databasehelper.DatabaseHelper;
import com.codewithhamad.headwaybuilders.models.WorkerModel;
import java.util.ArrayList;

public class AllWorkersAdapter extends RecyclerView.Adapter<AllWorkersAdapter.ViewHolder> {

    Context context;
    ArrayList<WorkerModel> workers;
    OnRefreshWorkerFragmentInterface onRefreshWorkerFragmentInterface;

    public AllWorkersAdapter(Context context, ArrayList<WorkerModel> workers, OnRefreshWorkerFragmentInterface onRefreshWorkerFragmentInterface){
        this.context= context;
        this.workers= workers;
        this.onRefreshWorkerFragmentInterface = onRefreshWorkerFragmentInterface;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample_worker_all_details, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        WorkerModel workerModel= workers.get(position);

        if(workerModel != null) {
            holder.id.setText(workerModel.getWorkerId() + "");
            holder.name.setText(workerModel.getWorkerName());
            holder.sal.setText(workerModel.getSalary() + "");
            holder.job.setText(workerModel.getJob());
            holder.buildingId.setText(workerModel.getBuildingId() + "");
        }

        holder.parent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog deleteDialog = new AlertDialog.Builder(context)
                        .setTitle("Delete Message")
                        .setMessage("Are you sure you want to remove this worker from database ?")
                        .setIcon(R.drawable.ic_delete)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                try {
                                    if (new DatabaseHelper(context).deleteRecByIdFromWorkerTable(workerModel.getWorkerId())) {
                                        Toast.makeText(context, "Worker deleted from database successfully.", Toast.LENGTH_SHORT).show();
                                        workers.remove(workerModel);
                                        notifyDataSetChanged();
                                        onRefreshWorkerFragmentInterface.refreshView();
                                    }
                                    else {
                                        Toast.makeText(context, "Unable to delete record from worker table", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                catch (Exception e) {
                                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();
                deleteDialog.show();

                return true;
            }
        });

    }


    @Override
    public int getItemCount() {
        return workers.size();
    }

    public void setWorkers(ArrayList<WorkerModel> workers){
        this.workers= workers;
        notifyDataSetChanged();
    }

    // all the views of sample_worker_all_details layout are initialized here
    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView id, name, sal, job, buildingId;
        CardView parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id= itemView.findViewById(R.id.txtWorkerId);
            name= itemView.findViewById(R.id.txtWorkerName);
            sal= itemView.findViewById(R.id.workerSalaryTxt);
            job= itemView.findViewById(R.id.workerJobTxt);
            buildingId= itemView.findViewById(R.id.workerBuildingId);
            parent= itemView.findViewById(R.id.workerAllDetailsParentCardView);

        }
    }
}
