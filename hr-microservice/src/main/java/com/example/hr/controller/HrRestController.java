package com.example.hr.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import com.example.hr.dto.error.ErrorResponse;
import com.example.hr.dto.request.HireEmployeeRequest;
import com.example.hr.dto.response.EmployeeResponse;
import com.example.hr.dto.response.FireEmployeeResponse;
import com.example.hr.dto.response.HireEmployeeResponse;
import com.example.hr.dto.response.PhotoResponse;
import com.example.hr.service.HrService;
import com.example.validation.TcKimlikNo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestScope
@RequestMapping("/employees")
@CrossOrigin
@Validated
@OpenAPIDefinition(
		info= @Info(title="HRServices REST API",contact=@Contact(name="Binnur Kurt",email="binnur.kurt@gmail.com",url="https://www.deepcloudlabs.com"))
)
public class HrRestController {
	private final HrService hrService;

	public HrRestController(HrService hrService) {
		this.hrService = hrService;
	}

	@Operation(summary="Retrieves employee information")
	@ApiResponses({
	   @ApiResponse(responseCode= "200", description="Employee Information",content= {@Content(mediaType = "application/json",schema = @Schema(implementation = EmployeeResponse.class))}),
	   @ApiResponse(responseCode= "400", description="Invalid identity no",content= {@Content(mediaType = "application/json",schema = @Schema(implementation = ErrorResponse.class))})
	})
	@GetMapping("/{identity}")
	public EmployeeResponse findEmployeeById(@PathVariable @TcKimlikNo String identity) {
		return hrService.findById(identity);
	}
	
	@GetMapping("/{identity}/photo")
	public PhotoResponse getEmployeePhotoById(@PathVariable @TcKimlikNo String identity) {
		return hrService.getEmployeePhoto(identity);
	}	

	@PostMapping
	public HireEmployeeResponse hireEmployee(@RequestBody @Validated HireEmployeeRequest request) {
		return hrService.hireEmployee(request);
	}

	@DeleteMapping("/{identity}")
	public FireEmployeeResponse fireEmployeeById(@PathVariable @TcKimlikNo String identity) {
		return hrService.fireEmployee(identity);

	}
}
