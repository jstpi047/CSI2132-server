package hotelchain.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import hotelchain.beans.UserAccount;
import hotelchain.response.mod.LoginResponse;
import hotelchain.utils.DBUtils;
import hotelchain.utils.MyUtils;
import hotelchain.utils.ValidateJWTUtils;

@WebServlet(urlPatterns = { "/userInfo" })
public class UserInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserInfoServlet() {
		super();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String sin = ValidateJWTUtils.validate(request);
		System.out.println(sin);

		UserAccount user = new UserAccount(null, null, null, null, null, null);

		if (sin == null) {
			response.setContentType("application/json");
			String json = new Gson().toJson(user);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
		}
		else {
			Connection conn = MyUtils.getStoredConnection(request);
			try {
				// Find the user in the DB.
				user = DBUtils.findInfo(conn, sin);

			} catch (SQLException e) {
				e.printStackTrace();

			}

			response.setContentType("application/json");
			String json = new Gson().toJson(user);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
		}
		
	}

}