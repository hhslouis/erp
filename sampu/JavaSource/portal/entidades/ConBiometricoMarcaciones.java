/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package portal.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Jimes
 */
@Entity
@Table(name = "con_biometrico_marcaciones", catalog = "sampu", schema = "public")
@NamedQueries({
    @NamedQuery(name = "ConBiometricoMarcaciones.findAll", query = "SELECT c FROM ConBiometricoMarcaciones c"),
    @NamedQuery(name = "ConBiometricoMarcaciones.findByIdeCobim", query = "SELECT c FROM ConBiometricoMarcaciones c WHERE c.ideCobim = :ideCobim"),
    @NamedQuery(name = "ConBiometricoMarcaciones.findByIdePersonaCobim", query = "SELECT c FROM ConBiometricoMarcaciones c WHERE c.idePersonaCobim = :idePersonaCobim"),
    @NamedQuery(name = "ConBiometricoMarcaciones.findByEventoRelojCobim", query = "SELECT c FROM ConBiometricoMarcaciones c WHERE c.eventoRelojCobim = :eventoRelojCobim"),
    @NamedQuery(name = "ConBiometricoMarcaciones.findByFechaEventoCobim", query = "SELECT c FROM ConBiometricoMarcaciones c WHERE c.fechaEventoCobim = :fechaEventoCobim"),
    @NamedQuery(name = "ConBiometricoMarcaciones.findByEstatusCobim", query = "SELECT c FROM ConBiometricoMarcaciones c WHERE c.estatusCobim = :estatusCobim"),
    @NamedQuery(name = "ConBiometricoMarcaciones.findByActivoCobim", query = "SELECT c FROM ConBiometricoMarcaciones c WHERE c.activoCobim = :activoCobim")})
public class ConBiometricoMarcaciones implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ide_cobim", nullable = false)
    private Integer ideCobim;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ide_persona_cobim", nullable = false, length = 20)
    private String idePersonaCobim;
    @Size(max = 50)
    @Column(name = "evento_reloj_cobim", length = 50)
    private String eventoRelojCobim;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_evento_cobim", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaEventoCobim;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estatus_cobim", nullable = false)
    private int estatusCobim;
    @Column(name = "activo_cobim")
    private Boolean activoCobim;
    @JoinColumn(name = "ide_corel", referencedColumnName = "ide_corel", nullable = false)
    @ManyToOne(optional = false)
    private ConReloj ideCorel;

    public ConBiometricoMarcaciones() {
    }

    public ConBiometricoMarcaciones(Integer ideCobim) {
        this.ideCobim = ideCobim;
    }

    public ConBiometricoMarcaciones(Integer ideCobim, String idePersonaCobim, Date fechaEventoCobim, int estatusCobim) {
        this.ideCobim = ideCobim;
        this.idePersonaCobim = idePersonaCobim;
        this.fechaEventoCobim = fechaEventoCobim;
        this.estatusCobim = estatusCobim;
    }

    public Integer getIdeCobim() {
        return ideCobim;
    }

    public void setIdeCobim(Integer ideCobim) {
        this.ideCobim = ideCobim;
    }

    public String getIdePersonaCobim() {
        return idePersonaCobim;
    }

    public void setIdePersonaCobim(String idePersonaCobim) {
        this.idePersonaCobim = idePersonaCobim;
    }

    public String getEventoRelojCobim() {
        return eventoRelojCobim;
    }

    public void setEventoRelojCobim(String eventoRelojCobim) {
        this.eventoRelojCobim = eventoRelojCobim;
    }

    public Date getFechaEventoCobim() {
        return fechaEventoCobim;
    }

    public void setFechaEventoCobim(Date fechaEventoCobim) {
        this.fechaEventoCobim = fechaEventoCobim;
    }

    public int getEstatusCobim() {
        return estatusCobim;
    }

    public void setEstatusCobim(int estatusCobim) {
        this.estatusCobim = estatusCobim;
    }

    public Boolean getActivoCobim() {
        return activoCobim;
    }

    public void setActivoCobim(Boolean activoCobim) {
        this.activoCobim = activoCobim;
    }

    public ConReloj getIdeCorel() {
        return ideCorel;
    }

    public void setIdeCorel(ConReloj ideCorel) {
        this.ideCorel = ideCorel;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ideCobim != null ? ideCobim.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConBiometricoMarcaciones)) {
            return false;
        }
        ConBiometricoMarcaciones other = (ConBiometricoMarcaciones) object;
        if ((this.ideCobim == null && other.ideCobim != null) || (this.ideCobim != null && !this.ideCobim.equals(other.ideCobim))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "portal.entidades.ConBiometricoMarcaciones[ ideCobim=" + ideCobim + " ]";
    }
    
}
