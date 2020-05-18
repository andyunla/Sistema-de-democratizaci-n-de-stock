package com.sistema.application.models;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.sistema.application.converters.ProductoConverter;
import com.sistema.application.entities.Item;
import com.sistema.application.entities.Lote;
import com.sistema.application.entities.Producto;
import com.sistema.application.services.IChangoService;
import com.sistema.application.services.IFacturaService;
import com.sistema.application.services.ILoteService;
import com.sistema.application.services.IPedidoStockService;
import com.sistema.application.services.IProductoService;

public class LocalModel {

	// Atributos
	private long idLocal;
	private String nombreLocal;
	private double latitud;
	private double longitud;
	private String direccion;
	private int telefono;
	private EmpleadoModel gerente;
	private Set<LoteModel> listaLotes;
	private Set<EmpleadoModel> listaEmpleados;
	private Set<ChangoModel> listaChangos;
	private Set<FacturaModel> listaFacturas;
	//Services
	@Autowired
	@Qualifier("iLoteService")
	ILoteService iLoteService;
	@Autowired
	@Qualifier("iChangoService")
	IChangoService iChangoService;
	@Autowired
	@Qualifier("iFacturaService")
	IFacturaService iFacturaService;
	@Autowired
	@Qualifier("iPedidoStockService")
	IPedidoStockService iPedidoStockService;
	@Autowired
	@Qualifier("iProductoService")
	IProductoService iProductoService;
	//Converters
	@Autowired
	@Qualifier("productoConverter")
	ProductoConverter productoConverter;
	// Constructores
	public LocalModel() {
	}

	public LocalModel(long idLocal, String nombreLocal, double latitud, double longitud, String direccion,
			int telefono, EmpleadoModel gerente, Set<LoteModel> listaLotes, Set<EmpleadoModel> listaEmpleados,
			Set<ChangoModel> listaChangos, Set<FacturaModel> listaFacturas) {
		super();
		this.idLocal = idLocal;
		this.nombreLocal = nombreLocal;
		this.latitud = latitud;
		this.longitud = longitud;
		this.direccion = direccion;
		this.telefono = telefono;
		this.gerente = gerente;
		this.listaLotes = listaLotes;
		this.listaEmpleados = listaEmpleados;
		this.listaChangos = listaChangos;
		this.listaFacturas = listaFacturas;
	}

	//constructor para los converter
	public LocalModel(long idLocal, String nombreLocal, double latitud, double longitud, String direccion, int telefono,
			EmpleadoModel gerente) {
		super();
		this.idLocal = idLocal;
		this.nombreLocal = nombreLocal;
		this.latitud = latitud;
		this.longitud = longitud;
		this.direccion = direccion;
		this.telefono = telefono;
		this.gerente = gerente;
	}

	public LocalModel(long idLocal, String nombreLocal, double latitud, double longitud, String direccion, int telefono) {
		super();
		this.idLocal = idLocal;
		this.nombreLocal = nombreLocal;
		this.latitud = latitud;
		this.longitud = longitud;
		this.direccion = direccion;
		this.telefono = telefono;
		this.gerente = null;
	}

	//Getters y Setters
	public long getIdLocal() {
		return idLocal;
	}

	public void setIdLocal(long idLocal) {
		this.idLocal = idLocal;
	}

	public String getNombreLocal() {
		return nombreLocal;
	}

