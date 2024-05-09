package controller;

import java.util.ArrayList;
import model.DAO;
import model.DTO;

public class Controller {
	private DAO dao = new DAO();

	public int join(DTO dto) {
		return dao.join(dto);
	}

	public DTO login(String id, String pw) {
		return dao.login(id, pw);
	}

	public int getUserHighestScores(String userId) {
		return dao.getUserHighestScores(userId);
	}

	public ArrayList<DTO> getQuestionList() {
		return dao.QuestionList();
	}

	public void save(DTO dto) {
		dao.save(dto);
	}
}