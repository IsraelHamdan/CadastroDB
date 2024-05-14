
package cadastrodb;

import java.sql.SQLException;


public class CadastroDB {
    private static Menu menu = new Menu(); 
    public static void main(String[] args) throws SQLException {
        menu.menu();
    }
    
}