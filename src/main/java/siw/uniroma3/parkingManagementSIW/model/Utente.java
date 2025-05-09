package siw.uniroma3.parkingManagementSIW.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Utente {

		@Id
		private String username;
		private String nome;
		private String cognome;
		private String password;
		@OneToMany
		private List<Operazione> operazioni;
		
		
		/*----METODI EUQALS AND HASHCODE----*/
		@Override
		public int hashCode() {
			return Objects.hash(username);
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Utente other = (Utente) obj;
			return Objects.equals(username, other.username);
		}
		
		
		
		/*----METODI GETTERS AND SETTERS----*/
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		
		public String getNome() {
			return nome;
		}
		public void setNome(String nome) {
			this.nome = nome;
		}
		
		public String getCognome() {
			return cognome;
		}
		public void setCognome(String cognome) {
			this.cognome = cognome;
		}
		
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public List<Operazione> getOperazioni() {
			return operazioni;
		}
		public void setOperazioni(List<Operazione> operazioni) {
			this.operazioni = operazioni;
		}
		
		
		
}
