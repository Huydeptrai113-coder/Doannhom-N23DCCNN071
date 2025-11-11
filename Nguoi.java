import java.io.Serializable;
import java.util.Scanner;

/**
 * Lớp cha (cơ sở) chứa các thông tin chung.
 * Phải implements Serializable để có thể ghi/đọc file đối tượng.
 */
public class Nguoi implements Serializable {
    protected String hoTen;
    protected String ngaySinh; // Có thể dùng LocalDate để nâng cao
    protected String gioiTinh; // Có thể dùng enum {NAM, NU}

    // --- Constructors ---
    public Nguoi() {
    }

    public Nguoi(String hoTen, String ngaySinh, String gioiTinh) {
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
    }

    // --- Getters and Setters ---
    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    // --- Phương thức nghiệp vụ ---

    /**
     * Nhập thông tin cơ bản cho Nguoi.
     * @param sc Đối tượng Scanner từ bên ngoài truyền vào.
     */
    public void nhapThongTin(Scanner sc) {
        System.out.print("  Nhap ho ten: ");
        this.hoTen = sc.nextLine();
        System.out.print("  Nhap ngay sinh (dd/mm/yyyy): ");
        this.ngaySinh = sc.nextLine();
        System.out.print("  Nhap gioi tinh (Nam/Nu): ");
        this.gioiTinh = sc.nextLine();
    }

    /**
     * Hiển thị thông tin cơ bản của Nguoi.
     */
    public void hienThiThongTin() {
        System.out.printf("  Ho ten: %s\n", this.hoTen);
        System.out.printf("  Ngay sinh: %s\n", this.ngaySinh);
        System.out.printf("  Gioi tinh: %s\n", this.gioiTinh);
    }
}