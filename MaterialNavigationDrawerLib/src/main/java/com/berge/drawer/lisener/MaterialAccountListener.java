package com.berge.drawer.lisener;

import com.berge.drawer.model.MaterialAccount;

/**
 * Listener with action in Accounts
 *
 * Created by Adrià Bergé on 16/01/15.
 */
public interface MaterialAccountListener {

    /**
     * this acction called with user click the image current account* 
     * @param account account clicked
     */
    public void onAccountOpening(MaterialAccount account);

    /**
     * this acction called with user click the image a account inactive
     * @param newAccount the nwe current account
     */
    public void onChangeAccount(MaterialAccount newAccount);

}
