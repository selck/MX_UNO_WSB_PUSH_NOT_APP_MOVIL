package mx.com.amx.unotv.app.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import mx.com.amx.unotv.app.dto.ParametrosDTO;
import mx.com.amx.unotv.app.dto.WrapperPushDTO;

import org.apache.log4j.Logger;

public class EnviaCorreo {
	
	private static Logger log = Logger.getLogger(EnviaCorreo.class);

		
	public static void main(String [] args){
		String email="chucho@hotmail.com|fer@gmail.com|ruth@gmail.com";
		String [] emails=email.split("\\|");
		for (String correo : emails) {
			System.out.println("Nombre: "+correo);
		}
	}
	public boolean sendMail(WrapperPushDTO wrapperPushDTO, int contadorSuccessAndroid, int contadorErrorAndroid,int contadorSuccessIOS,int contadorErrorIOS) throws Exception {
			
			CargaProperties cargaProperties=new CargaProperties();
			ParametrosDTO parametrosDTO=cargaProperties.obtenerPropiedades("ambiente.resources.properties");
			Properties props = new Properties();
			
			String smtpserver=parametrosDTO.getSmtpserver();
			String smtpsender=parametrosDTO.getSmtpsender();
			String smtpAutenUserName=parametrosDTO.getSmtpAutenUserName();
			String smtpAutenPassword=parametrosDTO.getSmtpAutenPassword();
			String subject=parametrosDTO.getAsuntoCorreo();
			String from = smtpsender;
		    
		    boolean salida=true;
		    String to[] =parametrosDTO.getDestinatarios().split("\\|");
		    String mensajeOrigen=cuerpoCorreo(wrapperPushDTO.getListPush().get(0).getTitulo(), contadorSuccessAndroid, contadorErrorAndroid, contadorSuccessIOS, contadorErrorIOS);
		    to=parametrosDTO.getDestinatarios().split("\\|");
		    
		    for(String quien:to){
		    	log.debug("to        : "+quien);
		    }
		    
			String protocol = "smtp";
		    props.put("mail.smtp.host", smtpserver);
		    props.put("mail.from", from);
		    props.put("mail." + protocol + ".auth", "true");
		    
		    Session session = Session.getInstance(props, null);
		    
		    try {
		    	// creates a new e-mail message
		        MimeMessage msg = new MimeMessage(session);
		        msg.setFrom();
		        //msg.setSender(new InternetAddress("contacto@heroesporlavida.org"));
		        InternetAddress[] addressTo = new InternetAddress[to.length];
	            for (int i = 0; i < to.length; i++)
	            {
	                addressTo[i] = new InternetAddress(to[i]);
	            }
		        msg.setRecipients(Message.RecipientType.TO,addressTo);
		        msg.setSubject(subject,"ISO-8859-1");
		        msg.setSentDate(new Date());
		        msg.setContent(mensajeOrigen, "text/html");
		        
		        // Create the message part 
		         //BodyPart messageBodyPart = new MimeBodyPart();
		         // Fill the message
		         //messageBodyPart.setContent(mensajeOrigen, "text/html");
		         // Create a multipar message
		         //Multipart multipart = new MimeMultipart();
		         // Set text message part
		         //multipart.addBodyPart(messageBodyPart);

		         // Part two is attachment
		         //MimeBodyPart attachPart = new MimeBodyPart();
		         //String attachFile = filePathPDF;
		         //attachPart.attachFile(attachFile);
		         //multipart.addBodyPart(attachPart);

		         // Send the complete message parts
		         //msg.setContent(multipart );
		        Transport t = session.getTransport(protocol);
		        t.connect(smtpserver,smtpAutenUserName, smtpAutenPassword);
		        //t.connect(smtpServer, 25, smtpAutenUserName, smtpAutenPassword);
		        t.sendMessage(msg, msg.getAllRecipients());
		        
		    } catch (MessagingException mex) {
		        //throw new Exception("send failed, exception: " + mex);
		    	log.error("Error sendMail: ",mex);
		        salida=false;
		        return salida;
		    } 
		    return salida;
		}
		
