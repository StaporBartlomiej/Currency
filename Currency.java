package application;

import java.math.BigDecimal;

import javafx.scene.control.TextField;

public interface Currency {
	public String findRateAndConvert(BigDecimal amount,String fromCurr, String toCurr);
	public String findRate(String fromCurr, String toCurr);
	public void forbidNonIntInput(TextField currencyBefore);
	
}
