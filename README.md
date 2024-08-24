# Sistema de gestión de tienda

Este proyecto implementa un sistema de gestión para una tienda que compra y vende productos envasados, bebidas y de limpieza.

## Funcionalidades principales

- Compra y venta de productos.
- Aplicación de descuentos con restricciones según el tipo de producto.
- Cálculo de precios de venta considerando impuestos para productos importados.
- Gestión de stock y disponibilidad de productos.
- Obtención de lista de productos comestibles con descuento menor a un porcentaje dado.

## Estructura del proyecto

El proyecto está organizado en tres paquetes principales:

1. `models`: Contiene las clases de productos y la interfaz Comestible.
2. `negocio`: Contiene la clase Tienda que maneja las operaciones de negocio.
3. `test`: Contiene las clases de prueba.

## Decisiones de diseño

- Se creó una interfaz `Comestible` para representar productos que tienen fecha de vencimiento y calorías. Esta interfaz es implementada por las clases `Envasado` y `Bebida`.
- La clase `Producto` se diseñó como abstracta para proporcionar una base común para todos los tipos de productos y definir métodos abstractos como `calcularPrecioVenta()` y `aplicarDescuento()`.
- Cada subclase de `Producto` (Envasado, Bebida, Limpieza) implementa sus propias validaciones específicas para porcentajes de ganancia y descuento, cumpliendo con las reglas de negocio establecidas.
- Se implementó un sistema de generación automática autoincremental de identificadores para cada tipo de producto (AB***, AC***, AZ***).
- Se utilizan excepciones personalizadas (`IllegalArgumentException`) para manejar casos de error en la creación y manipulación de productos.
- Cada clase implementa su propio método `toString()` con `StringBuilder` para proporcionar una mejor representación del objeto.
- Se utilizó un `Map` en la clase `Tienda` para almacenar los productos y sus cantidades. Esto permite un acceso y manipulación eficientes del inventario.

## Algunos métodos clave

1. `Tienda.obtenerComestiblesConMenorDescuento`: Utiliza Stream API para filtrar y ordenar productos comestibles basados en descuentos.
2. `Tienda.comprarProducto` y `Tienda.venderProductos`: Gestionan las operaciones principales de la tienda con validaciones robustas.
3. `Bebida.calcularCalorias`: Implementa una lógica específica para calcular calorías en bebidas alcohólicas.
4. `Limpieza.setTipoAplicacion`: Asegura que solo se asignen tipos de aplicación válidos a productos de limpieza.
5. Métodos abstractos en `Producto`: Fuerzan a las subclases a implementar lógicas específicas para cálculo de precios y aplicación de descuentos.

## Consideraciones especiales

- Los productos comestibles tienen restricciones en el porcentaje de ganancia.
- Los productos de limpieza tienen restricciones específicas en porcentajes de ganancia y descuento.
- Se aplica un impuesto adicional a los productos importados.
- La venta está limitada a un máximo de 3 productos diferentes por transacción.
