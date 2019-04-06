package hotelchain.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import hotelchain.beans.Book;
import hotelchain.beans.Chain_admin;
import hotelchain.beans.Employee;
import hotelchain.beans.Hotel;
import hotelchain.beans.Rent;
import hotelchain.beans.Room;
import hotelchain.beans.UserAccount;
import hotelchain.utils.DBUtils;
import hotelchain.utils.MyUtils;
import hotelchain.response.mod.LoginResponse;
import hotelchain.response.mod.JWTResponse;

import com.google.gson.*;

@WebServlet(urlPatterns = { "/addEmployeeByHotel" })
public class AddEmployeeByHotelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddEmployeeByHotelServlet() {
		super();
	}

	// When the user enters the area of a hotel in search bad and click Search.
	// This method will be executed.
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		StringBuffer jb = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null)
				jb.append(line);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Gson g = new Gson();
		Employee emp = new Employee(null, null, null, null, null, null,null,null);

		try {
			emp = g.fromJson(jb.toString(), Employee.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}

		boolean hasError = false;
		String errorString = null;


		Connection conn = MyUtils.getStoredConnection(request);
		try {
			// Find the hotels in the DB.
			DBUtils.addEmployeeByHotel(conn, emp);
			

		} catch (SQLException e) {
			e.printStackTrace();

		}

		response.setContentType("application/json");
		String json = new Gson().toJson(emp);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);

	}

}
