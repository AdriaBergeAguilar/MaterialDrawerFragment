package com.berge.drawer.model;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;

import com.berge.drawer.lisener.MaterialSectionListener;

/**
 * Created by Adrià Bergé on 18/01/2015.
 */

public interface MaterialSection extends View.OnTouchListener {

    /**
     * Selecciona la seccion y desencadena todos sus eventos y efectos
     */
    public void select();

    /**
     * Desselecciona la seccion
     */
    public void unSelect();

    /**
     * indica si esta seccion esta seleccionada o no.
     * @return true o false
     */
    public boolean isSelected();

    /**
     * cambia su posicion dentro de la lista
     * @param position posicion de la lista
     */
    public void setPosition(int position);

    /**
     * Devuelve la posicion que se le asigno.
     * @return
     */
    public int getPosition();

    /**
     * Se le otorga un listener para poder comunicar con el fragment y la actividad
     * @param listener
     */
    public void setOnClickListener(MaterialSectionListener listener);

    /**
     * Devuelve su vista
     * @return
     */
    public View getView();

    /**
     * Recuperas el titulo de la seccion
     * @return
     */
    public String getTitle();

    /**
     * cambias el titulo de la seccion
     * @param title
     */
    public void setTitle(String title);

    /**
     * cambias el icono de la seccion
     * @param icon
     */
    public void setIcon(Drawable icon);

    /**
     * cambias el icono de la seccion
     * @param icon
     */
    public void setIcon(Bitmap icon);

    /**
     * se combian los colores primary y dark
     * @param color
     * @param colorDark
     */
    public void setSectionColor(int color, int colorDark);

    /**
     * indica si tiene un color asignado
     * @return
     */
    public boolean hasSectionColor();

    /**
     * indica si tiene color drak asignado
     * @return
     */
    public boolean hasSectionColorDark();

    /**
     * devuelve el color
     * @return
     */
    public int getSectionColor();

    /**
     * devuelve el color oscuro
     * @return
     */
    public int getSectionColorDark();

    /**
     * modifica la notificacion con el numero de contador
     * @param notifications
     */
    public void setNotifications(int notifications);

    /**
     * modifica la notificacion con un texto
     * @param text
     */
    public void setNotificationsText(String text);

    /**
     * devuelve el contador
     * @return
     */
    public int getNotifications();

    /**
     * devuelve el texto de la notificacion
     * @return
     */
    public String getNotificationsText();

    /**
     * abilita o desabilita el poder clicar
     * @param isTouchable
     */
    public void setTouchable(boolean isTouchable);

    /**
     * devuelve el objeto que representa un Intent o un Fragment 
     * para la accion que se deba al hacer click
     */
    public Object getContent();

    /**
     * El objecto representa un Intent o un Fragment
     */
    public void setContent(Object o);

    void afterClick();
}
