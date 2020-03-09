package com.example.studentdatabase;


import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    final  String Tag="MainActivity";
    Context context;
    public  AlertDialog.Builder AlertDialogBuilder;
    private ListView listView;
    private EditText editname,editmobile,editaddress;

    private Button Add,load;
    List<Student> stuList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list_item);
        Add = findViewById(R.id.add);
        load = findViewById(R.id.load);

        context = MainActivity.this;
        getStudentData();
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getStudentData();

            }
        });
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                AlertDialogBuilder.setCancelable(true);

                View mView = getLayoutInflater().inflate(R.layout.add_dialoge_box, null);
                Button save = mView.findViewById(R.id.save);
                editname = mView.findViewById(R.id.editname);
                editmobile = mView.findViewById(R.id.editmobile);
                editaddress = mView.findViewById(R.id.editaddress);
                final AlertDialog alert = AlertDialogBuilder.create();
                alert.setView(mView);
///save/insert_data///
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name = editname.getText().toString();
                        String mobile = editmobile.getText().toString();
                        String address = editaddress.getText().toString();

                        if (TextUtils.isEmpty(name)) {
                            editname.setError("Please input text");
                            return;

                        }
                        if (TextUtils.isEmpty(mobile)) {
                            editmobile.setError("Please input text");
                            return;

                        }
                        if (TextUtils.isEmpty(address)) {
                            editaddress.setError("Please input text");
                            return;

                        }
                        final Gson gson = new GsonBuilder()
                                .setLenient()
                                .create();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://100.43.0.37/")
                                .addConverterFactory(GsonConverterFactory.create(gson))
                                .build();

                        StudentInterface studentInterface = retrofit.create(StudentInterface.class);

                        final Call<String> addStudent = studentInterface.addStudent(name,mobile,address,"");
                        addStudent.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if (!response.isSuccessful()) {
                                    return;
                                }
                                String  res= response.body();
                                Toast.makeText(MainActivity.this, "response " + res, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Log.i(Tag, "" + t.getMessage());
                            }
                        });
                        alert.dismiss();
                    }

                });
                alert.show();
//
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                final Student value=stuList.get(position);
                Toast.makeText(context, value.getName(), Toast.LENGTH_SHORT).show();
                AlertDialogBuilder =new AlertDialog.Builder(context);
                AlertDialogBuilder.setCancelable(true);

                View mView=getLayoutInflater().inflate(R.layout.delete_update_alert_custom_dialog_box,null);

                Button update = mView.findViewById(R.id.update);
                Button cancel = mView.findViewById(R.id.cancel);
                Button delete = mView.findViewById(R.id.delete);



//
//                edtName.setText(value.getName());
//                edtPhone.setText(value.getMobile());
//                edtAddress.setText(value.getAddress());

                final AlertDialog alert2 = AlertDialogBuilder.create();


                alert2.dismiss();
                alert2.setView(mView);
                alert2.show();
//                alert2.setCancelable(false);

                AlertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                AlertDialogBuilder.setCancelable(true);
///cancel///
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alert2.dismiss();
                    }
                });
////delete////
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

//                        int id = Integer.parseInt(textid.getText().toString());
                        String id = value.getId();
                        Log.i(Tag, "id" + id);


                        final Gson gson = new GsonBuilder()
                                .setLenient()
                                .create();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://100.43.0.37/")
                                .addConverterFactory(GsonConverterFactory.create(gson))
                                .build();

                        StudentInterface studentInterface = retrofit.create(StudentInterface.class);

                        final Call<String> deleteStudent = studentInterface.deleteStudent(id);
                        deleteStudent.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if (response.isSuccessful()) {

                                    String  res= response.body();
                                    StudentlistAdapter studentlistAdapter = new StudentlistAdapter(stuList, context);
                                    listView.setAdapter(studentlistAdapter);
                                    Toast.makeText(context, "deleted successfully! " + res, Toast.LENGTH_SHORT).show();
                                }
                            }


                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                t.printStackTrace();
                                Log.i(Tag, "failed  " + t.getMessage());
                            }
                        });
                        alert2.dismiss();

                    }
                });

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialogBuilder =new AlertDialog.Builder(context);
                        AlertDialogBuilder.setCancelable(true);

                        View mView=getLayoutInflater().inflate(R.layout.studentinformationcustomdialog,null);

                        Button Confirmupdate = mView.findViewById(R.id.updateData);
                        Button cancelUpdate = mView.findViewById(R.id.no);

                        final EditText edtid = mView.findViewById(R.id.edtId);
                        final EditText edtname = mView.findViewById(R.id.edtName);
                        final EditText editmobile = mView.findViewById(R.id.edtMobile);
                        final EditText edtaddress = mView.findViewById(R.id.edtAddress);

                        edtid.setText(value.getId());
                        edtname.setText(value.getName());
                        editmobile.setText(value.getMobile());
                        edtaddress.setText(value.getAddress());

                        final AlertDialog alert = AlertDialogBuilder.create();


                        alert.dismiss();
                        alert.setView(mView);
                        alert.show();

                        AlertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                        AlertDialogBuilder.setCancelable(true);
                        Confirmupdate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String id = edtid.getText().toString();
                                Log.i(Tag, "id" + id);
                                String name = edtname.getText().toString();
                                Log.i(Tag, "name" + name);
                                String mobile = editmobile.getText().toString();
                                String address = edtaddress.getText().toString();

                                final Gson gson = new GsonBuilder()
                                        .setLenient()
                                        .create();

                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl("http://100.43.0.37/")
                                        .addConverterFactory(GsonConverterFactory.create(gson))
                                        .build();

                                final StudentInterface studentInterface = retrofit.create(StudentInterface.class);

                                final Call<String> updateStudent = studentInterface.updateStudent(name,mobile,address,id);
                                updateStudent.enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        if (!response.isSuccessful()) {
                                        return;
                                        }
                              String res = response.body();

                                        Toast.makeText(context, "updated successfully! " + res , Toast.LENGTH_SHORT).show();
                                    }


                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                        t.printStackTrace();
                                        Log.i(Tag, "failed  " + t.getMessage());
                                    }
                                });
                                alert.dismiss();
                                alert2.dismiss();

                            }
                        });

                        cancelUpdate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alert.dismiss();

                            }
                        });
                    }
                });

            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(context,MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void getStudentData(){
        final Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://100.43.0.37/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        StudentInterface studentInterface = retrofit.create(StudentInterface.class);

        final Call<List<Student>> savePost = studentInterface.getStudents();
        savePost.enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                stuList.removeAll(stuList);
                if (!response.isSuccessful()) {
                    return;
                }
                stuList = response.body();
                StudentlistAdapter studentlistAdapter = new StudentlistAdapter(stuList, context);
                listView.setAdapter(studentlistAdapter);
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
                Log.i(Tag, "" + t.getMessage());
            }
        });
    }
}



