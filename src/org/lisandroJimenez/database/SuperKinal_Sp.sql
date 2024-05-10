 Use superkinalin5cvdb;
 -- --------------------------------------------------------Cargos---------------------------------------------------------------------
 -- agregar
 delimiter $$
create procedure sp_agregarCargo(nomCar varchar(30), desCar varchar(100)) 
	begin
		insert into Cargos(nombreCargo, descripcionCargo) values
        (nomCar,desCar);
	end$$
delimiter ;
-- listar 
delimiter $$
create procedure sp_listarCargo()
	begin 
		select * from Cargos;
    end $$
delimiter ;
-- buscar
delimiter $$
create procedure sp_buscarCargo(carId int)
	begin
		select * from Cargos C
        where carId = C.cargoId;
    end$$
delimiter ;
-- eliminar
delimiter $$
create procedure sp_eliminarCargo(carId int)
	begin
		delete from Cargos 
        where carId = cargoId;
    end $$
delimiter ;
-- editar
delimiter $$
create procedure sp_editarCargo(carId int, nomCar varchar(30), desCar varchar(100))
	begin 
		update Cargos C set
			C.nombreCargo = nomCar,
			C.descripcionCargo = desCar
			where carId = C.cargoId;
    end $$
delimiter ;
 -- --------------------------------------------------------Compras-------------------------------------------------------------------- 
 -- listar
delimiter $$
 create procedure sp_listarCompra()
	begin
		select * from Compras;
        
    end $$
delimiter ;
 -- agregar
delimiter $$
 create procedure sp_agregarCompra(in proId int, in can int)
	begin 
    DECLARE nuevaCompraId INT;
		insert into Compras (fechaCompra) values
			(date(now()));
            
	set nuevaCompraId = LAST_INSERT_ID();
    call sp_agregarDetalleCompra(can, proId, nuevaCompraId);
    select fn_calcularTotalCompras(nuevaCompraId);
    update Productos 
		set cantidadStock = cantidadStock + can 
		where productoId = proId;
    end $$
delimiter ;
 -- buscar
delimiter $$
 create procedure sp_buscarCompra(in comId int)
	begin	
		select * from Compras 
			where compraId = comId;
    end $$
delimiter ;
 -- editar
delimiter $$
 create procedure sp_editarCompra(in fecCom date)
	begin 
		update Compras
			set 
				fechaCompra = fecCom
                
                where compraId = comId;
    end $$
delimiter ;
 -- eliminar 
delimiter $$
 create procedure sp_eliminarCompra(in comId int)
	begin 
		delete from Compras
        where compraId = comId;
    end $$
delimiter ;
-- --------------------------------------------------Categoria producto---------------------------------------------------------------
-- agregar
delimiter $$
create procedure sp_agregarCategoriaProducto(nomCat varchar(30),desCat varchar(100))
	begin 
		insert into CategoriaProductos(nombreCategoria,descripcionCategoria) values
			(nomCat,desCat);
    end$$
delimiter ;
-- listar
delimiter $$
create procedure sp_listarCategoriaProducto()
	begin
		select * from CategoriaProductos;
    end$$
delimiter ;
-- buscar
delimiter $$
create procedure sp_buscarCategoriaProducto(catProId int)
	begin
		select * from CategoriaProductos
        where catProId = categoriaProductosId;
    end$$
delimiter ;
-- eliminar
delimiter $$
create procedure sp_eliminarCategoriaProducto(catProId int)
	begin 
		delete from CategoriaProductos
        where catProId = categoriaProductosId;
    end $$
delimiter ;
-- editar
delimiter $$
create procedure sp_editarCategoriaProducto(catProId int,nomCat varchar(30),desCat varchar(100) )
	begin
		update CategoriaProductos CP set
		CP.nombreCategoria = nomCat,
        CP.descripcionCategoria = desCat
        where catProId = CP.categoriaProductosId;
    end$$
delimiter ;

-- ----------------------------------------------------- Distribuidores --------------------------------------------------------------
-- agregar
delimiter $$
create procedure sp_agregarDistribuidor(nomDis varchar(30), dirDis varchar(200), nitDis varchar(15), telDis varchar(15),web varchar(50))
	begin
		insert into Distribuidores(nombreDistribuidor,direccionDistribuidor,nitDistribuidor,telefonoDistribuidor,web) values
			(nomDis,dirDis,nitDis,telDis,web);
    end$$
