// ==================== CO1: FOUNDATIONS & CONTROL STRUCTURES ====================
// Here CO1 is mainly represented inside AttendanceCalculator (weighted formula)
// and basic validations (if-else) in the GUI methods.

// ==================== IMPORTS (USED ACROSS COs) ====================
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

// ==================== CO4: OOP – DATA MODEL & METHODS ====================

class AttendanceComponents {
    private String studentName;         // String usage (CO3)
    private String studentRollNumber;   // String usage (CO3)
    private String subject;             // String usage (CO3)
    private double lectureAttendance;
    private double tutorialAttendance;
    private double practicalAttendance;
    private double skillAttendance;
    private boolean hasLecture, hasTutorial, hasPractical, hasSkill; // Track available components

    // Constructor – initializes all fields
    public AttendanceComponents(String studentName, String studentRollNumber, String subject,
                                double lecture, double tutorial, double practical, double skill) {
        this.studentName = studentName;
        this.studentRollNumber = studentRollNumber;
        this.subject = subject;
        this.lectureAttendance = lecture;
        this.tutorialAttendance = tutorial;
        this.practicalAttendance = practical;
        this.skillAttendance = skill;
        
        // Mark which components are available (non-zero)
        this.hasLecture = lecture > 0;
        this.hasTutorial = tutorial > 0;
        this.hasPractical = practical > 0;
        this.hasSkill = skill > 0;
    }

    // Getters – encapsulation
    public String getStudentName() { return studentName; }
    public String getStudentRollNumber() { return studentRollNumber; }
    public String getSubject() { return subject; }
    public double getLectureAttendance() { return lectureAttendance; }
    public double getTutorialAttendance() { return tutorialAttendance; }
    public double getPracticalAttendance() { return practicalAttendance; }
    public double getSkillAttendance() { return skillAttendance; }
    public boolean hasLecture() { return hasLecture; }
    public boolean hasTutorial() { return hasTutorial; }
    public boolean hasPractical() { return hasPractical; }
    public boolean hasSkill() { return hasSkill; }
}

// Business logic class – weighted attendance formula (CO1 + CO4)
class AttendanceCalculator {
    private static final double WEIGHT_LECTURE = 1.0;
    private static final double WEIGHT_TUTORIAL = 0.25;
    private static final double WEIGHT_PRACTICAL = 0.5;
    private static final double WEIGHT_SKILL = 0.25;

    public static double calculateFinalAttendance(AttendanceComponents ac) {
        double totalAttendance = 0;
        double totalWeight = 0;

        // Dynamic calculation based on available components ONLY (CO1 enhanced)
        if (ac.hasLecture()) {
            totalAttendance += ac.getLectureAttendance() * WEIGHT_LECTURE;
            totalWeight += WEIGHT_LECTURE;
        }
        if (ac.hasTutorial()) {
            totalAttendance += ac.getTutorialAttendance() * WEIGHT_TUTORIAL;
            totalWeight += WEIGHT_TUTORIAL;
        }
        if (ac.hasPractical()) {
            totalAttendance += ac.getPracticalAttendance() * WEIGHT_PRACTICAL;
            totalWeight += WEIGHT_PRACTICAL;
        }
        if (ac.hasSkill()) {
            totalAttendance += ac.getSkillAttendance() * WEIGHT_SKILL;
            totalWeight += WEIGHT_SKILL;
        }

        // Avoid division by zero
        return totalWeight > 0 ? totalAttendance / totalWeight : 0;
    }
    
    // New: Get calculation details for display (CO3 - StringBuilder)
    public static String getCalculationDetails(AttendanceComponents ac) {
        StringBuilder details = new StringBuilder("Components used: ");
        if (ac.hasLecture()) details.append("Lecture(1.0)");
        if (ac.hasTutorial()) details.append(" T(0.25)");
        if (ac.hasPractical()) details.append(" P(0.5)");
        if (ac.hasSkill()) details.append(" S(0.25)");
        return details.toString();
    }
}

// ==================== CO2 & CO3: ARRAYS, STRINGS, RECURSION, BITWISE ====================

// Helper class showing arrays + recursion based on attendance values
class AttendanceAnalytics {

