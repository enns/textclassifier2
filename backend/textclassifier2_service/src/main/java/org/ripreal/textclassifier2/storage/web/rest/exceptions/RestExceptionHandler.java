package org.ripreal.textclassifier2.storage.web.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<WebControllerException> handleThereIsNoSuchCharacteristicException(ResourceNotFoundException e) {
        return new ResponseEntity<>(new WebControllerException(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IncorrectClassifierType.class)
    protected ResponseEntity<WebControllerException> handleIncorrectClassifierTypeException() {
        return new ResponseEntity<>(new WebControllerException("Illegal argument \"classifierType\" in options parameters"), HttpStatus.BAD_REQUEST);
    }

    private static class WebControllerException {
        private String message;

        @java.beans.ConstructorProperties({"message"})
        public WebControllerException(String message) {
            this.message = message;
        }

        public String getMessage() {return this.message;}

        public void setMessage(String message) {this.message = message; }

        public boolean equals(Object o) {
            if (o == this) return true;
            if (!(o instanceof WebControllerException)) return false;
            final WebControllerException other = (WebControllerException) o;
            if (!other.canEqual((Object) this)) return false;
            final Object this$message = this.message;
            final Object other$message = other.message;
            if (this$message == null ? other$message != null : !this$message.equals(other$message)) return false;
            return true;
        }

        public int hashCode() {
            final int PRIME = 59;
            int result = 1;
            final Object $message = this.message;
            result = result * PRIME + ($message == null ? 43 : $message.hashCode());
            return result;
        }

        protected boolean canEqual(Object other) {return other instanceof WebControllerException;}

        public String toString() {return "RestExceptionHandler.WebControllerException(message=" + this.message + ")";}
    }
}
