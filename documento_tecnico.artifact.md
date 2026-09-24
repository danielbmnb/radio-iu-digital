# Documento Técnico: Aplicación Móvil Android "IU Digital Radio"

**Evidencia de Aprendizaje 3**
**Programa**: Tecnología en Desarrollo de Software
**Asignatura**: Desarrollo de Aplicaciones Móviles
**Institución**: Institución Universitaria Digital de Antioquia (IU Digital)

---

## 1. Portada e Información del Proyecto

| Campo | Detalle |
| :--- | :--- |
| **Título del Proyecto** | IU Digital Radio - Aplicación Móvil Nativa Android |
| **Modalidad de Trabajo** | Opción B: Trabajo Individual (Desarrollador Full-Stack Android) |
| **Estudiante** | Desarrollador Full-Stack Android |
| **Herramientas & Lenguaje** | Android Studio, Kotlin 2.2, Jetpack Compose, Media3 ExoPlayer |
| **Estilo de Diseño UI/UX** | Dark Glassmorphism, Gradientes Neón, Microanimaciones |
| **Estado del Proyecto** | 100% Completado y Compilado (APK Ejecutable) |

---

## 2. Definición de Arquitectura y Manejo de Estado

La aplicación fue desarrollada siguiendo las **buenas prácticas recomendadas por Google para Android**, implementando la arquitectura **MVVM (Model-View-ViewModel)** y diseño de interfaz completamente declarativo con **Jetpack Compose (Material 3)**.

### Estructura de Capas y Componentes
```
com.app.iudigitalradio
├── data/
│   ├── model/
│   │   └── RadioStation.kt           # Modelo inmutable de la emisora de radio
│   └── repository/
│       └── RadioRepository.kt        # Repositorio de streaming y catálogo de emisoras
├── utils/
│   └── HapticFeedbackManager.kt     # Gestor del servicio de vibración (VibratorManager)
├── ui/
│   ├── state/
│   │   └── RadioState.kt             # Estado global e inmutable de la interfaz de usuario
│   ├── viewmodel/
│   │   └── RadioViewModel.kt         # ViewModel centralizador con Media3 ExoPlayer
│   ├── components/
│   │   ├── UserProfileHeader.kt      # Encabezado de perfil + Anillo neón + Cámara en vivo
│   │   ├── RadioPlayerCard.kt        # Reproductor Glassmorphic + Ecualizador 7 barras + Controles
│   │   └── StationListSection.kt     # Catálogo dinámico con Chips de filtro y LazyColumn
│   ├── screens/
│   │   └── MainRadioScreen.kt        # Pantalla principal con fondo de gradiente continuo y Scaffold
│   └── theme/
│       ├── Color.kt                  # Paleta neón y superficies de cristal glassmorphic
│       ├── Type.kt                   # Tipografía moderna de alta legibilidad
│       └── Theme.kt                  # Tema Material 3
└── MainActivity.kt                   # Punto de entrada principal de la aplicación
```

### Gestión de Estado
- **`StateFlow` y Estado Inmutable (`RadioState`)**: El ViewModel expone un flujo de estado unificado `StateFlow<RadioState>` a la interfaz.
- **Resistencia a Rotaciones de Pantalla**: Al utilizar `ViewModel`, el estado de la transmisión, emisora seleccionada, foto de perfil y controles se preserva automáticamente ante cambios de configuración (ej. rotación del dispositivo).
- **Jetpack Compose Declarativo**: Se descartaron por completo los XML tradicionales de vistas (`RF-01`), utilizando componentes `Column`, `Row`, `Card`, `LazyColumn` y `Scaffold`.

---

## 3. Integración con Hardware y Servicios del Sistema

### A. Captura de Foto con Cámara Nativa (`RF-02`, `RF-03`)
- **Contrato de Actividad**: Se utilizó `ActivityResultContracts.TakePicturePreview()` para lanzar la aplicación de cámara predeterminada del sistema y obtener el `Bitmap` en tiempo real.
- **Gestión de Permisos en Tiempo de Ejecución**: Se declaró el permiso `android.permission.CAMERA` en `AndroidManifest.xml` y se implementó la solicitud dinámica en tiempo de ejecución utilizando `rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission())`.

