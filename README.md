# 🌌 API Mars Rover 🚀

El objetivo de este proyecto es controlar un rover en Marte y moverlo por un terreno sembrado de obstáculos. Esta aplicación está desarrollada utilizando Spring Boot para el backend y se conecta a un frontend interactivo para ofrecer una experiencia de usuario completa y fluida.

<p align="center">
  <img src="https://github.com/miguelmallquidiaz/api-spring-boot-mars-rover/blob/main/image/rover_poster.jpg" height="200px" alt="rover">
</p>

## 🛠️ Tecnologías Utilizadas
- Spring Boot: Framework para el desarrollo del backend, proporcionando una configuración rápida y eficiente para la creación de aplicaciones basadas en Java.
- Frontend: Interfaz de usuario interactiva que permite a los usuarios controlar el rover y visualizar su movimiento en tiempo real.
- API REST: Comunicación entre el frontend y el backend para enviar comandos al rover y recibir información sobre su estado y entorno.
- Postman: Herramienta utilizada para interactuar, enviar datos en formato JSON y realizar pruebas durante el desarrollo del proyecto para la creación del rover.

## 🌍 Funcionalidades Principales
- Control del Rover: Enviar comandos para mover el rover en diferentes direcciones (adelante, atrás, girar a la derecha, girar a la izquierda).
- Detección de Obstáculos: El rover puede detectar obstáculos en su camino y responder adecuadamente para evitar colisiones.
- Visualización en Tiempo Real: El frontend muestra la posición actual del rover y los obstáculos en el terreno de Marte.

## 📸 Capturas de Pantalla
- Inicio de pantalla de Rover en Marte:
<img src="https://github.com/miguelmallquidiaz/api-spring-boot-mars-rover/blob/main/image/mars-rover.PNG" alt="rover">

- Obstáculo Detectado:
<img src="https://github.com/miguelmallquidiaz/api-spring-boot-mars-rover/blob/main/image/obstacle-detected.PNG" alt="rover">

## EndPoint:

### Rover

| Method   | Route                      | Description                                      |
| -------- | -------------------------- | ------------------------------------------------ |
| POST     | /api/rover/               | Create rover at default position                          |
| POST      | /api/rover/command/      |  Move the rover             |

### Obstacle

| Method   | Route                      | Description                                      |
| -------- | -------------------------- | ------------------------------------------------ |
| POST     | /api/obstacle/               | Create obstacle                          |


## Workspace

Desde Postman, interactúo para enviar en formato JSON y realizar pruebas. Esto es para crear el rover.
```
{
    "x": 1,
    "y": 2,
    "direction": "NORTH"
}
```

## Para ejecutar el proyecto
- Una vez dado a spring boor run
- Ingresar a `http://localhost:8080/`
