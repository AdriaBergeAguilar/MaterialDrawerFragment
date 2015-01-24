package com.berge.drawer.model;

import android.accounts.Account;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.berge.drawer.lisener.OnAccountDataLoaded;

import java.io.Serializable;

/**
 * Encapsule info with user to representate in drawer
 *  
 * Created by Adrià Bergé on 18/01/2015.
 */
public interface MaterialAccount{

    /**
     * Change photo 
     * @param photo
     */
    public void setPhoto(int photo);

    /**
     * Change photo 
     * @param photo
     */
    public void setPhoto(Bitmap photo);

    /**
     * Change background 
     * @param background
     */
    public void setBackground(Bitmap background);

    /**
     * Change background 
     * @param background
     */
    public void setBackground(int background);

    /**
     * Change the title 
     * @param title
     */
    public void setTitle(String title);

    /**
     * Change the subtitle 
     * @param subTitle
     */
    public void setSubTitle(String subTitle);

    /**
     * listener finish image loadeds and createds 
     * @param listener
     */
    public void setAccountListener(OnAccountDataLoaded listener);

    /**
     * return the photo 
     * @return
     */
    public Drawable getPhoto();

    /**
     * return the background 
     * @return
     */
    public Drawable getBackground();

    /**
     * Circula drawable with base is photo 
     * @return
     */
    public Drawable getCircularPhoto();

    /**
     * return title 
     * @return
     */
    public String getTitle();

    /**
     * return subtitle 
     * @return
     */
    public String getSubTitle();

    /**
     * eliminate all drawables, bitmaps and/or image representations 
     */
    public void recycle();

    /**
     * return id 
     * @return
     */
    public int getId();

    /**
     * change id user 
     * @param id
     */
    public void setId(int id);

    /**
     * change the rigth account, with your sdk 
     * @param account
     */
    public void setModel(Object account);

    /**
     * return your account sdk
     * @return
     */
    public Object getModel();
}

