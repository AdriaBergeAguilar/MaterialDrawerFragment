package com.berge.drawer.example;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Toast;

import com.berge.drawer.MenuDrawerAccountFragment;
import com.berge.drawer.model.MaterialAccountImpl;
import com.berge.drawer.model.MaterialSectionImp;

/**
 * Created by Adria on 25/01/2015.
 */
public class SimpleAccountDrawerFragment extends MenuDrawerAccountFragment{
    @Override
    protected void onConfigureAccounts(View view) {
        setAddUserDrawable(android.R.drawable.ic_menu_add);
        addAccount(
                new MaterialAccountImpl(
                        getResources(),
                        1,
                        "ADRIA",
                        "adriaberge@gmail.com",
                        R.drawable.photo,
                        R.drawable.mat2),
                true
        );

        addAccount(
                new MaterialAccountImpl(
                        getResources(),
                        2,
                        "Test",
                        "adriaberge@gmail.com",
                        R.drawable.photo2,
                        R.drawable.mat3),
                true
        );
    }

    @Override
    protected void onCreateAccount() {
        Toast.makeText(getActivity(), "not impl", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStartDrawerStructure() {
        setIdStatusBar(R.id.statusBar);

        addSubheader("Bucle");
        for(int i = 0; i != 10 ; i++) {
            MaterialSectionImp materialSection2 = new MaterialSectionImp(getActivity(), false);
            materialSection2.setTitle("Hola title " + i);
            materialSection2.setNotifications(11 * i);
            materialSection2.setSectionColor(
                    Color.rgb((int) (255 - (Math.round(Math.random() * 255))), (int) (255 - (Math.round(Math.random() * 255))), (int) (33 * (Math.round(Math.random() * 255))))
            );
            //materialSection2.setContent(FragmentText.newInstance(current));
            addSection(materialSection2);
        }


        for(int i = 0; i != 1 ; i++) {
            MaterialSectionImp materialSection2 = new MaterialSectionImp(getActivity(), false);
            materialSection2.setTitle("Hola title " + i);
            materialSection2.setNotifications(11 * i);
            materialSection2.setSectionColor(
                    getResources().getColor(R.color.color_one)
            );
            materialSection2.setContent(new Intent(Intent.ACTION_VIEW));
            addBottomSection(materialSection2);
        }
    }
}
