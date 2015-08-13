/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.omer.kapalicarsi;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.omer.kapalicarsi.bean.KullaniciFacade;
import org.omer.kapalicarsi.bean.MucevherDetayFacade;
import org.omer.kapalicarsi.bean.MucevherPiyasaFacade;

/**
 *
 * @author pc
 */
@ManagedBean
@ApplicationScoped
public class Servis extends javax.mail.Authenticator implements Serializable {

    private String selectedItem; // +getter+setter
    private static List<MucevherDetay> availableItems; // +getter
    private static List<Kullanici> kullanicilar;
    private static List<MucevherPiyasa> mucevherPiyasalar;
    private MucevherDetay selectedMucevher; 
    private int kullaniciId;
    private String kullaniciEmail;
    private String kullaniciAdi;
    private String sifre;
    private boolean admin;

    
    private double fiyat;

    private String _user;
    private String _pass;

    private UploadedFile file;

    @EJB
    private  KullaniciFacade servisKullanici;
    @EJB
    private  MucevherDetayFacade servisMucevher;
    @EJB
    private  MucevherPiyasaFacade servisMucevherPiyasa;

    @PostConstruct
    public void init() {
        availableItems = servisMucevher.getMucevherDetaylar();
        kullanicilar = servisKullanici.getKullanicilar();
        mucevherPiyasalar = servisMucevherPiyasa.getMucevherPiyasalar();
        
        
         
    }

    public Servis() {
        _user = "obuyuksar@hotmail.com";
        _pass = "Omer123456";
    }

    public void handleFileUpload(FileUploadEvent event) {
        BufferedImage resizeImageJpg = null;
        InputStream is = null;
        this.file = event.getFile();
        if (file != null) {
            try {
                BufferedImage originalImage = ImageIO.read(file.getInputstream());
                int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
                System.out.println(type);
                resizeImageJpg = resizeImage(originalImage, type);//call to resize the image

                // BufferedImage to ByteArrayInputStream
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                ImageIO.write(resizeImageJpg, "jpg", os);
                is = new ByteArrayInputStream(os.toByteArray());

            } catch (IOException e) {
                System.out.println(e.getMessage());
                return;
            }

            // Create connection
            try {
                // Load driver

                System.out.println("size:" + file.getSize() + " selected: " + selectedItem);

                Class.forName("org.apache.derby.jdbc.ClientDriver");

                // Connect to the database
                Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/kapaliCarsi", "myuser", "myuser");
                // Set autocommit to false to manage it by hand
                connection.setAutoCommit(true);

                // Create the statement object
                PreparedStatement statement = connection.prepareStatement("UPDATE MUCEVHER_DETAY SET RESIM=? WHERE ADI=? ");
                // Set file data

                statement.setBlob(1, is);
                statement.setString(2, selectedItem);
                // Insert data to the database
                statement.executeUpdate();
                statement.close();

                // Commit & close
                connection.commit();    // when autocommit=false
                connection.close();

                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Upload success", file.getFileName() + " is uploaded.");
                FacesContext.getCurrentInstance().addMessage(null, msg);

            } catch (Exception e) {
                e.printStackTrace();

                // Add error message
                FacesMessage errorMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Upload error", e.getMessage());
                FacesContext.getCurrentInstance().addMessage(null, errorMsg);
            }
        }
    }

    public String signUp() throws ClassNotFoundException, SQLException {

        try {
            insertNewKullanici(kullaniciAdi, kullaniciEmail, sifre);
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Kullanıcı Kayıdı", " Oluşturulamamıştır");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "kayit";

        }
        try {
            mailAt();
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Onay Maili", " Gönderilememiştir");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return "kayit";

        }

        return "login";
    }