    // Build 1D array of final attendance values from all components (CO2 – arrays)
    public static double[] buildFinalAttendanceArray(java.util.Collection<AttendanceComponents> values) {
        double[] result = new double[values.size()];
        int i = 0;
        for (AttendanceComponents ac : values) {
            result[i++] = AttendanceCalculator.calculateFinalAttendance(ac);
        }
        return result;
    }

    // Recursive function to find minimum value in array (CO3 – recursion)
    public static double findMinRecursive(double[] arr, int index, double currentMin) {
        if (index == arr.length) {      // base case
            return currentMin;
        }
        if (arr[index] < currentMin) {  // recursive case – update min
            currentMin = arr[index];
        }
        return findMinRecursive(arr, index + 1, currentMin);
    }
}

// Bitwise utilities demo (CO3 – bitwise operations)
class BitwiseUtils {

    // Check if given integer id is even using bitwise AND
    public static boolean isEvenId(int id) {
        return (id & 1) == 0; // if last bit is 0, number is even
    }
}

// ==================== CO5 & CO6: MANAGER, INHERITANCE, FILE I/O, COLLECTIONS ====================

// Base manager – implements storage and basic operations (CO4, CO5, CO6)
class AttendanceManager {
    // Collections & Generics (CO6)
    protected java.util.Map<String, AttendanceComponents> attendanceMap = new java.util.HashMap<>();
    private static final String FILE_NAME = "attendance_data.txt";

    // ---------- CO6: FILE I/O + EXCEPTION HANDLING ----------
    public void loadAttendance() throws IOException {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                // String[] (array) usage (CO2), string splitting (CO3)
                String[] parts = line.split(",");
                if (parts.length == 7) {
                    String studentName = parts[0];
                    String studentRollNumber = parts[1];
                    String subject = parts[2];
                    double lecture = Double.parseDouble(parts[3]);
                    double tutorial = Double.parseDouble(parts[4]);
                    double practical = Double.parseDouble(parts[5]);
                    double skill = Double.parseDouble(parts[6]);

                    AttendanceComponents ac = new AttendanceComponents(
                            studentName, studentRollNumber, subject,
                            lecture, tutorial, practical, skill);

                    String key = studentRollNumber + "_" + subject; // string concatenation (CO3)
                    attendanceMap.put(key, ac);
                }
            }
        }
    }

    public void saveAttendance() throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (AttendanceComponents ac : attendanceMap.values()) {
                String line = String.format("%s,%s,%s,%.2f,%.2f,%.2f,%.2f",
                        ac.getStudentName(), ac.getStudentRollNumber(), ac.getSubject(),
                        ac.getLectureAttendance(), ac.getTutorialAttendance(),
                        ac.getPracticalAttendance(), ac.getSkillAttendance());
                bw.write(line);
                bw.newLine();
            }
        }
    }

    // ---------- CO4: BASIC OOP OPERATIONS ----------
    public void addOrUpdateAttendance(AttendanceComponents ac) {
        String key = ac.getStudentRollNumber() + "_" + ac.getSubject();
        attendanceMap.put(key, ac);
    }

    // ✅ NEW: Delete attendance record (CO6 - data management)
    public boolean deleteAttendance(String studentRollNumber, String subject) {
        String key = studentRollNumber + "_" + subject;
        return attendanceMap.remove(key) != null;
    }

    public AttendanceComponents searchAttendance(String studentRollNumber, String subject) {
        String key = studentRollNumber + "_" + subject;
        return attendanceMap.get(key);
    }

    public Double getFinalAttendance(String studentRollNumber, String subject) {
        String key = studentRollNumber + "_" + subject;
        AttendanceComponents ac = attendanceMap.get(key);
        return ac != null ? AttendanceCalculator.calculateFinalAttendance(ac) : null;
    }

    public List<AttendanceComponents> getAll() {
        return attendanceMap.values().stream().collect(Collectors.toList());
    }
}

// Child class – adds search/sort logic (CO5: inheritance + polymorphism)
class AttendanceManagerWithSearchSort extends AttendanceManager {
    public AttendanceComponents getStudentWithLeastAttendance() {
        return attendanceMap.values().stream()
                .min(Comparator.comparing(AttendanceCalculator::calculateFinalAttendance))
                .orElse(null);
    }

