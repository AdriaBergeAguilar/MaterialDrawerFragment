package com.berge.drawer.lisener;

import com.berge.drawer.model.MaterialAccount;

/**
 * Created by Adrià Bergé on 18/01/2015.
 */
public interface OnAccountDataLoaded {

    public void onUserPhotoLoaded(MaterialAccount account);

    public void onBackgroundLoaded(MaterialAccount account);
}