package com.tariqeeesciencelab.blogapp;





import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Calendar;

public class SolveAdminUpdate extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextDate,editTextUDate;
    private EditText editTextNumber;
    private EditText editTextTitle;
    private EditText editTextDic,editTextImage,editTextVideo;

    private ProgressBar progSaveProgress;
    private FirebaseFirestore db;

    private SolveDatabase product;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve_admin_update);

        product = (SolveDatabase) getIntent().getSerializableExtra("Question_Video_Solve");
        db = FirebaseFirestore.getInstance();


        editTextDate = findViewById(R.id.edittext_date);
        editTextUDate = findViewById(R.id.edittext_udate);
        editTextNumber = findViewById(R.id.edittext_number);
        editTextTitle = findViewById(R.id.edittext_title);
        editTextDic = findViewById(R.id.edittext_desc);

        editTextImage = findViewById(R.id.edittext_image);
        editTextVideo = findViewById(R.id.edittext_video);


        editTextDate.setText(product.getDate());

        editTextNumber.setText(String.valueOf(product.getNumber()));
        editTextTitle.setText(product.getTitle());
        editTextDic.setText(product.getDescription());
        editTextImage.setText(product.getImage());
        editTextVideo.setText(product.getVideo());





        findViewById(R.id.button_update).setOnClickListener(this);
        findViewById(R.id.button_delete).setOnClickListener(this);
    }

    private boolean hasValidationErrors(String date, String number, String title, String desc,String image, String video) {
        if (date.isEmpty()) {
            editTextDate.setError("Date required");
            editTextDate.requestFocus();
            return true;
        }



        if (number.isEmpty()) {
            editTextNumber.setError("Number required");
            editTextNumber.requestFocus();
            return true;
        }

        if (title.isEmpty()) {
            editTextTitle.setError("Title required");
            editTextTitle.requestFocus();
            return true;
        }

        if (desc.isEmpty()) {
            editTextDic.setError("Description required");
            editTextDic.requestFocus();
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


    private void updateProduct() {
        String date = editTextDate.getText().toString().trim();
        String number = editTextNumber.getText().toString().trim();
        String title = editTextTitle.getText().toString().trim();
        String descp = editTextDic.getText().toString().trim();
        String image = editTextImage.getText().toString().trim();
        String video = editTextVideo.getText().toString().trim();



        if (!hasValidationErrors(date, number, title, descp,image,video)) {




            SolveDatabase p = new SolveDatabase(
                    date,Integer.parseInt(number),title, descp,image,video
            );


            db.collection("Question_Video_Solve").document(product.getId())
                    .update(
                            "date", p.getDate(),

                            "number", p.getNumber(),

                            "title", p.getTitle(),
                            "description", p.getDescription(),
                            "image", p.getImage(),
                            "video",p.getVideo()


                    )
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(SolveAdminUpdate.this, "Post Updated", Toast.LENGTH_LONG).show();


                        }
                    });
        }
    }

    private void deleteProduct() {
        db.collection("Question_Video_Solve").document(product.getId()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SolveAdminUpdate.this, "Product deleted", Toast.LENGTH_LONG).show();
                            finish();

                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_update:
                updateProduct();
                break;
            case R.id.button_delete:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Are you sure about this?");
                builder.setMessage("Deletion is permanent...");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteProduct();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog ad = builder.create();
                ad.show();

                break;
        }
    }
}
