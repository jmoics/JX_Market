package pe.com.jx_market.service;

import java.util.Properties;
import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;

public class EnviarCorreo 
{
	private String host = "smtp.mail.yahoo.co.in";
	private String transporte= "smtp";
    private String puerto= "25";//puerto del servidor de yahoo
    private String user = "imdm87@yahoo.com";
    private String pass="isa21mar11";//mi password
    private String from = user ;
    private String to = "";
    private String cc = null;
    private String bcc = null;
    private String subject="";
    private String mess="";
    
    private String directorio = null;
    private String archivo = null;

    public  boolean enviarCorreo(String para, String subject , String mens)
    {
    	boolean  b = false;
    	to = para;
    	this. mess = mens;
    	this.subject= subject;

    	b = enviar();
    	
    	return b;
    }
    
    public  boolean enviarCorreo(String para, String cc,  String subject , String mens)
    {
    	boolean  b = false;
    	to =para;
    	this.cc=cc;
    	this. mess = mens;
    	this.subject= subject;
    	
    	b= enviar();
    	
    	return b;
    }
    
    public  boolean enviarCorreo(String para, String cc, String bcc, String subject , String mens)
    {
    	boolean  b = false;      
    	to =para;
    	this.cc=cc;
    	this.bcc=bcc; 
    	this. mess = mens;
    	this.subject= subject;
    	
    	b= enviar();
    	return b;
    }
    
    
    public  boolean enviarCorreo(String para, String cc, String bcc,
            String subject , String mens, String directorio, String archivo)
    {
        boolean  b = false;
        to =para;
        this.cc=cc;
        this.bcc=bcc;
        this. mess = mens;
        this.subject= subject;
        this.directorio=directorio;
        this.archivo=archivo;
        b = enviar();
        
        return b;
    }
    
    private   boolean  enviar()
    {
    	boolean  b = false;
    	try {
    	  Properties props = System.getProperties();
    	  props.put("mail.smtp.host", host);
    	  props.put("mail.smtp.user", user);
    	  props.put("mail.smtp.password", pass);
    	  props.put("mail.smtp.port", puerto); // 587 is the port number of yahoo mail
    	  props.put("mail.smtp.auth", "true");
    	  Session session = Session.getDefaultInstance(props, null);
    	  MimeMessage message = new MimeMessage(session);
    	  message.setFrom(new InternetAddress(from));
    	  
    	  message.addRecipients(Message.RecipientType.TO, to);
    	  if ( cc!=null)
    		  message.addRecipients(Message.RecipientType.CC, cc);
    	  if ( bcc!=null)
            message.addRecipients(Message.RecipientType.BCC, bcc);
   
          message.setSubject(subject);
          message.setText(mess);
          
          //se debe verificar si se desea emplear ataxado (arxivo adjunto)
          if(directorio!=null &&archivo!=null)
          {
        	  // Create the message part
        	  BodyPart messageBodyPart = new MimeBodyPart();
        	  messageBodyPart.setText("dayhana la q no se lava la maraca a3a3a3");
        	  Multipart multipart = new MimeMultipart();
        	  multipart.addBodyPart(messageBodyPart);
        	  
        	  // Part two is attachment
        	  messageBodyPart = new MimeBodyPart();
        	  String filename = directorio+archivo;
        	  System.out.println(filename);
        	  
        	  DataSource source = new FileDataSource(filename);
        	  messageBodyPart.setDataHandler(new DataHandler(source));
        	  messageBodyPart.setFileName(archivo);//nombre del arxivo cuando llega adjunto al correo
        	  multipart.addBodyPart(messageBodyPart);
        	  
        	  // Put parts in message
        	  message.setContent(multipart);//lo adjunta al correo
        	}
        
          //conectarse al server para enviar al correo
          Transport transport = session.getTransport(transporte);        
          transport.connect(host, user, pass);
          
          //enviando el correo
          transport.sendMessage(message, message.getAllRecipients());
          
          //cerrando el envio o el correo
          transport.close();
          b= true;
          System.out.println(" enviado...");
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Error al enviar correo: " + e);
    }
     return b;
    }

}
