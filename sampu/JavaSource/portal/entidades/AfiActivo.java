/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package portal.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "afi_activo", catalog = "sampu", schema = "public")
@NamedQueries({
    @NamedQuery(name = "AfiActivo.findAll", query = "SELECT a FROM AfiActivo a"),
    @NamedQuery(name = "AfiActivo.findByIdeAfact", query = "SELECT a FROM AfiActivo a WHERE a.ideAfact = :ideAfact"),
    @NamedQuery(name = "AfiActivo.findByIdeAfubi", query = "SELECT a FROM AfiActivo a WHERE a.ideAfubi = :ideAfubi"),
    @NamedQuery(name = "AfiActivo.findByIdeAfnoa", query = "SELECT a FROM AfiActivo a WHERE a.ideAfnoa = :ideAfnoa"),
    @NamedQuery(name = "AfiActivo.findByFechaBajaAfact", query = "SELECT a FROM AfiActivo a WHERE a.fechaBajaAfact = :fechaBajaAfact"),
    @NamedQuery(name = "AfiActivo.findByCantidadAfact", query = "SELECT a FROM AfiActivo a WHERE a.cantidadAfact = :cantidadAfact"),
    @NamedQuery(name = "AfiActivo.findByRazonBajaAfact", query = "SELECT a FROM AfiActivo a WHERE a.razonBajaAfact = :razonBajaAfact"),
    @NamedQuery(name = "AfiActivo.findByMarcaAfact", query = "SELECT a FROM AfiActivo a WHERE a.marcaAfact = :marcaAfact"),
    @NamedQuery(name = "AfiActivo.findBySerieAfact", query = "SELECT a FROM AfiActivo a WHERE a.serieAfact = :serieAfact"),
    @NamedQuery(name = "AfiActivo.findByModeloAfact", query = "SELECT a FROM AfiActivo a WHERE a.modeloAfact = :modeloAfact"),
    @NamedQuery(name = "AfiActivo.findByValorUnitarioAfact", query = "SELECT a FROM AfiActivo a WHERE a.valorUnitarioAfact = :valorUnitarioAfact"),
    @NamedQuery(name = "AfiActivo.findByEgresoBodegaAfact", query = "SELECT a FROM AfiActivo a WHERE a.egresoBodegaAfact = :egresoBodegaAfact"),
    @NamedQuery(name = "AfiActivo.findByVidaUtilAfact", query = "SELECT a FROM AfiActivo a WHERE a.vidaUtilAfact = :vidaUtilAfact"),
    @NamedQuery(name = "AfiActivo.findByDetalleAfact", query = "SELECT a FROM AfiActivo a WHERE a.detalleAfact = :detalleAfact"),
    @NamedQuery(name = "AfiActivo.findByFechaAltaAfact", query = "SELECT a FROM AfiActivo a WHERE a.fechaAltaAfact = :fechaAltaAfact"),
    @NamedQuery(name = "AfiActivo.findByValorNetoAfact", query = "SELECT a FROM AfiActivo a WHERE a.valorNetoAfact = :valorNetoAfact"),
    @NamedQuery(name = "AfiActivo.findByFechaReavaluoAfact", query = "SELECT a FROM AfiActivo a WHERE a.fechaReavaluoAfact = :fechaReavaluoAfact"),
    @NamedQuery(name = "AfiActivo.findByValorCompraAfact", query = "SELECT a FROM AfiActivo a WHERE a.valorCompraAfact = :valorCompraAfact"),
    @NamedQuery(name = "AfiActivo.findByFechaCalculoAfact", query = "SELECT a FROM AfiActivo a WHERE a.fechaCalculoAfact = :fechaCalculoAfact"),
    @NamedQuery(name = "AfiActivo.findByValorRevaluoAfact", query = "SELECT a FROM AfiActivo a WHERE a.valorRevaluoAfact = :valorRevaluoAfact"),
    @NamedQuery(name = "AfiActivo.findByDepresiacionAcumuladaAfact", query = "SELECT a FROM AfiActivo a WHERE a.depresiacionAcumuladaAfact = :depresiacionAcumuladaAfact"),
    @NamedQuery(name = "AfiActivo.findByCuotaDepresiacionAfact", query = "SELECT a FROM AfiActivo a WHERE a.cuotaDepresiacionAfact = :cuotaDepresiacionAfact"),
    @NamedQuery(name = "AfiActivo.findByValDepresiacionPeriodoAfact", query = "SELECT a FROM AfiActivo a WHERE a.valDepresiacionPeriodoAfact = :valDepresiacionPeriodoAfact"),
    @NamedQuery(name = "AfiActivo.findByFechaGarantiaAfact", query = "SELECT a FROM AfiActivo a WHERE a.fechaGarantiaAfact = :fechaGarantiaAfact"),
    @NamedQuery(name = "AfiActivo.findByActivoAfact", query = "SELECT a FROM AfiActivo a WHERE a.activoAfact = :activoAfact"),
    @NamedQuery(name = "AfiActivo.findByUsuarioIngre", query = "SELECT a FROM AfiActivo a WHERE a.usuarioIngre = :usuarioIngre"),
    @NamedQuery(name = "AfiActivo.findByFechaIngre", query = "SELECT a FROM AfiActivo a WHERE a.fechaIngre = :fechaIngre"),
    @NamedQuery(name = "AfiActivo.findByHoraIngre", query = "SELECT a FROM AfiActivo a WHERE a.horaIngre = :horaIngre"),
    @NamedQuery(name = "AfiActivo.findByUsuarioActua", query = "SELECT a FROM AfiActivo a WHERE a.usuarioActua = :usuarioActua"),
    @NamedQuery(name = "AfiActivo.findByFechaActua", query = "SELECT a FROM AfiActivo a WHERE a.fechaActua = :fechaActua"),
    @NamedQuery(name = "AfiActivo.findByHoraActua", query = "SELECT a FROM AfiActivo a WHERE a.horaActua = :horaActua")})
