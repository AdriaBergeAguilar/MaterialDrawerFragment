package com.berge.drawer.lisener;

import com.berge.drawer.model.MaterialAccount;

/**
 * Listener user click other account  
 * Created by Adrià Bergé on 16/01/15
 */
public interface OnClickOtherAccountListener {

    /**
     * Event user click account inactive
     * @param position position in the list with account cliked
     * @param materialAccount account clicked
     */
    public void onClick(int position, MaterialAccount materialAccount);
}
