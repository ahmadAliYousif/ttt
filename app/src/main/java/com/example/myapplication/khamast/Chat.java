package com.example.myapplication.khamast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.example.myapplication.khamast.model.Messages;
import com.example.myapplication.khamast.model.Resturant;
import com.example.myapplication.khamast.model.Student;
import com.example.myapplication.khamast.model.msg;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.myapplication.R;
import com.google.firebase.database.ValueEventListener;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Chat extends AppCompatActivity implements View.OnClickListener {

    LinearLayout layout;
    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;
    ArrayList<Orde> allUsers = new ArrayList<>();
    Orde reciver = new Orde();
    Orde sender = new Orde();
    boolean isNewUsers = true;
    ProgressDialog progress;
    ArrayList<Messages> chatData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        progress = new ProgressDialog(this);
        layout = (LinearLayout) findViewById(R.id.layout1);
        sendButton = (ImageView) findViewById(R.id.sendButton);
        messageArea = (EditText) findViewById(R.id.messageArea);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        reciver = UserDetails.getReciver();
        this.setTitle("Chat with" + " " + reciver.getName());
        sendButton.setOnClickListener(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
        getMyData();
        getMessages();

    }

    private void getMyData() {
        String emial = UserDetails.getUsername().trim();
        allUsers = UserDetails.getAllUsers();
        for (int i = 0; i < this.allUsers.size(); i++) {
            if (allUsers.get(i).getEmail().trim().equals(emial)) {
                this.sender = this.allUsers.get(i);
                break;
            }
        }
        Toast.makeText(Chat.this, this.sender.getName() + "--" + this.reciver.getName(), Toast.LENGTH_LONG).show();
    }

    private void getMessages() {
        chatData = new ArrayList<>();

        if (sender.getMyMessages() != null) {
            if (sender.getMyMessages().size() > 0) {
                for (int i = 0; i < sender.getMyMessages().size(); i++) {
                    if (this.sender.getMyMessages().get(i).getReciverKey().equals(reciver.getKey()) ||
                            this.sender.getMyMessages().get(i).getSenderKey().equals(reciver.getKey())) {
                        chatData.add(this.sender.getMyMessages().get(i));
                    }

                }

            } else {
                Toast.makeText(Chat.this, "New User", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(Chat.this, "New User", Toast.LENGTH_LONG).show();
            // UpdateUsers();
        }
        UpdateChatData();

        progress.dismiss();
    }

    private void UpdateChatData() {
        int type = 0;
        String msg = "";
        if (chatData != null) {
            if (chatData.size() > 0) {
                for (int i = 0; i < chatData.size(); i++) {
                    if (chatData.get(i).getSenderKey().equals(sender.getKey()))
                        type = 2;
                    else
                        type = 1;
                    if (chatData.get(i).getMsgs().size() > 0)
                        msg = chatData.get(i).getSenderName() + " " + ":" + " " + chatData.get(i).getMsgs().get(0).getMessage();
                    else
                        msg = "";
                    addMessageBox(msg, type);
                }
            }
        }
    }

    private void UpdateUsers(msg mess) {
        Messages messages = new Messages();
        ArrayList<msg> msgArrayList = new ArrayList<>();
        messages.setSenderKey(sender.getKey());
        messages.setSenderName(sender.getName());
        messages.setReciverKey(reciver.getKey());
        messages.setReciverNmae(reciver.getName());
        msgArrayList.add(mess);
        messages.setMsgs(msgArrayList);

        ArrayList<Messages> SMsg = new ArrayList<>();
        SMsg = sender.getMyMessages();
        if (SMsg == null)
            SMsg = new ArrayList<>();
        SMsg.add(messages);
        sender.setMyMessages(SMsg);
        ArrayList<Messages> RMsg = new ArrayList<>();
        RMsg = reciver.getMyMessages();
        if (RMsg == null)
            RMsg = new ArrayList<>();
        RMsg.add(messages);
        reciver.setMyMessages(RMsg);
        sendRequestAndStartChat();
//        final FirebaseDatabase tal = FirebaseDatabase.getInstance();
//        final DatabaseReference ref = tal.getReference("Orde");
//        ref.child(sender.getKey()).setValue(sender).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                ref.child(reciver.getKey()).setValue(reciver).
//                        addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                Toast.makeText(Chat.this, "Users Added To EachOther Succesfully", Toast.LENGTH_LONG).show();
//                            }
//                        });
//            }
//        });
    }

    private void sendRequestAndStartChat() {
        // It is assumed that the sender and receiver have values
        final FirebaseDatabase tal = FirebaseDatabase.getInstance();
        final DatabaseReference ref = tal.getReference("Resturant").
                child(reciver.getKey()).child("requests").child(sender.getKey()).child("studentInfo");
        ref.setValue(sender);
        ref.child("chatting").setValue("PutChatHere").
                addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Chat.this, "Users Added To EachOther Succesfully", Toast.LENGTH_LONG).show();

                    }
                });


        tal.getReference().child("Student").child(sender.getKey()).setValue(sender);
        tal.getReference().child("Student")
                .child(sender.getKey())
                .child("myRequests").child(reciver.getKey()).setValue(reciver);


