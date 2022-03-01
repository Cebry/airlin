package airlin.model;

import java.time.LocalDate;
import java.util.Objects;

public class Factura {
	private Long id;
	private LocalDate fecha;
	private Long idCliente;

	public Factura(Long id, LocalDate fecha, Long idCliente) {
		this.setId(id);
		this.setFecha(fecha);
		this.setIdCliente(idCliente);
	}

	public Factura(LocalDate fecha, Long idCliente) {
		this.setFecha(fecha);
		this.setIdCliente(idCliente);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	@Override
	public int hashCode() {
		return Objects.hash(fecha, id, idCliente);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Factura other = (Factura) obj;
		return Objects.equals(fecha, other.fecha) && Objects.equals(id, other.id)
				&& Objects.equals(idCliente, other.idCliente);
	}

	@Override
	public String toString() {
		return "Factura [id=" + id + ", fecha=" + fecha + ", idCliente=" + idCliente + "]";
	}

}