    public void insertNewKullanici(String adi, String email, String şifre) throws ClassNotFoundException, SQLException, ParseException {

        Class.forName("org.apache.derby.jdbc.ClientDriver");
        // Connect to the database
        Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/kapaliCarsi", "myuser", "myuser");
        // Set autocommit to false to manage it by hand
        connection.setAutoCommit(false);

        // Create the statement object
        PreparedStatement statement = connection.prepareStatement("INSERT INTO KULLANICI (ADI,EMAIL,SIFRE,KAYIT_TARIH,IS_ADMIN) VALUES (?,?,?,?,false)");
        // Set file data
        statement.setString(1, adi);
        statement.setString(2, email);
        statement.setString(3, sifre);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date myDate = dateFormat.parse(dateFormat.format(Calendar.getInstance().getTime()));
        // Notice the ".util." of package name.

        java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
        statement.setDate(4, sqlDate);
        // Insert data to the database
        statement.executeUpdate();

        // Commit & close
        connection.commit();    // when autocommit=false
        connection.close();

        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Kullanıcı Kayıdı", " Başarıyla Yapılmıştır");
        FacesContext.getCurrentInstance().addMessage(null, msg);

    }

    public static Connection getDbConnection() throws SQLException, ClassNotFoundException {

        // Connect to the database
        return DriverManager.getConnection("jdbc:derby://localhost:1527/kapaliCarsi", "myuser", "myuser");

    }/*
     public StreamedContent getImage() throws IOException {
     FacesContext context = FacesContext.getCurrentInstance();

     if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
     // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
     return new DefaultStreamedContent();
     }
     else {
     // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
     String muc_id = context.getExternalContext().getRequestParameterMap().get("MUC_ID");
     System.err.println(""+muc_id+"--xx--"+Integer.getInteger(muc_id));
     MucevherDetay mucevher = service.findById(Integer.getInteger(muc_id));
     return new DefaultStreamedContent(new ByteArrayInputStream((byte[]) mucevher.getResim()));
     }
     }*/


    private static BufferedImage resizeImage(BufferedImage originalImage, int type) {
        BufferedImage resizedImage = new BufferedImage(80, 80, type);//set width and height of image
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, 80, 80, null);
        g.dispose();

