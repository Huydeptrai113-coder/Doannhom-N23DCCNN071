import java.util.ArrayList;

public interface IFileHandler {
    /**
     * Ghi danh sách sinh viên xuống file.
     * @param ds Danh sách sinh viên cần ghi.
     * @param tenFile Tên file (ví dụ: "sinhvien.dat").
     */
    void ghiFile(ArrayList<SinhVien> ds, String tenFile) throws Exception;

    /**
     * Đọc danh sách sinh viên từ file.
     * @param tenFile Tên file (ví dụ: "sinhvien.dat").
     * @return Một ArrayList<SinhVien> đọc được từ file.
     */
    ArrayList<SinhVien> docFile(String tenFile) throws Exception;
}