	public void setNombreLocal(String nombreLocal) {
		this.nombreLocal = nombreLocal;
	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public EmpleadoModel getGerente() {
		return gerente;
	}

	public void setGerente(EmpleadoModel gerente) {
		this.gerente = gerente;
	}

	public Set<LoteModel> getListaLotes() {
		return listaLotes;
	}

	public void setListaLotes(Set<LoteModel> listaLotes) {
		this.listaLotes = listaLotes;
	}

	public Set<EmpleadoModel> getListaEmpleados() {
		return listaEmpleados;
	}

	public void setListaEmpleados(Set<EmpleadoModel> listaEmpleados) {
		this.listaEmpleados = listaEmpleados;
	}

	public Set<ChangoModel> getListaChangos() {
		return listaChangos;
	}

	public void setListaChangos(Set<ChangoModel> listaChangos) {
		this.listaChangos = listaChangos;
	}

	public Set<FacturaModel> getListaFacturas() {
		return listaFacturas;
	}

	public void setListaFacturas(Set<FacturaModel> listaFacturas) {
		this.listaFacturas = listaFacturas;
	}

	// toString
	@Override
	public String toString() {
		return "LocalModel [idLocal=" + idLocal + ", nombreLocal=" + nombreLocal + ", latitud=" + latitud
				+ ", longitud=" + longitud + ", direccion=" + direccion + ", telefono=" + telefono
				+ ", gerente=" + gerente + ", listaLotes=" + listaLotes + ", listaChangos=" + listaChangos + ", listaFacturas=" + listaFacturas + "]";
	}

	public double calcularDistancia(LocalModel local) {
		double radioTierra = 6371; // en kilómetros
		double dLat = Math.toRadians(local.latitud - this.latitud);
		double dLng = Math.toRadians(local.longitud - this.longitud);
		double sindLat = Math.sin(dLat / 2);
		double sindLng = Math.sin(dLng / 2);
		double va1 = Math.pow(sindLat, 2)
				+ Math.pow(sindLng, 2) * Math.cos(Math.toRadians(this.latitud)) 
				* Math.cos(Math.toRadians(local.latitud));
		double va2 = 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1));
		return radioTierra * va2;
	}
	/****************************************************************************************************/
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//5) ALTA, Y CONSUMO DE STOCK/////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	/****************************************************************************************************/
			
	public LoteModel crearLote (int cantidadInicial ,ProductoModel producto ) {
		return iLoteService.insertOrUpdate(new LoteModel( cantidadInicial, cantidadInicial, LocalDate.now(), producto, this ));
	}
	public boolean restarLote(ProductoModel producto, int cantidad) {
		int i =0;
		//traigo la lista de lotes del producto en el local
		Set<LoteModel> lista = iLoteService.findByLoteProductoActivo(producto.getIdProducto(), this.idLocal);
		Iterator<LoteModel> itr = lista.iterator();
		LoteModel lo = null;// creo un LoteModel objeto vacio
		while(cantidad>0 && itr.hasNext()) {//mientras haya cantidad que restar
			lo = itr.next(); 
			if(lo.getCantidadActual() - cantidad <=0 ) {// si la cantidad actual queda en 0 doy de baja el lote
				cantidad = cantidad - lo.getCantidadActual(); // actualizo la cantidad a restar
				lo.setCantidadActual(0);
				//lo.setActivo(false); //esta validación la agregué dentor del set cantidadActual
				iLoteService.insertOrUpdate(lo);// actualizo el lote en la base de datos
				}
			else if (lo.getCantidadActual() - cantidad >=1) {
				lo.setCantidadActual(lo.getCantidadActual()- cantidad);
				iLoteService.insertOrUpdate(lo);// actualizo el lote en la base de datos
				cantidad =0;// seteo en cero para salir del bucle, ya no hay mas que restar
			}			
		i++;	
		}		
		return true;
	}
	public boolean sumarLote(ProductoModel producto, int cantidad) {
		int i =0;
		//traigo la lista de lotes del producto en el local
		Set<LoteModel> lista = iLoteService.findByLoteProductoBaja(producto.getIdProducto(), this.idLocal);
		Iterator<LoteModel> itr = lista.iterator();
		LoteModel lo = null;// creo un LoteModel objeto vacio
		while(cantidad>0 && itr.hasNext()) {//mientras haya cantidad que restar
			lo = itr.next(); 
			if(lo.getCantidadActual() + cantidad >= lo.getCantidadInicial() ) {// si supero la cantidad del lote con la suma, paso sumar en el siguiente lote
				cantidad = cantidad - lo.getCantidadInicial(); // actualizo la cantidad a restar
				lo.setCantidadActual(lo.getCantidadInicial());
				//lo.setActivo(true);//esta validación la agregué dentor del set cantidadActual
				iLoteService.insertOrUpdate(lo);// actualizo el lote en la base de datos
				}
			else if (lo.getCantidadActual() + cantidad < lo.getCantidadInicial()) {
				lo.setCantidadActual(lo.getCantidadActual()+ cantidad);
				iLoteService.insertOrUpdate(lo);// actualizo el lote en la base de datos
				cantidad =0;// seteo en cero para salir del bucle, ya no hay mas que sumar
			}			
		i++;	
		}		
		return true;
	}
	/****************************************************************************************************/
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//7) VALIDAR STOCK Y POSIBILIDADES DE LOCALES A SOLICITAR STOCK///////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	/****************************************************************************************************/
	public int calcularStockLocal(ProductoModel producto) {		
		int cantidadStock = 0;		
		Set<LoteModel> lista = iLoteService.findByLoteProductoActivo(producto.getIdProducto(), this.idLocal);
		for(LoteModel lo : lista) {
			cantidadStock = cantidadStock + lo.getCantidadActual();			
		}		
		return cantidadStock;
	}	
	public boolean validarStock(ProductoModel producto, int cantidad) {
		return calcularStockLocal(producto)>= cantidad;		
	}
	/****************************************************************************************************/
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//8) GENERACION DE SOLICITUD DE USO DE STOCK DE OTRO LOCAL////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	/****************************************************************************************************/
	public boolean crearPedidoStock(ProductoModel producto, int cantidad, EmpleadoModel solicitante){	
		iPedidoStockService.insertOrUpdate(new PedidoStockModel(producto, cantidad, solicitante));		
		return true;
	}
	/****************************************************************************************************/
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//9) ACEPTAR O RECHAZAR SOLICITUD DE STOCK////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	/****************************************************************************************************/
	public boolean modificarPedidoStock(long idPedidoStock, boolean aceptado, EmpleadoModel oferente) throws Exception{		
		PedidoStockModel pedidoStockModel = iPedidoStockService.findByIdPedidoStock(idPedidoStock); //traiugo el Pedido de la base de datos
		pedidoStockModel.setEmpleadoOferente(oferente); //seteo el oferente
		pedidoStockModel.setAceptado(aceptado); //seteo el estado del pedido
		
		if (pedidoStockModel.isAceptado()) {// si es un pedidoStock aceptado
			iPedidoStockService.insertOrUpdate(pedidoStockModel); // lo actualizo en la base de datos
			pedidoStockModel.getEmpleadoOferente().getLocal().restarLote(pedidoStockModel.getProducto(), pedidoStockModel.getCantidad());			
		}
		else { 
			iPedidoStockService.remove(idPedidoStock);// si no lo elimino
		}
		return true;
	}
	/****************************************************************************************************/
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//12) GENERAR FACTURA/////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	/****************************************************************************************************/
		
	public boolean crearFactura(ClienteModel cliente, ChangoModel chango,LocalDate fecha, double costeTotal, EmpleadoModel empleado) {		
		iFacturaService.insertOrUpdate(new FacturaModel(cliente, chango, fecha, costeTotal, empleado, this)); //creo la factura
		if(chango.getPedidoStock() == null) restarChango(chango); // Si no hay pedidoStock, resto todos los productos del chango a este local
		return true;
	}
	public void restarChango(ChangoModel chango) {
		for ( ItemModel it : chango.getListaItems()) {
			restarLote(it.getProductoModel() , it.getCantidad());
		}
	}
//	public List<Factura> traerFactura (LocalDate fecha1, LocalDate fecha2) {
//		List<Factura> list = new ArrayList<Factura>();		
//			int i= 0;
//			while (i<listaFacturas.size() && listaFacturas.get(i).getFechaFactura().isBefore(fecha2) ) {
//				if(listaFacturas.get(i).getFechaFactura().isEqual(fecha1) || listaFacturas.get(i).getFechaFactura().isAfter(fecha1) )list.add(listaFacturas.get(i));
//				i++;
//			}
//		return list;
//	}

}
