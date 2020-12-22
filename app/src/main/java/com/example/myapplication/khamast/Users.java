package com.example.myapplication.khamast;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import com.example.myapplication.khamast.model.Messages;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//import com.naifahmad2020.getinglocation.Callback.OrdeCallback;
//import com.naifahmad2020.getinglocation.Controller.OrdeController;
//import com.naifahmad2020.getinglocation.Model.Orde;
import com.example.myapplication.R;

import android.app.ProgressDialog;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class Users extends AppCompatActivity {

    ListView usersList;
    TextView noUsersText;
    ArrayList<String> al = new ArrayList<>();
    int totalUsers = 0;
    ArrayList<Orde> ordeArrayList = new ArrayList<>();
    ProgressDialog pd;
    String myEmail = "";
    Orde currentUser = new Orde();
    AlertDialog.Builder builder;
    Orde reciver = new Orde();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        usersList = findViewById(R.id.usersList);
        builder = new AlertDialog.Builder(this);

        //get email sender from extra email from main activity
        //note i can use also this myEmail=Edt_Email.getText().toString();
        UserDetails.setUsername("ahmed@ahmed.com");
        myEmail = UserDetails.getUsername();


        getAllUser();

    }


    private void getAllUser() {
        new OrdeController().getAllUsers(new OrdeCallback() {
            @Override
            public void Onsucess(double lat, double lng) {

            }

            @Override
            public void OnError(String errMsg) {

            }

            @Override
            public void Onsucess(ArrayList<Orde> ordes) {
                ordeArrayList = new ArrayList<>();
                UserDetails.setAllUsers(ordes);
                for (int i = 0; i < ordes.size(); i++) {
                    /// this is the current user email
                    // so i put a condition inside for that if the email == the current user email do not add it to the list of the list view
                    if (!ordes.get(i).getEmail().trim().equals(myEmail.trim())) {
                        ordeArrayList.add(ordes.get(i));
                    } else {
                        currentUser = ordes.get(i);
                    }
                }
                if (currentUser.getKey() == null) {
                    Toast.makeText(Users.this, "Current User Is Null", Toast.LENGTH_LONG).show();
                }

                usersList.setAdapter(new SizesAdabter());
            }
        });
    }


    private class SizesAdabter extends BaseAdapter {
        @Override
        public int getCount() {
            return ordeArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return ordeArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder2 viewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(Users.this).inflate(R.layout.list_item, null);
                viewHolder = new ViewHolder2();
                viewHolder.container = convertView.findViewById(R.id.container);
                viewHolder.mail = convertView.findViewById(R.id.txtMail);
                viewHolder.name = convertView.findViewById(R.id.txtName);
                viewHolder.pass = convertView.findViewById(R.id.txtPass);
                viewHolder.delete = convertView.findViewById(R.id.delete);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder2) convertView.getTag();
            }


            viewHolder.mail.setText(ordeArrayList.get(position).getEmail());
            viewHolder.name.setText(ordeArrayList.get(position).getName());
            viewHolder.pass.setText(ordeArrayList.get(position).getPasswords());
            viewHolder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reciver = ordeArrayList.get(position);
                    UserDetails.setReciver(reciver);
                    Intent intent = new Intent(Users.this, Chat.class);
                    intent.putExtra("Email", myEmail);

                    startActivity(intent);
                }
            });
            viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reciver = ordeArrayList.get(position);
                    checkAndDelete(position);
                }
            });

            return convertView;

        }

        private void checkAndDelete(final int index) {
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(Users.this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(Users.this);
            }
            builder.setTitle("تحذير!!")
                    .setMessage("هل تريد حذف المحادثة")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //    ordeArrayList.remove(index);
                            usersList.setAdapter(new SizesAdabter());

                            ArrayList<Messages> currentUserMsgs = currentUser.getMyMessages();
                            if (currentUserMsgs == null)
                                currentUserMsgs = new ArrayList<>();
                            ArrayList<Messages> newCurrentUserMsgs = new ArrayList<>();
                            for (int i = 0; i < currentUserMsgs.size(); i++) {
                                if (!currentUserMsgs.get(i).getReciverKey().equals(reciver.getKey()) &&
                                        !currentUserMsgs.get(i).getSenderKey().equals(reciver.getKey()))
                                    newCurrentUserMsgs.add(currentUserMsgs.get(i));
                            }
                            currentUser.setMyMessages(newCurrentUserMsgs);

                            ArrayList<Messages> otherUserMsgs = reciver.getMyMessages();
                            if (otherUserMsgs == null)
                                otherUserMsgs = new ArrayList<>();
                            ArrayList<Messages> newOtherUserMsgs = new ArrayList<>();
                            for (int i = 0; i < otherUserMsgs.size(); i++) {
                                if (!otherUserMsgs.get(i).getReciverKey().equals(currentUser.getKey()) &&
                                        !otherUserMsgs.get(i).getSenderKey().equals(currentUser.getKey()))
                                    newOtherUserMsgs.add(otherUserMsgs.get(i));
                            }
                            reciver.setMyMessages(newOtherUserMsgs);
                            if (currentUserMsgs.size() == newCurrentUserMsgs.size()) {
                                Toast.makeText(Users.this, "لا يوجد محاثة مع هذا المستخدم ", Toast.LENGTH_LONG).show();
                            } else {
                                final FirebaseDatabase tal = FirebaseDatabase.getInstance();
                                final DatabaseReference ref = tal.getReference("Orde");
                                ref.child(currentUser.getKey()).setValue(currentUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        ref.child(reciver.getKey()).setValue(reciver).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(Users.this, "تم حذف المحادثة بنجاح !", Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }
                                });
                            }

                        }
                    }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).setIcon(android.R.drawable.ic_dialog_alert).show();
        }
    }

    private class ViewHolder2 {
        RelativeLayout container;
        TextView mail, name, pass;
        ImageView delete;
    }
}