//                setValue(sender).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                ref.child(reciver.getKey()).setValue(reciver).
//                        addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                Toast.makeText(Chat.this, "Users Added To EachOther Succesfully", Toast.LENGTH_LONG).show();
//                            }
//                        });
//            }
//        });

    }

    private void deleteRequestAndChat(String keyStudent, String keyResturant) {
        // keyResturant = receiver.getKey(); , keyStudent=sender.getKey();
        final FirebaseDatabase tal = FirebaseDatabase.getInstance();
        tal.getReference("Resturant").
                child(keyResturant).child("requests").child(keyStudent).setValue(null).
                addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Chat.this, "Users Added To EachOther Succesfully", Toast.LENGTH_LONG).show();

                    }
                });


        tal.getReference().child("Student").child(keyStudent).setValue(sender);
        tal.getReference().child("Student").child(keyStudent)
                .child("myRequests").child(reciver.getKey()).setValue(null);


    }

    private List<Resturant> showRestaurantsRequired(String studentKey) {
        Semaphore semaphore = new Semaphore(0);
        List<Resturant> resturants = new ArrayList<>();
        final FirebaseDatabase tal = FirebaseDatabase.getInstance();
        tal.getReference().child("Student")
                .child(studentKey)
                .child("myRequests").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    resturants.add(dataSnapshot.getValue(Resturant.class));
                }
                semaphore.release();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return resturants;
    }

    private List<Resturant> getAllResturants(Student student) {
        List<Resturant> resturants = new ArrayList<>();
        Semaphore semaphore = new Semaphore(0);
        final FirebaseDatabase tal = FirebaseDatabase.getInstance();
        tal.getReference("Resturant").
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Resturant resturant = dataSnapshot.getValue(Resturant.class);
//                            if (resturant.get ... equal(student.get ...))
                            {
                                resturants.add(resturant);

                            }

                        }
                        semaphore.release();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return resturants;

    }


    public void addMessageBox(String message, int type) {
        TextView textView = new TextView(Chat.this);
        textView.setLayoutParams(
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
        textView.setText(message);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 10);
        textView.setLayoutParams(lp);
//her type of shakel content sed or recive
        if (type == 1) {
            textView.setBackgroundResource(R.drawable.common_full_open_on_phone);
        } else {
            textView.setBackgroundResource(R.drawable.common_full_open_on_phone);
        }

        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }

    @Override
    public void onClick(View v) {
        sendMessage();
    }

    private void sendMessage() {
        String txt = "";
        if (!String.valueOf(messageArea.getText()).equals("")) {
            txt = String.valueOf(messageArea.getText());
            msg mmsg = new msg();
            mmsg.setMessage(String.valueOf(messageArea.getText()));
            mmsg.setReciverKey(reciver.getKey());
            mmsg.setSendDate(Calendar.getInstance().getTime());
            mmsg.setSenderKey(sender.getKey());
            addMessageBox(txt, 2);
            messageArea.setText("");
            UpdateUsers(mmsg);
        } else {
            Toast.makeText(Chat.this, "Please Enter Some Data First", Toast.LENGTH_LONG).show();
        }
    }

}
