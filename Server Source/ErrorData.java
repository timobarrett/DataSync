package fivetwentysix.ware.com.dataSync;

import javax.validation.constraints.NotNull;
import com.sun.javafx.beans.IDProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.persistence.*;

/**
 * Created by tim on 1/29/2017.
 */

@Entity
@Table(name = "error_table")
public class ErrorData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    private long date;

    @NotNull
    private String module;

    @NotNull
    private Integer line_number;

    @NotNull
    private String error_message;

    public ErrorData() {}

    public ErrorData(int id){
        this.id = id;
    }

    public ErrorData(int date, String module, Integer line, String errorMsg){
        this.date = date;
        this.module = module;
        this.line_number = line;
        this.error_message = errorMsg;
    }

    public int getId(){
        return id;
    }
    public void setId(int value){
        this.id = value;
    }

    public long getDate(){
        return date;
    }

    public void setDate(long value){
        this.date = value;
    }

    public String getModule(){
        return module;
    }

    public void setModule(String value){
        this.module = value;
    }

    public int getLineNumber(){
        return line_number;
    }

    public void setLineNumber(Integer value){
        this.line_number = value;
    }

    public String getErrorMessage(){
        return error_message;
    }

    public void setErrorMessage(String value){
        this.error_message = value;
    }

} //class ErrorData