delimiter ;
-- listar
delimiter $$
create procedure sp_listarDistribuidor()
	begin
		select * from Distribuidores;
    end$$
delimiter ;
-- buscar 
delimiter $$
create procedure sp_buscarDistribuidor(disId int)
	begin 
		select * from Distribuidores D 
        where disId = D.distribuidorId;
    end$$
delimiter ;
-- eliminar
delimiter $$
create procedure sp_eliminarDistribuidor(dirId int)
	begin
		delete from Distribuidores 
        where dirId = distribuidorId;
    end$$
delimiter ;
-- editar
delimiter $$
create procedure sp_editarDistribuidor(dirId int, nomDis varchar(30), dirDis varchar(200), nitDis varchar(15), telDis varchar(15),web varchar(50))
	begin
		update Distribuidores D set
        D.nombreDistribuidor = nomDis,
        D.direccionDistribuidor = dirDis,
        D.nitDistribuidor = nitDis,
        D.telefonoDistribuidor = telDis,
        D.web = web
        where dirId = D.distribuidorId;
    end$$
delimiter ;

--  -------------------------------------------------------Empleados-----------------------------------------------------------------
-- Agregar
delimiter $$
create procedure sp_agregarEmpleado(in nomEmp varchar(30),in apeEmp varchar(30), in sue decimal(10, 2), in hoEn time, in hoSa time, in carId int, in encarId int)
	begin
		insert into Empleados (nombreEmpleado, apellidoEmpleado, sueldo, horaEntrada, horaSalida, cargoId, encargadoId) values
			(nomEmp, apeEmp, sue, hoEn, hoSa, carid, encarId);
    end $$
delimiter ;
-- listar

-- buscar
delimiter $$
create procedure sp_buscarEmpleados(in empId int)
	begin
		select * from Empleados
			where empleadoId = empId;
    end $$
delimiter ;
-- eliminar
delimiter $$
create procedure sp_eliminarEmpleados(in empId int)
	begin
		delete 
			from Empleados
				where empleadoId = empId;
    end $$
delimiter ;
-- editar
delimiter $$
create procedure sp_editarEmpleados(in empId int, in nomEmp varchar(30),in apeEmp varchar(30), in sue decimal(10, 2), in hoEn time, in hoSa time, in carId int, in encarId int)
	begin
		update Empleados
			set 
            nombreEmpleado = nomEmp,
            apeEmp = apellidoEmpleado,
            sueldo = suel,
            horaEntrada = hoEn,
            horaSalida = hoSa,
            cargoId = carId,
            encargadoId = encarId
            where empleadoId = empId;
    end $$
delimiter ; 

 -- -------------------------------------------------------CLIENTES--------------------------------------------------------------------
-- Agregar
delimiter $$
create procedure sp_agregarCliente(in nom varchar(40),in ape varchar(40),in tel varchar(15),in dir varchar(150),in nit varchar(15))
	begin
		insert into Clientes (nombre, apellido, telefono, direccion, nit) values
			(nom, ape, tel, dir, nit);
    end $$
delimiter ;
-- listar
delimiter $$
create procedure sp_listarCliente()
	begin
		select * from Clientes;
    end $$
delimiter ;
-- buscar
delimiter $$
create procedure sp_buscarCliente(in cliId int)
	begin
		select * from Clientes
			where clienteId = cliId;
    end $$
delimiter ;
-- eliminar
delimiter $$
create procedure sp_eliminarCliente(in cliId int)
	begin
		delete from Clientes
        where clienteId = cliId;
    end $$
delimiter ;
-- editar
delimiter $$
create procedure sp_editarCliente(in cliId int, in nom varchar(40),in ape varchar(40),in tel varchar(15),in dir varchar(150),in nit varchar(15))
	begin
		update Clientes
			set 
            nombre = nom,
            apellido = ape,
            telefono = tel,
            direccion = dir, 
            nit = nit
            where clienteId = cliId;
    end $$
delimiter ;
--  ------------------------------------------------------Facturas--------------------------------------------------------------------
-- Agregar
delimiter $$
create procedure sp_agregarFactura(in fe date, in ho time, in tot decimal(10, 2), in cliId int, in empId int)
	begin
		insert into Facturas (fecha, hora, total, clienteId, empleadoId) values
			(fe, ho, tot, cliId, empId);
    end $$
