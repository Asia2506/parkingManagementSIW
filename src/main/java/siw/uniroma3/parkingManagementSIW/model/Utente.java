package siw.uniroma3.parkingManagementSIW.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Utente {

		@Id
		private String username;
		private String nome;
		private String cognome;
		private String password;
		
		
		
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
		
		
}
