package gui;

/*
* PROYECTO UNIDAD 1 AGENTE-COMPRADOR
* 19290936 Miguel Angel Perez Anacleto
*/
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import agents.BookBuyerAgent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class BookBuyerGui extends JFrame{
    private BookBuyerAgent myAgent;
 
    private JTextField libroF;
    public JTextArea msg = new JTextArea("");
    public BookBuyerGui(BookBuyerAgent a) {
        super(a.getLocalName());

        myAgent = a;

        JPanel p = new JPanel();
        JPanel pTitulo = new JPanel();
        JPanel pMsg = new JPanel();
        pMsg.setLayout(new GridLayout(1,1));
        pTitulo.setLayout(new GridLayout(1,2));
        p.setLayout(new GridLayout(3, 1));
        pTitulo.add(new JLabel("Titulo de libro:"));
        libroF = new JTextField(15);
        pTitulo.add(libroF);

        msg.setLineWrap(true);
        msg.setWrapStyleWord(true);
        msg.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(msg);
        pMsg.add(scrollPane);
        p.add(pTitulo);
        p.add(pMsg);
        getContentPane().add(p, BorderLayout.CENTER);
   
        JButton addButton = new JButton("Comprar");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                try {
                    String libro = libroF.getText().trim();
                    myAgent.BookBuyer(libro);
                    libroF.setText("");
                    msg.setText("Espere...");
                }catch(Exception e) {
                    JOptionPane.showMessageDialog(BookBuyerGui.this, "Invalid values","Exception",JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        p = new JPanel();
        p.add(addButton);
        getContentPane().add(p, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
               // myAgent.doDelete();
            }
        });
        setResizable(true);
    }

    public void showGui() {
        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (int)screenSize.getWidth() / 2;
        int centerY = (int)screenSize.getHeight() / 2;

        setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
        super.setVisible(true);
    }
}