import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainUI {
    private JPanel MainPanel;
    private JPanel SouthPanel;
    private JButton addButton;
    private JButton modifyButton;
    private JButton deleteButton;
    private JTable courseTable;

    public MainUI() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddDialog.createAndShowDialog(-1);  // create the add dialog
                updateTable(); //refresh the table after adding
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentSelectedRow = courseTable.getSelectedRow(); // get the index of currently selected row
                if(currentSelectedRow != -1) {
                    int reply =JOptionPane.showConfirmDialog(null,"Are you sure you want to delete this class?",
                            "Delete a class",JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION){
                        DefaultTableModel model = (DefaultTableModel) courseTable.getModel();
                        model.removeRow(currentSelectedRow); // remove the selected row
                        Data.courseList.remove(currentSelectedRow); // remove the corresponding data
                        Data.save(); // save data to txt file
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a class first",
                            "", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentSelectedRow = courseTable.getSelectedRow();
                if (currentSelectedRow != -1){
                    AddDialog.createAndShowDialog(currentSelectedRow);  // create the modify dialog
                    updateTable(); //refresh the table after modifying
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a class first",
                            "", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    private void updateTable() {
        String[] columnNames = {"Room","Subject","Department","Lecturer","Date","Start time","End time"};// column headers
        String[][] courseData = new String[Data.courseList.size()][7]; // using a 2D array of strings to store data
        Course currentCourse;
        for (int i=0;i<Data.courseList.size();i++){
            currentCourse = Data.courseList.get(i);
            courseData[i][0] = currentCourse.getRoom();
            courseData[i][1] = currentCourse.getSubject();
            courseData[i][2] = currentCourse.getDepartment();
            courseData[i][3] = currentCourse.getLecturer();
            courseData[i][4] = currentCourse.getDate();
            courseData[i][5] = currentCourse.getStartTime();
            courseData[i][6] = currentCourse.getEndTime();
        }
        DefaultTableModel model = new DefaultTableModel(courseData,columnNames) { //load data from 2D array to a table model
            public boolean isCellEditable(int row, int column){
                return false;
            } // disable cell editing
        };
        courseTable.setModel(model); // set the model of the JTable
        courseTable.getColumnModel().getColumn(0).setPreferredWidth(40);  // adjust column size
        courseTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        courseTable.getColumnModel().getColumn(2).setPreferredWidth(65);
        courseTable.getColumnModel().getColumn(3).setPreferredWidth(150);
        courseTable.getColumnModel().getColumn(4).setPreferredWidth(60);
        courseTable.getColumnModel().getColumn(5).setPreferredWidth(40);
        courseTable.getColumnModel().getColumn(6).setPreferredWidth(40);
    }

    private void createUIComponents() {
        courseTable = new JTable(); // initialize the JTable
        updateTable(); // refresh table
        courseTable.getTableHeader().setReorderingAllowed(false);
    }

    public static void createAndShowGUI() {
        JFrame frame = new JFrame("Classroom Management");
        frame.setContentPane(new MainUI().MainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(720,400);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}