delimiter ; 
-- listar
delimiter $$
create procedure sp_listarFacturas()
	begin
		select * from Facturas;
    end $$
delimiter ;
-- buscar
delimiter $$
create procedure sp_buscarFacturas(in facId int)
	begin
		select * from Facturas
			where facturaId = facId;
    end $$
delimiter ;
-- eliminar
delimiter $$
create procedure sp_eliminarFacturas(in facId int)
	begin
		delete 
			from Facturas
				where facturaId = facId;
    end $$
delimiter ;
-- editar
delimiter $$
create procedure sp_editarFacturas(in facId int, in fe date, in ho time, in tot decimal(10, 2), in cliId int, in empId int)
	begin
		update Facturas
			set 
            fecha = fe,
            hora = ho,
            total = tot,
            clienteId = cliId,
            empleadoId = empId
            where facturaId = facId;
    end $$
delimiter ;

-- ----------------------------------------------------ticket Soporte-----------------------------------------------------------------
-- agregar
DELIMITER $$
create procedure sp_AgregarTicketSoporte(in des varchar(250), in cliId int, in facId int)
begin
	insert into TicketSoporte(descripcionTicket,estatus,clienteId,facturaId) values
		(des,'recien Creado',cliId,facId);
end $$
DELIMITER ;
-- listar
DELIMITER $$
create procedure sp_ListarTicketSoporte()
begin
	select TS.ticketSoporteId, TS.descripcionTicket, TS.estatus, 
    CONCAT('Id: ', C.clienteId, ' ', C.nombre,' ', C.apellido) as 'cliente',
        TS.facturaId from TicketSoporte TS
    Join Clientes  C on TS.clienteId = C.clienteId;
end $$
DELIMITER ;
-- eliminar
DELIMITER $$
create procedure sp_EliminarTicketSoporte(in tikId int)
begin
	delete
		from TicketSoporte
			where ticketSoporteId = tikId;
end$$
DELIMITER ;
-- buscar 
DELIMITER $$
create procedure sp_BuscarTicketSoporte(in tikiId int)
begin 
	select
		TicketSoporte.ticketSoporteId,
        TicketSoporte.descripcionTicket,
        TicketSoporte.estatus,
        TicketSoporte.clienteId,
        TicketSoporte.facturaId
			from TicketSoporte
			where ticketSoporteId = tikId;
end $$
DELIMITER ;
-- editar 
DELIMITER $$
create procedure sp_EditarTicketSoporte(in tikId int,in des varchar(250), in est varchar(30), in cliId int, in facId int )
begin
	update TicketSoporte
		set 
			descripcionTicket = des,
            estatus = est,
            clienteId = cliId,
            facturaId = facId
				where ticketSoporteId = tikId;
end $$
DELIMITER ;
 -- ------------------------------------------------------Productos-------------------------------------------------------------------
  -- agregar
delimiter $$
 create procedure sp_agregarProducto(in nom varchar(50),in des varchar(100),in can int, in preU decimal(10,2),in preM decimal(10,2),in preC decimal(10,2), in ima longblob, in disId int, in catId int)
	begin
		insert into Productos(nombreProducto, descripcionProducto, cantidadStock, precioVentaUnitario, precioVentaMayor, precioCompra, imagenProducto, distribuidorId, categoriaProductosId ) values
			(nom, des, can, preU, preM, preC, ima, disId, catId);
	end $$
delimiter ;
 -- listar
delimiter $$
 create procedure sp_listarProducto()
	begin 
		select P.productoId, P.nombreProducto, P.descripcionProducto, P.cantidadStock, P.precioVentaUnitario, P.precioVentaMayor, P.precioCompra, P.imagenProducto,
        Concat('Id: ', D.distribuidorId, ' | ', D.nombreDistribuidor) as 'Distribuidor',
        Concat('Id: ', C.categoriaProductosId, ' | ', C.nombreCategoria)as 'CategoriaProducto' from Productos P
        Join Distribuidores D on P.distribuidorId = D.distribuidorId
        Join CategoriaProductos C on P.categoriaProductosId = C.categoriaProductosId;
    end $$
delimiter ;
 -- eliminar 
 delimiter $$
 create procedure sp_eliminarProducto(in proId int)
	begin
    
		delete from Productos
			where productoId = proId;
    end $$
 delimiter ;
 -- buscar