    public AttendanceComponents getStudentWithMostAttendance() {
        return attendanceMap.values().stream()
                .max(Comparator.comparing(AttendanceCalculator::calculateFinalAttendance))
                .orElse(null);
    }

    public List<AttendanceComponents> getAttendanceSortedAscending() {
        return attendanceMap.values().stream()
                .sorted(Comparator.comparing(AttendanceCalculator::calculateFinalAttendance))
                .collect(Collectors.toList());
    }
}

// ==================== CO6: SWING GUI CAPSTONE ====================

public class Main extends JFrame {

    private AttendanceManagerWithSearchSort manager;

    // GUI fields
    private JTextField txtStudentName, txtRollNumber, txtSubject;
    private JTextField txtLecture, txtTutorial, txtPractical, txtSkill;
    private JTextField txtSearchRoll, txtSearchSubject;
    private JTextArea txtAreaOutput;
    private JTable table;
    private DefaultTableModel tableModel;

    public Main() {
        manager = new AttendanceManagerWithSearchSort();

        // Load existing data (File I/O + exception handling – CO6)
        try {
            manager.loadAttendance();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading attendance data: " + e.getMessage());
        }

        setTitle("Attendance Management System – CO1 to CO6 Integrated");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 700);  // ✅ INCREASED WIDTH FOR BUTTON VISIBILITY
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ---------- LEFT: COMPACT Add/Update form (uses OOP classes – CO4) ----------
        JPanel inputPanel = new JPanel(new GridLayout(9, 2, 3, 3));  // ✅ SMALLER GRID
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add Attendance"));
        inputPanel.setPreferredSize(new Dimension(220, 400));  // ✅ FIXED SMALLER SIZE

        inputPanel.add(new JLabel("Name:"));
        txtStudentName = new JTextField();
        inputPanel.add(txtStudentName);

        inputPanel.add(new JLabel("Roll:"));
        txtRollNumber = new JTextField();
        inputPanel.add(txtRollNumber);

        inputPanel.add(new JLabel("Subject:"));
        txtSubject = new JTextField();
        inputPanel.add(txtSubject);

        inputPanel.add(new JLabel("Lecture:"));
        txtLecture = new JTextField();
        inputPanel.add(txtLecture);

        inputPanel.add(new JLabel("Tutorial:"));
        txtTutorial = new JTextField();
        inputPanel.add(txtTutorial);

        inputPanel.add(new JLabel("Practical:"));
        txtPractical = new JTextField();
        inputPanel.add(txtPractical);

        inputPanel.add(new JLabel("Skill:"));
        txtSkill = new JTextField();
        inputPanel.add(txtSkill);

        JButton btnAddUpdate = new JButton("Add/Update");
        inputPanel.add(btnAddUpdate);
        inputPanel.add(new JLabel());

