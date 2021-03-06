package com.lexandroid.movieapp.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.lexandroid.movieapp.HomePageActivity;
import com.lexandroid.movieapp.R;

public class LoginTabFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false);

        AppCompatButton button = root.findViewById(R.id.guestButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GuestButtonClick();
            }
        });

        return root;
    }

    public void GuestButtonClick() {
        Intent intent = new Intent(getActivity(), HomePageActivity.class);
        startActivity(intent);
    }

}
