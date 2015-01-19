package com.berge.drawer.lisener;

import com.berge.drawer.model.MaterialSection;

/**
 * Created by Adrià Bergé on 16/01/15
 */
public interface OnItemDrawerSelectedListener {
    public void onSectionSelected(MaterialSection section);

    public void closeDrawer();
}
