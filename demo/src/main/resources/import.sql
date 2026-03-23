Insert into proyectos(nombre, id_cliente, fecha) values('Diseño de Base de Datos', 1, '2024-01-10');
Insert into proyectos(nombre, id_cliente, fecha) values('Desarrollo Frontend', 2, '2024-03-15');
Insert into proyectos(nombre, id_cliente, fecha) values('Integración de API', 1, '2024-06-20');
Insert into proyectos(nombre, id_cliente, fecha) values('Pruebas de Software', 3, '2025-01-05');

Insert into materiales(nombre, descripcion, valor_unitario) values('Cemento', 'Bulto de cemento 50kg', 25000);
Insert into materiales(nombre, descripcion, valor_unitario) values('Arena', 'Metro cubico de arena fina', 80000);
Insert into materiales(nombre, descripcion, valor_unitario) values('Varilla', 'Varilla de acero 1/2 pulgada', 15000);
Insert into materiales(nombre, descripcion, valor_unitario) values('Ladrillo', 'Ladrillo tolete unidad', 1200);

Insert into presupuestos(id_proyecto, total) values(1, 4500);
Insert into presupuestos(id_proyecto, total) values(2, 12000);
Insert into presupuestos(id_proyecto, total) values(3, 7800);
Insert into presupuestos(id_proyecto, total) values(1, 3200);

Insert into detalle_presupuesto(id_presupuesto, id_material, stock, subtotal) values(1, 1, 20, 2000);
Insert into detalle_presupuesto(id_presupuesto, id_material, stock, subtotal) values(1, 2, 5, 1000);
Insert into detalle_presupuesto(id_presupuesto, id_material, stock, subtotal) values(1, 3, 15, 1500);
Insert into detalle_presupuesto(id_presupuesto, id_material, stock, subtotal) values(2, 4, 10, 3200);