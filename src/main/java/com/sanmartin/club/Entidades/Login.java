package com.sanmartin.club.Entidades;

import java.sql.SQLException;
import java.sql.Statement;



import javax.swing.JOptionPane;

import org.jboss.logging.Logger;

import com.sanmartin.club.ConexionMySQL;





public class Login {
	public void jButtonActionPerformed(java.awt.event.ActionEvent evt, Statement sentencia, String username, String password) {
		try {
			ConexionMySQL con= new ConexionMySQL();
			String u = username;
			String c = password;
			con.ConectarBasedeDatos();
			String SQL = "SELECT COUNT(id) AS i FROM socio WHERE socio_nombre='"+u+"'"+"AND socio_password ='"+c+"'";
			con.resultado= sentencia.executeQuery(SQL);
			while(con.resultado.next()) {
				if (con.resultado.getString("i").equals("1")) {
					setVisible(false);
					Login login =new Login();
					login.setVisible(true);
				}else {
					JOptionPane.showMessageDialog(null, "Usuario o contraseña invalida");
				}
			}
			con.DesconectarBasedeDatos();
			
		}catch (SQLException ex) {
			Logger.getLogger(Login.class.getName());
		
}
}

	public void setVisible(boolean b) {
		b=false;
		
	}
}