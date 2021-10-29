package com.example.healthcareapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.healthcareapp.adapter.ChatBotAdapter;
import com.example.healthcareapp.model.ChatBot;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChatBotActivity extends AppCompatActivity {

    private RecyclerView chatsRV;
    private ImageView sendMsgIB;
    private EditText userMsgEdt;
    private final String USER_KEY = "user";
    private final String BOT_KEY = "bot";
    //creating a variable for our volley request queue.
    private RequestQueue mRequestQueue;
    //creating a variable for array list and adapter class.
    private ArrayList<ChatBot> messageModalArrayList;
    private ChatBotAdapter messageRVAdapter;
    private static String baseUrl ="http://api.brainshop.ai/get?bid=160552&key=xZawVlXB7QeNUpcy&uid=[uid]&msg=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);
        chatsRV = findViewById(R.id.idRVChats);
        sendMsgIB = findViewById(R.id.idIBSend);
        userMsgEdt = findViewById(R.id.idEdtMessage);
        mRequestQueue = Volley.newRequestQueue(ChatBotActivity.this);
        mRequestQueue.getCache().clear();
        //creating a new array list
        messageModalArrayList = new ArrayList<>();


        messageRVAdapter = new ChatBotAdapter(messageModalArrayList, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChatBotActivity.this, RecyclerView.VERTICAL, false);
        chatsRV.setLayoutManager(linearLayoutManager);
        chatsRV.setAdapter(messageRVAdapter);
        sendMsgIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checking if the message entered by user is empty or not.
                if (userMsgEdt.getText().toString().isEmpty()) {
                    Toast.makeText(ChatBotActivity.this, "Please enter your message..", Toast.LENGTH_SHORT).show();
                    return;
                }
                sendMessage(userMsgEdt.getText().toString());
                userMsgEdt.setText("");

            }
        });
    }

    private void sendMessage(String userMsg) {
        messageModalArrayList.add(new ChatBot(userMsg, USER_KEY));
        messageRVAdapter.notifyDataSetChanged();
        String url = baseUrl+ userMsg+"]";
        RequestQueue queue = Volley.newRequestQueue(ChatBotActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String botResponse = response.getString("cnt");
                    messageModalArrayList.add(new ChatBot(botResponse, BOT_KEY));
                    messageRVAdapter.notifyDataSetChanged();
                    chatsRV.scrollToPosition(messageModalArrayList.size()-1);

                } catch (JSONException e) {
                    e.printStackTrace();
                    messageModalArrayList.add(new ChatBot("No response", BOT_KEY));
                    messageRVAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Loi roi ", error.toString());
                //error handling.
                messageModalArrayList.add(new ChatBot("Sorry no response found", BOT_KEY));
            }
        });
        //at last adding json object request to our queue.
        queue.add(jsonObjectRequest);


    }
}