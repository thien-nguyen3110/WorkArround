package com.example.androidexample;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class projectActivity extends AppCompatActivity {
    private LinearLayout projectListLayout;
    private List<Map<String, String>> projectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.projectassgin);

        // Set up the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Enable the back button
        getSupportActionBar().setTitle("Projects"); // Set the title


        //Button createProjectButton = findViewById(R.id.create_project_button);
        projectListLayout = findViewById(R.id.project_list_layout);
        projectList = new ArrayList<>();

        checkProjects();

        /*
        createProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewProject("Project Name", "Project Description", "Employer Name", "Due Date", "High");
            }
        });

         */
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // Handle back button action
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createNewProject(String name, String description, String assignedTo, String dueDate, String importance) {
        Map<String, String> newProject = new HashMap<>();
        newProject.put("name", name);
        newProject.put("description", description);
        newProject.put("assignedTo", assignedTo);
        newProject.put("dueDate", dueDate);
        newProject.put("importance", importance);

        projectList.add(newProject);
        checkProjects();
    }

    private void checkProjects() {
        projectListLayout.removeAllViews();
        if (projectList.isEmpty()) {
            TextView noProjectsText = new TextView(this);
            noProjectsText.setText("No projects available. Please create a new project.");
            noProjectsText.setTextSize(18);
            projectListLayout.addView(noProjectsText);
        } else {
            for (Map<String, String> project : projectList) {
                View projectView = createProjectView(project);
                projectListLayout.addView(projectView);
            }
        }
    }

    private View createProjectView(Map<String, String> project) {
        LinearLayout projectView = new LinearLayout(this);
        projectView.setOrientation(LinearLayout.VERTICAL);
        TextView nameText = new TextView(this);
        nameText.setText("Name: " + project.get("name"));
        TextView descText = new TextView(this);
        descText.setText("Description: " + project.get("description"));
        TextView assignedText = new TextView(this);
        assignedText.setText("Assigned to: " + project.get("assignedTo"));
        TextView dueText = new TextView(this);
        dueText.setText("Due Date: " + project.get("dueDate"));
        TextView importanceText = new TextView(this);
        importanceText.setText("Importance: " + project.get("importance"));

        projectView.addView(nameText);
        projectView.addView(descText);
        projectView.addView(assignedText);
        projectView.addView(dueText);
        projectView.addView(importanceText);

        return projectView;
    }
}



