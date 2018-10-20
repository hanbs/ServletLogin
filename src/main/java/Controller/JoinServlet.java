package Controller;

import DAO.MemberDAO;
import dto.MemberDTO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/join.do")
public class JoinServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("member/join.jsp");
        dispatcher.forward(request, response);
    }

        protected void doPost(HttpServletRequest request,
                              HttpServletResponse response) throws ServletException, IOException {
            request.setCharacterEncoding("UTF-8");

                String name = request.getParameter("name");
                String userid = request.getParameter("userid");
                String pwd = request.getParameter("pwd");
                String email = request.getParameter("email");
                String phone = request.getParameter("phone");
                String admin = request.getParameter("admin");

                MemberDTO mVo = new MemberDTO();

                mVo.setName(name);
                mVo.setUserid(userid);
                mVo.setPwd(pwd);
                mVo.setEmail(email);
                mVo.setPhone(phone);
                mVo.setAdmin(Integer.parseInt(admin));

                MemberDAO mDao = MemberDAO.getInstance();

                int result = mDao.insertMember(mVo);

                HttpSession session = request.getSession();
            if (result == 1) {
                session.setAttribute("userid", mVo.getUserid());
                request.setAttribute("message", "회원 가입에 성공했습니다.");
            } else {
                request.setAttribute("message", "회원 가입에 실패했습니다.");
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher("member/login.jsp");
            dispatcher.forward(request, response);
        }
}
