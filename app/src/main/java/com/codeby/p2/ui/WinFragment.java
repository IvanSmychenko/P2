package com.codeby.p2.ui;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.codeby.p2.R;


public class WinFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";

    private String username;

    public WinFragment() {
        // Required empty public constructor
    }

    public static WinFragment newInstance(String username) {
        WinFragment fragment = new WinFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, username);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            username = getArguments().getString(ARG_PARAM1);
        }
    }
    public static final String WINNAME = "T-Rex";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_win, container, false);
        TextView tvWin = v.findViewById(R.id.tv_win);
        Button button = v.findViewById(R.id.btn_win);
        if (WINNAME.equals(username)){
            tvWin.setText(String.format("Congratulations, %s\n you win!", username));
            tvWin.setTextColor(Color.GREEN);
        } else {
            tvWin.setText(String.format("Game Over!\n %s you loose!", username));
            tvWin.setTextColor(Color.RED);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });
        return v;
    }

}