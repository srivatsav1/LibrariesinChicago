package srivatsav.naga.satya.isukapalli;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListDisplay extends AppCompatActivity {


    List<String> mobileArray2 = new ArrayList<>();

    HashMap map = new HashMap();
    HashMap<String, HashMap> globalMap = new HashMap<String, HashMap>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_display);

        JSONArray jsonArray = HttpURLUtility.getRequestArray("https://data.cityofchicago.org/resource/x8fc-8rcq.json");
        try {
            for (int count = 0; count < jsonArray.length(); count++) {
                JSONObject jsonObject = jsonArray.getJSONObject(count);
                mobileArray2.add(jsonObject.get("name_").toString());

                map.put("name_",jsonObject.get("name_").toString());
                map.put("address",jsonObject.get("address").toString());

                JSONObject jsonObject2 = jsonObject.getJSONObject("location");

                map.put("latitude",jsonObject2.get("latitude"));
                map.put("longitude",jsonObject2.get("longitude").toString());

                globalMap.put(jsonObject.get("name_").toString(),map);
                map = new HashMap();
            }
        }catch(Exception e){

        }
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview, mobileArray2);

        ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //inflated row layout textview
                TextView tagText = (TextView) view.findViewById(R.id.label);
                String tag = tagText.getText().toString();

                Intent intent = new Intent(ListDisplay.this, Satya.class);
                Bundle mBundle = new Bundle();
                //Set Values
                mBundle.putString("latitude", globalMap.get(tag).get("latitude").toString());
                mBundle.putString("longitude", globalMap.get(tag).get("longitude").toString());
                mBundle.putString("address", globalMap.get(tag).get("address").toString());

                intent.putExtras(mBundle);
                startActivity(intent);

            }
        });
    }
}
