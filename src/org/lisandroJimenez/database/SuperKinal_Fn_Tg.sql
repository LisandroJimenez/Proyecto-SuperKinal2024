use superkinalin5cvdb;

-- --------------------------------------------FACTURA -------------------------------------------
delimiter $$
create function fn_calcularTotalFacturas (factId int) returns decimal(10,2) deterministic
begin
 
	declare total decimal(10,2) default 0.0;
    declare precio decimal(10,2);
    declare i int default 1;
    declare curFacturaId, curProductoId int;
    declare curPromPrecio decimal(10,2);

    declare cursorDetalleFactura cursor for 
    select DF.facturaId , DF.productoId from DetalleFactura DF;
 
    open cursorDetalleFactura;
    totalLoop :loop
 
    fetch cursorDetalleFactura into curFacturaId, curProductoId;
    select PR.precioPromocion into curPromPrecio
        from Promociones PR
        where PR.productoId = curProductoId
        and NOW() between PR.fechaInicio and PR.fechaFinalizacion
        order by PR.fechaInicio desc
        Limit 1;
 
	if factId = curFacturaId then
			if curPromPrecio is not null then
				set precio = curPromPrecio;
            else 
				set precio = (select P.precioVentaUnitario from Productos P where P.productoId = curProductoId);
			end if;
        set total = total + precio;
    end if;
 
    if i = (select count(*) from detalleFactura) then
		leave totalLoop;
	end if;
 
    set i = i + 1;
    end loop totalLoop;
 
    call sp_asignarTotalFactura(total, factId);
    return total;
end $$
delimiter ;


delimiter $$
create trigger tg_totalFactura
after insert on DetalleFactura
for each row
begin
	declare total decimal(10,2);
    set total = fn_calcularTotalFacturas(new.facturaId);
end $$
delimiter ;
-- --------------------------------------Compras----------------------------------------------------
delimiter $$

create function fn_calcularTotalCompras(comId int) returns decimal(10,2) deterministic
begin
    declare total decimal(10,2) default 0.0;
    declare precio decimal(10,2);
    declare i int default 1;
    declare curCan, curProId, curComId int;

    declare cursorDetalleCompra cursor for
        Select cantidadCompra, productoId, compraId from DetalleCompra where compraId = comId;

    open cursorDetalleCompra;

    totalLoop: loop
        fetch cursorDetalleCompra into curCan, curProId, curComId;
        if comId = curComId then
            set precio = (select precioCompra from Productos where productoId = curProId);
            set total = total + (precio * curCan);  
        end if;
        if i = (select count(*) from DetalleCompra where compraId = comId) then
            leave totalLoop;
        end if;
        set i = i + 1;
        
    end loop totalLoop;

    close cursorDetalleCompra;

    call sp_asignarTotalCompra(total, comId);
    

    return total;
end $$

delimiter ;

delimiter $$
create trigger tg_totalCompra
after insert on DetalleCompra
for each row
begin
	declare total decimal(10,2);
    set total = fn_calcularTotalCompras(new.compraId);
end $$
delimiter ;


