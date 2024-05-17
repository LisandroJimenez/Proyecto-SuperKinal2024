use superkinalin5cvdb;

-- --------------------------------------------FACTURA -------------------------------------------
delimiter $$
create function fn_calcularTotalFactura(facId int) returns decimal(10,2) deterministic
begin
	declare total decimal(10,2) default 0.0;
    declare precio decimal(10,2);
    declare i int default 1;
    declare curFacId, curProId int;
    
    declare cursorDetalleFactura cursor for
		Select DF.facturaId, DF.productoId from DetalleFactura DF;
        
	open cursorDetalleFactura;
    
    totalLoop :loop
    fetch cursorDetalleFactura into curFacId, curProId;
	if facId = curFacId then
		set precio = (select P.precio from Productos P where P.productoId = curProId);
        set total = total + precio;
    end if;
    if i = (select count(*) from detalleFactura) then
		leave totalLoop;
	end if;
    set i = i + 1;
    end loop totalLoop;
    call sp_asignarTotalFactura(total, facId);
    return total;
end $$
delimiter ;


delimiter $$
create trigger tg_totalFactura
after insert on DetalleFactura
for each row
begin
	declare total decimal(10,2);
    set total = fn_calcularTotal(new.facturaId);
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






select * from DetalleCompra;

call sp_agregarCompra(1,2);
select * from Compras;
select * from Productos;
select fn_calcularTotalCompras(2);
call sp_ListarDetalleCompra()