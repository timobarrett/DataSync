package fivetwentysix.ware.com.dataSync;

/**
 * Created by tim on 1/29/2017.
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/error_table")
public class ErrorController {

    @Autowired
    private ErrorDao _errorDao;

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
                         @RequestParam("error_message")String errorMsg,
                         @RequestParam("line_number")Integer line,
                         @RequestParam("module")String module ) {

        try {
            ErrorData error = new ErrorData(date, module,line,errorMsg);
            _errorDao.save(error);
        }
        catch(Exception ex) {
            return ex.getMessage();
        }
        return "Error succesfully saved!";
    }

} // class ErrorController
