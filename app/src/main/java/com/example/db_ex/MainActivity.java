package com.example.db_ex;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.ContentValues;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.os.Bundle;
        import android.text.method.SingleLineTransformationMethod;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ListView;
        import android.widget.SimpleCursorAdapter;

        import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button insert_btn;
    Button select_btn;
    Button delete_btn;
    Button update_btn;

    DBHelper helper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        insert_btn = (Button)findViewById(R.id.insert_btn);
        select_btn = (Button)findViewById(R.id.select_btn);
        delete_btn = (Button)findViewById(R.id.delete_btn);
        update_btn = (Button)findViewById(R.id.update_btn);

        helper = new DBHelper(MainActivity.this, "person.db", null, 1);
        db = helper.getWritableDatabase();
        helper.onCreate(db);

        insert_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();

                values.put("name","홍길동");
                values.put("age",20);
                values.put("address","부산시");
                db.insert("student", null, values);
            }
        });

        select_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Cursor c = db.query("student", null,null,null,null,null,null,null);

                SimpleCursorAdapter adapter =null;
                adapter = new SimpleCursorAdapter(MainActivity.this,
                                    android.R.layout.simple_expandable_list_item_2,c,
                                    new String[] {"name", "age", "address"},
                                    new int[] {android.R.id.text1, android.R.id.text2}, 0);

                ListView list = (ListView)findViewById(R.id.list);
                list.setAdapter(adapter);
            }
        });

        delete_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                db.delete("student","name=?", new String[]{"홍길동"});

            }
        });

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues new_values = new ContentValues();
                new_values.put("age",200);

                db.update("student",new_values, "name=?", new String[]{"홍길동"});
            }
        });




    }
}
