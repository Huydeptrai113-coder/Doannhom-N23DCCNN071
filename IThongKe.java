import java.util.ArrayList;

public interface IThongKe {
    /**
     * Thống kê số lượng sinh viên theo từng ngành.
     */
    void thongKeTheoNganh(ArrayList<SinhVien> ds);

    /**
     * Thống kê số lượng sinh viên theo giới tính.
     */
    void thongKeTheoGioiTinh(ArrayList<SinhVien> ds);

    /**
     * Tìm và hiển thị sinh viên có điểm TB cao nhất và thấp nhất.
     */
    void timTopSinhVien(ArrayList<SinhVien> ds);
}