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

### Para la migración manual
En la sentencia Create Table, al lanzarse la aplicación, me estaba dando un error por detalles o diferencias entre la estructura esperada de una tabla
nueva por la definición de su entidad, y por la sentencia Create Table en el objeto de migración. 
Para resolverlo, podemos simplemente revisar la sentencia CREATE TABLE que corresponde en la clase que genera Room en la ruta
app/build/generated/ksp/debug/java, en una clase con el nombre de la clase de la base de datos  + _Impl.

### Para establecer relaciones entre tablas
Esta guía está muy bien: https://medium.com/androiddevelopers/database-relations-with-room-544ab95e4542
Y la de Android no está mal. Nos da la posibilidad de usar el enfoque de multimap, evitando clases intermediarias
https://developer.android.com/training/data-storage/room/relationships

### Para el campo de selección de Ciclos
https://github.com/material-components/material-components-android/blob/master/docs/components/Menu.md#exposed-dropdown-menus