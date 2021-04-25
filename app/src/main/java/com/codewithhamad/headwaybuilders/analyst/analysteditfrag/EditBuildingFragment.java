package com.codewithhamad.headwaybuilders.analyst.analysteditfrag;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import com.codewithhamad.headwaybuilders.R;
import com.codewithhamad.headwaybuilders.databasehelper.DatabaseHelper;
import com.codewithhamad.headwaybuilders.models.BuildingModel;
import static android.app.Activity.RESULT_OK;
import static com.codewithhamad.headwaybuilders.analyst.analystaddfrag.AddBuildingFragment.PICK_IMAGE_REQUEST;


public class EditBuildingFragment extends Fragment {
    private ImageView buildingImage;
    private Spinner spinnerBuildingTypes;
    private EditText buildingId, buildingName, buildingArea, numberOfFlats, numberOfFloors, numberOfLifts,
            parkingArea, shortDetails, buildingLocation;
    private Button updateBtn;

    private Bitmap bitmapImage;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_edit_building, container, false);

        // init views
        buildingImage= view.findViewById(R.id.buildingManualImageEditBuild);
        spinnerBuildingTypes= view.findViewById(R.id.buildingTypeSpinnerEditBuild);
        buildingId= view.findViewById(R.id.buildingIdEditBuild);
        buildingName= view.findViewById(R.id.buildingNameEditBuild);
        buildingArea= view.findViewById(R.id.buildingAreaEditBuild);
        numberOfFlats= view.findViewById(R.id.numbOfFlatsEditBuild);
        numberOfFloors= view.findViewById(R.id.numbOfFloorsEditBuild);
        numberOfLifts= view.findViewById(R.id.numbOfLiftsEditBuild);
        parkingArea= view.findViewById(R.id.parkingAreaEditBuild);
        shortDetails= view.findViewById(R.id.shortDetailsEditBuild);
        buildingLocation= view.findViewById(R.id.locationEditBuild);
        updateBtn = view.findViewById(R.id.updtBtn);


        // disabling views at the start except buildingId
        disableViews();


        Bundle bundle= this.getArguments();
        if(bundle!=null){
            if(bundle.getInt("id") != 0 && bundle.getInt("id") != -1){
                buildingId.setText(bundle.getInt("id") + "");
                validateData();
            }
        }


        // setting data to the views retrieved from buildings table based on buildingId
        buildingId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateData();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        // navigating to gallery/images
        buildingImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage(v);
            }
        });


        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // temp vars
                int id;
                String buildingType, name;
                int bArea,flats, floors, lifts, pArea;
                String details, location;

                try {

                    // validating data

                    if(buildingId.getText().length()==0){
                        buildingId.setError("Id is required.");
                        Toast.makeText(getContext(), "Id is required field.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if(buildingName.getText().length()==0){
                        buildingName.setError("Name is required.");
                        Toast.makeText(getContext(), "Name is required field.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if(buildingArea.getText().length()==0){
                        buildingArea.setError("Building Area is required.");
                        Toast.makeText(getContext(), "Area is required field.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if(parkingArea.getText().length()==0){
                        parkingArea.setError("Parking Area is required.");
                        Toast.makeText(getContext(), "Parking area is required field.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if(buildingLocation.getText().length()==0){
                        buildingLocation.setError("Location is required.");
                        Toast.makeText(getContext(), "Location is required field.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if(bitmapImage == null){
                        Toast.makeText(getContext(), "Building Image is required.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    id = Integer.parseInt(buildingId.getText().toString());
                    name= buildingName.getText().toString();
                    bArea= Integer.parseInt(buildingArea.getText().toString());

                    if(numberOfFlats.getText().length()==0)
                        flats= -1;
                    else
                        flats = Integer.parseInt(numberOfFlats.getText().toString());

                    if(numberOfFloors.getText().length()==0)
                        floors= -1;
                    else
                        floors= Integer.parseInt(numberOfFloors.getText().toString());

                    if(numberOfLifts.getText().length()==0)
                        lifts= -1;
                    else
                        lifts= Integer.parseInt(numberOfLifts.getText().toString());

                    pArea= Integer.parseInt(parkingArea.getText().toString());
                    buildingType= spinnerBuildingTypes.getSelectedItem().toString();

                    if(shortDetails.getText().length()==0)
                        details= "No Details Provided.";
                    else
                        details= shortDetails.getText().toString();

                    location= buildingLocation.getText().toString();


                    BuildingModel record = new BuildingModel(bitmapImage, buildingType, id, name, bArea, flats, floors,
                            lifts, pArea, details, location);

                    new DatabaseHelper(getContext()).updateRecordInToBuildingsTable(record);
                }
                catch (Exception e){
                    Toast.makeText(getContext(), "Fill the required fields properly.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    public void validateData(){

        try{
            if(buildingId.getText().length() != 0){

                int bId= Integer.parseInt(buildingId.getText().toString());

                if(new DatabaseHelper(getContext()).doesExistInBuildingTable(bId)){

                    buildingId.setTextColor(getResources().getColor(R.color.green));
                    BuildingModel buildingModel= new DatabaseHelper(getContext()).getByIdFromBuildingTable(bId);

                    if(buildingModel != null) {
                        enableViews();
                        setDataToTheViews(buildingModel);
                    }
                    else{
                        Toast.makeText(getContext(), "buildingModel is null", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    buildingId.setTextColor(getResources().getColor(R.color.red));
                    buildingId.setError("Building does not exist.");

                    // disable views
                    disableViews();
                }

            }
            else{
                // disable views
                disableViews();
            }
        }
        catch(Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void enableViews() {

        buildingImage.setEnabled(true);

        spinnerBuildingTypes.setEnabled(true);

        buildingName.setInputType(InputType.TYPE_CLASS_TEXT);
        buildingName.setBackground(getResources().getDrawable(R.drawable.edittext_border_color));

        buildingArea.setInputType(InputType.TYPE_CLASS_NUMBER);
        buildingArea.setBackground(getResources().getDrawable(R.drawable.edittext_border_color));

        numberOfFlats.setInputType(InputType.TYPE_CLASS_NUMBER);
        numberOfFlats.setBackground(getResources().getDrawable(R.drawable.edittext_border_color));

        numberOfFloors.setInputType(InputType.TYPE_CLASS_NUMBER);
        numberOfFloors.setBackground(getResources().getDrawable(R.drawable.edittext_border_color));

        numberOfLifts.setInputType(InputType.TYPE_CLASS_NUMBER);
        numberOfLifts.setBackground(getResources().getDrawable(R.drawable.edittext_border_color));

        parkingArea.setInputType(InputType.TYPE_CLASS_NUMBER);
        parkingArea.setBackground(getResources().getDrawable(R.drawable.edittext_border_color));

        buildingLocation.setInputType(InputType.TYPE_CLASS_TEXT);
        buildingLocation.setBackground(getResources().getDrawable(R.drawable.edittext_border_color));

        shortDetails.setInputType(InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE);
        shortDetails.setBackground(getResources().getDrawable(R.drawable.edittext_border_color));

        updateBtn.setEnabled(true);
    }

    private void disableViews() {

        // converting default image into bitmap and setting it to imageView
        try {
            bitmapImage = BitmapFactory.decodeResource(getResources(), R.drawable.addimg);
            buildingImage.setImageBitmap(bitmapImage);
            buildingImage.setEnabled(false);
        }
        catch(Exception e){
            Toast.makeText(getContext(), "Error loading default image", Toast.LENGTH_SHORT).show();
        }

        spinnerBuildingTypes.setEnabled(false);

        buildingName.setText("");
        buildingName.setInputType(InputType.TYPE_NULL);
        buildingName.setBackground(getResources().getDrawable(R.drawable.edittext_border_color_disabled));

        buildingArea.setText("");
        buildingArea.setInputType(InputType.TYPE_NULL);
        buildingArea.setBackground(getResources().getDrawable(R.drawable.edittext_border_color_disabled));

        numberOfFlats.setText("");
        numberOfFlats.setInputType(InputType.TYPE_NULL);
        numberOfFlats.setBackground(getResources().getDrawable(R.drawable.edittext_border_color_disabled));

        numberOfFloors.setText("");
        numberOfFloors.setInputType(InputType.TYPE_NULL);
        numberOfFloors.setBackground(getResources().getDrawable(R.drawable.edittext_border_color_disabled));

        numberOfLifts.setText("");
        numberOfLifts.setInputType(InputType.TYPE_NULL);
        numberOfLifts.setBackground(getResources().getDrawable(R.drawable.edittext_border_color_disabled));

        parkingArea.setText("");
        parkingArea.setInputType(InputType.TYPE_NULL);
        parkingArea.setBackground(getResources().getDrawable(R.drawable.edittext_border_color_disabled));

        buildingLocation.setText("");
        buildingLocation.setInputType(InputType.TYPE_NULL);
        buildingLocation.setBackground(getResources().getDrawable(R.drawable.edittext_border_color_disabled));

        shortDetails.setText("");
        shortDetails.setInputType(InputType.TYPE_NULL);
        shortDetails.setBackground(getResources().getDrawable(R.drawable.edittext_border_color_disabled));

        updateBtn.setEnabled(false);
    }


    private void setDataToTheViews(BuildingModel buildingModel) {

        buildingImage.setImageBitmap(buildingModel.getBuildingImage());
        bitmapImage = buildingModel.getBuildingImage();

        buildingName.setText(buildingModel.getBuildingName());

        buildingArea.setText(String.valueOf(buildingModel.getBuildingAreaInSqFt()));

        // spinner
        spinnerBuildingTypes.setSelection(getIndex(spinnerBuildingTypes, buildingModel.getBuildingType()));

        // flats
        if (buildingModel.getNumbOfFlats() == -1) {
            numberOfFlats.setText("");
        }
        else {
            numberOfFlats.setText(String.valueOf(buildingModel.getNumbOfFlats()));
        }

        // floors
        if(buildingModel.getNumbOfFloors() == -1) {
            numberOfFloors.setText("");
        }
        else{
            numberOfFloors.setText(String.valueOf(buildingModel.getNumbOfFloors()));
        }

        // lifts
        if(buildingModel.getNumbOfLifts() == -1){
            numberOfLifts.setText("");
        }
        else {
            numberOfLifts.setText(String.valueOf(buildingModel.getNumbOfLifts()));
        }

        parkingArea.setText(String.valueOf(buildingModel.getParkingAreaInSqFt()));

        buildingLocation.setText(buildingModel.getBuildingLocation());


        shortDetails.setText(buildingModel.getShortDetails());
    }

    // getting index of required item of spinner
    private int getIndex(Spinner spinnerBuildingTypes, String buildingType) {
        for(int i=0; i<spinnerBuildingTypes.getCount(); i++){
            if(spinnerBuildingTypes.getItemAtPosition(i).toString().equalsIgnoreCase(buildingType))
                return i;
        }
        return 0;
    }

    // navigating to images/gallery
    public void chooseImage(View objectView){

        try{

            Intent intent= new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);

        }
        catch (Exception e){
            Toast.makeText(getContext(), "Error getting picture from gallery", Toast.LENGTH_SHORT).show();
        }

    }

    // setting image to imageView on Runtime after image is selected
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        try {
            super.onActivityResult(requestCode, resultCode, data);

            if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){

                // converting image into uri
                Uri imageUri = data.getData();

                // converting uri into bitmap
                bitmapImage = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);

                // setting image to imageView
                buildingImage.setImageBitmap(bitmapImage);
            }
        }
        catch (Exception e){
            Toast.makeText(getContext(), "Error setting picture into imageView", Toast.LENGTH_SHORT).show();
        }
    }

}
