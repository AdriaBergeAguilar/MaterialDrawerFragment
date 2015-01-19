# MaterialDrawerFragment
### Una pequeña librería para poder tener el fragment del drawer lateral con una estética material y con cabecera personaliza-ble o una de por defecto de usuarios.

---

El funcionamiento es simple, heredas de MenuDrawerFragment o de MenuDrawerAccountFragment, que es una herencia del primero, y simplemente tienes que añadir las secciones del menú y/o los usuarios. 

**Ejemplo MenuDrawerFragment** 

```java
public class DrawerFragment extends MenuDrawerAccountFragment {
    @Override
    public void onStartDrawerStructure() {
        MaterialSectionImp materialSection2 = new MaterialSectionImp(getActivity(), false);
        materialSection2.setTitle("Hola title" + 1);
        materialSection2.setNotifications(11 );
        materialSection2.setSectionColor(
             Color.rgb(210, 230, 33),
             Color.rgb(210, 230, 33)
        );
        addSection(materialSection2);
    }
    @Override
    public View onConfigureHeader() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.my_header, null);
        return view;
    }
}
-
```
En próximas mejoras se añadirán las animaciones.

Gracias a <a href="https://github.com/neokree">neokree</a> por su librería del drawer <a href="https://github.com/neokree/MaterialNavigationDrawer">MaterialNavigationDrawer</a> esta es una estructuración de la suya para no forzar la herencia de la actividad.
