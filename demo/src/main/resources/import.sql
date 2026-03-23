-- 1. Primero proyectos
Insert into proyectos(nombre, id_cliente, fecha) values('Diseño de Base de Datos', 1, '2024-01-10');
Insert into proyectos(nombre, id_cliente, fecha) values('Desarrollo Frontend', 2, '2024-03-15');
Insert into proyectos(nombre, id_cliente, fecha) values('Integración de API', 1, '2024-06-20');

-- 2. Luego materiales
Insert into materiales(nombre, descripcion, valor_unitario) values('Cemento', 'Bulto de cemento 50kg', 25000);
Insert into materiales(nombre, descripcion, valor_unitario) values('Arena', 'Metro cubico de arena fina', 80000);
Insert into materiales(nombre, descripcion, valor_unitario) values('Varilla', 'Varilla de acero 1/2 pulgada', 15000);
Insert into materiales(nombre, descripcion, valor_unitario) values('Ladrillo', 'Ladrillo tolete unidad', 1200);

-- 3. Luego presupuestos (total inicia en 0, se calcula con detalles)
Insert into presupuestos(id_proyecto, total) values(1, 0);
Insert into presupuestos(id_proyecto, total) values(2, 0);
Insert into presupuestos(id_proyecto, total) values(3, 0);

-- 4. Por último detalles
-- Detalles presupuesto 1 (Diseño de Base de Datos) — total: 4500
Insert into detalle_presupuesto(id_presupuesto, id_material, stock, subtotal) values(1, 1, 20, 2000);
Insert into detalle_presupuesto(id_presupuesto, id_material, stock, subtotal) values(1, 2, 5, 1000);
Insert into detalle_presupuesto(id_presupuesto, id_material, stock, subtotal) values(1, 3, 15, 1500);

-- Detalles presupuesto 2 (Desarrollo Frontend) — total: 12000
Insert into detalle_presupuesto(id_presupuesto, id_material, stock, subtotal) values(2, 1, 30, 3000);
Insert into detalle_presupuesto(id_presupuesto, id_material, stock, subtotal) values(2, 2, 10, 2000);
Insert into detalle_presupuesto(id_presupuesto, id_material, stock, subtotal) values(2, 3, 20, 3000);
Insert into detalle_presupuesto(id_presupuesto, id_material, stock, subtotal) values(2, 4, 25, 4000);

-- Detalles presupuesto 3 (Integración de API) — total: 7800
Insert into detalle_presupuesto(id_presupuesto, id_material, stock, subtotal) values(3, 2, 15, 3000);
Insert into detalle_presupuesto(id_presupuesto, id_material, stock, subtotal) values(3, 3, 10, 2800);
Insert into detalle_presupuesto(id_presupuesto, id_material, stock, subtotal) values(3, 4, 50, 2000);