delimiter $$
 create procedure sp_buscarProducto(in proId int)
	begin 
		select * from Productos
        where productoId = proId;
    end $$
delimiter ;
 -- editar
 delimiter $$
 create procedure sp_editarProducto(in proId int, in nom varchar(50),in des varchar(100),in can int, in preU decimal(10,2),in preM decimal(10,2),in preC decimal(10,2), in ima longblob, in disId int, in catId int )
	begin
		update Productos	
			set 
            nombreProducto = nom,
            descripcionProducto = des,
            cantidadStock = can,
            precioVentaUnitario = preU,
            precioVentaMayor = preM,
            precioCompra = preC,
            imagenProducto = ima,
            distribuidorId = disId,
            categoriaProductosId = catId
            where productoId = proId;
    end $$
delimiter ;
-- ------------------------------------------------------Promociones-------------------------------------------------------------------
-- Agregar
delimiter $$
create procedure sp_agregarPromocion(in prePro decimal(10, 2), in descPro varchar(100), in feIni date, in feFina date, in proId int)
	begin
		insert into Promociones (precioPromocion, descripcionPromocion, fechaInicio, fechaFinalizacion, productoId) values 
			(prePro, descPro, feIni, feFina, proId);
    end $$
delimiter ;
-- listar
delimiter $$
create procedure sp_listarPromocion()
	begin
		select P.promocionId,P.precioPromocion, P.descripcionPromocion, P.fechaInicio, P.fechaFinalizacion,
				CONCAT('Id: ', PR.productoId, ' | ', PR.nombreProducto) as 'Producto' from Promociones P
        Join Productos PR on P.productoId = PR.productoId;
    end $$
delimiter ;
-- buscar
delimiter $$
create procedure sp_buscarPromocion(in promoId int)
	begin
		select * from Promociones
			where promocionId = promoId;
    end $$
delimiter ;
-- eliminar
delimiter $$
create procedure sp_eliminarPromocion(in promoId int)
	begin
		delete 
			from Promociones
				where promocionId = promoId;
    end $$
delimiter ;
-- editar
delimiter $$
create procedure sp_editarPromocion(in promoId int, in prePro decimal(10, 2), in descPro varchar(100), in feIni date, in feFina date, in proId int)
	begin
		update Promociones
			set 
            precioPromocion = prePro,
            descripcionPromocion = descPro,
            fechaInicio = feIni,
            fechaFinalizacion = feFina,
            productoId = proId
            where promocionId = promoId;
    end $$
delimiter ;
-- ----------------------------------------------------detalle factura-----------------------------------------------------------------
-- agregar
DELIMITER $$
create procedure sp_AgregarDetalleFactura(in factId int, in prodId int)
begin
	insert into DetalleFactura(facturaId, productoId) values
		(factId, prodId);
end $$
DELIMITER ;
-- listar
DELIMITER $$
create procedure sp_ListarDetalleFactura()
begin
	select 
		DetalleFactura.detalleFacturaId,
        DetalleFactura.facturaId,
        DetalleFactura.productoId
			from DetalleFactura;
end $$
DELIMITER ;
-- eliminar
DELIMITER $$
create procedure sp_EliminarDetalleFactura(in detId int)
begin
	delete
		from DetalleFactura
			where detalleFacturaId = detId;
end $$
DELIMITER ;
-- buscar
DELIMITER $$
create procedure sp_BuscarDetalleFactura(in detId int)
begin
	select 
		DetalleFactura.detalleFacturaId,
        DetalleFactura.facturaId,
        DetalleFactura.productoId
			from DetalleFactura
			where detalleFacturaId = detId;
end $$
DELIMITER ;
-- editar
DELIMITER $$
create procedure sp_EditarDetalleFactura(in detId int, in factId int, in prodId int)
begin
	update DetalleFactura
		set 
			facturaId = factId,
            productoId = prodId
            where detalleFacturaId = detId;
end $$
DELIMITER ;
 -- ----------------------------------------------------DetalleCompra------------------------------------------------------------------
 -- agregar
delimiter $$
 create procedure sp_agregarDetalleCompra(in canC int, in proId int, in comId int)
	begin 
		insert into DetalleCompra(cantidadCompra, productoId, compraID)values
			(canC, proId, comId);
    end $$
delimiter ;
 -- listar
