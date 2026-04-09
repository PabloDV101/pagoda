# Integración Pagoda Admin ↔ Backend API

## Descripción General
El proyecto **pagoda-admin** (Angular 21) está completamente integrado con el **backend** (Spring Boot). Los datos ya no son estáticos, ahora se obtienen del API REST.

## Configuración de Conexión

### Backend (Spring Boot)
- **Puerto:** 8080
- **Base URL:** `http://localhost:8080/api`
- **CORS:** Habilitado para `http://localhost:4200`
- **Base de datos:** PostgreSQL (localhost:5432)
  - Usuario: admin
  - Contraseña: 12345
  - Base de datos: pagoda

### Frontend (Angular)
- **Puerto:** 4200 (desarrollo)
- **Base URL API:** Configurado en `src/environments/environment.ts`
  - Desarrollo: `http://localhost:8080/api`
  - Producción: `https://api.pagoda.com/api` (personalizar según tu dominio)

## Estructura de Servicios

Todos los servicios Angular están inyectados y conectados al backend:

### 1. **JornadaService** (`src/app/services/jornada.ts`)
- Carga la jornada actual
- Endpoint: `GET /api/operacion/jornadas/estado`
- Almacena la jornada en un signal para compartir con otros componentes

### 2. **VentasService** (`src/app/services/ventas.ts`)
- `getJornadaActual()` - obtiene el estado actual
- `getResumenVentas(jornadaId)` - obtiene resumen diario
- `getVentasActivas()` - obtiene las ventas activas
- Endpoints: 
  - `GET /api/operacion/jornadas/estado`
  - `GET /api/reportes/ventas-diarias/jornada/{id}`
  - `GET /api/ventas/activas`

### 3. **MenuService** (`src/app/services/menu.ts`)
- CRUD completo de productos
- Endpoints:
  - `GET /api/operacion/productos`
  - `POST /api/operacion/productos`
  - `PUT /api/operacion/productos/{id}`
  - `DELETE /api/operacion/productos/{id}`

### 4. **PropinasService** (`src/app/services/propinas.ts`)
- `getPropinas(jornadaId)` - obtiene propinas por jornada
- Endpoint: `GET /api/reportes/propinas-diarias/jornada/{id}`

### 5. **Top5Service** (`src/app/services/top5.ts`)
- `getTop5(jornadaId)` - obtiene los 5 platillos más vendidos
- Endpoint: `GET /api/reportes/platillos-diarios/jornada/{id}`

## Componentes Actualizados

Todos los componentes principales ahora cargan datos del backend:

1. **App Component** - Carga la jornada al inicializar la aplicación
2. **Ventas Component** - Obtiene datos de ventas del servicio
3. **Propinas Component** - Obtiene datos de propinas del servicio
4. **Top5 Component** - Obtiene datos de platillos del servicio
5. **Menu Component** - Obtiene y gestiona productos del catálogo

## Interceptor HTTP

Se creó `src/app/services/api.interceptor.ts` que:
- Agrega headers requeridos automáticamente
- Maneja errores y los registra en la consola
- Intercepta todas las solicitudes HTTP

## Cómo Usar

### Desarrollo

1. **Iniciar Backend:**
   ```bash
   cd backend
   mvn spring-boot:run
   ```

2. **Iniciar Frontend:**
   ```bash
   cd pagoda-admin
   npm start
   ```

3. **Acceder a la aplicación:**
   - Frontend: http://localhost:4200
   - Backend API: http://localhost:8080/api

### Producción

1. Actualizar `src/environments/environment.prod.ts` con la URL correcta del API
2. Compilar: `npm run build`
3. Desplegar los archivos en `dist/pagoda-admin`

## Respuestas del API

Todos los endpoints devuelven la siguiente estructura:

```json
{
  "success": true,
  "message": "Operación exitosa",
  "data": {
    // Datos específicos del endpoint
  }
}
```

Los servicios utilizan RxJS `map()` para extraer automáticamente el campo `data`.

## Manejo de Errores

- Los errores se capturan en los componentes y se asignan a un signal de error
- Se muestra un indicador de carga (`cargando` signal) mientras se obtienen los datos
- Los errores se registran en la consola del navegador

## Próximos Pasos Recomendados

1. Implementar autenticación (JWT)
2. Agregar un guard para rutas privadas
3. Implementar un servicio de notificaciones para errores
4. Agregar un Loader/Spinner global
5. Configurar HTTPS en producción
6. Agregar validación de tipos más estricta con DTOs específicos

---

**Última actualización:** 7 de abril de 2026
