package com.berge.drawer.model;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import com.berge.drawer.Utils;
import com.berge.drawer.lisener.OnAccountDataLoaded;

/**
 * Created by Adrià Bergé on 16/01/15
 */
public class MaterialAccountImpl implements MaterialAccount {

    private Drawable photo;
    private Drawable background;
    private Drawable circularPhoto;
    private String title;
    private String subTitle;
    private int id;

    private Resources resources;
    private OnAccountDataLoaded listener;


    public MaterialAccountImpl(Resources res, int id, String title, String subTitle, int photo, Bitmap background) {
        this.title = title;
        this.subTitle = subTitle;
        this.id = id;
        resources = res;

        resizePhotoResource.execute(photo);
        if (background != null) {
            resizeBackgroundBitmap.execute(background);
        }

    }

    public MaterialAccountImpl(Resources res, int id, String title, String subTitle, int photo, int background) {
        this.title = title;
        this.subTitle = subTitle;
        this.id = id;
        resources = res;

        resizePhotoResource.execute(photo);
        resizeBackgroundResource.execute(background);
    }

    public MaterialAccountImpl(Resources res, int id, String title, String subTitle, Bitmap photo, int background) {
        this.title = title;
        this.subTitle = subTitle;
        this.id = id;
        resources = res;

        if (photo != null) {
            resizePhotoBitmap.execute(photo);
        }
        resizeBackgroundResource.execute(background);
    }

    public MaterialAccountImpl(Resources res, int id, String title, String subTitle, Bitmap photo, Bitmap background) {
        this.title = title;
        this.subTitle = subTitle;
        this.id = id;
        resources = res;

        if (photo != null) {
            resizePhotoBitmap.execute(photo);
        }
        if (background != null) {
            resizeBackgroundBitmap.execute(background);
        }
    }

    @Override
    public void setPhoto(int photo) {
        resizePhotoResource.execute(photo);
    }

    @Override
    public void setPhoto(Bitmap photo) {
        resizePhotoBitmap.execute(photo);
    }

    @Override
    public void setBackground(Bitmap background) {
        resizeBackgroundBitmap.execute(background);
    }

    @Override
    public void setBackground(int background) {
        resizeBackgroundResource.execute(background);
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    @Override
    public void setAccountListener(OnAccountDataLoaded listener) {
        this.listener = listener;
    }

    @Override
    public Drawable getPhoto() {
        return photo;
    }

    @Override
    public Drawable getBackground() {
        return background;
    }

    @Override
    public Drawable getCircularPhoto() {
        return circularPhoto;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getSubTitle() {
        return subTitle;
    }

    @Override
    public void recycle() {
        Utils.recycleDrawable(photo);
        Utils.recycleDrawable(circularPhoto);
        Utils.recycleDrawable(background);
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    private AsyncTask<Integer, Void, BitmapDrawable> resizePhotoResource = new AsyncTask<Integer, Void, BitmapDrawable>() {
        @Override
        protected BitmapDrawable doInBackground(Integer... params) {
            Point photoSize = Utils.getUserPhotoSize(resources);

            Bitmap photo = Utils.resizeBitmapFromResource(resources, params[0], photoSize.x, photoSize.y);

            circularPhoto = new BitmapDrawable(resources, Utils.getCroppedBitmapDrawable(photo));
            return new BitmapDrawable(resources, photo);
        }

        @Override
        protected void onPostExecute(BitmapDrawable drawable) {
            photo = drawable;

            if (listener != null)
                listener.onUserPhotoLoaded(MaterialAccountImpl.this);
        }
    };
    private AsyncTask<Bitmap, Void, BitmapDrawable> resizePhotoBitmap = new AsyncTask<Bitmap, Void, BitmapDrawable>() {
        @Override
        protected BitmapDrawable doInBackground(Bitmap... params) {
            Point photoSize = Utils.getUserPhotoSize(resources);


            Bitmap photo = Utils.resizeBitmap(params[0], photoSize.x, photoSize.y);
            params[0].recycle();

            circularPhoto = new BitmapDrawable(resources, Utils.getCroppedBitmapDrawable(photo));
            return new BitmapDrawable(resources, photo);
        }

        @Override
        protected void onPostExecute(BitmapDrawable drawable) {
            photo = drawable;

            if (listener != null)
                listener.onUserPhotoLoaded(MaterialAccountImpl.this);
        }
    };
    private AsyncTask<Integer, Void, BitmapDrawable> resizeBackgroundResource = new AsyncTask<Integer, Void, BitmapDrawable>() {
        @Override
        protected BitmapDrawable doInBackground(Integer... params) {
            Point backSize = Utils.getBackgroundSize(resources);

            Bitmap back = Utils.resizeBitmapFromResource(resources, params[0], backSize.x, backSize.y);

            return new BitmapDrawable(resources, back);
        }

        @Override
        protected void onPostExecute(BitmapDrawable drawable) {
            background = drawable;

            if (listener != null)
                listener.onBackgroundLoaded(MaterialAccountImpl.this);
        }
    };
    private AsyncTask<Bitmap, Void, BitmapDrawable> resizeBackgroundBitmap = new AsyncTask<Bitmap, Void, BitmapDrawable>() {
        @Override
        protected BitmapDrawable doInBackground(Bitmap... params) {
            Point backSize = Utils.getBackgroundSize(resources);

            Bitmap back = Utils.resizeBitmap(params[0], backSize.x, backSize.y);
            params[0].recycle();

            return new BitmapDrawable(resources, back);
        }

        @Override
        protected void onPostExecute(BitmapDrawable drawable) {
            background = drawable;

            if (listener != null)
                listener.onBackgroundLoaded(MaterialAccountImpl.this);
        }
    };
}
