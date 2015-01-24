package com.berge.drawer.lisener;

import com.berge.drawer.model.MaterialSection;

/**
 * Listener with acction in the Drawer
 *  
 * Created by Adrià Bergé on 16/01/15
 */
public interface OnItemDrawerSelectedListener {
    /**
     * Section Selected 
     * @param section
     */
    public void onSectionSelected(MaterialSection section);

    /**
     * Solicitated close the drawer
     */
    public void closeDrawer();
}
