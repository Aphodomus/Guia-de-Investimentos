function ValidarSenhaForca(){
    var senha = document.getElementById('algo').value;
    var forca = 0;
    //alert(key)

    //document.getElementById("impSenha").innerHTML = "Senha " + senha;

    if((senha.length >= 4) && (senha.length <= 7)){
        forca += 10;
    }else if(senha.length > 7){
        forca += 25;
    }

    if((senha.length >= 5) && (senha.match(/[a-z]+/))){
        forca += 10;
    }

    if((senha.length >= 6) && (senha.match(/[A-Z]+/))){
        forca += 20;
    }

    if((senha.length >= 6) && (senha.match(/[@#$%&;*]/))){
        forca += 25;
    }


    mostrarForca(forca);
}

function mostrarForca(forca){
    //document.getElementById("impForcaSenha").innerHTML = "Forca " + forca;

    if(forca < 30){
        document.getElementById("erroForca").innerHTML = "<span style='color: #CD0000'>Senha Fraca</span>";
    }else if((forca >= 30) && (forca < 50)){
        document.getElementById("erroForca").innerHTML = "<span style='color: #CDBE70'>Senha Media</span>";
    }else if((forca >= 50) && (forca < 70)){
        document.getElementById("erroForca").innerHTML = "<span style='color: #228B22'>Senha Forte</span>";
    }else if((forca >= 70) && (forca < 100)){
        document.getElementById("erroForca").innerHTML = "<span style='color: #006400'>Senha Excelente</span>";
    }

}