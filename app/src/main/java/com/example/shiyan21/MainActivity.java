package com.example.shiyan21;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

//主活动类
@SuppressWarnings("ALL")
public class MainActivity extends BaseActivity{
    private final List<Integer> IDList = new ArrayList<>();
    private final List<String> TADList = new ArrayList<>();
    ArrayAdapter simpleAdapter;    //把所携带的数据映射（或说是填充）到用户界面上。
    Button ButtonSeek;
    EditText EditTextSeek;
    String EditTextSeekString ;
    Intent intent1;
    String author;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent1= getIntent();    //直接使用intent的get方法，会自动从bundle获取数据
        author=intent1.getStringExtra("author");   //传回的数据
        InitNote();
        Button ButtonAdd;
        ButtonAdd = (Button)findViewById(R.id.ButtonAdd);
        ButtonAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Add.class); ////得到编辑框输入发的值
            intent.putExtra("author",author);//通过intent 将得到的值传送到另一个页面
            startActivity(intent);
        });
    //SimpleAdapter类是用来处理ListView显示数据的，这个类可以将任何自定义的xml布局文件作为列表项来使用。
        simpleAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1,TADList);  //配置适配器
        ListView ListView = (ListView)findViewById(R.id.ListView);
        ListView.setAdapter(simpleAdapter);       //将两个list中的值通过ArrayList显示出来


        ButtonSeek = findViewById(R.id.ButtonSeek);
        EditTextSeek = findViewById(R.id.EditTextSeek);
        //点击跳转查询界面
        ButtonSeek.setOnClickListener(v -> {
            EditTextSeekString="";
            EditTextSeekString = String.valueOf(EditTextSeek.getText());
            //Log.d("title is ",EditTextSeekString);
            if(EditTextSeekString.length()==0){             //查询为空，给出提示信息
                RefreshTADList();
                Toast.makeText(MainActivity.this,"查询值不能为空",Toast.LENGTH_LONG).show();
            }
            else{           //否则通过intent给查询界面传入查询的title
                Intent intent = new Intent(MainActivity.this, Research.class);
                //intent.putExtra("tranTitle",EditTextSeekString);
                intent.putExtra("tranTitletoRE",EditTextSeekString);
                startActivity(intent);

            }
        });

        ListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){      //配置ArrayList点击按钮
            @Override
            public void  onItemClick(AdapterView<?> parent, View view , int position , long id){
                int tran = IDList.get(position);        //点击不同的行，返回不同的id，根据ID对应其相应的文本
                Intent intent = new Intent(MainActivity.this, Edit.class);
                intent.putExtra("tran",tran);
                startActivity(intent);      //通过intent传输
            }
        });
    }

    private void InitNote() {       //进行数据填装
        MyDataBaseHelper dbHelper = new MyDataBaseHelper(this,"Diary.db",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();     //通过dbhelper获得可写文件
        @SuppressLint("Recycle") Cursor cursor  = db.rawQuery("select * from Diary",null);
        IDList.clear();
        TADList.clear();        //清空两个list
        while(cursor.moveToNext()){
            int id=cursor.getInt(cursor.getColumnIndex("id"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String name = cursor.getString(cursor.getColumnIndex("author"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            if(name.equals(author)){
                IDList.add(id);
                TADList.add(title + "\n" + date);      //对两个list填充数据
            }

        }
    }

    public void RefreshTADList(){       //返回该界面时刷新的方法
        TADList.size();
        //if(size>0){
        TADList.removeAll(TADList);
        IDList.removeAll(IDList);
        simpleAdapter.notifyDataSetChanged();       //清空两个list中的值
        //}
        MyDataBaseHelper dbHelper = new MyDataBaseHelper(this,"Diary.db",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();         //实例化SQLitedatabase
        Cursor cursor  = db.rawQuery("select * from Diary",null);
        while(cursor.moveToNext()){         //对两个list重新赋予值
            int id=cursor.getInt(cursor.getColumnIndex("id"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String name = cursor.getString(cursor.getColumnIndex("author"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            if(name.equals(author)){
                IDList.add(id);
                TADList.add(title+"\n"+ date);      //对两个list填充数据
            }
        }
    }



    @Override
    protected void onStart() {
        super.onStart();
        RefreshTADList();       //调用刷新方法
    }

}