<<<<<<< HEAD
package connectdb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SinhVienDAO extends BaseDAO {

    // Helper: lấy điểm thứ index, nếu thiếu thì trả 0.0
    private double getDiemAt(SinhVien sv, int index) {
        if (sv.getDanhSachDiem() == null || sv.getDanhSachDiem().size() <= index) {
            return 0.0;
        }
        Double d = sv.getDanhSachDiem().get(index);
        return d != null ? d : 0.0;
    }

    // Helper: map 1 dòng ResultSet -> SinhVien (đỡ lặp)
    private SinhVien mapRowToSinhVien(ResultSet rs) throws SQLException {
        return new SinhVien(
                rs.getString("mssv"),
                rs.getString("hoTen"),
                rs.getString("ngaySinh"),   // DATE nhưng lấy String cũng ok
                rs.getString("gioiTinh"),
                rs.getString("nganhHoc"),
                rs.getDouble("diem1"),
                rs.getDouble("diem2"),
                rs.getDouble("diem3")
        );
    }

    // 1. Lấy toàn bộ danh sách từ DB
    public List<SinhVien> getAll() {
        List<SinhVien> list = new ArrayList<>();
        Connection conn = getConnection();
        String sql = "SELECT * FROM sinhvien";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRowToSinhVien(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn, ps, rs);
        }
        return list;
    }

    // 1b. Lấy tất cả SV sắp xếp theo tên
    public List<SinhVien> getAllSortedByName() {
        List<SinhVien> list = new ArrayList<>();
        Connection conn = getConnection();
        String sql = "SELECT * FROM sinhvien ORDER BY hoTen ASC";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRowToSinhVien(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn, ps, rs);
        }
        return list;
    }

    // 1c. Lấy tất cả SV sắp xếp theo điểm TB giảm dần
    public List<SinhVien> getAllSortedByDiemTB() {
        List<SinhVien> list = new ArrayList<>();
        Connection conn = getConnection();
        String sql = "SELECT * FROM sinhvien "
                   + "ORDER BY (diem1 + diem2 + diem3) / 3 DESC";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRowToSinhVien(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn, ps, rs);
        }
        return list;
    }

    // 2. Thêm sinh viên mới
    public boolean insert(SinhVien sv) {
        Connection conn = getConnection();
        String sql = "INSERT INTO sinhvien"
                + " (mssv, hoTen, ngaySinh, gioiTinh, nganhHoc, diem1, diem2, diem3)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, sv.getMssv());
            ps.setString(2, sv.getHoTen());
            ps.setString(3, sv.getNgaySinh());
            ps.setString(4, sv.getGioiTinh());
            ps.setString(5, sv.getNganhHoc());
            ps.setDouble(6, getDiemAt(sv, 0));
            ps.setDouble(7, getDiemAt(sv, 1));
            ps.setDouble(8, getDiemAt(sv, 2));

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnection(conn, ps, null);
        }
    }

    // 3. Sửa sinh viên (mssv cũ -> svMoi)
    public boolean update(String mssvCu, SinhVien svMoi) {
        Connection conn = getConnection();
        String sql = "UPDATE sinhvien SET "
                + "mssv = ?, "
                + "hoTen = ?, "
                + "ngaySinh = ?, "
                + "gioiTinh = ?, "
                + "nganhHoc = ?, "
                + "diem1 = ?, "
                + "diem2 = ?, "
                + "diem3 = ? "
                + "WHERE mssv = ?";
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, svMoi.getMssv());
            ps.setString(2, svMoi.getHoTen());
            ps.setString(3, svMoi.getNgaySinh());
            ps.setString(4, svMoi.getGioiTinh());
            ps.setString(5, svMoi.getNganhHoc());
            ps.setDouble(6, getDiemAt(svMoi, 0));
            ps.setDouble(7, getDiemAt(svMoi, 1));
            ps.setDouble(8, getDiemAt(svMoi, 2));
            ps.setString(9, mssvCu);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnection(conn, ps, null);
        }
    }

    // 4. Xóa sinh viên theo mssv
    public boolean delete(String mssv) {
        Connection conn = getConnection();
        String sql = "DELETE FROM sinhvien WHERE mssv = ?";
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, mssv);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnection(conn, ps, null);
        }
    }

    // 5. Tìm kiếm theo tên (gần đúng)
    public List<SinhVien> searchByName(String keyword) {
        List<SinhVien> list = new ArrayList<>();
        Connection conn = getConnection();
        String sql = "SELECT * FROM sinhvien WHERE hoTen LIKE ?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRowToSinhVien(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn, ps, rs);
        }
        return list;
    }
}
=======
package connectdb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SinhVienDAO extends BaseDAO {

    // Helper: lấy điểm thứ index, nếu thiếu thì trả 0.0
    private double getDiemAt(SinhVien sv, int index) {
        if (sv.getDanhSachDiem() == null || sv.getDanhSachDiem().size() <= index) {
            return 0.0;
        }
        Double d = sv.getDanhSachDiem().get(index);
        return d != null ? d : 0.0;
    }

    // Helper: map 1 dòng ResultSet -> SinhVien (đỡ lặp)
    private SinhVien mapRowToSinhVien(ResultSet rs) throws SQLException {
        return new SinhVien(
                rs.getString("mssv"),
                rs.getString("hoTen"),
                rs.getString("ngaySinh"),   // DATE nhưng lấy String cũng ok
                rs.getString("gioiTinh"),
                rs.getString("nganhHoc"),
                rs.getDouble("diem1"),
                rs.getDouble("diem2"),
                rs.getDouble("diem3")
        );
    }

    // 1. Lấy toàn bộ danh sách từ DB
    public List<SinhVien> getAll() {
        List<SinhVien> list = new ArrayList<>();
        Connection conn = getConnection();
        String sql = "SELECT * FROM sinhvien";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRowToSinhVien(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn, ps, rs);
        }
        return list;
    }

    // 1b. Lấy tất cả SV sắp xếp theo tên
    public List<SinhVien> getAllSortedByName() {
        List<SinhVien> list = new ArrayList<>();
        Connection conn = getConnection();
        String sql = "SELECT * FROM sinhvien ORDER BY hoTen ASC";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRowToSinhVien(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn, ps, rs);
        }
        return list;
    }

    // 1c. Lấy tất cả SV sắp xếp theo điểm TB giảm dần
    public List<SinhVien> getAllSortedByDiemTB() {
        List<SinhVien> list = new ArrayList<>();
        Connection conn = getConnection();
        String sql = "SELECT * FROM sinhvien "
                   + "ORDER BY (diem1 + diem2 + diem3) / 3 DESC";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRowToSinhVien(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn, ps, rs);
        }
        return list;
    }

    // 2. Thêm sinh viên mới
    public boolean insert(SinhVien sv) {
        Connection conn = getConnection();
        String sql = "INSERT INTO sinhvien"
                + " (mssv, hoTen, ngaySinh, gioiTinh, nganhHoc, diem1, diem2, diem3)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, sv.getMssv());
            ps.setString(2, sv.getHoTen());
            ps.setString(3, sv.getNgaySinh());
            ps.setString(4, sv.getGioiTinh());
            ps.setString(5, sv.getNganhHoc());
            ps.setDouble(6, getDiemAt(sv, 0));
            ps.setDouble(7, getDiemAt(sv, 1));
            ps.setDouble(8, getDiemAt(sv, 2));

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnection(conn, ps, null);
        }
    }

    // 3. Sửa sinh viên (mssv cũ -> svMoi)
    public boolean update(String mssvCu, SinhVien svMoi) {
        Connection conn = getConnection();
        String sql = "UPDATE sinhvien SET "
                + "mssv = ?, "
                + "hoTen = ?, "
                + "ngaySinh = ?, "
                + "gioiTinh = ?, "
                + "nganhHoc = ?, "
                + "diem1 = ?, "
                + "diem2 = ?, "
                + "diem3 = ? "
                + "WHERE mssv = ?";
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, svMoi.getMssv());
            ps.setString(2, svMoi.getHoTen());
            ps.setString(3, svMoi.getNgaySinh());
            ps.setString(4, svMoi.getGioiTinh());
            ps.setString(5, svMoi.getNganhHoc());
            ps.setDouble(6, getDiemAt(svMoi, 0));
            ps.setDouble(7, getDiemAt(svMoi, 1));
            ps.setDouble(8, getDiemAt(svMoi, 2));
            ps.setString(9, mssvCu);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnection(conn, ps, null);
        }
    }

    // 4. Xóa sinh viên theo mssv
    public boolean delete(String mssv) {
        Connection conn = getConnection();
        String sql = "DELETE FROM sinhvien WHERE mssv = ?";
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, mssv);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnection(conn, ps, null);
        }
    }

    // 5. Tìm kiếm theo tên (gần đúng)
    public List<SinhVien> searchByName(String keyword) {
        List<SinhVien> list = new ArrayList<>();
        Connection conn = getConnection();
        String sql = "SELECT * FROM sinhvien WHERE hoTen LIKE ?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRowToSinhVien(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn, ps, rs);
        }
        return list;
    }
}
>>>>>>> 02cc1f1 (update)
