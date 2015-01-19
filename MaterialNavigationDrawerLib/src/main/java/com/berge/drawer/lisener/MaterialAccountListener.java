package com.berge.drawer.lisener;

import com.berge.drawer.model.MaterialAccount;

/**
 * Created by Adrià Bergé on 16/01/15.
 */
public interface MaterialAccountListener {

    public void onAccountOpening(MaterialAccount account);

    public void onChangeAccount(MaterialAccount newAccount);

    public void onCreateAccount();
}
