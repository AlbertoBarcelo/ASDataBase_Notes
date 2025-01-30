package com.example.asdatabase_notes;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText editTextName, editTextComment;
    Button btnCreate, btnView, btnDelete;
    TextView textViewComment;
    Spinner spinnerComments;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.editTextName);
        editTextComment = findViewById(R.id.editTextComment);
        btnCreate = findViewById(R.id.btnCreate);
        btnView = findViewById(R.id.btnView);
        btnDelete = findViewById(R.id.btnDelete);
        textViewComment = findViewById(R.id.textViewComment);
        spinnerComments = findViewById(R.id.spinnerComments);
        dbHelper = new DatabaseHelper(this);

        loadComments();

        View.OnClickListener resetFieldsListener = view -> {
            editTextName.setText("");
            editTextComment.setText("");
            textViewComment.setText("Veure comentari");
        };

        btnCreate.setOnClickListener(view -> {
            String name = editTextName.getText().toString();
            String commentText = editTextComment.getText().toString();
            dbHelper.addComment(name, commentText);
            loadComments();
            resetFieldsListener.onClick(view);
        });

        btnView.setOnClickListener(view -> {
            String selectedName = spinnerComments.getSelectedItem().toString();
            String comment = dbHelper.getCommentByName(selectedName);
            textViewComment.setText(comment);
        });

        btnDelete.setOnClickListener(view -> {
            String selectedName = spinnerComments.getSelectedItem().toString();
            dbHelper.deleteComment(selectedName);
            loadComments();
            resetFieldsListener.onClick(view);
        });
    }

    private void loadComments() {
        ArrayList<String> names = dbHelper.getAllNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerComments.setAdapter(adapter);
    }
}
