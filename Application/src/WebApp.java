import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Servlet implementation class WebApp
 */
@WebServlet("/WebApp")
public class WebApp extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WebApp() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		Statement statement = null;
		Connection connect = null;
		PrintWriter out = response.getWriter();
		out.println("<style>table, th, td {border: 1px solid black;border-collapse: collapse;}th, td {padding: 15px;}</style>");
		try{ 
			InitialContext context = new InitialContext();
			DataSource dataSource = (DataSource) context.lookup("java:/comp/env/jdbc/mydb");
			connect = dataSource.getConnection();
			// Create the statement to be used to get the results.
			statement = connect.createStatement();
			String query = "SELECT * FROM schedule";

			// Execute the query and get the result set.
			ResultSet resultSet = statement.executeQuery(query);
			out.println("<strong>Student Schedule</strong><br>");
			out.println("<table style=\"width:100%\"><tr><th>Course</th><th>Days</th><th>Time</th><th>Instructor</th><th>Location</th></tr>");
			while (resultSet.next()) {
				String subject = resultSet.getString("subject");
				String days = resultSet.getString("days");
				String time = resultSet.getString("time");
				String instructor = resultSet.getString("prof");
				String location = resultSet.getString("class_room");
				out.println("<tr><td>" + subject + "</td><td>" + days + "</td><td>" + time + "</td><td>" + instructor + "</td><td>" + location + "</td></tr>");
			}
			out.println("</table>");
		} catch (SQLException e) { e.printStackTrace(out);
		} catch (NamingException e) { e.printStackTrace(out);
		} finally {
			// Close the connection and release the resources used.
			try { statement.close(); } catch (SQLException e) { e.printStackTrace(out); }
			try { connect.close(); } catch (SQLException e) { e.printStackTrace(out); }
		}
	}
}
