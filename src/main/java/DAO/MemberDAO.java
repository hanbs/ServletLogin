package DAO;

import dto.MemberDTO;
import oracle.jdbc.proxy.annotation.Pre;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberDAO {

    private MemberDAO() {

    }

    private static MemberDAO instance = new MemberDAO();

    public static MemberDAO getInstance() {
        return instance;
    }

    public Connection getConnection() throws Exception {
        Connection conn = null;
        Context initContext = new InitialContext();
        Context envContext = (Context) initContext.lookup("java:/comp/env");
        DataSource ds = (DataSource) envContext.lookup("jdbc/myoracle");
        conn = ds.getConnection();
        return conn;
    }

    // 사용자 인증 시 사용하는 메소드
    public int userCheck(String userid, String pwd) {
        int result = -1;
        String sql = "select pwd from membervo where userid=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                if (rs.getString("pwd") != null && rs.getString("pwd").equals(pwd)) {
                    result = 1;
                } else {
                    result = 0;
                }
            } else {
                result = -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    // 아이디로 회원 정보를 가져오는 메소드
    public MemberDTO getMember(String userid) {
        MemberDTO mVo = null;
        String sql = "select * from membervo where userid=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                mVo = new MemberDTO();
                mVo.setName(rs.getString("name"));
                mVo.setUserid(rs.getString("userid"));
                mVo.setPwd(rs.getString("pwd"));
                mVo.setEmail(rs.getString("email"));
                mVo.setPhone(rs.getString("phone"));
                mVo.setAdmin(rs.getInt("admin"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return mVo;
    }

    // 아이디 중복 체크하는 메소드
    public int confirmID(String userid) {
        int result = -1;
        String sql = "select userid from membervo where userid=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userid);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                result = 1;
            } else {
                result = -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    // 회원 정보를 DB에 추가 하기 위한 메소드 추가
    public int insertMember(MemberDTO mVo) {
        int result = -1;
        String sql = "insert into membervo values(?,?,?,?,?,?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, mVo.getName());
            pstmt.setString(2, mVo.getUserid());
            pstmt.setString(3, mVo.getPwd());
            pstmt.setString(4, mVo.getEmail());
            pstmt.setString(5, mVo.getPhone());
            pstmt.setInt(6, mVo.getAdmin());
            result = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    // 회원 정보를 변경하기 위한 메소드 추가 하기
    public int updateMember(MemberDTO mVo){
        int result = -1;
        String sql = "update membervo set pwd=?, email=?, phone=?, admin=? where userid=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, mVo.getPwd());
            pstmt.setString(2, mVo.getEmail());
            pstmt.setString(3,mVo.getPhone());
            pstmt.setInt(4, mVo.getAdmin());
            pstmt.setString(5, mVo.getUserid());
            result = pstmt.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (pstmt != null) pstmt.close();
                if(conn != null) conn.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return  result;
    }
}