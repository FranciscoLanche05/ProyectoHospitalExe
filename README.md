# Proyecto Grupal: Gestión de Pacientes (.exe con BD en la nube)

Este repositorio contiene el código fuente y las instrucciones de despliegue para la aplicación de Gestión de Pacientes, desarrollada en JavaFX con conexión a una base de datos MySQL alojada en la nube.

## 👥 Integrantes del Grupo
* Leonel Moreira
* Francisco Lanche
* Leandro Jacho
* Kevin Mendoza
* Erick Patiño
* Alexander Lluglla
* Karen Lozano
* Alex Lascano

## ⚙️ Control de Tarea: Pasos Realizados

Para cumplir con los requerimientos del proyecto (ejecutable `.exe` conectado a una base de datos en la nube de forma colaborativa), se siguieron los siguientes pasos:

### 1. Despliegue de la Base de Datos en la Nube
Se creó una instancia de base de datos MySQL en la nube utilizando el proveedor **Aiven**. 
Se ejecutó el siguiente script para generar la estructura inicial en la base de datos `defaultdb`:

```sql
CREATE table pacientes(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre varchar(100) NOT NULL,
    apellido varchar(100) NOT NULL,
    edad varchar(10) not null,
    cedula varchar(10) NOT NULL
);

INSERT INTO pacientes (id, nombre, apellido, edad, cedula) VALUES
(1, 'María', 'González', 32, '1723456789'),
(2, 'Carlos', 'Pérez', 45, '0912345678'),
(3, 'Andrea', 'López', 28, '1109876543'),
(4, 'José', 'Martínez', 56, '0804567891'),
(5, 'Sofía', 'Romero', 39, '1711122233');
