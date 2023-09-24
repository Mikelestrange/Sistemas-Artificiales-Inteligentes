/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package agents;

/**
 *
 * @author Miguel
 */
import jade.core.Agent;
import behaviours.RequestPerformer;
import jade.core.AID;
import jade.core.behaviours.*;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import gui.BookBuyerGui;
import javax.swing.JOptionPane;

public class BookBuyerAgent extends Agent {
  private String bookTitle;
  public BookBuyerGui gui;
  private AID[] sellerAgents;
  private int ticker_timer = 10000;
  private BookBuyerAgent this_agent = this;
  
  protected void setup() {
    gui = new BookBuyerGui(this);
    gui.showGui();
    System.out.println("Buyer agent " + getAID().getName() + " is ready");
  }
  public void BookBuyer(final String libro) {
     if(libro != null && libro.length() > 0) {
            bookTitle = libro;
            addBehaviour(new TickerBehaviour(this, ticker_timer) {
            @Override
            protected void onTick() {
                System.out.println("Intentando comprar: " + bookTitle);
                gui.msg.setText("Intentando comprar: " + bookTitle);
                DFAgentDescription template = new DFAgentDescription();
                ServiceDescription sd = new ServiceDescription();
                sd.setType("book-selling");
                template.addServices(sd);

                try {
                    gui.msg.setText("Espere un momento...");
                    DFAgentDescription[] result = DFService.search(myAgent, template);
                    String msgVendedores="";
                    sellerAgents = new AID[result.length];
                    for(int i = 0; i < result.length; i++) {
                        sellerAgents[i] = result[i].getName();
                        msgVendedores = msgVendedores+sellerAgents[i].getName()+"/n";
                    }
                    gui.msg.setText(msgVendedores);
                }catch(FIPAException fe) {
                    JOptionPane.showMessageDialog(gui, fe,"Exception",JOptionPane.ERROR_MESSAGE);
                }

                myAgent.addBehaviour(new RequestPerformer(this_agent));
            }
            });
        } else {
            JOptionPane.showMessageDialog(gui,"No hay vendedores para el libro: "+bookTitle,"Comprador",JOptionPane.INFORMATION_MESSAGE);
            doDelete();
        }
    }

  
  protected void takeDown() {
    System.out.println("Buyer agent " + getAID().getName() + " terminating");
  }
  
  public AID[] getSellerAgents() {
    return sellerAgents;
  }
  
  public String getBookTitle() {
    return bookTitle;
  }
}