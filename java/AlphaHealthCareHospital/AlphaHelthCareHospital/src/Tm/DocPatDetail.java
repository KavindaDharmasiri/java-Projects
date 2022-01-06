package Tm;

public class DocPatDetail {
    String apoinmentId;
    String patid;
    String patName;
    String labId;
    String drinkId;
    String clothId;
    String mediId;
    String eqId;

    public DocPatDetail() {
    }

    public DocPatDetail(String apoinmentId, String patid, String patName, String labId, String drinkId, String clothId, String mediId, String eqId) {
        this.apoinmentId = apoinmentId;
        this.patid = patid;
        this.patName = patName;
        this.labId = labId;
        this.drinkId = drinkId;
        this.clothId = clothId;
        this.mediId = mediId;
        this.eqId = eqId;
    }

    @Override
    public String toString() {
        return "DocPatDetail{" +
                "apoinmentId='" + apoinmentId + '\'' +
                ", patid='" + patid + '\'' +
                ", patName='" + patName + '\'' +
                ", labId='" + labId + '\'' +
                ", drinkId='" + drinkId + '\'' +
                ", clothId='" + clothId + '\'' +
                ", mediId='" + mediId + '\'' +
                ", eqId='" + eqId + '\'' +
                '}';
    }

    public String getApoinmentId() {
        return apoinmentId;
    }

    public void setApoinmentId(String apoinmentId) {
        this.apoinmentId = apoinmentId;
    }

    public String getPatid() {
        return patid;
    }

    public void setPatid(String patid) {
        this.patid = patid;
    }

    public String getPatName() {
        return patName;
    }

    public void setPatName(String patName) {
        this.patName = patName;
    }

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }

    public String getDrinkId() {
        return drinkId;
    }

    public void setDrinkId(String drinkId) {
        this.drinkId = drinkId;
    }

    public String getClothId() {
        return clothId;
    }

    public void setClothId(String clothId) {
        this.clothId = clothId;
    }

    public String getMediId() {
        return mediId;
    }

    public void setMediId(String mediId) {
        this.mediId = mediId;
    }

    public String getEqId() {
        return eqId;
    }

    public void setEqId(String eqId) {
        this.eqId = eqId;
    }
}
