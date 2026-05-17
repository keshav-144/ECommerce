package in.spring.ecom.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "customers")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private boolean banned;
	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
	private User user;
	public Customer() {
		super();
	}
	public Customer(int id, boolean banned, User user) {
		super();
		this.id = id;
		this.banned = banned;
		this.user = user;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isBanned() {
		return banned;
	}
	public void setBanned(boolean banned) {
		this.banned = banned;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}

