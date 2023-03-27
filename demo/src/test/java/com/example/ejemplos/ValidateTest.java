package com.example.ejemplos;


import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.example.core.test.SpaceCamelCase;

public class ValidateTest {

	Validate validate;
	
	@BeforeEach
	void setUp() {
		validate = new Validate();
	}
	
	@Nested
	@DisplayName("Pruebas del validadors")
	@DisplayNameGeneration(SpaceCamelCase.class)
	class ValidateDniMayuscula{
		
		@Nested
		@DisplayName("ValidateDniMayuscula Ok")
		class OK{
			@ParameterizedTest(name = "DNI = {0} Test={1}")
			@CsvSource(value = {
					"'37216026W', 'DNI Valido'",
					"'14670704Q', 'DNI Valido'",
					"'01053998T', 'DNI Valido'",
					"'49065753Z', 'DNI Valido'",
					"'00000000T', 'DNI Valido'",
					"'00000013J', 'DNI Valido'",
					})
			public void validateDniMayuscula(String value) {
				
				assertTrue(validate.validaDNI(value));
			}
			
		}
		
		@Nested

		@DisplayName("ValidateDniMayuscula Ko")
		class KO{

			
			@ParameterizedTest(name = "DNI = {0} Test={1}")
			@CsvSource(value = {
					"'123456711', 'Todo numeros, lenght bueno'",
					"'12345678X', 'DNI inventado, lenght bueno',",
					"'87654321A', 'DNI inventado, lenght bueno',",
					"'1234567D', 'Un numero menos'",
					"'12345671', 'Todo numeros, lenght malo'",
					"'D2345678D', 'Letra principio numeros, lenght bueno'",
					"'ABCDEFGHI', 'Todo letras, lenght bueno'",
					"'', 'Campo DNI Vacio'",
					})
			public void validateDniMayuscula(String value) {
				
					assertTrue(!validate.validaDNI(value));
			}
			
		}
	}
	


}


