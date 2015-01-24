package com.berge.drawer.model;

import android.accounts.Account;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.berge.drawer.lisener.OnAccountDataLoaded;

/**
 * Created by Adrià Bergé on 18/01/2015.
 */
public interface MaterialAccount {

    public void setPhoto(int photo);

    public void setPhoto(Bitmap photo);

    public void setBackground(Bitmap background);

    public void setBackground(int background);

    public void setTitle(String title);

    public void setSubTitle(String subTitle);

    public void setAccountListener(OnAccountDataLoaded listener);

    public Drawable getPhoto();

    public Drawable getBackground();

    public Drawable getCircularPhoto();

    public String getTitle();

    public String getSubTitle();

    public void recycle();

    public int getId();

    public void setId(int id);

    public void setModel(Object account);

    public Object getModel();
}

