package com.berge.drawer.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.berge.drawer.R;
import com.berge.drawer.lisener.MaterialSectionListener;


/**
 *
 * Created by Adrià Bergé on 16/01/15
 */
public class MaterialSectionImp implements MaterialSection{

    private int position;

    private View view;

    private TextView text;
    private TextView notifications;
    private ImageView icon;

    private MaterialSectionListener listener;

    private boolean isSelected;
    private boolean sectionColor;
    private boolean hasColorDark;
    private boolean touchable;

    // COLORS
    private int colorPressed;
    private int colorUnpressed;
    private int colorSelected;
    private int iconColor;
    private int colorDark;

    private int numberNotifications;

    private String title;
    private Object objAcction;

    public MaterialSectionImp(Context ctx, boolean hasIcon) {

        if (!hasIcon) {
            view = LayoutInflater.from(ctx).inflate(R.layout.layout_material_section, null);

            text = (TextView) view.findViewById(R.id.section_text);
            notifications = (TextView) view.findViewById(R.id.section_notification);
        } else {
            view = LayoutInflater.from(ctx).inflate(R.layout.layout_material_section_icon, null);

            text = (TextView) view.findViewById(R.id.section_text);
            icon = (ImageView) view.findViewById(R.id.section_icon);
            notifications = (TextView) view.findViewById(R.id.section_notification);
        }


        view.setOnTouchListener(this);


        colorPressed = Color.parseColor("#16000000");
        colorUnpressed = Color.parseColor("#00FFFFFF");
        colorSelected = Color.parseColor("#0A000000");
        iconColor = Color.BLACK;
        isSelected = false;
        sectionColor = false;
        hasColorDark = false;
        touchable = true;
        numberNotifications = 0;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(touchable) {

            if (event.getAction() == MotionEvent.ACTION_DOWN) {

                view.setBackgroundColor(colorPressed);
                return true;
            }

            if (event.getAction() == MotionEvent.ACTION_CANCEL) {

                if (isSelected) {
                    view.setBackgroundColor(colorSelected);
                }else {
                    view.setBackgroundColor(colorUnpressed);
                }
                return true;
            }


            if (event.getAction() == MotionEvent.ACTION_UP) {
                view.setBackgroundColor(colorSelected);
                afterClick();
                return true;
            }
        }

        return false;
    }

    @Override
    public void select() {
        isSelected = true;
        view.setBackgroundColor(colorSelected);

        if(sectionColor) {
            text.setTextColor(iconColor);

            if(icon != null) {
                icon.setColorFilter(iconColor);
                setAlpha(icon, 1f);
            }
        }
    }

    @Override
    public void unSelect() {
        isSelected = false;
        view.setBackgroundColor(colorUnpressed);

        if (sectionColor) {
            text.setTextColor(Color.BLACK);

            if (icon != null) {
                icon.setColorFilter(Color.BLACK);
                setAlpha(icon, 0.54f);
            }
        }
    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void setOnClickListener(MaterialSectionListener listener) {
        this.listener = listener;
    }

    @Override
    public View getView() {
        return view;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
        this.text.setText(title);
    }

    @Override
    public void setIcon(Drawable icon) {
        this.icon.setImageDrawable(icon);
        this.icon.setColorFilter(iconColor);
    }

    @Override
    public void setIcon(Bitmap icon) {
        this.icon.setImageBitmap(icon);
        this.icon.setColorFilter(iconColor);
    }

    @Override
    public void setSectionColor(int color,int colorDark) {
        sectionColor = true;
        iconColor = color;
        hasColorDark = true;
        this.colorDark = colorDark;
    }

    public void setSectionColor(int color){
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.8f; // value component
        int colorDark =  Color.HSVToColor(hsv);
        setSectionColor(color, colorDark);
    }

    @Override
    public boolean hasSectionColor() {
        return sectionColor;
    }

    @Override
    public boolean hasSectionColorDark() {
        return hasColorDark;
    }

    @Override
    public int getSectionColor() {
        return iconColor;
    }

    @Override
    public int getSectionColorDark() {
        return colorDark;
    }

    @Override
    public void setNotifications(int notifications) {
        String textNotification;

        textNotification = String.valueOf(notifications);

        if(notifications < 1) {
            textNotification = "";
        }
        if(notifications > 99) {
            textNotification = "99+";
        }

        this.notifications.setText(textNotification);
        numberNotifications = notifications;
    }

    @Override
    public void setNotificationsText(String text) {
        this.notifications.setText(text);
    }

    @Override
    public int getNotifications() {
        return numberNotifications;
    }

    @Override
    public String getNotificationsText() {
        return this.notifications.getText().toString();
    }

    @Override
    public void setTouchable(boolean isTouchable) {
        touchable = isTouchable;
    }

    @Override
    public Object getContent() {
        return objAcction;
    }

    @Override
    public void setContent(Object o) {
        this.objAcction = o;
    }

    // private methods

    void setAlpha(View v, float alpha) {
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
           v.setAlpha(alpha);
       } else {
           AlphaAnimation animation = new AlphaAnimation(alpha, alpha);
           animation.setDuration(0);
           animation.setFillAfter(true);
           v.startAnimation(animation);
       }
    }

    private void afterClick() {
        isSelected = true;

        if (sectionColor) {
            text.setTextColor(iconColor);

            if (icon != null) {
                icon.setColorFilter(iconColor);
                setAlpha(icon, 1f);
            }
        }

        if (listener != null)
            listener.onClick(this);
    }

}
