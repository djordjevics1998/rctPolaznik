package com.rct.rctpolaznik.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rct.rctpolaznik.R;
import com.google.android.gms.plus.PlusOneButton;
import com.rct.rctpolaznik.fragments.dialogs.NotLoggedIn;
import com.rct.rctpolaznik.interfaces.OnFragmentInteractionListener;

import static com.rct.rctpolaznik.Global.ARG_EXAMPLE1;
import static com.rct.rctpolaznik.Global.ARG_EXAMPLE2;
import static com.rct.rctpolaznik.Global.TAG_FRAGMENT_MAIN_MENU;
import static com.rct.rctpolaznik.Global.changeFragment;


public class MainMenu extends Fragment {
    private Context context;
    private OnFragmentInteractionListener activity;
    private String example1;
    private int example2;

    public static void add(Context context, String example1, int example2, boolean pop) {
        Bundle args = new Bundle();
        args.putString(ARG_EXAMPLE1, example1);
        args.putInt(ARG_EXAMPLE2, example2);
        changeFragment(context, TAG_FRAGMENT_MAIN_MENU, pop, args);
    }

    public MainMenu() { } // Required empty public constructor

    public static MainMenu newInstance(Bundle args) {
        MainMenu fragment = new MainMenu();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            example1 = getArguments().getString(ARG_EXAMPLE1);
            example2 = getArguments().getInt(ARG_EXAMPLE2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) { return inflater.inflate(R.layout.fragment_main_menu, container, false); }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        AppCompatTextView actvExample = view.findViewById(R.id.fragment_main_menu_example);
        actvExample.setText(example1 + " " + example2);
        actvExample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login.add(context, true);
            }
        });

        AppCompatTextView actvExample2 = view.findViewById(R.id.fragment_main_menu_example_2);
        actvExample2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotLoggedIn.add(context, false);
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        this.context = context;
        activity = (OnFragmentInteractionListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //activity.getCsManager().getRequestQueue().cancelAll(TAG_FRAGMENT_RECOVER);
        activity = null;
        context = null;
    }
}
