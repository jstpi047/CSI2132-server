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

import hotelchain.beans.Chain_admin;
import hotelchain.beans.Employee;
import hotelchain.beans.Hotel;
import hotelchain.beans.Room;
import hotelchain.beans.UserAccount;
import hotelchain.utils.DBUtils;
import hotelchain.utils.MyUtils;
import hotelchain.response.mod.LoginResponse;
import hotelchain.response.mod.SignupResponse;
import hotelchain.response.mod.JWTResponse;

import com.google.gson.*;
import java.util.*;
import java.text.SimpleDateFormat;

@WebServlet(urlPatterns = { "/newRoom" })
public class NewRoomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public NewRoomServlet() {
		super();
	}

	// for creation of a new room
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
		Room room = new Room(1, null, null, 1, 1, null, false);

		try {
			room = g.fromJson(jb.toString(), Room.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}

		boolean hasError = false;
		String errorString = "";

		Connection conn = MyUtils.getStoredConnection(request);
		try {
			// Creates a new room
			DBUtils.createRoom(conn, room);

		} catch (SQLException e) {
			e.printStackTrace();

		}

		response.setContentType("application/json");
		String json = new Gson().toJson(room);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);

	}

}
