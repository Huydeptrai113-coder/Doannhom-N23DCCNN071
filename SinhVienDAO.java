package connectdb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class SinhVienDAO extends BaseDAO {

    // Giữ nguyên tên hàm này
    private double getDiemAt(SinhVien sv, int index) {
        if (sv.getDanhSachDiem() == null || sv.getDanhSachDiem().size() <= index) {
            return 0.0;
        }
        Double d = sv.getDanhSachDiem().get(index);
        return d != null ? d : 0.0;
    }

    private SinhVien mapRowToSinhVien(ResultSet rs) throws SQLException {
        return new SinhVien(
            rs.getString("mssv"),
            rs.getString("hoTen"),
            rs.getString("ngaySinh"),
            rs.getString("gioiTinh"),
            rs.getString("nganhHoc"),
            rs.getDouble("diem1"),
            rs.getDouble("diem2"),
            rs.getDouble("diem3")
        );
    }

    // Đóng kết nối
    private void close(Connection conn, PreparedStatement ps, ResultSet rs) {
        closeConnection(conn, ps, rs);
    }

    // ================== SELECT ==================

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
            close(conn, ps, rs);
        }
        return list;
    }

    // Mấy hàm search giữ nguyên tên
    public SinhVien findByMssv(String mssv) {
        SinhVien sv = null;
        Connection conn = getConnection();
        String sql = "SELECT * FROM sinhvien WHERE mssv = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, mssv);
            rs = ps.executeQuery();
            if (rs.next()) {
                sv = mapRowToSinhVien(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, ps, rs);
        }
        return sv;
    }

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
            close(conn, ps, rs);
        }
        return list;
    }

    public List<SinhVien> searchByNganh(String keyword) {
        List<SinhVien> list = new ArrayList<>();
        Connection conn = getConnection();
        String sql = "SELECT * FROM sinhvien WHERE nganhHoc LIKE ?";
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
            close(conn, ps, rs);
        }
        return list;
    }

    public Map<String, Integer> thongKeTheoGioiTinh() {
        Map<String, Integer> kq = new HashMap<>();
        Connection conn = getConnection();
        String sql = "SELECT gioiTinh, COUNT(*) AS soLuong FROM sinhvien GROUP BY gioiTinh";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                kq.put(rs.getString("gioiTinh"), rs.getInt("soLuong"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, ps, rs);
        }
        return kq;
    }

    // ================== CRUD (Giữ nguyên tên hàm) ==================

    public boolean insert(SinhVien sv) {
        Connection conn = getConnection();
        String sql = "INSERT INTO sinhvien (mssv, hoTen, ngaySinh, gioiTinh, nganhHoc, diem1, diem2, diem3) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
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
            close(conn, ps, null);
        }
    }

    public boolean update(String mssvCu, SinhVien svMoi) {
        Connection conn = getConnection();
        String sql = "UPDATE sinhvien SET mssv=?, hoTen=?, ngaySinh=?, gioiTinh=?, nganhHoc=?, diem1=?, diem2=?, diem3=? WHERE mssv=?";
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
            close(conn, ps, null);
        }
    }

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
            close(conn, ps, null);
        }
    }
}
