/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Carlos
 */
public class Functions {
    
    public String ucFirst(String txt) {
        return txt.substring(0,1).toUpperCase()+ txt.substring(1).toLowerCase();
    }
    
    public String dataAtual() {
        Date data = new Date();
        SimpleDateFormat formatar = new SimpleDateFormat("yyyy/dd/MM");
        return formatar.format(data);
    }

    public String fData2BR(String data) {
        String dia, mes, ano;

        ano = data.substring(0, 4);
        dia = data.substring(5, 7);
        mes = data.substring(8, 10);

        return dia + "/" + mes + "/" + ano;
    }
}
