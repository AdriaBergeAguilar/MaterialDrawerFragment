package com.berge.drawer;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.berge.drawer.adapter.UsersAdapter;
import com.berge.drawer.lisener.MaterialAccountListener;
import com.berge.drawer.lisener.OnAccountDataLoaded;
import com.berge.drawer.lisener.OnClickOtherAccountListener;
import com.berge.drawer.model.MaterialAccount;

import java.util.List;

/**
 * This implementation not header configuration, the header is container
 * to accounts.
 *  
 * Created by Adrià Bergé on 16/01/15
 */
public abstract class MenuDrawerAccountFragment extends MenuDrawerFragment {

    private TextView userEmail;
    private TextView userName;
    private RecyclerView otherUsers;
    private ImageView userImage;
    private ImageView userCover;
    private ImageView userCoverSwitcher;
    private ImageView addUser;

    private boolean drawerTouchLocked;

    protected MaterialAccountListener accountListener;

    private MaterialAccount currentAccount;
    private UsersAdapter mAdapter;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        accountListener = (MaterialAccountListener) activity;
    }

    @Override
    public View onConfigureHeader() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.header_account, null);

        userCoverSwitcher = (ImageView) view.findViewById(R.id.user_cover_switcher);
        userCover = (ImageView) view.findViewById(R.id.user_cover);
        userImage = (ImageView) view.findViewById(R.id.user_photo);
        otherUsers = (RecyclerView) view.findViewById(R.id.other_users);
        userName = (TextView) view.findViewById(R.id.user_nome);
        userEmail = (TextView) view.findViewById(R.id.user_email);
        addUser = (ImageView) view.findViewById(R.id.add_user);

        userImage.setOnClickListener(currentAccountListener);
        userCover.setOnClickListener(currentAccountListener);

        drawerTouchLocked = false;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        otherUsers.setLayoutManager(linearLayoutManager);
        //otherUsers.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new UsersAdapter();
        mAdapter.setOnClickOtherAccountListener(onClickOtherAccountListener);
        otherUsers.setAdapter(mAdapter);

        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreateAccount();
                listenerActivity.closeDrawer();
            }
        });

        onConfigureAccounts(view);

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        accountListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        List<MaterialAccount> list = mAdapter.getAllItems();
        for(MaterialAccount materialAccount : list){
            materialAccount.recycle();
        }
    }

    /**
     * change imatge with user clck add ather user in fragment
     * @param resource
     */
    public void setAddUserDrawable(int resource){
        addUser.setImageDrawable(getResources().getDrawable(resource));
    }

    /**
     * delete all acounts in the view, not deletet with sistem 
     */
    public void removedAccounts() {
        for(MaterialAccount materialAccount : mAdapter.getAllItems()){
            mAdapter.removeAccount(materialAccount);
        }
    }

    /**
     * add one matirialAccount and determinet with this account is current or not 
     * @param materialAccount
     * @param current
     */
    public void addAccount(MaterialAccount materialAccount, boolean current){
        materialAccount.setAccountListener(accountDataLoadedListener);
        if(current) {
            currentAccount = materialAccount;
        }
    }

    /**
     * set all views with a current account 
     * @param materialAccount
     */
    private void addCurrentAccount(MaterialAccount materialAccount) {
        if(materialAccount != null) {
            this.currentAccount = materialAccount;
            this.userEmail.setText(currentAccount.getSubTitle());
            this.userName.setText(currentAccount.getTitle());
            this.userImage.setImageDrawable(currentAccount.getCircularPhoto());
            this.userCover.setImageDrawable(currentAccount.getBackground());
            this.userCover.setOnClickListener(currentAccountListener);
            this.userImage.setOnClickListener(currentAccountListener);
        }else{
            this.currentAccount = null;
            this.userEmail.setText("");
            this.userName.setText("");
            this.userImage.setImageDrawable(null);
            this.userCover.setImageDrawable(null);
            this.userCover.setOnClickListener(null);
            this.userImage.setOnClickListener(null);
        }
    }

    /**
     * add inactive account in the list 
     * @param materialAccount
     * @param index
     */
    private void addOtherAccount(MaterialAccount materialAccount, int index) {
        mAdapter.addAccount(materialAccount, index);
    }

    /**
     * generate all acctions in the change one account with current 
     * @param position
     * @param newAccount
     */
    protected void switchAccount(int position, MaterialAccount newAccount){
        mAdapter.removeAccount(newAccount);
        addOtherAccount(currentAccount, position);
        currentAccount = newAccount;
        addCurrentAccount(currentAccount);
        accountListener.onChangeAccount(currentAccount);
        removedStructure();
        onStartDrawerStructure();
        startCurrentSection();
    }

    /**
     * get the current account 
     * @return
     */
    public MaterialAccount getCurrentMaterialAccount(){
        return this.currentAccount;
    }

    /**
     * configure all accounts
     * @param view
     */
    protected abstract void onConfigureAccounts(View view);

    /**
     * user click a button add account
     */
    protected abstract void onCreateAccount();

    private View.OnClickListener currentAccountListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            if(!drawerTouchLocked) {
                // enter into account properties
                if (accountListener != null) {
                    accountListener.onAccountOpening(currentAccount);
                    listenerActivity.closeDrawer();
                }
            }
        }
    };

    private OnClickOtherAccountListener onClickOtherAccountListener = new OnClickOtherAccountListener() {

        @Override
        public void onClick(int position, MaterialAccount materialAccount) {
            switchAccount(position, materialAccount);
            listenerActivity.closeDrawer();
        }
    };

    private OnAccountDataLoaded accountDataLoadedListener = new OnAccountDataLoaded(){

        @Override
        public void onUserPhotoLoaded(MaterialAccount account) {
            if(currentAccount.equals(account)){
                addCurrentAccount(account);
                removedStructure();
                onStartDrawerStructure();
                startCurrentSection();
                
            }else {
                addOtherAccount(account, -1);
            }
        }

        @Override
        public void onBackgroundLoaded(MaterialAccount account) {
            if(currentAccount.equals(account)){
                MenuDrawerAccountFragment.this.userCover.setImageDrawable(account.getBackground());
            }
        }
    };

}
