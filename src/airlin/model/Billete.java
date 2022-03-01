package airlin.model;

import java.time.LocalDate;
import java.util.Objects;

public class Billete {

	private Long id;
	private Long plaza;
	private Float precio;
	private LocalDate fecha;
	private Long idFactura;

	public Billete(Long id, Long plaza, Float precio, LocalDate fecha, Long idFactura)
			throws BilleteIncorrectoException {
		setId(id);
		setPlaza(plaza);
		setPrecio(precio);
		setFecha(fecha);
		setIdFactura(idFactura);
	};

	public Billete(Long plaza, Float precio, LocalDate fecha, Long idFactura) throws BilleteIncorrectoException {
		setPrecio(precio);
		setPlaza(plaza);
		setFecha(fecha);
		setIdFactura(idFactura);
	};

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPlaza() {
		return plaza;
	}

	public void setPlaza(Long plaza) throws BilleteIncorrectoException {
		if (plaza > (long) 0) {
			this.plaza = plaza;
		} else {
			throw new BilleteIncorrectoException("La plaza debe ser mayor de 0.");
		}
	}

	public Float getPrecio() {
		return precio;
	}

	public void setPrecio(Float precio) throws BilleteIncorrectoException {
		if (precio > 0) {
			this.precio = precio;
		} else {
			throw new BilleteIncorrectoException("El precio debe ser mayor que 0.");
		}
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public Long getIdFactura() {
		return idFactura;
	}

	public void setIdFactura(Long idFactura) {
		this.idFactura = idFactura;
	}

	@Override
	public int hashCode() {
		return Objects.hash(fecha, id, idFactura, plaza, precio);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Billete other = (Billete) obj;
		return Objects.equals(fecha, other.fecha) && Objects.equals(id, other.id)
				&& Objects.equals(idFactura, other.idFactura) && plaza == other.plaza
				&& Float.floatToRawIntBits(precio) == Float.floatToRawIntBits(other.precio);
	}

	@Override
	public String toString() {
		return "Billete [id=" + id + ", plaza=" + plaza + ", precio=" + precio + ", fecha=" + fecha + ", idFactura="
				+ idFactura + "]";
	}

}
