package com.berge.drawer;

import android.app.Activity;
import android.graphics.drawable.Drawable;
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

    private MaterialAccountListener accountListener;

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
                accountListener.onCreateAccount();
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

    public void setAddUserDrawable(int resource){
        addUser.setImageDrawable(getResources().getDrawable(resource));
    }

    public void addAccount(MaterialAccount materialAccount, boolean current){
        materialAccount.setAccountListener(accountDataLoadedListener);
        if(current) {
            currentAccount = materialAccount;
        } else {
            mAdapter.addAccount(materialAccount, 0);
        }
    }

    private void addCurrentAccount(MaterialAccount materialAccount) {
        this.currentAccount = materialAccount;
        this.userEmail.setText(currentAccount.getSubTitle());
        this.userName.setText(currentAccount.getTitle());
        this.userImage.setImageDrawable(currentAccount.getCircularPhoto());
        this.userCover.setImageDrawable(currentAccount.getBackground());
        this.userCover.setOnClickListener(currentAccountListener);
        this.userImage.setOnClickListener(currentAccountListener);
    }

    private void addOtherAccount(MaterialAccount materialAccount, int index) {
        mAdapter.addAccount(materialAccount, index);
    }

    private void switchAccount(int position, MaterialAccount newAccount){
        mAdapter.removeAccount(newAccount);
        addOtherAccount(currentAccount, position);
        currentAccount = newAccount;
        addCurrentAccount(currentAccount);
        accountListener.onChangeAccount(currentAccount);
    }

    protected abstract void onConfigureAccounts(View view);

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
