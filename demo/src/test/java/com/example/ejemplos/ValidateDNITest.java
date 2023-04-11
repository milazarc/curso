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
import com.example.domains.core.validations.CadenasValidator;

public class ValidateDNITest {
	
	@BeforeEach
	void setUp() {
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
					"'37216026w', 'DNI Valido con minuscula'",
					"'14670704Q', 'DNI Valido'",
					"'01053998T', 'DNI Valido'",
					"'49065753Z', 'DNI Valido'",
					"'00000000T', 'DNI Valido'",
					"'00000013J', 'DNI Valido'",
					})
			public void validateDniMayuscula(String value) {
				
				assertTrue(CadenasValidator.isNIF(value));
			}
			
		}
		
		@Nested

		@DisplayName("ValidateDniMayuscula Ko")
		class KO{

			
			@ParameterizedTest(name = "DNI = {0} Test={1}")
			@CsvSource(value = {
					"'123456711', 'Todo numeros, lenght bueno'",
					"'12345678X', 'DNI inventado, lenght bueno',",
					"'12345671', 'Todo numeros, lenght malo'",
					"'D2345678D', 'Letra principio numeros, lenght bueno'",
					"'', 'Campo DNI Vacio'",
					})
			public void validateDniMayuscula(String value) {
				
					assertTrue(CadenasValidator.isNotNIF(value));
			}
			
		}
	}
	


}


