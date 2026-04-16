# API de Gestión de Biblioteca Universitaria

API RESTful desarrollada con **Spring Boot** para la gestión completa de bibliotecas universitarias.
Permite registrar libros, usuarios, préstamos y devoluciones con autenticación segura mediante **JWT**
y control de acceso por roles (**ROLE_ADMIN** y **ROLE_USER**).

**Proyecto de Evaluación Final**  
**Unidad Didáctica:** Desarrollo de los Componentes del Negocio  
**Escuela:** IDAT - Escuela de Tecnología

---

## Características

- Autenticación **JWT** sin estado (stateless)
- Control de acceso por roles: **ROLE_ADMIN** y **ROLE_USER**
- Gestión completa de Libros, Usuarios, Préstamos y Devoluciones
- Persistencia con **JPA + Hibernate + Spring Data JPA**
- Base de datos **PostgreSQL**
- Validaciones con Bean Validation
- DTOs + **MapStruct** para una arquitectura limpia
- Código estructurado, comentado y mantenible

---

## Tecnologías

- **Java**: 21
- **Spring Boot**: 3.5.13
- **Spring Security + JWT**
- **Spring Data JPA + Hibernate**
- **PostgreSQL** (versión 18)
- **MapStruct + Lombok**
- **Maven** 3.9.14

---

##  Cómo Levantar la API (paso a paso)

### 1. Abrir terminal en IntelliJ / PowerShell
```powershell
PS C:\Users\jhonn\Downloads\biblioteca.api> cd biblioteca.api
PS C:\Users\jhonn\Downloads\biblioteca.api\biblioteca.api> mvn clean spring-boot:run
