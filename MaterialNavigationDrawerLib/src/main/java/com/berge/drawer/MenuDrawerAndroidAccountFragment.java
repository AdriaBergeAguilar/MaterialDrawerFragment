package com.berge.drawer;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.berge.drawer.model.MaterialAccount;

import java.io.IOException;

/**
 * Created by Adria on 23/01/2015.
 */
public abstract class MenuDrawerAndroidAccountFragment extends MenuDrawerAccountFragment {
    private AccountManager accountManager;
    private SharedPreferences sharedPreferences;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountManager = AccountManager.get(getActivity());
        sharedPreferences = getActivity().getSharedPreferences("SystemAccounts", Context.MODE_PRIVATE);
    }

    @Override
    protected void onCreateAccount() {
        newAccount();
    }

    @Override
    public void onResume() {
        super.onResume();
        onStartAccount();
    }

    /**
     * get the type of account, packatge is a good option 
     * @return
     */
    public abstract String getAccountByType();

    /**
     * return the id with user current 
     * @return
     */
    private int getIdCurrentAccount(){
        return sharedPreferences.getInt("current", -1);
    }

    /**
     * change the id with user current 
     * @param id
     */
    private void setIdCurrentAccount(int id){
        sharedPreferences.edit().putInt("current", id).commit();
    }

    /**
     * return the id with account 
     * @param account
     * @return
     */
    public abstract int getIdAccount(Account account);

    /**
     * generate MatirialAccount with Account system 
     * @param account
     * @return
     */
    public abstract MaterialAccount getMatirialAccount(Account account);

    /**
     * get the tocken with determinet the permisions user. 
     * @return
     */
    public abstract String getAccountAuthTokenAccess();

    /**
     * reed all accounts in the system. 
     */
    private void onStartAccount() {
        removedAccounts();
        Account current = null;
        Account[] accounts = accountManager.getAccountsByType(getAccountByType());
        if (accounts.length > 0) {
            int idCurrent = getIdCurrentAccount();
            if (idCurrent != -1) {
                if (current == null) {
                    current = getAccount(idCurrent, accounts);
                    if(current == null){
                        current = accounts[0];
                        idCurrent = getIdAccount(current);
                        setIdCurrentAccount(idCurrent);
                    }                    
                }
                addAccount(getMatirialAccount(current), true);
            } else {
                current = accounts[0];
                addAccount(getMatirialAccount(current), true);
                idCurrent = getIdAccount(current);
                setIdCurrentAccount(idCurrent);
            }
            for (Account account : accounts) {
                int id = getIdAccount(account);
                if (id != idCurrent) {
                    addAccount(getMatirialAccount(account), false);
                }
            }
            
        } else {
            newAccount();
        }
    }

    private class OnAccountAddComplete implements AccountManagerCallback<Bundle> {
        @Override
        public void run(AccountManagerFuture<Bundle> result) {
            Bundle bundle;
            try {
                bundle = result.getResult();
            } catch (OperationCanceledException e) {
                e.printStackTrace();
                return;
            } catch (AuthenticatorException e) {
                e.printStackTrace();
                return;
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            onStartAccount();
        }
    }

    /**
     * Open activity with system add user 
     */
    public void newAccount(){
        accountManager.addAccount(
                getAccountByType(),
                getAccountAuthTokenAccess(),
                null,
                new Bundle(),
                getActivity(),
                new OnAccountAddComplete(),
                null);

    }

    /**
     * get a Account system current 
     * @return
     */
    public Account getCurrentAccount(){
        MaterialAccount materialAccount = getCurrentMaterialAccount();
        if(materialAccount != null) {
            return (Account) materialAccount.getModel();
        }else{
            return null;
        }        
    }

    @Override
    protected void switchAccount(int position, MaterialAccount newAccount) {
        super.switchAccount(position, newAccount);
        setIdCurrentAccount(newAccount.getId());
    }

    /**
     * return account system with your id
     * @param id
     * @param list
     * @return
     */
    private Account getAccount(int id, Account[] list){
        for(Account account : list){
            if( id == getIdAccount(account)){
                return account;
            }
        }
        return null;
    }
}
