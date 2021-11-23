package com.demo.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.constant.Constants;
import com.demo.customer.service.CustomerService;
import com.demo.customer.service.impl.CustomerServiceImpl;
import com.demo.request.CustomerRequest;
import com.demo.request.LoginRequest;
import com.demo.response.GetCustomerResponse;
import com.demo.response.SuccessResponse;
import com.demo.utils.PasswordEncryptionArgon2;
import com.demo.utils.ResponseHandler;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import javax.validation.Valid;

@Slf4j
@RestController
@Api(tags = "Customers APIs")
@RequestMapping("/accountserviceapi/v1/customers")
public class CustomerController {
	
    @Autowired
    private CustomerService customerService;
    
    @Value("${argon2.saltLength}")
    private int saltLength;
    
    @PostMapping
    @ApiOperation(value = "Customer registration API")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = Constants.BAD_REQUEST),
            @ApiResponse(code = 403, message = Constants.ACCESS_DENIED),
            @ApiResponse(code = 409, message = Constants.CUSTOMER_ALREADY_IN_USE),
            @ApiResponse(code = 500, message = Constants.INTERNAL_SERVER_ERROR)
    })
    public @ResponseBody ResponseEntity<Object> saveCustomer(@ApiParam("Customer Request Body")
                                                @RequestBody @Valid CustomerRequest customer) {
        log.debug("Start of CustomerController::saveCustomer method");
        return customerService.saveCustomer(customer);
    }
    
    @GetMapping
    @ApiOperation(value = "List of Customers API")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = Constants.BAD_REQUEST),
            @ApiResponse(code = 403, message = Constants.ACCESS_DENIED)})
    public ResponseEntity<Object> retrieveAll() {
        log.info("Start of CustomerController::retrieveAll method");
        return customerService.retrieveAll();
    }
    
    @GetMapping("/{email}")
    @ApiOperation(value = "Get Customer Detail API")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = Constants.BAD_REQUEST),
            @ApiResponse(code = 403, message = Constants.ACCESS_DENIED),
            @ApiResponse(code = 404, message = Constants.RESOURCE_NOT_FOUND),
            @ApiResponse(code = 500, message = Constants.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<Object> retrieveByEmail(@ApiParam("Customer Email") @PathVariable String email,
    										@RequestHeader(value="store") String storeName) {
        log.info("Start of CustomerController::retrieveByEmail method " + storeName);
        return customerService.retrieveByEmail(email, storeName);
    }
    
    
}
