package com.codewithhamad.headwaybuilders.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.codewithhamad.headwaybuilders.R;
import com.codewithhamad.headwaybuilders.BuildingDetailsFragment;
import com.codewithhamad.headwaybuilders.databasehelper.DatabaseHelper;
import com.codewithhamad.headwaybuilders.models.BuildingModel;
import com.google.gson.Gson;

import java.util.ArrayList;

public class BuildingAdapter extends RecyclerView.Adapter<BuildingAdapter.ViewHolder> {

    Context context;
    ArrayList<BuildingModel> buildings;
    String callingActivity;
    String order;

    public BuildingAdapter(Context context, ArrayList<BuildingModel> buildings, String callingActivity, String order){
        this.context= context;
        this.buildings= buildings;
        this.callingActivity= callingActivity;
        this.order= order;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample_building, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BuildingModel buildingModel= buildings.get(position);

        // changing view values on runTime
        holder.buildingImage.setImageBitmap(buildingModel.getBuildingImage());
        holder.buildingName.setText(buildingModel.getBuildingName());

        // TODO: 3/24/2021 navigate user to building details fragment
        // adding onClickListener to each building item
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, buildings.get(position).getBuildingName() + " selected", Toast.LENGTH_SHORT).show();

                // sending data/clickedBuildingModel to BuildingDetailsFragment
                Gson gson = new Gson();
                String jsonItem = gson.toJson(buildings.get(position));
                Bundle bundle = new Bundle();
                bundle.putString("building", jsonItem);
                bundle.putString("key", callingActivity);
                bundle.putString("order", order);

                // navigating to BuildingDetailsFragment
                BuildingDetailsFragment buildingDetailsFragment= new BuildingDetailsFragment();
                buildingDetailsFragment.setArguments(bundle);
                AppCompatActivity appCompatActivity= (AppCompatActivity) v.getContext();

                if(callingActivity.equals("ManagerActivity")){
                    appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.managerContainerFrameLayout, buildingDetailsFragment).commit();
                }
                else if(callingActivity.equals("AnalystActivity")){
                    appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.analystContainerFrameLayout, buildingDetailsFragment).commit();
                }

            }
        });

            holder.parent.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    if(context.toString().toLowerCase().contains("manageractivity")) {

                        AlertDialog deleteDialog = new AlertDialog.Builder(context)
                                .setTitle("Delete Message")
                                .setMessage("Are you sure you want to remove this building from database ?")
                                .setIcon(R.drawable.ic_delete)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        try {
                                            if (new DatabaseHelper(context).deleteRecByIdFromBuildingsTable(buildingModel.getBuildingId())) {
                                                Toast.makeText(context, "Building deleted from database successfully.", Toast.LENGTH_SHORT).show();
                                                buildings.remove(buildingModel);
                                                notifyDataSetChanged();
                                            } else {
                                                Toast.makeText(context, "Unable to delete record from buildings table", Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (Exception e) {
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
                    return true;
                }
            });

    }

    @Override
    public int getItemCount() {
        return buildings.size();
    }

    public void setBuildings(ArrayList<BuildingModel> buildings, String order){
        this.buildings= buildings;
        this.order= order;
        notifyDataSetChanged();
    }

    // all the views of analyst_sample_building layout are initialized here
    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView buildingImage;
        TextView buildingName;
        CardView parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            buildingImage= itemView.findViewById(R.id.analystBuildingImageView);
            buildingName= itemView.findViewById(R.id.analystBuildingName);
            parent= itemView.findViewById(R.id.analystParentCardView);
        }
    }


}
