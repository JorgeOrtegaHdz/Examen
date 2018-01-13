package cliente;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
/**
 *
 * @author Jorge Ortega Hernandez
 */
public class Paneltiempo extends JPanel{
    JTextField tiempo;
    int tiem=0;
    public Paneltiempo(){
        setLayout(new BorderLayout());
        tiempo=new JTextField("Tiempo transcurrido: ");
        
        Timer timer = new Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                tiem++;
                tiempo.setText("Tiempo transcurrido: "+tiem+"s");
            }
        });
        timer.start();
        add(tiempo);
    }
}
