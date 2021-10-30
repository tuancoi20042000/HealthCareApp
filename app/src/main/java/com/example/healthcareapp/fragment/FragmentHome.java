package com.example.healthcareapp.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.healthcareapp.ChatBotActivity;
import com.example.healthcareapp.ListItemActivity;
import com.example.healthcareapp.LocationActivity;
import com.example.healthcareapp.R;

public class FragmentHome extends Fragment implements View.OnClickListener {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_home,container,false);

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ImageView imChatBot = view.findViewById(R.id.navChatBot);
        ImageView imMap = view.findViewById(R.id.navMap);
        ImageView imShop = view.findViewById(R.id.navShop);
        ImageView imCall = view.findViewById(R.id.navCall);

        imChatBot.setOnClickListener(this);
        imMap.setOnClickListener(this);
        imShop.setOnClickListener(this);
        imCall.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.navChatBot:
                intent = new Intent(getActivity(), ChatBotActivity.class);
                startActivity(intent);
                break;
            case R.id.navMap:
                intent = new Intent(getActivity(), LocationActivity.class);
                startActivity(intent);
                break;
            case R.id.navShop:
                intent = new Intent(getActivity(), ListItemActivity.class);
                startActivity(intent);
                break;
            case R.id.navCall:
                intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:115"));
                startActivity(intent);
                break;
        }
    }

}
