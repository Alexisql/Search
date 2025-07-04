# SearchApp
<p>
<img src="https://img.shields.io/badge/Android-%23FFFFFF?logo=android">
<img src="https://img.shields.io/badge/Kotlin-%23FFFFFF?logo=kotlin">
</p>

Para la resolución del challenge se decide guardar la información en Room, para luego realizar la búsqueda de manera paginada con Paging 3, con el fin de mostrar los datos bajo demanda.

Room:
<br>
Room utiliza SQLite como base de datos, la cual está diseñada para búsquedas rápidas en dispositivos móviles.

Paging 3:
<br>
Paging funciona bajo demanda, lo que permite obtener solo los datos necesarios para mostrar en pantalla, mejorando la experiencia de usuario.
<br>

## Observaciones:
- Las pruebas unitarias se encuentran en el módulo androidTest debido a la necesidad de obtener el Contexto de android, para la inicialización de Room en las pruebas.