import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Lớp SinhVien kế thừa từ Nguoi.
 * Chứa thông tin riêng của SV và phương thức tính điểm TB.
 */
public class SinhVien extends Nguoi implements Serializable {
    private String MSSV;
    private String nganhHoc;
    private ArrayList<Double> danhSachDiem;

    // --- Constructors ---
    public SinhVien() {
        super(); // Gọi constructor của lớp Nguoi
        this.danhSachDiem = new ArrayList<>(); // Khởi tạo danh sách
    }

    public SinhVien(String MSSV, String hoTen, String ngaySinh, String gioiTinh, String nganhHoc) {
        super(hoTen, ngaySinh, gioiTinh); // Gọi constructor có tham số của Nguoi
        this.MSSV = MSSV;
        this.nganhHoc = nganhHoc;
        this.danhSachDiem = new ArrayList<>();
    }

    // --- Getters and Setters ---
    public String getMSSV() {
        return MSSV;
    }

    public void setMSSV(String MSSV) {
        this.MSSV = MSSV;
    }

    public String getNganhHoc() {
        return nganhHoc;
    }

    public void setNganhHoc(String nganhHoc) {
        this.nganhHoc = nganhHoc;
    }

    public ArrayList<Double> getDanhSachDiem() {
        return danhSachDiem;
    }

    public void setDanhSachDiem(ArrayList<Double> danhSachDiem) {
        this.danhSachDiem = danhSachDiem;
    }

    // --- Phương thức nghiệp vụ (Ghi đè và bổ sung) ---

    /**
     * Phương thức tính điểm trung bình.
     * @return Điểm trung bình, hoặc 0.0 nếu chưa có điểm.
     */
    public double tinhDiemTrungBinh() {
        if (danhSachDiem == null || danhSachDiem.isEmpty()) {
            return 0.0;
        }

        double tongDiem = 0;
        for (double diem : danhSachDiem) {
            tongDiem += diem;
        }
        return tongDiem / danhSachDiem.size();
    }

    /**
     * Ghi đè (Override) phương thức nhapThongTin của lớp cha.
     */
    @Override
    public void nhapThongTin(Scanner sc) {
        System.out.print("Nhap Ma So Sinh Vien (MSSV): ");
        this.MSSV = sc.nextLine();
        
        // Gọi phương thức của lớp cha để nhập thông tin chung
        super.nhapThongTin(sc); 
        
        System.out.print("  Nhap nganh hoc: ");
        this.nganhHoc = sc.nextLine();
        
        // Nhập danh sách điểm
        System.out.print("  Nhap so luong mon hoc: ");
        int soMon = Integer.parseInt(sc.nextLine()); // Dùng parseInt để tránh lỗi newline
        
        this.danhSachDiem = new ArrayList<>(); // Khởi tạo lại
        for (int i = 0; i < soMon; i++) {
            System.out.printf("  Nhap diem mon thu %d: ", (i + 1));
            this.danhSachDiem.add(Double.parseDouble(sc.nextLine()));
        }
    }

    /**
     * Ghi đè (Override) phương thức hienThiThongTin của lớp cha.
     */
    @Override
    public void hienThiThongTin() {
        System.out.println("---------------------------------");
        System.out.printf("MSSV: %s\n", this.MSSV);
        
        // Gọi phương thức của lớp cha để hiển thị thông tin chung
        super.hienThiThongTin(); 
        
        System.out.printf("  Nganh hoc: %s\n", this.nganhHoc);
        
        // Hiển thị điểm
        System.out.println("  Danh sach diem: " + this.danhSachDiem.toString());
        System.out.printf("  => DIEM TRUNG BINH: %.2f\n", this.tinhDiemTrungBinh());
    }
}