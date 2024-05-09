
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAO {
	Connection conn = null;
	PreparedStatement psmt = null;
	ResultSet rs = null;

	public int getUserHighestScores(String userId) {
		int highestScoreH = 0;
		int highestScoreE = 0;

		try {
			connection();

			String sql = "SELECT MAX(SCORE_H) AS MAX_SCORE_H, MAX(SCORE_E) AS MAX_SCORE_E FROM RANK WHERE USER_ID=? GROUP BY USER_NIC";
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, userId);
			rs = psmt.executeQuery();

			if (rs.next()) {
				highestScoreH = rs.getInt("MAX_SCORE_H");
				highestScoreE = rs.getInt("MAX_SCORE_E");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		System.out.println("★ 이지모드 최고 점수 : " + highestScoreE);
		System.out.println("☆ 하드모드 최고 점수 : " + highestScoreH);

		return highestScoreH + highestScoreE;
	}

	public int join(DTO dto) {
		int cnt = 0;
		try {
			connection();

			String sql = "INSERT INTO MEMBER VALUES(?,?,?)";
			psmt = conn.prepareStatement(sql);

			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getPw());
			psmt.setString(3, dto.getNic());

			cnt = psmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return cnt;
	}

	public DTO login(String id, String pw) {
		connection();

		DTO info = null;
		String sql = "SELECT * FROM MEMBER WHERE USER_ID=? AND USER_PW=?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			psmt.setString(2, pw);

			rs = psmt.executeQuery();

			if (rs.next() == true) {
				String login_id = rs.getString(1);
				String login_pw = rs.getString(2);
				String login_nic = rs.getString(3);

				info = new DTO(login_id, login_pw, login_nic);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return info;
	}

	public ArrayList<DTO> QuestionList() {
		ArrayList<DTO> list = new ArrayList<DTO>();
		try {
			connection();
			String sql = "SELECT PB_Q, PB_A FROM PROBLEM";
			psmt = conn.prepareStatement(sql);

			rs = psmt.executeQuery();

			while (rs.next()) {
				String que = rs.getString("PB_Q");
				String ans = rs.getString("PB_A");
				DTO dto = new DTO(que, ans);
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return list;
	}

	private void close() {
		try {
			if (rs != null) {
				psmt.close();
			}
			if (psmt != null) {
				psmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void connection() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			String db_url = "jdbc:oracle:thin:@project-db-campus.smhrd.com:1524:xe";
			String db_id = "campus_23K_AI18_p1_2";
			String db_pw = "smhrd2";

			conn = DriverManager.getConnection(db_url, db_id, db_pw);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 테이블에 점수 저장.
	public void save(DTO dto) {
		connection();

		String sql = "INSERT INTO RANK VALUES(?,?,?,?)";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getNic());
			psmt.setInt(3, dto.getRankH());
			psmt.setInt(4, dto.getRankL());

			psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
}