### B. Retroalimentación Háptica / Vibración (`RF-05`)
- **Motor de Vibración Dual**: Implementación en `HapticFeedbackManager` utilizando `VibratorManager` para dispositivos Android 12+ (API 31+) y retrocompatibilidad con `Vibrator` para versiones anteriores.
- **Disparo de Pulsación**: Se activa una pulsación háptica corta (50ms) cada vez que el usuario presiona los botones de **Play**, **Pausa**, **Mute** o cambia de emisora en la lista.

### C. Reproducción de Streaming Audio en Vivo (`RF-07`)
- **Media3 ExoPlayer**: Integración de la librería moderna `androidx.media3:media3-exoplayer`.
- **Señales de Radio Reales**: Conexión a streams de audio online de alta calidad, incluyendo la emisora institucional de IU Digital Radio y canales de radio aliados.
- **Gestión del Ciclo de Vida**: Liberación de recursos del reproductor (`exoPlayer.release()`) al destruirse la actividad en `onCleared()`.

---

## 4. Diseño UI/UX y Componentes Destacados

1. **Estilo Dark Glassmorphism**:
   - Uso de tarjetas con fondo de gradiente traslúcido y bordes finos con brillo blanco de opacidad reducida (`Color(0x26FFFFFF)`).
   - Paleta de colores atractiva con Azul Oscuro Noche (`#0B0F19`), Cian Neón (`#00F2FE`), Azul Neón (`#4FACFE`) y Dorado Universitario (`#FFB703`).
2. **Microanimaciones e Indicadores de Estado**:
   - Badge "● FULLSTACK ONLINE" con pulso verde continuo en el perfil de usuario.
   - Insignia "● SEÑAL EN VIVO" con luz pulsante en la tarjeta del reproductor.
   - Ecualizador de 7 barras animadas con movimiento asíncrono y gradientes cuando el audio está sonando.
3. **Filtros e Interacción**:
   - Barra de Chips de filtrado horizontal ("Todas", "Institucional", "Noticias", "Música").
   - Tarjetas de emisoras con bordes iluminados en cian neón al seleccionarse.

---

## 5. Matriz de Cumplimiento de Requerimientos Funcionales (RF)

| ID | Requerimiento Funcional | Estado | Implementación Técnica |
| :--- | :--- | :---: | :--- |
| **RF-01** | Maquetación UI Declarativa | **100%** | Construido 100% en Jetpack Compose (`Column`, `Row`, `Card`, `LazyColumn`, `Scaffold`). |
| **RF-02** | Perfil con Captura de Cámara | **100%** | Captura de foto en tiempo real con `TakePicturePreview` expuesta en avatar circular con anillo neón. |
| **RF-03** | Gestión de Permisos en Tiempo de Ejecución | **100%** | Permisos `CAMERA`, `VIBRATE` e `INTERNET` en manifest y diálogo en ejecución. |
| **RF-04** | Reproductor Interactivo y Estado Dinámico | **100%** | `RadioViewModel` + `StateFlow` reactivo con preservación ante rotación de pantalla. |
| **RF-05** | Retroalimentación Háptica (Vibración) | **100%** | Integración de `VibratorManager` / `Vibrator` para vibración en Play, Pause, Mute. |
| **RF-06** | Lista Dinámica de Emisoras | **100%** | `LazyColumn` con chips de filtrado y mini-ecualizador en tiempo real. |
| **RF-07** | Streaming / Audio Player | **100%** | `Media3 ExoPlayer` integrado transmitiendo señales en vivo. |

---

## 6. Guía de Compilación e Instalación del APK (.apk)

### Generación del Ejecutable
Para compilar la aplicación y generar el paquete instalable en formato APK:
1. Abrir la terminal de Android Studio en la raíz del proyecto.
2. Ejecutar el comando de compilación:
   ```bash
   ./gradlew app:assembleDebug
   ```
3. El archivo APK resultante se genera en la siguiente ruta:
   `app/build/outputs/apk/debug/app-debug.apk`

---

## 7. Conclusiones

1. **Impacto Visual y UX**: La combinación de Glassmorphism, colores neón y animaciones otorga a la aplicación un aspecto moderno, fluido e interactivo.
2. **Eficiencia con Jetpack Compose**: La adopción del paradigma declarativo permitió reducir la cantidad de código y facilitar la aplicación de gradientes y formas avanzadas.
3. **Mantenibilidad y Código Limpio**: La separación en arquitectura MVVM facilita la expansión futura del sistema manteniendo alta calidad en el software.
