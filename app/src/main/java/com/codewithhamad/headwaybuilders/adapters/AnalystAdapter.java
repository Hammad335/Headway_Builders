package com.codewithhamad.headwaybuilders.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.codewithhamad.headwaybuilders.R;
import com.codewithhamad.headwaybuilders.databasehelper.DatabaseHelper;
import com.codewithhamad.headwaybuilders.models.AnalystLoginModel;
import java.util.ArrayList;

public class AnalystAdapter extends RecyclerView.Adapter<AnalystAdapter.ViewHolder>{

    Context context;
    ArrayList<AnalystLoginModel> analysts;

    public AnalystAdapter(Context context, ArrayList<AnalystLoginModel> analysts){
        this.context= context;
        this.analysts = analysts;
    }

    @NonNull
    @Override
    public AnalystAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample_analyst, parent, false);
        return new AnalystAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AnalystLoginModel analyst= analysts.get(position);

        if(analyst != null) {
            holder.userName.setText(analyst.getName());
            holder.pass.setText(analyst.getPassword());

            holder.removeAnalystBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog deleteDialog= new AlertDialog.Builder(context)
                            .setTitle("Delete Message")
                            .setMessage("Are you sure you want to remove this analyst from database ?")
                            .setIcon(R.drawable.ic_delete)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    try {
                                        if (new DatabaseHelper(context).deleteRecByNameFromAnalystsTable(analyst.getName())) {
                                            Toast.makeText(context, "Analyst deleted from database successfully.", Toast.LENGTH_SHORT).show();
                                            analysts.remove(analyst);
                                            notifyDataSetChanged();
                                        }
                                        else {
                                            Toast.makeText(context, "Unable to delete record from analysts table", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    catch (Exception e){
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
                }
            });

        }
        else{
            Toast.makeText(context, "analyst is null", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public int getItemCount() {
        return analysts.size();
    }

    public void setAnalysts(ArrayList<AnalystLoginModel> analysts) {
        this.analysts = analysts;
        notifyDataSetChanged();
    }

    // all the views of analyst_sample_worker layout are initialized here
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView userName, pass;
        Button removeAnalystBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userName= itemView.findViewById(R.id.txtAnalystName);
            pass= itemView.findViewById(R.id.txtAnalystPass);
            removeAnalystBtn= itemView.findViewById(R.id.removeAnalaystBtn);

        }
    }
}
