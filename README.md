# Desafio_libros_Alura
# 📚 Libros Alura

Aplicación Java con Spring Boot que permite buscar libros a través de la API de [Gutendex](https://gutendex.com/), guardarlos en una base de datos MySQL y listarlos según distintos criterios. Incluye un sistema de menú por consola para la interacción del usuario.

## 🚀 Tecnologías utilizadas

- Java 21
- Spring Boot 3
- Gson
- API REST (Gutendex)
- MySQL
- Maven

## 🧩 Funcionalidades principales

- 🔍 Buscar libros por título desde la API de Gutendex
- 💾 Guardar libros en la base de datos evitando duplicados
- 📋 Listar libros registrados
- 📚 Buscar libros por idioma
- 🧠 Evita mostrar libros duplicados por autor
- 📁 Gestión de idiomas desde archivo `idiomas.json`
- 👨‍💻 Interfaz por consola con validaciones

## 📂 Estructura del proyecto
LibrosAlura/
├── src/
│ ├── main/
│ │ ├── java/com/brendablanco/LibrosAlura/
│ │ │ ├── model/ # Clases entidad y DTO
│ │ │ ├── repository/ # Repositorios JPA
│ │ │ ├── service/ # Lógica de negocio y conexión con la API
│ │ │ ├── Menu.java # Menú principal por consola
│ │ │ └── LibrosAluraApplication.java
│ │ └── resources/
│ │ ├── application.properties
│ │ └── idiomas.json # Diccionario de idiomas disponibles
└── README.md

🌍 API utilizada
Este proyecto utiliza la API pública de Gutendex:
📎 https://gutendex.com/

👩‍💻 Autora
Brenda Blanco
💼 Desarrolladora Backend Jr.
