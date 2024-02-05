## Notas de interés

### Para que al mostrarse el teclado, no se ajuste el tamaño de los dos fragmentos 
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

### Para incluir llamadas a Compose
https://developer.android.com/jetpack/compose/setup?hl=es-419#setup-compose
Importante: Para elegir la versión de kotlinCompilerExtensionVersion, revisar
la lista de compatibilidad: https://developer.android.com/jetpack/androidx/releases/compose-kotlin

Y añadir la dependencia fragment-ktx para usar composables en fragmentos
Para migrar el Adapter a una LacyList podemos seguir esta guía:
https://developer.android.com/jetpack/compose/migrate/migration-scenarios/recycler-view
o este tutorial https://dev.to/mahendranv/using-viewmodel-livedata-with-jetpack-compose-31h8

   