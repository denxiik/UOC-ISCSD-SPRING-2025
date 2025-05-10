package edu.uoc.epcsd.user.domain.service;

public class UserNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

	public UserNotFoundException(Long idUser) {
        super("User with idUser '" + idUser + "' not found");
    }
}
