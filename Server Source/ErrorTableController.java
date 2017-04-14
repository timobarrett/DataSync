package fivetwentysix.ware.com.dataSync;

/**
 * Created by tim on 3/29/2017.
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;

@RepositoryRestController

@RequestMapping(value="/error_table")
public class ErrorTableController  {
    private JavaMailSender javaMailSender = null;
    private SimpleMailMessage mailMessage;
    private static final String PATH = "/error";


    @Autowired
    private ErrorDao _errorDao;

    @Autowired
    ErrorTableController(JavaMailSender javaMailSender) {this.javaMailSender = javaMailSender;}


    @RequestMapping(value="/delete")
    @ResponseBody
    public String delete(int id) {
        try {
            ErrorData error = new ErrorData(id);
            _errorDao.delete(error);
        }
        catch(Exception ex) {
            return ex.getMessage();
        }
        return "Error Succesfully deleted!";
    }


    @RequestMapping(value="/save",
            params = {"date","module","line_number","error_message"})
    @ResponseBody
    public String create(@RequestParam("date")int date,
                         @RequestParam("module")String module,
                         @RequestParam("line_number")Integer line,
                         @RequestParam("error_message")String errorMsg){

        try {
            ErrorData error = new ErrorData(date, module,line,errorMsg);
            _errorDao.save(error);
        }
        catch(Exception ex) {
            return ex.getMessage();
        }
        return "Error succesfully saved!";
    }


    @RequestMapping(value="/getAll")
    @ResponseBody
    public String getAll(HttpServletResponse response){
        List<ErrorData> errorData = null;
        StringBuilder errors = new StringBuilder();
        response.setContentType("text/plain; charset=utf-8");
        try{
          errorData =  _errorDao.getAll();
        }catch(Exception e){
            System.out.println("getAll Exception = "+e.getMessage());
            return e.getMessage();
        }
        for (int i=0;i<errorData.size();i++){
            errors.append("Error Message = ").append(errorData.get(i).getErrorMessage());
            errors.append("  Error Line Number = ").append(errorData.get(i).getLineNumber());
            errors.append("  Error Module = ").append(errorData.get(i).getModule());
        }
        return errors.toString();
    }

    @RequestMapping(value="/send-mail")
    @ResponseBody
    public String sendEmail(){
        List<ErrorData> errorData = null;
        StringBuilder errors = new StringBuilder();
        try{
            errorData =  _errorDao.getAll();
        }catch(Exception e){
            System.out.println("getAll Exception = "+e.getMessage());
        }
        for (int i=0;i<errorData.size();i++){
            errors.append("Error Message = ").append(errorData.get(i).getErrorMessage());
            errors.append("  Error Line Number = ").append(errorData.get(i).getLineNumber());
            errors.append("  Error Module = ").append(errorData.get(i).getModule());
        }
        mailMessage = new SimpleMailMessage();
        setupMailHeader();
        mailMessage.setText(errors.toString());
        javaMailSender.send(mailMessage);
        return "Email send complete";
    }

    private void setupMailHeader(){
        mailMessage.setTo("timobarrett@comcast.net");
        mailMessage.setFrom("timobarrett@gmail.com");
        mailMessage.setSubject("Errors Found");
    }

} // class ErrorController
