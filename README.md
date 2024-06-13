
# Proyecto de Gestión de Créditos

Este proyecto es una aplicación web para la gestión de créditos, donde se pueden registrar, consultar y eliminar créditos, así como realizar pagos y consultar créditos por deudor o cobrador.
- **Vista**: El consumo de esta API se encuentra en el siguiente repositorio, un proyecto creado con el framework de Angular
     ```
     https://github.com/Yesid-r/frontend_prueba
     ```

## Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3.1.0**
- **Spring Data JPA**
- **H2 Database** (para pruebas en memoria)
- **JUnit 5**
- **Spring Security**
- **Mockito** (opcional)
- **Maven**
- **Lombok**
## Base de datos
![Diseno de base de datos](/images/uml.png)

## Configuración del Entorno

1. **Java 17**: Asegúrate de tener instalado JDK 17.
2. **Maven**: Asegúrate de tener instalado Maven.

## Instalación

1. Clona el repositorio:
   ```sh
   git clone https://github.com/Yesid-r/backend-prueba-tecnica.git
   ```
2. Navega al directorio del proyecto:
   ```sh
   cd pruebatecnica
   ```
3. Compila y ejecuta los tests:
   ```sh
   mvn clean install
   ```

## Ejecución de la Aplicación

Para ejecutar la aplicación, utiliza el siguiente comando o ejecutar desde un ide como IntelliJ IDEA:
```sh
mvn spring-boot:run
```

La aplicación estará disponible en `http://localhost:8081`.

## Endpoints
### User 

#### Listar usuarios
```
GET /api/v1/users
```
Devuelve una lista de usuarios.

#### Encontrar usuarios por role
```
GET /api/v1/users/find-usersrole/{role}
```
Devuelve lista de usuarios dependiendo el role, por ejemplo: ADMIN, DEUDOR, COBRADOR

#### Eliminar usuario
```
DELETE /api/v1/users/{id}
```
Elimina un usuario por un id especifico

#### Actulizar usuario

- `PUT /api/v1/users/{id}`

    - **Body**:
        ```json
        {
        "firstname": "Juan update",
        "lastname": "Ramirez update",
        "email":"juan@gmail.com",
        "phone": "312312312"
        }
        ```
  Actualiza un usuario sin modificar email o contraseña

### Authentication Endpoints

#### Register
- `POST /api/v1/auth/register`
    - **Body**:
        ```json
        {
          "firstname":"Ejemplo",
          "lastname":"Apellido ejemplo",
          "email": "ejemplo@gmail.com",
          "password": "1234",
          "phone":"312312312",
          "role":"DEUDOR"
        }
        ```
Registrar nuevo usuario.

#### Authenticate
- `POST /api/v1/auth/authenticate`
    - **Body**:
        ```json
        {
          "email":"ejemplo@gmail.com",
          "password":"1234"
        }
        ```    
autentica a un usuario y devuelve un JWT token.
### Créditos

- `GET /api/v1/credit`: Obtiene todos los créditos.
- `POST /api/v1/credit`: Registra un nuevo crédito.
    - **Body**:
      ```json
      {
        "idDeudor": 3,
        "idCobrador": 4,
        "saldo": 500.00
      }
      ```
- `GET /api/v1/credit/{id}`: Obtiene un crédito por su ID.
- `DELETE /api/v1/credit/{id}`: Elimina un crédito por su ID.

### Pagos

- `POST /api/v1/credit/payment`: Realiza un pago a un crédito.
    - **Body**:
      ```json
      {
        "id": 1,
        "amount": 500.00
      }
      ```

### Consultas por Deudor y Cobrador

- `GET /api/v1/credit/credits-deudor/{id}`: Obtiene los créditos asociados a un deudor por su ID.
- `GET /api/v1/credit/credits-cobrador/{id}`: Obtiene los créditos asociados a un cobrador por su ID.

## Base de Datos en Memoria

El proyecto utiliza una base de datos H2 en memoria para las pruebas. La configuración de la base de datos se encuentra el archivo `application.properties`.

### Consultas SQL de Ejemplo
Puede crear un archivo sql en la carpeta resources dentro de la carpeta test, donde se pueden agregar las siguientes consultas
```sql
CREATE TABLE IF NOT EXISTS _USER (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    firstname VARCHAR(255),
    lastname VARCHAR(255),
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(20),
    password VARCHAR(255),
    role VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS CREDITO (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    deudor_id INTEGER,
    cobrador_id INTEGER,
    saldo DOUBLE,
    created_at DATE,
    updated_at DATE,
    FOREIGN KEY (deudor_id) REFERENCES _USER(id),
    FOREIGN KEY (cobrador_id) REFERENCES _USER(id)
);

INSERT INTO _user (id, firstname, lastname, email, phone, password, role)
VALUES (2, 'John', 'Doe', 'john.doe@example.com', '123456789', '$2a$10$2yERXj5/R5PfVLEpG34h5OVsSTUVv54VjTpdW7qO3QcZk31Q8W5SW', 'ADMIN');

INSERT INTO _user (id, firstname, lastname, email, phone, password, role)
VALUES (3, 'Jane', 'Doe', 'jane.doe@example.com', '987654321', '$2a$10$5jYK7jxZS2i/dvPbtQewVOn6sUuRQ0i2IR9w1KXTgJ5D0TmweMNyK', 'DEUDOR');

INSERT INTO _user (id, firstname, lastname, email, phone, password, role)
VALUES (4, 'Alice', 'Smith', 'alice.smith@example.com', '456123789', '$2a$10$eA4Qb1M1tOsoDXH56W6yvOzXaIyPHaTB3Pfz1Y/wTt/7F1soGZdI2', 'COBRADOR');

INSERT INTO _user (id, firstname, lastname, email, phone, password, role)
VALUES (5, 'Pedro', 'Perez', 'pedro.perez@example.com', '456123789', '$2a$10$eA4Qb1M1tOsoDXH56W6yvOzXaIyPHaTB3Pfz1Y/wTt/7F1soGZdI2', 'COBRADOR');

INSERT INTO credito (id, deudor_id, cobrador_id, saldo)
VALUES (1, 2, 3, 1000.00);

INSERT INTO credito (id, deudor_id, cobrador_id, saldo)
VALUES (2, 2, 3, 500.00);

INSERT INTO credito (id, deudor_id, cobrador_id, saldo)
VALUES (3, 3, 2, 750.00);
```

## Pruebas

Para ejecutar las pruebas unitarias, utiliza el siguiente comando:
```sh
mvn test
```

## Autor: Yesid Rincon 
