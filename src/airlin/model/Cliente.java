package airlin.model;

import java.util.Objects;

public class Cliente {

	private static final int MAX_APELLIDOS_LENGTH = 20;
	private static final int MAX_NOMBRE_LENGTH = 20;

	private Long id;
	private String nombre;
	private String apellidos;
	private String dni;
	private String telefono;

	public Cliente(Long id, String nombre, String apellidos, String dni, String telefono)
			throws ClienteIncorrectoException {
		this.setId(id);
		this.setNombre(nombre);
		this.setApellidos(apellidos);
		this.setDni(dni);
		this.setTelefono(telefono);
	}

	public Cliente(String nombre, String apellidos, String dni, String telefono) throws ClienteIncorrectoException {
		this.setNombre(nombre);
		this.setApellidos(apellidos);
		this.setDni(dni);
		this.setTelefono(telefono);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) throws ClienteIncorrectoException {
		if (nombre.length() < MAX_NOMBRE_LENGTH) {
			this.nombre = nombre;
		} else {
			throw new ClienteIncorrectoException("La longitud del nombre no debe ser mayor que ." + MAX_NOMBRE_LENGTH);
		}
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) throws ClienteIncorrectoException {
		if (apellidos.length() < MAX_APELLIDOS_LENGTH) {
			this.apellidos = apellidos;
		} else {
			throw new ClienteIncorrectoException(
					"La longitud de los apellidos no debe ser mayor que ." + MAX_APELLIDOS_LENGTH);
		}
	}

	public String getDni() {
		return this.dni;
	}

	public void setDni(String dni) throws ClienteIncorrectoException {
		if (esDNIValido(dni)) {
			this.dni = dni;
		}
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) throws ClienteIncorrectoException {
		if (esTelefonoValido(telefono)) {
			this.telefono = telefono;
		}
	}

	private boolean esDNIValido(String dni) throws ClienteIncorrectoException {
		String letraMayuscula = "";

		if (dni.length() != 9) {
			throw new ClienteIncorrectoException("El dni debe tener 9 caracteres.");
		}

		for (int i = 0; i < 8; i++) {
			if (!Character.isDigit(dni.charAt(i))) {
				throw new ClienteIncorrectoException("Los caracteres del teléfono deben ser dígitos.");
			}
		}

		if (Character.isLetter(dni.charAt(8)) == false) {
			throw new ClienteIncorrectoException("El noveno carácter del dni debe ser una letra.");
		}

		letraMayuscula = (dni.substring(8)).toUpperCase();

		int resto = 0;
		String[] letrasDNI = { "T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S", "Q", "V",
				"H", "L", "C", "K", "E" };

		resto = Integer.parseInt(dni.substring(0, 8)) % 23;

		if (!letrasDNI[resto].equals(letraMayuscula)) {
			throw new ClienteIncorrectoException("La letra no es correcta.");
		}

		return true;

	}

	private boolean esTelefonoValido(String telefono) throws ClienteIncorrectoException {

		if (telefono.length() != 9) {
			throw new ClienteIncorrectoException("El teléfono debe tener 9 dígitos.");
		}

		for (int i = 0; i < 8; i++) {
			if (!Character.isDigit(telefono.charAt(i))) {
				throw new ClienteIncorrectoException("Los caracteres del teléfono deben ser dígitos.");
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(apellidos, dni, id, nombre, telefono);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(apellidos, other.apellidos) && Objects.equals(dni, other.dni)
				&& Objects.equals(id, other.id) && Objects.equals(nombre, other.nombre)
				&& Objects.equals(telefono, other.telefono);
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", dni=" + dni + ", telefono="
				+ telefono + "]";
	}

}
