/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spksukmajayabaru;

/**
 *
 * @author PC
 */
public class exceptionData extends Exception {
    public exceptionData() {
        super("Field cannot be empty");
    }
    
    public exceptionData(String message) {
        super(message);
    }
    
    public String showMessageError(){
        String notice = "Silahkan isi data dengan lengkap..";
        return notice;
    } 
}
