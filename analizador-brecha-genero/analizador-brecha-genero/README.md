# Sistema Analizador de Brecha Salarial e Índice de Participación por Género

Proyecto Integrador de Aula 2026-2 — Universidad Simón Bolívar.
Cursos: Estructura de Datos (Prof. Omar Gutiérrez Acosta) y Base de Datos (Prof. Juan Carlos Calabria Sarmiento).
ODS 5: Igualdad de género.

## Estructura

```
src/co/edu/unisimon/brecha/
  modelo/       Empleado
  estructuras/  NodoDoble, ListaDobleEmpleados, NodoAVL, ArbolAVL
  servicios/    AnalizadorEquidad
  app/          MainPrueba
sql/
  01_ddl_oracle.sql        tablas, llaves y restricciones
  02_dml_oracle.sql        datos de prueba simulados
  03_consultas_oracle.sql  consultas de indicadores (INNER JOIN)
```

Las estructuras son propias: no se usa ninguna colección de `java.util`.

## Ejecutar

```
javac -encoding UTF-8 -d out src/co/edu/unisimon/brecha/*/*.java
java -cp out co.edu.unisimon.brecha.app.MainPrueba
```

En NetBeans o IntelliJ: abrir la carpeta, marcar `src` como carpeta de fuentes y ejecutar `MainPrueba`.

## Base de datos

En Oracle APEX: SQL Workshop > SQL Scripts, subir y ejecutar los scripts en orden (01, 02, 03).

## Integrantes

- Jesús Enrique Marín Pacheco — Coordinador e investigador
- Juan Davis Romero Velásquez — Desarrollador de estructuras
- Hemel Andrés Barrios Guerra — Desarrollador de aplicación
- Luis Ángel Arango Verdooren — Desarrollador de base de datos
- Angélica Marier López Jiménez — Documentadora
