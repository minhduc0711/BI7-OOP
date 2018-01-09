import javax.swing.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddDialog extends JDialog {
    private int index;

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel mainPanel;
    private JTextField subjectField;
    private JTextField lecturerField;
    private JComboBox departmentField;
    private JFormattedTextField dateField;
    private JFormattedTextField startTimeField;
    private JFormattedTextField endTimeField;
    private JComboBox roomField;
    private JPanel buttonPanel;

    public AddDialog(int index){
        this.index = index;
        if (index != -1) setTextFields(); // if we are modifying then the data field will be set corresponding to selected row

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // show an error dialog if there is an empty field
        if ((subjectField.getText().isEmpty()) || (lecturerField.getText().isEmpty()) || (dateField.getText().isEmpty()) ||
                (startTimeField.getText().isEmpty()) || (endTimeField.getText().isEmpty())){
            JOptionPane.showMessageDialog(null,
                    "All fields must be filled", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String room = String.valueOf(roomField.getSelectedItem());
            String subject = subjectField.getText();
            String lecturer = lecturerField.getText();
            String department = String.valueOf(departmentField.getSelectedItem());
            Date startTime = formStrings(dateField.getText(),startTimeField.getText());
            Date endTime = formStrings(dateField.getText(),endTimeField.getText());
            if (startTime.compareTo(endTime)>-1){
                JOptionPane.showMessageDialog(null,
                        "End time must be later than start time", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                if (index == -1) { // add the new object into our list
                    Data.courseList.add(new Course(room, subject, department, lecturer, startTime, endTime));
                    JOptionPane.showMessageDialog(null, "Class successfully added",
                            "", JOptionPane.INFORMATION_MESSAGE);
                } else{            // update the content of the selected object
                    Course currentCourse = Data.courseList.get(index);
                    currentCourse.setRoom(room);
                    currentCourse.setSubject(subject);
                    currentCourse.setDepartment(department);
                    currentCourse.setLecturer(lecturer);
                    currentCourse.setStartTime(startTime);
                    currentCourse.setEndTime(endTime);
                    JOptionPane.showMessageDialog(null, "Class successfully updated",
                            "", JOptionPane.INFORMATION_MESSAGE);
                }
                Data.save();   // save the data to txt file
                dispose();     // close the dialog
            }
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void createUIComponents() {
        // create JFormattedTextfield
        DateFormat myDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat myTimeFormat = new SimpleDateFormat("HH:mm");
        dateField = new JFormattedTextField(myDateFormat);
        startTimeField = new JFormattedTextField(myTimeFormat);
        endTimeField = new JFormattedTextField(myTimeFormat);
    }

    // method to convert string input to a date object
    public static Date formStrings(String date, String time) {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String s = date+" "+time;
        try {
            return format.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setTextFields(){ // set data fields to corresponding rows
        Course currentCourse = Data.courseList.get(index);
        int roomIndex =-1;
        for (int i=0;i<roomField.getItemCount();i++){
            if (String.valueOf(roomField.getItemAt(i)).equals(currentCourse.getRoom())){
                roomIndex = i;
                break;
            }
        }
        roomField.setSelectedIndex(roomIndex);
        int departmentIndex =-1;
        for (int i=0;i<departmentField.getItemCount();i++){
            if (String.valueOf(departmentField.getItemAt(i)).equals(currentCourse.getDepartment())){
                departmentIndex = i;
                break;
            }
        }
        departmentField.setSelectedIndex(departmentIndex);
        subjectField.setText(currentCourse.getSubject());
        lecturerField.setText(currentCourse.getLecturer());
        dateField.setText(currentCourse.getDate());
        startTimeField.setText(currentCourse.getStartTime());
        endTimeField.setText(currentCourse.getEndTime());
    }

    public static void createAndShowDialog(int rowIndex) {
        AddDialog dialog = new AddDialog(rowIndex);
        if (rowIndex == -1) dialog.setTitle("Add a class");
        else dialog.setTitle("Modify a class");
        dialog.pack();
        dialog.setSize(500,300);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}
