## Notas de interés
 - Para que al mostrarse el teclado, no se ajuste el tamaño de los dos fragmentos 
(el de nuevo módulo y el del listado), lo que provoca que se oculten los botones de añadir nuevo módulo,
añadimos la propiedad android:windowSoftInputMode="adjustPan" en la etiqueta activity de las actividades
en las que nos interesa este comportamiento.
```xml
<activity
    android:name=".ui.MainActivity"
    android:exported="true"
    android:windowSoftInputMode="adjustPan">
    <!-- [...] -->
</activity>
```
   