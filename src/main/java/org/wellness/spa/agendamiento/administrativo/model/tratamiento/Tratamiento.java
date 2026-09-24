    package main.java.org.wellness.spa.agendamiento.administrativo.model.tratamiento;

    import java.math.BigDecimal;

    public class Tratamiento {

        private String idTratamiento;
        private String nombreTratamiento;
        private BigDecimal costoTratamiento;
        private String descripcionTratamiento;
        private int duracionTratamiento;

        public Tratamiento() {
        }

        public Tratamiento(String idTratamiento, String nombreTratamiento,
                BigDecimal costoTratamiento, String descripcionTratamiento,
                int duracionTratamiento) {

            this.idTratamiento = idTratamiento;
            this.nombreTratamiento = nombreTratamiento;
            this.costoTratamiento = costoTratamiento;
            this.descripcionTratamiento = descripcionTratamiento;
            this.duracionTratamiento = duracionTratamiento;
        }

        public String getIdTratamiento() {
            return idTratamiento;
        }

        public void setIdTratamiento(String idTratamiento) {
            this.idTratamiento = idTratamiento;
        }

        public String getNombreTratamiento() {
            return nombreTratamiento;
        }

        public void setNombreTratamiento(String nombreTratamiento) {
            this.nombreTratamiento = nombreTratamiento;
        }

        public BigDecimal getCostoTratamiento() {
            return costoTratamiento;
        }

        public void setCostoTratamiento(BigDecimal costoTratamiento) {
            this.costoTratamiento = costoTratamiento;
        }

        public String getDescripcionTratamiento() {
            return descripcionTratamiento;
        }

        public void setDescripcionTratamiento(String descripcionTratamiento) {
            this.descripcionTratamiento = descripcionTratamiento;
        }

        public int getDuracionTratamiento() {
            return duracionTratamiento;
        }

        public void setDuracionTratamiento(int duracionTratamiento) {
            this.duracionTratamiento = duracionTratamiento;
        }
    }