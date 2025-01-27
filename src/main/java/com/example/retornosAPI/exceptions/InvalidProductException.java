package com.example.retornosAPI.exceptions;

    public class InvalidProductException extends RuntimeException {
        public InvalidProductException(String message) {
            super(message);
        }
}
