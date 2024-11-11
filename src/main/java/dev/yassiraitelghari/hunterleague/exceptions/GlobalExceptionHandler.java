package dev.yassiraitelghari.hunterleague.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.converter.HttpMessageNotReadableException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        String message = "Invalid input: ";

        // Check if the exception message contains information about enum parsing
        if (ex.getMostSpecificCause().getMessage().contains("Cannot deserialize value of type")) {
            String enumPart = ex.getMostSpecificCause().getMessage();
            // Extracting the relevant part of the error message
            if (enumPart.contains("Role")) {
                String invalidValue = enumPart.split("from String")[1].split(":")[0].trim().replace("\"", "");
                message += String.format("The value '%s' is not a valid role. Accepted values are: [JURY, ADMIN, MEMBER]", invalidValue);
            } else if(enumPart.contains("SpeciesType")){
                String invalidValue = enumPart.split("from String")[1].split(":")[0].trim().replace("\"", "");
                message += String.format("The value '%s' is not a valid Specie Type. Accepted values are: [SEA , BIG_GAME , BIRD]", invalidValue);
            } else if(enumPart.contains("Difficulty")){
                String invalidValue = enumPart.split("from String")[1].split(":")[0].trim().replace("\"", "");
                message += String.format("The value '%s' is not a valid Specie Type. Accepted values are: [COMMON, RARE, EPIC, LEGENDARY]", invalidValue);
            }else {
                message += ex.getMostSpecificCause().getMessage();
            }
        } else {
            // Generic message for other parsing errors
            message += ex.getMostSpecificCause().getMessage();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
}