		public String cuerpoCorreo(String tituloNota,int contadorSuccessAndroid, int contadorErrorAndroid,int contadorSuccessIOS,int contadorErrorIOS){
			StringBuffer sb=new StringBuffer();
			sb.append(" <!DOCTYPE html>  ");
			sb.append(" <html>  ");
			sb.append(" <html>  ");
			sb.append("   <head>  ");
			sb.append("     <meta charset=\"utf-8\">  ");
			sb.append("     <title>Envio de Notificaciones Push</title>  ");
			sb.append("     <meta name=\"description\" content=\"\">  ");
			sb.append("     <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">  ");
			sb.append("     <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\">  ");
			sb.append("   </head>  ");
			sb.append("     <body>  ");
			sb.append("       <table width=\"500\" style=\"margin: 0 auto;\">  ");
			sb.append("         <tr>  ");
			sb.append("           <td>  ");
			sb.append("             <a href=\"http://www.unotv.com/noticias/\" target=\"_blank\"><img style=\"display: block; float: right; margin-top: 20px;\" width=\"160\" height=\"60\" src='http://www.unotv.com/utils/img/logo-unotv-dark.png'></img></a>  ");
			sb.append("           </td>  ");
			sb.append("         </tr>  ");
			sb.append("         <tr>  ");
			sb.append("           <td style=\"border-bottom: dashed red 1px;\">  ");
			sb.append("               <p style=\"font-size: 1.3em; font-weight: 500; font-family: Arial, sans-serif, Helvetica; color: #505151; \">Estimad@ Usuari@ </p>");
			sb.append("               <p style=\"font-size: 1.3em; font-weight: 500; font-family: Arial, sans-serif, Helvetica; color: #505151; \"> ");
			sb.append(" 			  Se envio la Notificación de la Nota:  "+tituloNota);
			sb.append(" 			  <ol style=\"list-style-type: circle\"> ");
			sb.append(" 				<li>Número de Notificaciones Android con Exito: "+contadorSuccessAndroid+"</li> ");
			sb.append(" 				<li>Número de Notificaciones Android con Error: "+contadorErrorAndroid+" </li> ");
			sb.append(" 				<li>Número de Notificaciones IOS con Exito: "+contadorSuccessIOS+" </li> ");
			sb.append(" 				<li>Número de Notificaciones IOS con Error: "+contadorErrorIOS+" </li> ");
			sb.append(" 			  </ol> ");
			sb.append(" 			  </p>  ");
			sb.append("               <p style='text-align: center; font-size: 1em; font-weight: 500; font-family: Arial, sans-serif, Helvetica;'>  ");
			sb.append("               <a  style=\"color: white; padding: 10px 15px; background: #2099dc; text-align: center; border-radius: 12px; text-decoration: none\" href='http://www.unotv.com/noticias/'>www.unotv.com</a></p>  ");
			sb.append("               <p style=\"color: #505151; font-family: Arial, sans-serif, Helvetica; text-align: center; margin-bottom:30px;\">Gracias por utlizar los servicios de AMX</p>  ");
			sb.append("           </td>  ");
			sb.append("         </tr>  ");
			sb.append("       </table>  ");
			sb.append("     </body>  ");
			sb.append(" </html>  ");
			return sb.toString();
		}
		
		private static String codificaCaracteres(String cadena){		
			cadena = cadena.replaceAll("á", "&aacute;");
			cadena = cadena.replaceAll("é", "&eacute;");
			cadena = cadena.replaceAll("í", "&iacute;");
			cadena = cadena.replaceAll("ó", "&oacute;");
			cadena = cadena.replaceAll("ú", "&uacute;");	
			cadena = cadena.replaceAll("Á", "&Aacute;");
			cadena = cadena.replaceAll("É", "&Eacute;");
			cadena = cadena.replaceAll("Í", "&Iacute;");
			cadena = cadena.replaceAll("Ó", "&Oacute;");
			cadena = cadena.replaceAll("Ú", "&Uacute;");
			cadena = cadena.replaceAll("Ñ", "&Ntilde;");
			cadena = cadena.replaceAll("ñ", "&ntilde;");		
			cadena = cadena.replaceAll("ª", "&#170;");		
			return cadena;
		}

}
