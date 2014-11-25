// JavaScript Document

function criptografaSenha()
{
	document.getElementById('ID_senha').value  =  calcMD5(document.getElementById('ID_senha').value);

	return true;
}

function validaFormulario()
{
	//Primeira questão: Obrigatoriedade
	var placa = document.getElementById("ID_PLACA").value;
	var renavam = document.getElementById("ID_RENAVAM").value;
	
	if(placa=="")
	{
		alert("Preencher o valor de placa!");
		document.getElementById("ID_PLACA").focus();		
		return false;
	}
	
	
	if(renavam=="")
	{
		alert("Preencher o valor de renavam!");
		document.getElementById("ID_RENAVAM").focus();		
		return false;
	}
	
	
	return true;
}

function trataDigitos(event)
{
    var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;
	 if ((keyCode < 48 || keyCode > 57) && (keyCode != 8 && keyCode!=9)){
			  
               event.keyCode ? event.keyCode=0 : event.which ? event.which=0 : event.charCode=0;
               
				
               return false;
        }
        return true;
}


















