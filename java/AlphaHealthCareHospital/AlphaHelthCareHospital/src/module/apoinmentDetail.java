package module;

public class apoinmentDetail {
    String apoiId;
    String patId;
    String docId;
    String nurseId;
    String time;
    String date;

    public apoinmentDetail() {
    }

    public apoinmentDetail(String apoiId, String patId, String docId, String nurseId, String time, String date) {
        this.apoiId = apoiId;
        this.patId = patId;
        this.docId = docId;
        this.nurseId = nurseId;
        this.time = time;
        this.date = date;
    }

    public String getPatId() {
        return patId;
    }

    public void setPatId(String patId) {
        this.patId = patId;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getApoiId() {
        return apoiId;
    }

    public void setApoiId(String apoiId) {
        this.apoiId = apoiId;
    }

    @Override
    public String toString() {
        return "apoinmentDetail{" +
                "apoiId='" + apoiId + '\'' +
                ", patId='" + patId + '\'' +
                ", docId='" + docId + '\'' +
                ", nurseId='" + nurseId + '\'' +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
