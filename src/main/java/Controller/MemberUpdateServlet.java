package Controller;

import DAO.MemberDAO;
import dto.MemberDTO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Member;

@WebServlet("/memberUpdate.do")
public class MemberUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String userid = request.getParameter("userid");
        MemberDAO mDao = MemberDAO.getInstance();

        MemberDTO mVo = mDao.getMember(userid);
        request.setAttribute("mVo", mVo);

        RequestDispatcher dispatcher = request.getRequestDispatcher("member/memberUpdate.jsp");
        dispatcher.forward(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8"); // 한글 깨짐 방지
        // 폼에서 입력한 회원정보 얻어오기.
        String userid = request.getParameter("userid");
        String pwd = request.getParameter("pwd");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String admin = request.getParameter("admin");

        // 회원 정보를 저장 할 객체를 생성
        MemberDTO mVo = new MemberDTO();
        mVo.setUserid(userid);
        mVo.setPwd(pwd);
        mVo.setEmail(email);
        mVo.setPhone(phone);
        mVo.setAdmin(Integer.parseInt(admin));

        MemberDAO mDao = MemberDAO.getInstance();

        mDao.updateMember(mVo);

        response.sendRedirect("login.do");
    }
}
