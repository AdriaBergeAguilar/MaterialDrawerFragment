package com.berge.drawer.lisener;

import com.berge.drawer.model.MaterialAccount;

/**
 * Listener account created yours drawables 
 * Created by Adrià Bergé on 18/01/2015.
 */
public interface OnAccountDataLoaded {

    /**
     * finish load photo 
     * @param account
     */
    public void onUserPhotoLoaded(MaterialAccount account);

    /**
     * finish load background
     * @param account
     */
    public void onBackgroundLoaded(MaterialAccount account);
}