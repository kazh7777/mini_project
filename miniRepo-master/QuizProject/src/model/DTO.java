package model;

public class DTO {

	private String id;
	private String pw;
	private String nic;
	private String question;
	private String answer;
	private int rankH;
	private int rankL;

	public int getRankH() {
		return rankH;
	}

	public int getRankL() {
		return rankL;
	}

	public String getId() {
		return id;
	}

	public String getPw() {
		return pw;
	}

	public String getNic() {
		return nic;
	}

	public String getQuestion() {
		return question;
	}

	public String getAnswer() {
		return answer;
	}

	public DTO(String id, String pw, String nic) {
		this.id = id;
		this.pw = pw;
		this.nic = nic;
	}

	public DTO(String question, String answer) {
		super();
		this.question = question;
		this.answer = answer;
	}

	public DTO(String id, String nic, int rankH, int rankL) {
		super();
		this.id = id;
		this.nic = nic;
		this.rankH = rankH;
		this.rankL = rankL;
	}

}