        return resizedImage;
    }

    public String login() {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage message = null;
        boolean loggedIn = false;
        admin = false;
        Kullanici kullanici = null;
        if (kullaniciEmail != null && sifre != null) {
            System.err.println("email:" + kullaniciEmail + "sifre:" + sifre);
        }
        if (kullaniciEmail != null && kullaniciEmail.equals("admin") && sifre != null && sifre.equals("admin")) {
            loggedIn = true;
            admin = true;
            kullaniciId=-1;
            kullaniciAdi="admin";
            kullaniciEmail = "admin";
            sifre = "admin";
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Hoşgeldiniz", kullaniciEmail);
            FacesContext.getCurrentInstance().addMessage(null, message);
            context.addCallbackParam("loggedIn", loggedIn);
            return "admin";
        } else {
            try {
                kullanici = servisKullanici.getKullanici(kullaniciEmail, sifre);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (kullanici == null) {
                loggedIn = false;
                message = new FacesMessage(FacesMessage.SEVERITY_WARN, "HATALI email veya şifre", "HATALI email veya şifre");
                FacesContext.getCurrentInstance().addMessage(null, message);
                context.addCallbackParam("loggedIn", loggedIn);
                return "login";
            } else {
                loggedIn = true;
                admin = true;
                kullaniciId = kullanici.getKulId();
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Hoşgeldiniz", kullaniciEmail);
                FacesContext.getCurrentInstance().addMessage(null, message);
                context.addCallbackParam("loggedIn", loggedIn);
                return "kullanici";
            }
        }

    }
    public void yeniFiyat(){
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            // Connect to the database
            Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/kapaliCarsi", "myuser", "myuser");
            // Set autocommit to false to manage it by hand
            connection.setAutoCommit(false);

            // Create the statement object
            PreparedStatement statement = connection.prepareStatement("INSERT INTO MUCEVHER_PIYASA (FIYAT,KAYDEDEN_KULLANICI_ID,KAYDEDILEN_MUCEVHER_ID) VALUES (?,?,?)");
            // Set file data
            statement.setDouble(1, fiyat);
            statement.setString(2, ""+kullaniciId);
            statement.setString(3, ""+selectedMucevher.getMucId());
            
            
            statement.executeUpdate();

            // Commit & close
            connection.commit();    // when autocommit=false
            connection.close();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mücevher Fiyatı", " Başarıyla Oluşturulmuştur");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            init();
        } catch (Exception e) {
            e.printStackTrace();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mücevher Fiyatı", " Oluşturulamamıştır");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            init();

        }
        
    }

    public void yeniMucevher() {

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            // Connect to the database
            Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/kapaliCarsi", "myuser", "myuser");
            // Set autocommit to false to manage it by hand
            connection.setAutoCommit(false);

            // Create the statement object
            PreparedStatement statement = connection.prepareStatement("INSERT INTO MUCEVHER_DETAY (ADI,KAYITTARIH) VALUES (?,?)");
            // Set file data
            statement.setString(1, selectedItem);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date myDate = dateFormat.parse(dateFormat.format(Calendar.getInstance().getTime()));
            // Notice the ".util." of package name.

            java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
            statement.setDate(2, sqlDate);
            // Insert data to the database
            statement.executeUpdate();

            // Commit & close
            connection.commit();    // when autocommit=false
            connection.close();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mücevher Oluşturma", " Başarıyla Yapılmıştır");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            init();
        } catch (Exception e) {
            e.printStackTrace();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mücevher Oluşturma", " Yapılamamıştır");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            init();

        }
    }

    public void mailAt() {
        final String username = "obuyuksar@hotmail.com";
        final String password = "Omer123456";
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.live.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, this);
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("obuyuksar@hotmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(kullaniciEmail));
            message.setSubject("Kapali Carsi Hesabınız Hakkında");
            message.setText("Sayın " + kullaniciAdi + " ,\n" + kullaniciEmail + " mail hesabınız onaylanmıştır.");
            Transport.send(message);

        } catch (MessagingException ex) {
            throw new RuntimeException(ex);
        }
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Onay Maili", " Başarıyla Gönderilmiştir");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    @Override
    public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(_user, _pass);
    }

    public void handleChange() {
        System.out.println("New value: " + selectedItem);
    }

    public String getKullaniciEmail() {
        return kullaniciEmail;
    }

    public void setKullaniciEmail(String kullaniciEmail) {
        this.kullaniciEmail = kullaniciEmail;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    // Getter method
    public UploadedFile getFile() {
        return file;
    }

    // Setter method
    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String getSelectedItem() {
        return selectedItem;
    }

    public String getKullaniciAdi() {
        return kullaniciAdi;
    }

    public void setKullaniciAdi(String kullaniciAdi) {
        this.kullaniciAdi = kullaniciAdi;
    }

    public void setSelectedItem(String selectedItem) {
        this.selectedItem = selectedItem;
    }

    public List<MucevherDetay> getAvailableItems() {
        return availableItems;
    }
    public int getKullaniciId() {
        return kullaniciId;
    }

    public void setKullaniciId(int kullaniciId) {
        this.kullaniciId = kullaniciId;
    }

    public double getFiyat() {
        return fiyat;
    }

    public void setFiyat(double fiyat) {
        this.fiyat = fiyat;
    }

    public MucevherDetay getSelectedMucevher() {
        return selectedMucevher;
    }
    public static List<MucevherDetay> getAvaibleItems(){
        return availableItems;
    }
    public void setSelectedMucevher(MucevherDetay selectedMucevher) {
        this.selectedMucevher = selectedMucevher;
    }

    public MucevherDetayFacade getServisMucevher() {
        return servisMucevher;
    }

    public void setServisMucevher(MucevherDetayFacade servisMucevher) {
        this.servisMucevher = servisMucevher;
    }

    public KullaniciFacade getServisKullanici() {
        return servisKullanici;
    }

    public void setServisKullanici(KullaniciFacade servisKullanici) {
        this.servisKullanici = servisKullanici;
    }

    public MucevherPiyasaFacade getServisMucevherPiyasa() {
        return servisMucevherPiyasa;
    }

    public void setServisMucevherPiyasa(MucevherPiyasaFacade servisMucevherPiyasa) {
        this.servisMucevherPiyasa = servisMucevherPiyasa;
    }

    public static List<Kullanici> getKullanicilar() {
        return kullanicilar;
    }

    public static List<MucevherPiyasa> getMucevherPiyasalar() {
        return mucevherPiyasalar;
    }

    
    

}
