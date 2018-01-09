import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Course implements Serializable{
    private String room;
    private String subject;
    private String department;
    private String lecturer;
    private Date startTime;
    private Date endTime;

    public Course(String room, String subject, String department, String lecturer, Date startTime, Date endTime){
        this.room = room;
        this.subject = subject;
        this.department = department;
        this.lecturer = lecturer;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getRoom() {
        return room;
    }

    public String getSubject() {
        return subject;
    }

    public String getDepartment() {
        return department;
    }

    public String getLecturer() {
        return lecturer;
    }

    public String getDate(){ // return date component of startTime as a string
        DateFormat myDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return myDateFormat.format(startTime);
    }

    public String getStartTime(){ // return time component of startTime as a string
        DateFormat myTimeFormat = new SimpleDateFormat("HH:mm");
        return myTimeFormat.format(startTime);
    }

    public String getEndTime() {  // return time component of endTime as a string
        DateFormat myTimeFormat = new SimpleDateFormat("HH:mm");
        return myTimeFormat.format(endTime);
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Room: "+this.getRoom()+
               "\nSubject: "+this.getSubject()+
               "\nDepartment "+this.getDepartment()+
               "\nLecturer: "+this.getLecturer()+
               "\nDate: "+this.getDate()+
               "\nStart time: "+this.getStartTime()+
               "\nEnd time: "+this.getEndTime()+"\n";
    }
}
