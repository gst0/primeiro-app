import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

class Usuario implements Serializable{
    
    private String login;
    private String senha;
    private String email;

    Usuario(String login, String email, String senha){
        this.login = login;
        this.email = email;
        this.senha = senha;
    }

    public String toString(){
        return login+" "+email+" "+senha;
    }
}

class ElementosGUI extends JFrame{

    private JTextField boxLogin;
    private JTextField boxEmail;
    private JPasswordField boxSenha;
    private JButton enter;
    private JButton cad;
    private JLabel textLogin; 
    private JLabel textEmail;
    private JLabel textSenha;

    ArrayList<Usuario> al = new ArrayList<>();
    File file = new File("bd.txt");
    ObjectOutputStream oos = null;
    ObjectInputStream ois = null;
    ListIterator li = null;

    ElementosGUI(){
        super("Cadastro");
        setLayout(new FlowLayout());

        textLogin = new JLabel("Login:");
        add(textLogin);
        boxLogin = new JTextField(20);
        add(boxLogin);
        textEmail = new JLabel("Email:");
        add(textEmail);
        boxEmail = new JTextField(20);
        add(boxEmail);
        textSenha = new JLabel("Senha:");
        add(textSenha);
        boxSenha = new JPasswordField(10);
        add(boxSenha);
        enter = new JButton("Cadastrar");
        add(enter);
        cad = new JButton("Ver Cadastros");
        add(cad);

        ActionHandler handler = new ActionHandler();

        enter.addActionListener(handler);
        cad.addActionListener(handler);
    }

    private class ActionHandler implements ActionListener {

        public void actionPerformed(ActionEvent event){

            if(event.getSource() == enter){
                
                    String field1 = boxLogin.getText();
                    String field2 = boxEmail.getText();
                    String field3 = new String(boxSenha.getPassword());

                    try{
                        if(file.isFile()){
                            ois = new ObjectInputStream(new FileInputStream(file));
                            al = (ArrayList<Usuario>)ois.readObject();
                            ois.close();
                        }

                        al.add(new Usuario(field1, field2, field3));
                        oos = new ObjectOutputStream(new FileOutputStream(file));
                        oos.writeObject(al);
                        oos.close();

                        JOptionPane.showMessageDialog(null, String.format("Usuário: %s\nEmail: %s\nSenha: %s", field1, field2, field3), "Usuário Cadastrado", JFrame.ICONIFIED);

                }catch(Exception e){
                    System.out.println(e);
                }
            }

            if(event.getSource() == cad){
                try{
                    if(file.isFile()){
                        ois = new ObjectInputStream(new FileInputStream(file));
                        al = (ArrayList<Usuario>)ois.readObject();
                        ois.close();
                    }

                    li = al.listIterator();
                    while(li.hasNext()){
                        System.out.println(li.next());
                    }


                }catch(Exception e){
                    System.out.println(e);
                }
            }
        }
    }
}

class App extends JFrame{
    public static void main(String[] args) {
        
        ElementosGUI e1 = new ElementosGUI();
        e1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        e1.setSize(310, 160);
        e1.setLocationRelativeTo(null);
        e1.setVisible(true);
    }
}