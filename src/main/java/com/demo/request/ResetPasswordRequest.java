package com.demo.request;


import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordRequest {

	@NotEmpty
	private String email;
	
	@NotEmpty
	private String rpToken;
	
	@NotEmpty
	private String password;
}
