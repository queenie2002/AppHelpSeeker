package insa.fr.msa.OrchestratorService.model;

public class User {
	private int id;
	private String nom;
	private String mdp;
	private String status;
	private String mail;

	public User(int id, String nom, String mdp, String status, String mail) {
		super();
		this.id = id;
		this.nom = nom;
		this.mdp = mdp;
		this.status = status;
		this.mail = mail;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getMdp() {
		return mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", nom=" + nom + ", mdp=" + mdp + ", status=" + status + ", mail=" + mail + "]";
	}

}
