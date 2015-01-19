package com.berge.drawer.example;

import android.graphics.Color;
import android.view.View;

import com.berge.drawer.MenuDrawerAccountFragment;

import com.berge.drawer.model.MaterialAccountImpl;
import com.berge.drawer.model.MaterialSectionImp;

/**
 * Created by Adria on 14/01/2015.
 */
public class DrawerFragment extends MenuDrawerAccountFragment {


    @Override
    public void onStartDrawerStructure() {

        setIdStatusBar(R.id.statusBar);

        for(int i = 0; i != 10 ; i++) {
            MaterialSectionImp materialSection2 = new MaterialSectionImp(getActivity(), false);
            materialSection2.setTitle("Hola title " + i);
            materialSection2.setNotifications(11 * i);
            /**
            materialSection2.setSectionColor(
                    Color.rgb(255 - (11*i), 255 - (22*i), 33*i),
                    Color.rgb(255 - (11*i), 255 - (22*i), 33*i)
            );
            **/
            addSection(materialSection2);
        }

        addSubheader("Bottom");
        for(int i = 0; i != 2 ; i++) {
            MaterialSectionImp materialSection2 = new MaterialSectionImp(getActivity(), false);
            materialSection2.setTitle("Hola title " + i);
            materialSection2.setNotifications(11 * i);
            materialSection2.setSectionColor(
                    getResources().getColor(R.color.color_one)
            );
            addBottomSection(materialSection2);
        }
    }

    @Override
    protected void onConfigureAccounts(View view) {
        setAddUserDrawable(android.R.drawable.ic_menu_add);

        MaterialAccountImpl account = new MaterialAccountImpl(getResources(), 1, "NeoKree", "neokree@gmail.com", R.drawable.photo, R.drawable.mat3);
        this.addAccount(account, true);

        MaterialAccountImpl account2 = new MaterialAccountImpl(getResources(), 2, "Hatsune Miky", "hatsune.miku@example.com", R.drawable.photo2, R.drawable.mat2);
        this.addAccount(account2, true);

        MaterialAccountImpl account3 = new MaterialAccountImpl(getResources(), 3, "Example", "example@example.com", R.drawable.photo, R.drawable.bamboo);
        this.addAccount(account3, true);
/**
        MaterialAccount account4 = new MaterialAccount(getResources(), "Hatsune Miky", "hatsune.miku@example.com", R.drawable.photo2, R.drawable.mat2);
        this.addAccount(account4, true);


        MaterialAccount account5 = new MaterialAccount(getResources(), "NeoKree", "neokree@gmail.com", R.drawable.photo, R.drawable.mat3);
        this.addAccount(account5, true);

        MaterialAccount account6 = new MaterialAccount(getResources(), "Hatsune Miky", "hatsune.miku@example.com", R.drawable.photo2, R.drawable.mat2);
        this.addAccount(account6, true);

        MaterialAccount account7 = new MaterialAccount(getResources(), "Example", "example@example.com", R.drawable.photo, R.drawable.bamboo);
        this.addAccount(account7, true);

        MaterialAccount account8 = new MaterialAccount(getResources(), "Hatsune Miky", "hatsune.miku@example.com", R.drawable.photo2, R.drawable.mat2);
        this.addAccount(account8, true);
**/

    }
}
