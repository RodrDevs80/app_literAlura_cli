# LiterAlura - Aplicación de Gestión Literaria

Una aplicación de consola Spring Boot para gestionar libros y autores, conectada a la API de Gutendex para buscar y almacenar información literaria.

## 📋 Características

- 🔍 **Búsqueda de libros** por título desde la API de Gutendex
- 📚 **Gestión de libros** almacenados en base de datos
- 👥 **Gestión de autores** con información biográfica
- 🌍 **Filtrado por idioma** (español, inglés, francés, portugués)
- 📅 **Búsqueda de autores vivos** en un año específico
- 💾 **Persistencia de datos** en PostgreSQL

## 🛠️ Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3.x** (Spring Data JPA, Spring Boot DevTools)
- **PostgreSQL** - Base de datos
- **Maven** - Gestión de dependencias
- **Jackson** - Procesamiento JSON
- **Gutendex API** - Fuente de datos de libros

## 📁 Estructura del Proyecto

```
literAlura/
├── src/main/java/com/javaalura/literAlura/
│   ├── LiterAluraApplication.java      # Clase principal de Spring Boot
│   ├── Principal.java                  # Lógica principal de la aplicación (menú)
│   ├── dto/                           # Objetos de Transferencia de Datos
│   │   ├── AutorDTO.java
│   │   └── LibroDTO.java
│   ├── model/                         # Entidades JPA
│   │   ├── Autor.java
│   │   ├── Datos.java
│   │   ├── DatosAutor.java
│   │   ├── DatosLibros.java
│   │   └── Libro.java
│   ├── repository/                    # Repositorios de datos
│   │   ├── RepositoryAutor.java
│   │   └── RepositoryLibro.java
│   └── services/                      # Lógica de negocio
│       ├── ConsumeAPI.java
│       ├── ConvierteDatos.java
│       ├── IConvierteDatos.java
│       ├── MostrarLibrosAndAutores.java
│       ├── ServiceAutor.java
│       ├── ServiceLibro.java
│       └── Validaciones.java
├── src/main/resources/
│   └── application.properties          # Configuración de la aplicación
├── src/test/                          # Pruebas unitarias
├── pom.xml                            # Configuración Maven
├── mvnw                               # Wrapper Maven para Unix
└── mvnw.cmd                           # Wrapper Maven para Windows
```

## ⚙️ Requisitos Previos

1. **Java 17** o superior
2. **Maven 3.6+** (incluido en el wrapper del proyecto)
3. **PostgreSQL** instalado y en ejecución
4. **Conexión a internet** para acceder a la API de Gutendex

## 🚀 Instalación y Configuración

### 1. Clonar el repositorio

```bash
git clone [url-del-repositorio]
cd literAlura
```

### 2. Configurar la base de datos

Crear una base de datos en PostgreSQL:

```sql
CREATE DATABASE literalura;
```

### 3. Configurar las credenciales de la base de datos

Editar el archivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### 4. Compilar y ejecutar la aplicación

**Usando Maven Wrapper (recomendado):**

**En Linux/Mac:**

```bash
./mvnw clean spring-boot:run
```

**En Windows:**

```cmd
mvnw.cmd clean spring-boot:run
```

**Usando Maven instalado:**

```bash
mvn clean spring-boot:run
```

## 🎮 Uso de la Aplicación

Al iniciar la aplicación, se mostrará un menú interactivo con las siguientes opciones:

### Opciones del Menú

1. **Buscar libro por título**

   - Ingresa el título del libro que deseas buscar
   - La aplicación buscará en la API de Gutendex
   - Si se encuentra, se guardará en la base de datos junto con su autor

2. **Listar libros registrados**

   - Muestra todos los libros almacenados en la base de datos
   - Incluye título, autor, idiomas y número de descargas

3. **Listar autores registrados**

   - Muestra todos los autores almacenados en la base de datos
   - Incluye nombre, años de nacimiento y muerte, y sus libros

4. **Listar autores vivos en un determinado año**

   - Ingresa un año específico
   - Muestra los autores que estaban vivos durante ese año

5. **Listar libros por idiomas**

   - Selecciona un idioma (es, en, fr, pt)
   - Muestra todos los libros disponibles en ese idioma

6. **Salir**
   - Finaliza la aplicación

## 🔧 Funcionalidades Técnicas

### Conexión a API Externa

- Utiliza la API pública de Gutendex (https://gutendex.com/books/)
- Realiza búsquedas por título
- Convierte respuestas JSON a objetos Java

### Persistencia de Datos

- **Autor**: Almacena nombre, años de nacimiento y muerte
- **Libro**: Almacena título, autor (relación ManyToOne), idiomas y descargas
- **Relaciones**: Un autor puede tener múltiples libros

### Validaciones

- Evita duplicados de libros en la base de datos
- Verifica la existencia de autores antes de crearlos

## 📊 Modelo de Datos

### Entidad Autor

```java
@Entity
@Table(name = "autores")
public class Autor {
    private Long id;
    private String nombre;
    private Integer anioNacimiento;
    private Integer anioMuerte;
    private List<Libro> libros;
}
```

### Entidad Libro

```java
@Entity
@Table(name = "libros")
public class Libro {
    private Long id;
    private String titulo;
    private Autor autor;
    private List<String> idiomas;
    private Double numeroDeDescargas;
}
```

## 🧪 Pruebas

Para ejecutar las pruebas:

```bash
./mvnw test
```

## 📝 Notas

- La aplicación utiliza el wrapper de Maven, por lo que no es necesario tener Maven instalado globalmente
- La primera ejecución puede tardar más mientras descarga las dependencias
- Los datos se persisten en PostgreSQL y se mantienen entre ejecuciones
- La aplicación está diseñada para uso educativo y demostrativo

## 🔄 Mantenimiento

### Actualizar dependencias

```bash
./mvnw versions:display-dependency-updates
./mvnw versions:display-plugin-updates
```

### Limpiar y recompilar

```bash
./mvnw clean compile
```

### Generar JAR ejecutable

```bash
./mvnw clean package
```

## 🤝 Contribuir

1. Haz fork del proyecto
2. Crea una rama para tu funcionalidad (`git checkout -b feature/nueva-funcionalidad`)
3. Realiza tus cambios y haz commit (`git commit -am 'Agrega nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está licenciado bajo la Licencia Apache 2.0. Ver el archivo `LICENSE` para más detalles.

## 🆘 Soporte

Si encuentras problemas o tienes preguntas:

1. Revisa la configuración de la base de datos
2. Verifica que tengas conexión a internet para acceder a la API
3. Asegúrate de tener Java 17 instalado
4. Revisa los logs de la aplicación para más detalles

---

**Desarrollado como proyecto educativo** - Este proyecto forma parte de un curso de formación en desarrollo Java con Spring Boot.
