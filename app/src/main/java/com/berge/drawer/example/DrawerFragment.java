package com.berge.drawer.example;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import com.berge.drawer.MenuDrawerAccountFragment;

import com.berge.drawer.MenuDrawerAndroidAccountFragment;
import com.berge.drawer.model.MaterialAccount;
import com.berge.drawer.model.MaterialAccountImpl;
import com.berge.drawer.model.MaterialSectionImp;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Adria on 14/01/2015.
 */
public class DrawerFragment extends MenuDrawerAndroidAccountFragment {

    @Override
    public void onStartDrawerStructure() {

        setIdStatusBar(R.id.statusBar);

        MaterialAccount current = getCurrentMaterialAccount();
        if(current != null) {
            addSubheader("Account");
            MaterialSectionImp materialSection = new MaterialSectionImp(getActivity(), false);
            materialSection.setTitle(current.getTitle());
            //materialSection.setNotifications();
            materialSection.setSectionColor(
                    Color.rgb((int)(255 - (Math.round(Math.random()*255))), (int)(255 - (Math.round(Math.random()*255))), (int)(33 * (Math.round(Math.random()*255))))
            );
            materialSection.setContent(FragmentText.newInstance(current));
            addSection(materialSection);
        }
        addSubheader("Bucle");
        for(int i = 0; i != 10 ; i++) {
            MaterialSectionImp materialSection2 = new MaterialSectionImp(getActivity(), false);
            materialSection2.setTitle("Hola title " + i);
            materialSection2.setNotifications(11 * i);
            materialSection2.setSectionColor(
                    Color.rgb((int)(255 - (Math.round(Math.random()*255))), (int)(255 - (Math.round(Math.random()*255))), (int)(33 * (Math.round(Math.random()*255))))
            );
            materialSection2.setContent(FragmentText.newInstance(current));
            addSection(materialSection2);
        }

        
        for(int i = 0; i != 1 ; i++) {
            MaterialSectionImp materialSection2 = new MaterialSectionImp(getActivity(), false);
            materialSection2.setTitle("Hola title " + i);
            materialSection2.setNotifications(11 * i);
            materialSection2.setSectionColor(
                    getResources().getColor(R.color.color_one)
            );
            materialSection2.setContent(new Intent(Intent.ACTION_VIEW));
            addBottomSection(materialSection2);
        }
    }

    @Override
    protected void onConfigureAccounts(View view) {
        setAddUserDrawable(android.R.drawable.ic_menu_add);
    }

    @Override
    public String getAccountByType(){
        return "com.google";        
    }

    HashMap<Account, Integer> table = new HashMap<>();
    @Override
    public int getIdAccount(Account account) {
        if(!table.containsKey(account)){
            table.put(account, (int)(Math.random() * 4096));
        }
        return table.get(account);
    }


    @Override
    public String getAccountAuthTokenAccess() {
        return "all";
    }

    @Override
    public MaterialAccount getMatirialAccount(Account account) {
        MaterialAccount materialAccount = new MaterialAccountImpl(
                getResources(),
                getIdAccount(account),
                account.name,
                account.type,
                getImageRandom(R.drawable.photo,R.drawable.photo2, R.drawable.ic_launcher),
                getImageRandom(R.drawable.mat2, R.drawable.mat3)
        );               
        materialAccount.setModel(account);
                
        return materialAccount;
    }

    
    public int getImageRandom(int... images){
        int position = (int)(Math.round(Math.random() * images.length));
        position = position >= images.length ? images.length -1 : position;
        return images[position];
    }
}