delimiter $$
 create procedure sp_ListarDetalleCompra()
	begin 
		select DC.cantidadCompra, 
        C.compraId, C.fechaCompra, C.totalCompra,
        P.nombreProducto from DetalleCompra DC
        Join Compras C on DC.compraId = C.compraId
        Join Productos P on DC.productoId = p.productoId;
    end $$
delimiter ;
-- eliminar 
delimiter $$
create procedure sp_eliminarDetalleCompra(in detCId int)
	begin 
    delete from DetalleCompra 
			where detalleCompraId = detCId;
    end $$
delimiter ;
 -- buscar
delimiter $$
create procedure sp_buscarDetalleCompra(in detCId int)
	begin 
		select * from DetalleCompra
			where detalleCompraId = detCId;
    end $$
delimiter ;
-- editar
delimiter $$
create procedure sp_editarDetalleCompra(in detCId int, in canC int, in proId int,in comId int)
	begin 
		update DetalleCompra
			set 
				cantidadCompra = canC,
                productoId = proId,
                compraId = comId
                where detalleCompraId = detCId;
    end $$
delimiter ;
-- ----------------------------------------------------------------------------------------------
call sp_listarCargo();
call sp_agregarCargo('Gerente', 'Supervisar las compras');
call sp_editarCargo(1,'xd','xd');
call sp_buscarCargo(1);
-- ----------------------------------------------------------------------------------------------
call sp_listarCompra();

-- ----------------------------------------------------------------------------------------------
call sp_listarCategoriaProducto();
call sp_agregarcategoriaProducto('Hogar','Silla  de lujo 4x4');
call sp_editarCategoriaProducto(1, 'Hola', 'si');
call sp_buscarCategoriaProducto(1);
call sp_eliminarCategoriaProducto(2);
-- ----------------------------------------------------------------------------------------------
call sp_listarDistribuidor();
call sp_agregarDistribuidor('Las sillas', 'San Juan', '29384085-9', '2384-4875','LasSillas.com');
call sp_editarDistribuidor(2, 's', 'i', 'y', 'n', 'o');
call sp_buscarDistribuidor(1);
call sp_eliminarDistribuidor(2);
-- ----------------------------------------------------------------------------------------------
call sp_agregarEmpleado('Lisandro ','Jimenez',  5000.00, '08:12', '17:00' , 1,1);
-- ----------------------------------------------------------------------------------------------
call sp_listarCliente();
call sp_agregarCliente('Aldair', 'Araujo', '4578-8513', 'Mixco','18273946-9');
call sp_editarCliente(1,'2','2','2','2','2');
-- ----------------------------------------------------------------------------------------------
call sp_agregarFactura('2024-05-05', '14:51',null, 1, 1);
-- ----------------------------------------------------------------------------------------------
call sp_agregarTicketSoporte('Problemas con la red',1,1);
-- ----------------------------------------------------------------------------------------------
call sp_listarProducto();	
call sp_editarProducto(1, 's', 'i', 9.00, 5.00,5.77,4.66, null, 1, 1);
call sp_agregarProducto('Silla de madera', 'silla de madera con diseños', 80 ,  80.7, 70.00, 85.00 ,null ,  1 ,  1 );
-- ----------------------------------------------------------------------------------------------
call sp_listarPromocion();
call sp_agregarPromocion('50.00', 'Ahorra con la compra de La silla ',  '2024-04-22',  '2024-04-05', 1);
call sp_editarPromocion(1,  '30.5','d','2024-03-03', '2024-03-03',1);
-- ----------------------------------------------------------------------------------------------
call sp_listarDetalleCompra();
set global time_zone = '-6:00'; 


delimiter $$
create procedure sp_listarEmpleado()
	begin
		select E1.empleadoId, Concat(E1.nombreEmpleado, E1.apellidoEmpleado)as 'Empleado', E1.sueldo, E1.horaEntrada, E1.horaSalida,
        C.nombreCargo,
        concat(E2.nombreEmpleado, E2.apellidoEmpleado)as 'Encargado' from Empleados E1
        join Cargos C on C.cargoId = E1.cargoId
        left join Empleados E2 on E1.encargadoId = E2.empleadoId;
    end $$
delimiter ;



call sp_listarEmpleado();

call sp_listarDetalleCompra();
call sp_listarCompra();
-- aaaa