package Tm;

public class docPatApoinmentDetail {
    String ApointmentId;
    String patientId;
    String patientName;
    String docId;
    String nurseId;
    String date;
    String time;
    String medicine;
    String eq;
    String drink;
    String cloth;
    String room;
    String lab;
    String parkingSlot;
    double price;
    String success;

    public docPatApoinmentDetail() {
    }

    public docPatApoinmentDetail(String apointmentId, String patientId, String patientName, String docId, String nurseId, String date, String time, String medicine, String eq, String drink, String cloth, String room, String lab, String parkingSlot, double price, String success) {
        ApointmentId = apointmentId;
        this.patientId = patientId;
        this.patientName = patientName;
        this.docId = docId;
        this.nurseId = nurseId;
        this.date = date;
        this.time = time;
        this.medicine = medicine;
        this.eq = eq;
        this.drink = drink;
        this.cloth = cloth;
        this.room = room;
        this.lab = lab;
        this.parkingSlot = parkingSlot;
        this.price = price;
        this.success = success;
    }

    public String getApointmentId() {
        return ApointmentId;
    }

    public void setApointmentId(String apointmentId) {
        ApointmentId = apointmentId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getNurseId() {
        return nurseId;
    }

    public void setNurseId(String nurseId) {
        this.nurseId = nurseId;
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

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getEq() {
        return eq;
    }

    public void setEq(String eq) {
        this.eq = eq;
    }

    public String getDrink() {
        return drink;
    }

    public void setDrink(String drink) {
        this.drink = drink;
    }

    public String getCloth() {
        return cloth;
    }

    public void setCloth(String cloth) {
        this.cloth = cloth;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getLab() {
        return lab;
    }

    public void setLab(String lab) {
        this.lab = lab;
    }

    public String getParkingSlot() {
        return parkingSlot;
    }

    public void setParkingSlot(String parkingSlot) {
        this.parkingSlot = parkingSlot;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "docPatApoinmentDetail{" +
                "ApointmentId='" + ApointmentId + '\'' +
                ", patientId='" + patientId + '\'' +
                ", patientName='" + patientName + '\'' +
                ", docId='" + docId + '\'' +
                ", nurseId='" + nurseId + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", medicine='" + medicine + '\'' +
                ", eq='" + eq + '\'' +
                ", drink='" + drink + '\'' +
                ", cloth='" + cloth + '\'' +
                ", room='" + room + '\'' +
                ", lab='" + lab + '\'' +
                ", parkingSlot='" + parkingSlot + '\'' +
                ", price=" + price +
                ", success='" + success + '\'' +
                '}';
    }
}
