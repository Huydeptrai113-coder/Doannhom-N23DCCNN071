import java.awt.*;
import java.io.IOException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

class LoginForm extends JFrame {
    private JTextField txtUser;
    private JPasswordField txtPass;
    private JButton btnLogin, btnExit;

    public LoginForm() {
        setTitle("ĐĂNG NHẬP HỆ THỐNG");
        setSize(300,160);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - getWidth()) / 2;
        int y = (screen.height - getHeight()) / 2;
        setLocation(x, y);

        setLayout(new GridLayout(3,2,10,10));

        add(new JLabel("Username:"));
        txtUser = new JTextField();
        add(txtUser);

        add(new JLabel("Password:"));
        txtPass = new JPasswordField();
        add(txtPass);

        btnLogin = new JButton("Login");
        btnExit = new JButton("Exit");
        add(btnLogin);
        add(btnExit);

        btnLogin.addActionListener(e -> {
            String user = txtUser.getText().trim();
            String pass = new String(txtPass.getPassword());

            if(user.equals("admin") && pass.equals("123")) {
                JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");
                new Giaodienqlsv().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Sai Username hoặc Password!");
            }
        });

        btnExit.addActionListener(e -> System.exit(0));
    }
}
public class Giaodienqlsv extends JFrame {
    private QuanLySinhVien quanLy = new QuanLySinhVien();
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtMSSV, txtHoTen, txtNgaySinh, txtNganh, txtDiem;
    private JRadioButton male, female;
    private JTextArea txtThongKe;
    private JButton btnThem, btnXoa, btnSua, btnTimMSSV, btnTimTen, btnTimNganh, btnSapXepTen, btnSapXepTB, btnGhiFile, btnDocFile, btnThongKeNganh, btnThongKeGioiTinh, btnXepHang;
    private JFileChooser fileChooser = new JFileChooser();
    public Giaodienqlsv() {
        setTitle("QUẢN LÝ SINH VIÊN");
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - getWidth()) / 2;
        int y = (screen.height - getHeight()) / 2;
        setLocation(x, y);
        // ===== FORM NHẬP =====
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Thông Tin Sinh Viên"));

        txtMSSV = new JTextField();
        txtHoTen = new JTextField();
        txtNgaySinh = new JTextField();
        txtNganh = new JTextField();
        txtDiem = new JTextField();

        male = new JRadioButton("Nam", true);
        female = new JRadioButton("Nữ");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(male);
        genderGroup.add(female);

        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        genderPanel.add(male);
        genderPanel.add(female);

        inputPanel.add(new JLabel("MSSV: *"));
        inputPanel.add(txtMSSV);
        inputPanel.add(new JLabel("Họ Tên: *"));
        inputPanel.add(txtHoTen);
        inputPanel.add(new JLabel("Ngày Sinh (dd/mm/yyyy):"));
        inputPanel.add(txtNgaySinh);
        inputPanel.add(new JLabel("Giới Tính:"));
        inputPanel.add(genderPanel);
        inputPanel.add(new JLabel("Ngành:"));
        inputPanel.add(txtNganh);
        inputPanel.add(new JLabel("Điểm (ngăn cách bởi ,):"));
        inputPanel.add(txtDiem);

        add(inputPanel, BorderLayout.NORTH);

        // ===== TABLE =====
        String[] columns = {"MSSV", "Họ Tên", "Ngày Sinh", "Giới Tính", "Ngành", "Điểm TB"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // ===== BUTTON CHỨC NĂNG =====
        JPanel funcPanel = new JPanel(new GridLayout(3, 3, 10, 10));
        funcPanel.setBorder(BorderFactory.createTitledBorder("Chức Năng"));

        btnThem = new JButton("Thêm");
        btnXoa = new JButton("Xóa");
        btnSua = new JButton("Sửa");
        btnTimMSSV = new JButton("Tìm MSSV");
        btnTimTen = new JButton("Tìm Tên");
        btnTimNganh = new JButton("Tìm Ngành");
        btnSapXepTen = new JButton("Sắp Theo Tên");
        btnSapXepTB = new JButton("Sắp Theo Điểm TB");
        btnThongKeNganh = new JButton("Thống Kê Ngành");
        btnThongKeGioiTinh = new JButton("Thống Kê Giới Tính");
        btnXepHang = new JButton("Xếp Hạng Điểm TB");
        btnGhiFile = new JButton("Save");
        btnGhiFile.addActionListener(e -> saveFile());
        btnDocFile = new JButton("Đọc File");
        btnDocFile.addActionListener(e -> loadFile());


        funcPanel.add(btnThem); funcPanel.add(btnXoa); funcPanel.add(btnSua); funcPanel.add(btnTimMSSV);
        funcPanel.add(btnTimTen); funcPanel.add(btnTimNganh); funcPanel.add(btnSapXepTen); funcPanel.add(btnSapXepTB);
        funcPanel.add(btnThongKeNganh); funcPanel.add(btnThongKeGioiTinh); funcPanel.add(btnXepHang);
        funcPanel.add(btnGhiFile); funcPanel.add(btnDocFile);

        add(funcPanel, BorderLayout.SOUTH);

        // ===== PANEL THỐNG KÊ =====
        txtThongKe = new JTextArea();
        txtThongKe.setFont(new Font("Monospaced", Font.PLAIN, 13));
        txtThongKe.setEditable(false);
        JScrollPane statsScroll = new JScrollPane(txtThongKe);
        statsScroll.setBorder(BorderFactory.createTitledBorder("Thống Kê"));
        statsScroll.setPreferredSize(new Dimension(300, 400));
        add(statsScroll, BorderLayout.EAST);
    }
     private void capNhatBang() {
        tableModel.setRowCount(0);
        for (SinhVien sv : quanLy.getDanhSachSV()) {
            tableModel.addRow(new Object[]{
                sv.getMssv(), sv.getHoTen(), sv.getNgaySinh(),
                sv.getGioiTinh(),  sv.getNganhHoc(),String.format("%.2f", sv.tinhDiemTB())
            });
        }
    }

    private void saveFile() {
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                String filePath = fileChooser.getSelectedFile().getPath();
                if (!filePath.toLowerCase().endsWith(".txt")) filePath += ".txt";
                quanLy.ghiFile(filePath);
                JOptionPane.showMessageDialog(this, "Lưu thành công!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi lưu: " + e.getMessage());
            }
        }
    }

    private void loadFile() {
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                String filePath = fileChooser.getSelectedFile().getPath();
                quanLy.docFile(filePath);
                capNhatBang();
                JOptionPane.showMessageDialog(this, "Đọc thành công!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi đọc file!");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }

}