        // ---------- TOP: Search & reports (CO2/CO3/CO5 algorithms) ----------
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 8));  // ✅ TIGHTER SPACING
        searchPanel.setBorder(BorderFactory.createTitledBorder("Search & Operations"));

        searchPanel.add(new JLabel("Roll:"));
        txtSearchRoll = new JTextField(8);  // ✅ SMALLER FIELDS
        searchPanel.add(txtSearchRoll);

        searchPanel.add(new JLabel("Subj:"));
        txtSearchSubject = new JTextField(8);  // ✅ SMALLER FIELDS
        searchPanel.add(txtSearchSubject);

        JButton btnSearch = new JButton("Search");
        searchPanel.add(btnSearch);

        JButton btnLeast = new JButton("Least");
        searchPanel.add(btnLeast);

        JButton btnMost = new JButton("Most");
        searchPanel.add(btnMost);

        JButton btnDisplaySorted = new JButton("Sorted");
        searchPanel.add(btnDisplaySorted);

        // ✅ DELETE & CLEAR BUTTONS NOW VISIBLE WITH COMPACT LAYOUT
        JButton btnDelete = new JButton("🗑️ Delete");
        searchPanel.add(btnDelete);

        JButton btnClearTable = new JButton("Clear");
        searchPanel.add(btnClearTable);

        // ---------- CENTER: Output text area ----------
        txtAreaOutput = new JTextArea(8, 50);
        txtAreaOutput.setEditable(false);
        JScrollPane scrollOutput = new JScrollPane(txtAreaOutput);

        // ---------- BOTTOM: Table (Collections & Generics – CO6) ----------
        tableModel = new DefaultTableModel(
                new String[]{"Name", "Roll", "Subject", "Final %", "Components"}, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        JScrollPane scrollTable = new JScrollPane(table);
        scrollTable.setPreferredSize(new Dimension(980, 180));

        add(inputPanel, BorderLayout.WEST);
        add(searchPanel, BorderLayout.NORTH);
        add(scrollOutput, BorderLayout.CENTER);
        add(scrollTable, BorderLayout.SOUTH);

        // ---------- Event handling ----------
        btnAddUpdate.addActionListener(e -> addOrUpdateAttendance());
        btnSearch.addActionListener(e -> searchAttendance());
        btnLeast.addActionListener(e -> showLeastAttendance());
        btnMost.addActionListener(e -> showMostAttendance());
        btnDisplaySorted.addActionListener(e -> displaySortedAttendance());
        
        // ✅ NEW EVENT HANDLERS FOR DELETE & CLEAR
        btnDelete.addActionListener(e -> deleteAttendance());
        btnClearTable.addActionListener(e -> clearTable());

        // Save data when window closes (CO6)
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    manager.saveAttendance();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(Main.this, "Error saving data: " + ex.getMessage());
                }
            }
        });
    }

    // ✅ NEW METHOD: Delete attendance record from data (CO6)
    private void deleteAttendance() {
        String roll = txtSearchRoll.getText().trim();
        String subject = txtSearchSubject.getText().trim();

        if (roll.isEmpty() || subject.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter Roll Number and Subject to delete.");
            return;
        }

        boolean deleted = manager.deleteAttendance(roll, subject);
        if (deleted) {
            txtAreaOutput.setText(String.format("✅ DELETED: Record for %s (%s) removed permanently.\n", roll, subject));
        } else {
            txtAreaOutput.setText(String.format("❌ No record found for %s (%s).\n", roll, subject));
        }
    }

    // ✅ NEW METHOD: Clear or Delete the sorted table (display only)
    private void clearTable() {
        tableModel.setRowCount(0);  // Completely clears all rows
        txtAreaOutput.setText("🗑️ Table cleared! All records removed from display.\n");
    }

    // ===== CO4 + CO6: Add or Update record =====
    private void addOrUpdateAttendance() {
        try {
            String studentName = txtStudentName.getText().trim();
            String rollNumber = txtRollNumber.getText().trim();
            String subject = txtSubject.getText().trim();
            double lecture = parseDouble(txtLecture.getText().trim(), 0);
            double tutorial = parseDouble(txtTutorial.getText().trim(), 0);
            double practical = parseDouble(txtPractical.getText().trim(), 0);
            double skill = parseDouble(txtSkill.getText().trim(), 0);

            if (studentName.isEmpty() || rollNumber.isEmpty() || subject.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all student and subject details.");
                return;
            }

            // CO3 – Bitwise example: check if numeric part of roll is even
            try {
                int numericPart = Integer.parseInt(rollNumber.replaceAll("\\D", ""));
                boolean isEven = BitwiseUtils.isEvenId(numericPart);
                txtAreaOutput.setText("Roll numeric part is " +
                        (isEven ? "EVEN" : "ODD") + " (bitwise check).\n");
            } catch (NumberFormatException ignore) {
                // if roll doesn't contain digits, ignore bitwise demo
            }

            AttendanceComponents ac = new AttendanceComponents(
                    studentName, rollNumber, subject, lecture, tutorial, practical, skill);
            manager.addOrUpdateAttendance(ac);

            Double finalAttendance = manager.getFinalAttendance(rollNumber, subject);
            String details = AttendanceCalculator.getCalculationDetails(ac);
            txtAreaOutput.append(String.format(
                    "Saved: %s (%s) in %s -> %.1f%%\n%s\n",
                    studentName, rollNumber, subject, finalAttendance, details));

            clearInputFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Enter valid numeric values for attendance.");
        }
    }

    private double parseDouble(String text, double defaultValue) {
        if (text.isEmpty()) return defaultValue;
        try {
            double value = Double.parseDouble(text);
            return Math.max(0, Math.min(100, value)); // Clamp 0-100
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    // ===== CO2/CO3: String-based search =====
    private void searchAttendance() {
        String roll = txtSearchRoll.getText().trim();
        String subject = txtSearchSubject.getText().trim();

        if (roll.isEmpty() || subject.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter both Roll Number and Subject.");
            return;
        }

        AttendanceComponents found = manager.searchAttendance(roll, subject);
        if (found != null) {
            double attendancePercent = AttendanceCalculator.calculateFinalAttendance(found);
            String details = AttendanceCalculator.getCalculationDetails(found);
            txtAreaOutput.setText(String.format(
                    "Found: %s (%s) in %s with Final Attendance: %.1f%%\n%s\n",
                    found.getStudentName(), found.getStudentRollNumber(),
                    found.getSubject(), attendancePercent, details));
        } else {
            txtAreaOutput.setText("No attendance record found for given details.\n");
        }
    }

    // ===== CO5: Using inherited min/max methods =====
    private void showLeastAttendance() {
        AttendanceComponents least = manager.getStudentWithLeastAttendance();
        if (least != null) {
            txtAreaOutput.setText(String.format(
                    "Student with Least Attendance: %s (%s) in %s: %.1f%%\n",
                    least.getStudentName(), least.getStudentRollNumber(),
                    least.getSubject(),
                    AttendanceCalculator.calculateFinalAttendance(least)));
        } else {
            txtAreaOutput.setText("No attendance records found.\n");
        }
    }

    private void showMostAttendance() {
        AttendanceComponents most = manager.getStudentWithMostAttendance();
        if (most != null) {
            txtAreaOutput.setText(String.format(
                    "Student with Most Attendance: %s (%s) in %s: %.1f%%\n",
                    most.getStudentName(), most.getStudentRollNumber(),
                    most.getSubject(),
                    AttendanceCalculator.calculateFinalAttendance(most)));
        } else {
            txtAreaOutput.setText("No attendance records found.\n");
        }
    }

    // ===== CO2 & CO3: Arrays + recursion demo inside sorted display =====
    // ✅ TABLE CLEARS AUTOMATICALLY HERE
    private void displaySortedAttendance() {
        List<AttendanceComponents> sorted = manager.getAttendanceSortedAscending();
        
        // ✅ TABLE CLEARS HERE - Resets all rows before adding new data
        tableModel.setRowCount(0);  // ← THIS CLEARS THE SORTED TABLE
        
        for (AttendanceComponents ac : sorted) {
            double finalAtt = AttendanceCalculator.calculateFinalAttendance(ac);
            String components = AttendanceCalculator.getCalculationDetails(ac).replace("Components used: ", "");
            tableModel.addRow(new Object[]{
                ac.getStudentName(), ac.getStudentRollNumber(),
                ac.getSubject(), String.format("%.1f", finalAtt), components
            });
        }

        // Build array and find min using recursion (CO2 + CO3)
        double[] attArray = AttendanceAnalytics.buildFinalAttendanceArray(manager.attendanceMap.values());
        if (attArray.length > 0) {
            double minByRec = AttendanceAnalytics.findMinRecursive(attArray, 0, attArray[0]);
            txtAreaOutput.setText(String.format("Displayed records sorted ascending.\n" +
                    "Recursive minimum attendance (CO2/CO3 demo): %.1f%%\n", minByRec));
        } else {
            txtAreaOutput.setText("No records to display.\n");
        }
    }

    private void clearInputFields() {
        txtStudentName.setText("");
        txtRollNumber.setText("");
        txtSubject.setText("");
        txtLecture.setText("");
        txtTutorial.setText("");
        txtPractical.setText("");
        txtSkill.setText("");
    }

    // ==================== MAIN METHOD ====================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main app = new Main();
            app.setVisible(true);
        });
    }
}
//final code with delete and clear and ascending also with search with roll number and name