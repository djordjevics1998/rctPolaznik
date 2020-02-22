package com.rct.rctpolaznik.fragments.dialogs;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rct.rctpolaznik.R;
import com.google.android.gms.plus.PlusOneButton;
import com.rct.rctpolaznik.interfaces.OnFragmentInteractionListener;

import static com.rct.rctpolaznik.Global.TAG_FRAGMENT_NOT_LOGGED_IN;
import static com.rct.rctpolaznik.Global.changeFragment;

public class NotLoggedIn extends Fragment {
    private Context context;
    private OnFragmentInteractionListener activity;

    public static void add(Context context, boolean pop) {
        Bundle args = new Bundle();
        changeFragment(context, TAG_FRAGMENT_NOT_LOGGED_IN, pop, args);
    }

    public NotLoggedIn() { } // Required empty public constructor

    public static NotLoggedIn newInstance(Bundle args) {
        NotLoggedIn fragment = new NotLoggedIn();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_not_logged_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
