package module;

public class FinalReportTbl {
    String apoiId;
    String patId;
    String patName;
    String patAddress;
    String patAge;
    String nic;
    String contact;
    String docName;
    String nurseName;
    String roomType;
    double roomPrice;
    String labType;
    String eqName;
    double eqPrice;
    String DrinkName;
    double drinkPrice;
    String clothName;
    double clothPrice;
    String mediName;
    String mediPrice;
    String date;
    String time;
    double fullTotal;

    public FinalReportTbl() {

    }

    public FinalReportTbl(String apoiId, String patId, String patName, String patAddress, String patAge, String nic, String contact, String docName, String nurseName, String roomType, double roomPrice, String labType, String eqName, double eqPrice, String drinkName, double drinkPrice, String clothName, double clothPrice, String mediName, String mediPrice, String date, String time, double fullTotal) {
        this.apoiId = apoiId;
        this.patId = patId;
        this.patName = patName;
        this.patAddress = patAddress;
        this.patAge = patAge;
        this.nic = nic;
        this.contact = contact;
        this.docName = docName;
        this.nurseName = nurseName;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
        this.labType = labType;
        this.eqName = eqName;
        this.eqPrice = eqPrice;
        DrinkName = drinkName;
        this.drinkPrice = drinkPrice;
        this.clothName = clothName;
        this.clothPrice = clothPrice;
        this.mediName = mediName;
        this.mediPrice = mediPrice;
        this.date = date;
        this.time = time;
        this.fullTotal = fullTotal;
    }

    public String getApoiId() {
        return apoiId;
    }

    public void setApoiId(String apoiId) {
        this.apoiId = apoiId;
    }

    public String getPatId() {
        return patId;
    }

    public void setPatId(String patId) {
        this.patId = patId;
    }

    public String getPatName() {
        return patName;
    }

    public void setPatName(String patName) {
        this.patName = patName;
    }

    public String getPatAddress() {
        return patAddress;
    }

    public void setPatAddress(String patAddress) {
        this.patAddress = patAddress;
    }

    public String getPatAge() {
        return patAge;
    }

    public void setPatAge(String patAge) {
        this.patAge = patAge;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getNurseName() {
        return nurseName;
    }

    public void setNurseName(String nurseName) {
        this.nurseName = nurseName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public double getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(double roomPrice) {
        this.roomPrice = roomPrice;
    }

    public String getLabType() {
        return labType;
    }

    public void setLabType(String labType) {
        this.labType = labType;
    }

    public String getEqName() {
        return eqName;
    }

    public void setEqName(String eqName) {
        this.eqName = eqName;
    }

    public double getEqPrice() {
        return eqPrice;
    }

    public void setEqPrice(double eqPrice) {
        this.eqPrice = eqPrice;
    }

    public String getDrinkName() {
        return DrinkName;
    }

    public void setDrinkName(String drinkName) {
        DrinkName = drinkName;
    }

    public double getDrinkPrice() {
        return drinkPrice;
    }

    public void setDrinkPrice(double drinkPrice) {
        this.drinkPrice = drinkPrice;
    }

    public String getClothName() {
        return clothName;
    }

    public void setClothName(String clothName) {
        this.clothName = clothName;
    }

    public double getClothPrice() {
        return clothPrice;
    }

    public void setClothPrice(double clothPrice) {
        this.clothPrice = clothPrice;
    }

    public String getMediName() {
        return mediName;
    }

    public void setMediName(String mediName) {
        this.mediName = mediName;
    }

    public String getMediPrice() {
        return mediPrice;
    }

    public void setMediPrice(String mediPrice) {
        this.mediPrice = mediPrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getFullTotal() {
        return fullTotal;
    }

    public void setFullTotal(double fullTotal) {
        this.fullTotal = fullTotal;
    }

    @Override
    public String toString() {
        return "FinalReportTbl{" +
                "apoiId='" + apoiId + '\'' +
                ", patId='" + patId + '\'' +
                ", patName='" + patName + '\'' +
                ", patAddress='" + patAddress + '\'' +
                ", patAge='" + patAge + '\'' +
                ", nic='" + nic + '\'' +
                ", contact='" + contact + '\'' +
                ", docName='" + docName + '\'' +
                ", nurseName='" + nurseName + '\'' +
                ", roomType='" + roomType + '\'' +
                ", roomPrice=" + roomPrice +
                ", labType='" + labType + '\'' +
                ", eqName='" + eqName + '\'' +
                ", eqPrice=" + eqPrice +
                ", DrinkName='" + DrinkName + '\'' +
                ", drinkPrice=" + drinkPrice +
                ", clothName='" + clothName + '\'' +
                ", clothPrice=" + clothPrice +
                ", mediName='" + mediName + '\'' +
                ", mediPrice='" + mediPrice + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", fullTotal=" + fullTotal +
                '}';
    }
}
