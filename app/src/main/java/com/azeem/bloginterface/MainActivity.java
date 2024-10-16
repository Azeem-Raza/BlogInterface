package com.azeem.bloginterface;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText etUserName, etComment, etSearchText, etSearchDate;
    private TextView tvEntries;
    private Button btnSubmit, btnSearch;

    private List<BlogEntry> blogEntries;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUserName = findViewById(R.id.etUserName);
        etComment = findViewById(R.id.etComment);
        etSearchText = findViewById(R.id.etSearchText);
        etSearchDate = findViewById(R.id.etSearchDate);
        tvEntries = findViewById(R.id.tvEntries);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSearch = findViewById(R.id.btnSearch);

        blogEntries = new ArrayList<>();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitEntry();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEntries();
            }
        });
    }

    private void submitEntry() {
        String userName = etUserName.getText().toString().trim();
        String comment = etComment.getText().toString().trim();

        // Validation
        if (userName.isEmpty()) {
            etUserName.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
        } else {
            etUserName.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        }

        if (comment.isEmpty()) {
            etComment.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
        } else {
            etComment.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        }

        if (!userName.isEmpty() && !comment.isEmpty()) {
            String currentDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
            BlogEntry newEntry = new BlogEntry(currentDateTime, userName, comment);
            blogEntries.add(0, newEntry); // Add to the start of the list
            displayEntries();
            etUserName.setText("");
            etComment.setText("");
        } else {
            Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayEntries() {
        StringBuilder entries = new StringBuilder();
        for (int i = 0; i < blogEntries.size(); i++) {
            BlogEntry entry = blogEntries.get(i);
            entries.append(i + 1).append(". ")
                    .append(entry.dateTime).append(" - ")
                    .append(entry.userName).append(": ")
                    .append(entry.comment).append("\n");
        }
        tvEntries.setText(entries.toString());
    }

    private void searchEntries() {
        String searchText = etSearchText.getText().toString().trim();
        String searchDate = etSearchDate.getText().toString().trim();
        StringBuilder searchResults = new StringBuilder();

        for (BlogEntry entry : blogEntries) {
            if ((searchText.isEmpty() || entry.comment.contains(searchText)) &&
                    (searchDate.isEmpty() || entry.dateTime.startsWith(searchDate))) {
                searchResults.append(entry.dateTime).append(" - ")
                        .append(entry.userName).append(": ")
                        .append(entry.comment).append("\n");
            }
        }

        if (searchResults.length() == 0) {
            searchResults.append("No results found.");
        }

        tvEntries.setText(searchResults.toString());
    }

    private static class BlogEntry {
        String dateTime;
        String userName;
        String comment;

        BlogEntry(String dateTime, String userName, String comment) {
            this.dateTime = dateTime;
            this.userName = userName;
            this.comment = comment;
        }
    }
}