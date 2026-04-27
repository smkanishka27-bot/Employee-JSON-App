package com.example.employee_json_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<HashMap<String, String>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        list = new ArrayList<>();

        loadJSON();
    }

    private void loadJSON() {
        try {
            // Read JSON file from assets
            InputStream is = getAssets().open("employee.json");
            int size = is.available();

            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String json = new String(buffer, "UTF-8");

            // Convert to JSON Object
            JSONObject obj = new JSONObject(json);
            JSONArray arr = obj.getJSONArray("employees");

            // Loop through employees
            for (int i = 0; i < arr.length(); i++) {
                JSONObject emp = arr.getJSONObject(i);

                String name = emp.getString("name");
                String details = "ID: " + emp.getString("id") +
                        "\nSalary: " + emp.getString("salary");

                // Store in HashMap
                HashMap<String, String> map = new HashMap<>();
                map.put("name", name);
                map.put("details", details);

                list.add(map);
            }

            // Connect data to UI
            SimpleAdapter adapter = new SimpleAdapter(
                    this,
                    list,
                    R.layout.list_item,
                    new String[]{"name", "details"},
                    new int[]{R.id.txtName, R.id.txtDetails}
            );

            listView.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}