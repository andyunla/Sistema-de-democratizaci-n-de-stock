package com.sistema.application.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.sistema.application.converters.EmpleadoConverter;
import com.sistema.application.dto.EmpleadoDto;
import com.sistema.application.dto.UserDto;
import com.sistema.application.helpers.ViewRouteHelper;
import com.sistema.application.models.EmpleadoModel;
import com.sistema.application.services.IEmpleadoService;
import com.sistema.application.services.IFacturaService;
import com.sistema.application.services.ILocalService;
import com.sistema.application.services.implementations.UserService;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/sueldos")
public class SueldoController {

	//Services
	@Autowired
    @Qualifier("userService")
    private UserService userService;
	@Autowired
    @Qualifier("empleadoService")
    private IEmpleadoService empleadoService;
	@Autowired
    @Qualifier("facturaService")
    private IFacturaService facturaService;
	@Autowired
    @Qualifier("localService")
    private ILocalService localService;
	
	//Converters
	@Autowired
    @Qualifier("empleadoConverter")
    private EmpleadoConverter empleadoConverter;

	
	
	@GetMapping("")
	public ModelAndView sueldo(Model modelo) {
		
		ModelAndView modelAndView = new ModelAndView(ViewRouteHelper.SUELDO_ROOT);		
		//Chequea que sea un gerente
		UserDto userDto = userService.getCurrentUser();
		modelAndView.addObject("currentUser", userDto);
		List<EmpleadoDto> vendedores = localService.calcularSueldos(userDto.getLocal().getIdLocal(), fecha);
		//Mando atributos al modelo
		modelAndView.addObject("empleados", vendedores );		
		//Muestro en pantalla
		return modelAndView;
	}
	
	
}
