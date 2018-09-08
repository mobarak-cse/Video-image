package com.tariqeeesciencelab.blogapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Calendar;

public class SolveNewPost extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextDate;
    private EditText editTextnumber;
    private EditText editTexttitle;
    private EditText editTextDes;
    private EditText editTextImage,editTextVideo;

    private ProgressBar progSaveProgress;




    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve_new_post);

        db = FirebaseFirestore.getInstance();

        editTextDate = findViewById(R.id.edittext_udate);

        editTextnumber = findViewById(R.id.edittext_number);
        editTexttitle = findViewById(R.id.edittext_title);
        editTextDes = findViewById(R.id.edittext_desc);

        editTextImage= findViewById(R.id.edittext_image);
        editTextVideo = findViewById(R.id.edittext_video);


        progSaveProgress = (ProgressBar) findViewById(R.id.saveProgressid) ;


        Calendar calendar = Calendar.getInstance();
        String currentdate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        editTextDate.setText(currentdate);



        findViewById(R.id.button_save).setOnClickListener(this);


    }

    private boolean hasValidationErrors(String date, String number, String title, String desciption,String image,String video) {
        if (date.isEmpty()) {
            editTextDate.setError("Date required");
            editTextDate.requestFocus();
            return true;
        }



        if (number.isEmpty()) {
            editTextnumber.setError("Number required");
            editTextnumber.requestFocus();
            return true;
        }

        if (title.isEmpty()) {
            editTexttitle.setError("Title required");
            editTexttitle.requestFocus();
            return true;
        }

        if (desciption.isEmpty()) {
            editTextDes.setError("Description required");
            editTextDes.requestFocus();
            return true;
        }

        if (image.isEmpty()) {
            editTextImage.setError("Image required");
            editTextImage.requestFocus();
            return true;
        }

        if (video.isEmpty()) {
            editTextVideo.setError("Video required");
            editTextVideo.requestFocus();
            return true;
        }


        return false;
    }


    private void saveProduct(){
        String date = editTextDate.getText().toString().trim();

        String number = editTextnumber.getText().toString().trim();
        String title = editTexttitle.getText().toString().trim();
        String desciption = editTextDes.getText().toString().trim();
        String image = editTextImage.getText().toString().trim();
        String video = editTextVideo.getText().toString().trim();


        if (!hasValidationErrors(date, number, title,desciption,image,video )) {

            CollectionReference dbProducts = db.collection("Question_Video_Solve");
            progSaveProgress.setVisibility(View.VISIBLE);

            SolveDatabase product = new SolveDatabase(
                    date, Integer.parseInt(number),

                    title,
                    desciption,image,video


            );

            dbProducts.add(product)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(SolveNewPost.this, editTexttitle.getText().toString()+" Product Added", Toast.LENGTH_LONG).show();
                            progSaveProgress.setVisibility(View.INVISIBLE);
                            progSaveProgress.setVisibility(View.INVISIBLE);
                            editTextnumber.setText(" ");
                            editTexttitle.setText(" ");
                            editTextDes.setText(" ");
                            editTextImage.setText(" ");
                            editTextVideo.setText(" ");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SolveNewPost.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

        }
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.button_save:
                saveProduct();
                break;



        }

    }
}

