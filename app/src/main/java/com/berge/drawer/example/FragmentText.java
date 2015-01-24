package com.berge.drawer.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.berge.drawer.model.MaterialAccount;

/**
 * Created by Adria on 24/01/2015.
 */
public class FragmentText extends Fragment {
    
    public static FragmentText newInstance(MaterialAccount materialAccount){
        FragmentText f = new FragmentText();
        Bundle arg = new Bundle();
        if(materialAccount != null) {
            arg.putString("account", materialAccount.getTitle());
        }
        f.setArguments(arg);
        return f;        
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String materialAccount = getArguments().getString("account");
        TextView view = new TextView(getActivity());
        if(materialAccount != null) {
            view.setText(materialAccount);
        }
        return  view;
    }
}
