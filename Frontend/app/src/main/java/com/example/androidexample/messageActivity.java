package com.example.androidexample;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Arrays;
import java.util.List;

public class messageActivity extends AppCompatActivity {

    private RecyclerView recyclerViewIndividual;
    private RecyclerView recyclerViewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messagehome);

        recyclerViewIndividual = findViewById(R.id.recyclerView_individual);
        recyclerViewGroup = findViewById(R.id.recyclerView_group);
        setTitle("Messages");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Sample data until backEnd finishes
        List<String> individualMessages = Arrays.asList("Alice", "Bob", "Charlie");
        List<String> groupMessages = Arrays.asList("Project Team", "Family Group");

        // Setting up adapters
        recyclerViewIndividual.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewIndividual.setAdapter(new messageAdapter(this, individualMessages, false));

        recyclerViewGroup.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewGroup.setAdapter(new messageAdapter(this, groupMessages, true));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //For permissions this will be chang depending on users (Admin or Employer or Employee) to make sure send back to right page
            Intent intent = new Intent(this, adminActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