public class AfiActivo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ide_afact", nullable = false)
    private Long ideAfact;
    @Column(name = "ide_afubi")
    private BigInteger ideAfubi;
    @Column(name = "ide_afnoa")
    private BigInteger ideAfnoa;
    @Column(name = "fecha_baja_afact")
    @Temporal(TemporalType.DATE)
    private Date fechaBajaAfact;
    @Column(name = "cantidad_afact")
    private BigInteger cantidadAfact;
    @Size(max = 2147483647)
    @Column(name = "razon_baja_afact", length = 2147483647)
    private String razonBajaAfact;
    @Size(max = 50)
    @Column(name = "marca_afact", length = 50)
    private String marcaAfact;
    @Size(max = 50)
    @Column(name = "serie_afact", length = 50)
    private String serieAfact;
    @Size(max = 50)
    @Column(name = "modelo_afact", length = 50)
    private String modeloAfact;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor_unitario_afact", precision = 10, scale = 2)
    private BigDecimal valorUnitarioAfact;
    @Size(max = 20)
    @Column(name = "egreso_bodega_afact", length = 20)
    private String egresoBodegaAfact;
    @Column(name = "vida_util_afact")
    private BigInteger vidaUtilAfact;
    @Size(max = 2147483647)
    @Column(name = "detalle_afact", length = 2147483647)
    private String detalleAfact;
    @Column(name = "fecha_alta_afact")
    @Temporal(TemporalType.DATE)
    private Date fechaAltaAfact;
    @Column(name = "valor_neto_afact", precision = 10, scale = 2)
    private BigDecimal valorNetoAfact;
    @Column(name = "fecha_reavaluo_afact")
    @Temporal(TemporalType.DATE)
    private Date fechaReavaluoAfact;
    @Column(name = "valor_compra_afact", precision = 10, scale = 2)
    private BigDecimal valorCompraAfact;
    @Column(name = "fecha_calculo_afact")
    @Temporal(TemporalType.DATE)
    private Date fechaCalculoAfact;
    @Column(name = "valor_revaluo_afact", precision = 10, scale = 2)
    private BigDecimal valorRevaluoAfact;
    @Column(name = "depresiacion_acumulada_afact", precision = 10, scale = 2)
    private BigDecimal depresiacionAcumuladaAfact;
    @Column(name = "cuota_depresiacion_afact", precision = 10, scale = 2)
    private BigDecimal cuotaDepresiacionAfact;
    @Column(name = "val_depresiacion_periodo_afact", precision = 10, scale = 2)
    private BigDecimal valDepresiacionPeriodoAfact;
    @Column(name = "fecha_garantia_afact")
    @Temporal(TemporalType.DATE)
    private Date fechaGarantiaAfact;
    @Column(name = "activo_afact")
    private Boolean activoAfact;
    @Size(max = 50)
    @Column(name = "usuario_ingre", length = 50)
    private String usuarioIngre;
    @Column(name = "fecha_ingre")
    @Temporal(TemporalType.DATE)
    private Date fechaIngre;
    @Column(name = "hora_ingre")
    @Temporal(TemporalType.TIME)
    private Date horaIngre;
    @Size(max = 50)
    @Column(name = "usuario_actua", length = 50)
    private String usuarioActua;
    @Column(name = "fecha_actua")
    @Temporal(TemporalType.DATE)
    private Date fechaActua;
    @Column(name = "hora_actua")
    @Temporal(TemporalType.TIME)
    private Date horaActua;
    @OneToMany(mappedBy = "ideAfact")
    private List<AfiCustodio> afiCustodioList;
    @JoinColumn(name = "ide_tepro", referencedColumnName = "ide_tepro")
    @ManyToOne
    private TesProveedor ideTepro;
    @JoinColumn(name = "ide_geare", referencedColumnName = "ide_geare")
    @ManyToOne
    private GenArea ideGeare;
    @JoinColumn(name = "ide_coest", referencedColumnName = "ide_coest")
    @ManyToOne
    private ContEstado ideCoest;
    @JoinColumn(name = "ide_aftip", referencedColumnName = "ide_aftip")
    @ManyToOne
    private AfiTipoPropiedad ideAftip;
    @JoinColumn(name = "ide_aftia", referencedColumnName = "ide_aftia")
    @ManyToOne
    private AfiTipoActivo ideAftia;
    @JoinColumn(name = "ide_afseg", referencedColumnName = "ide_afseg")
    @ManyToOne
    private AfiSeguro ideAfseg;
    @JoinColumn(name = "ide_afacd", referencedColumnName = "ide_afacd")
    @ManyToOne
    private AfiActividad ideAfacd;

    public AfiActivo() {
    }

    public AfiActivo(Long ideAfact) {
        this.ideAfact = ideAfact;
    }

    public Long getIdeAfact() {
        return ideAfact;
    }

    public void setIdeAfact(Long ideAfact) {
        this.ideAfact = ideAfact;
    }

    public BigInteger getIdeAfubi() {
        return ideAfubi;
    }

    public void setIdeAfubi(BigInteger ideAfubi) {
        this.ideAfubi = ideAfubi;
    }

    public BigInteger getIdeAfnoa() {
        return ideAfnoa;
    }

    public void setIdeAfnoa(BigInteger ideAfnoa) {
        this.ideAfnoa = ideAfnoa;
    }

    public Date getFechaBajaAfact() {
        return fechaBajaAfact;
    }

    public void setFechaBajaAfact(Date fechaBajaAfact) {
        this.fechaBajaAfact = fechaBajaAfact;
    }

    public BigInteger getCantidadAfact() {
        return cantidadAfact;
    }

    public void setCantidadAfact(BigInteger cantidadAfact) {
        this.cantidadAfact = cantidadAfact;
    }

    public String getRazonBajaAfact() {
        return razonBajaAfact;
    }

    public void setRazonBajaAfact(String razonBajaAfact) {
        this.razonBajaAfact = razonBajaAfact;
    }

    public String getMarcaAfact() {
        return marcaAfact;
    }

    public void setMarcaAfact(String marcaAfact) {
        this.marcaAfact = marcaAfact;
    }

    public String getSerieAfact() {
        return serieAfact;
    }

    public void setSerieAfact(String serieAfact) {
        this.serieAfact = serieAfact;
    }

    public String getModeloAfact() {
        return modeloAfact;
    }

    public void setModeloAfact(String modeloAfact) {
        this.modeloAfact = modeloAfact;
    }

    public BigDecimal getValorUnitarioAfact() {
        return valorUnitarioAfact;
    }

    public void setValorUnitarioAfact(BigDecimal valorUnitarioAfact) {
        this.valorUnitarioAfact = valorUnitarioAfact;
    }

    public String getEgresoBodegaAfact() {
        return egresoBodegaAfact;
    }

    public void setEgresoBodegaAfact(String egresoBodegaAfact) {
        this.egresoBodegaAfact = egresoBodegaAfact;
    }

    public BigInteger getVidaUtilAfact() {
        return vidaUtilAfact;
    }

    public void setVidaUtilAfact(BigInteger vidaUtilAfact) {
        this.vidaUtilAfact = vidaUtilAfact;
    }

    public String getDetalleAfact() {
        return detalleAfact;
    }

    public void setDetalleAfact(String detalleAfact) {
        this.detalleAfact = detalleAfact;
    }

    public Date getFechaAltaAfact() {
        return fechaAltaAfact;
    }

    public void setFechaAltaAfact(Date fechaAltaAfact) {
        this.fechaAltaAfact = fechaAltaAfact;
    }

    public BigDecimal getValorNetoAfact() {
        return valorNetoAfact;
    }

    public void setValorNetoAfact(BigDecimal valorNetoAfact) {
        this.valorNetoAfact = valorNetoAfact;
    }

    public Date getFechaReavaluoAfact() {
        return fechaReavaluoAfact;
    }

    public void setFechaReavaluoAfact(Date fechaReavaluoAfact) {
        this.fechaReavaluoAfact = fechaReavaluoAfact;
    }

    public BigDecimal getValorCompraAfact() {
        return valorCompraAfact;
    }

    public void setValorCompraAfact(BigDecimal valorCompraAfact) {
        this.valorCompraAfact = valorCompraAfact;
    }

    public Date getFechaCalculoAfact() {
        return fechaCalculoAfact;
    }

    public void setFechaCalculoAfact(Date fechaCalculoAfact) {
        this.fechaCalculoAfact = fechaCalculoAfact;
    }

    public BigDecimal getValorRevaluoAfact() {
        return valorRevaluoAfact;
    }

    public void setValorRevaluoAfact(BigDecimal valorRevaluoAfact) {
        this.valorRevaluoAfact = valorRevaluoAfact;
    }

    public BigDecimal getDepresiacionAcumuladaAfact() {
        return depresiacionAcumuladaAfact;
    }

    public void setDepresiacionAcumuladaAfact(BigDecimal depresiacionAcumuladaAfact) {
        this.depresiacionAcumuladaAfact = depresiacionAcumuladaAfact;
    }

    public BigDecimal getCuotaDepresiacionAfact() {
        return cuotaDepresiacionAfact;
    }

    public void setCuotaDepresiacionAfact(BigDecimal cuotaDepresiacionAfact) {
        this.cuotaDepresiacionAfact = cuotaDepresiacionAfact;
    }

    public BigDecimal getValDepresiacionPeriodoAfact() {
        return valDepresiacionPeriodoAfact;
    }

    public void setValDepresiacionPeriodoAfact(BigDecimal valDepresiacionPeriodoAfact) {
        this.valDepresiacionPeriodoAfact = valDepresiacionPeriodoAfact;
    }

    public Date getFechaGarantiaAfact() {
        return fechaGarantiaAfact;
    }

    public void setFechaGarantiaAfact(Date fechaGarantiaAfact) {
        this.fechaGarantiaAfact = fechaGarantiaAfact;
    }

    public Boolean getActivoAfact() {
        return activoAfact;
    }

    public void setActivoAfact(Boolean activoAfact) {
        this.activoAfact = activoAfact;
    }

    public String getUsuarioIngre() {
        return usuarioIngre;
    }

    public void setUsuarioIngre(String usuarioIngre) {
        this.usuarioIngre = usuarioIngre;
    }

    public Date getFechaIngre() {
        return fechaIngre;
    }

    public void setFechaIngre(Date fechaIngre) {
        this.fechaIngre = fechaIngre;
    }

    public Date getHoraIngre() {
        return horaIngre;
    }

    public void setHoraIngre(Date horaIngre) {
        this.horaIngre = horaIngre;
    }

    public String getUsuarioActua() {
        return usuarioActua;
    }

    public void setUsuarioActua(String usuarioActua) {
        this.usuarioActua = usuarioActua;
    }

    public Date getFechaActua() {
        return fechaActua;
    }

    public void setFechaActua(Date fechaActua) {
        this.fechaActua = fechaActua;
    }

    public Date getHoraActua() {
        return horaActua;
    }

    public void setHoraActua(Date horaActua) {
        this.horaActua = horaActua;
    }

    public List<AfiCustodio> getAfiCustodioList() {
        return afiCustodioList;
    }

    public void setAfiCustodioList(List<AfiCustodio> afiCustodioList) {
        this.afiCustodioList = afiCustodioList;
    }

    public TesProveedor getIdeTepro() {
        return ideTepro;
    }

    public void setIdeTepro(TesProveedor ideTepro) {
        this.ideTepro = ideTepro;
    }

    public GenArea getIdeGeare() {
        return ideGeare;
    }

    public void setIdeGeare(GenArea ideGeare) {
        this.ideGeare = ideGeare;
    }

    public ContEstado getIdeCoest() {
        return ideCoest;
    }

    public void setIdeCoest(ContEstado ideCoest) {
        this.ideCoest = ideCoest;
    }

    public AfiTipoPropiedad getIdeAftip() {
        return ideAftip;
    }

    public void setIdeAftip(AfiTipoPropiedad ideAftip) {
        this.ideAftip = ideAftip;
    }

    public AfiTipoActivo getIdeAftia() {
        return ideAftia;
    }

    public void setIdeAftia(AfiTipoActivo ideAftia) {
        this.ideAftia = ideAftia;
    }

    public AfiSeguro getIdeAfseg() {
        return ideAfseg;
    }

    public void setIdeAfseg(AfiSeguro ideAfseg) {
        this.ideAfseg = ideAfseg;
    }

    public AfiActividad getIdeAfacd() {
        return ideAfacd;
    }

    public void setIdeAfacd(AfiActividad ideAfacd) {
        this.ideAfacd = ideAfacd;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ideAfact != null ? ideAfact.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AfiActivo)) {
            return false;
        }
        AfiActivo other = (AfiActivo) object;
        if ((this.ideAfact == null && other.ideAfact != null) || (this.ideAfact != null && !this.ideAfact.equals(other.ideAfact))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "portal.entidades.AfiActivo[ ideAfact=" + ideAfact + " ]";
    }
    
}
